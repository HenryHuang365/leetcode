"use client";

import React, {
  useRef,
  useEffect,
  useState,
  MouseEvent,
  Fragment,
} from "react";
import * as d3 from "d3";
import { ToggleGroup, ToggleGroupItem } from "../ui/toggle-group";
import { renderToStaticMarkup } from "react-dom/server";
import { D3BrushEvent } from "d3-brush";
import { GraphTooltip } from "./graph-tooltip";
import { AlertType } from "@/lib/alerts/alert";
import { useAppSelector } from "@/store/hooks";
import { selectIsExporting } from "@/lib/export/exportSlice";
import { VisualisationType } from "@/lib/devices/device-data";
type DataPoint = {
  x: number;
  y: number | null;
};

export interface DataSet {
  id: string;
  points: DataPoint[];
  name: string;
  units: string;
  parameter: string;
}

export interface TimeSeriesDataSet extends DataSet {
  color?: string;
  plotType: VisualisationType;
}

export type Threshold = {
  parameter: string;
  value: number;
  alertType: AlertType;
};

type AxisType = "time" | "displacement";

type MultiLineGraphProps = {
  unitDatasets: Map<string, TimeSeriesDataSet[]>;
  thresholds: Threshold[];
  height: number;
  padding: number;
  horizontalAxisType: AxisType;
  autoscaleY: boolean;
  start?: Date;
  end?: Date;
};

const graphColors = [
  "#1f77b4",
  "#ff7f0e",
  "#2ca02c",
  "#d62728",
  "#9467bd",
  "#8c564b",
  "#e377c2",
  "#7f7f7f",
  "#bcbd22",
  "#17becf",
  "#aec7e8",
  "#ffbb78",
  "#98df8a",
  "#ff9896",
  "#c5b0d5",
  "#c49c94",
  "#f7b6d2",
  "#c7c7c7",
  "#dbdb8d",
  "#9edae5",
];
const cursorColor = "#A1A1AA";

const MultiLineGraph: React.FC<MultiLineGraphProps> = ({
  unitDatasets,
  thresholds,
  height,
  padding,
  horizontalAxisType,
  start,
  end,
  autoscaleY,
}) => {
  const domRef = useRef<HTMLDivElement>(null);
  const svgRef = useRef<SVGSVGElement>(null);
  const [width, setWidth] = useState(500);
  const xScaleDomain = useRef<[number | Date, number | Date] | null>();

  const marginWidth = 40;
  const baseMargin = 10;
  const marginTop = 25;
  const marginBottom = horizontalAxisType === "time" ? 75 : 30;
  const scatterPadding = 0.05;

  useEffect(() => {
    // Clear before redrawing
    d3.select(svgRef.current).selectAll("*").remove();
    d3.select(domRef.current).selectAll("*").remove();

    const svg = d3
      .select(svgRef.current)
      .attr("height", height)
      .attr("viewBox", `0 0 ${width} ${height}`)
      .on("pointerenter pointermove", pointermoved)
      .on("pointerleave", pointerleft);

    // Handle empty graph
    if (unitDatasets.size === 0) {
      svg
        .append("text")
        .attr("x", width / 2)
        .attr("y", height / 2)
        .attr("text-anchor", "middle")
        .text("No data to display");
      return;
    }

    const marginLeft =
      baseMargin + marginWidth * Math.ceil(unitDatasets.size / 2);
    const marginRight =
      baseMargin + marginWidth * Math.ceil((unitDatasets.size - 1) / 2);

    // Get an iterable of all x values to determine x axis range
    const allDatasets = Array.from(unitDatasets.values()).flat();
    const allPoints = allDatasets.flatMap((d) => d.points);
    const hasScatter = allDatasets.some((d) => d.plotType === "scatterplot");
    const numDays =
      ((end?.getTime() ?? 0) - (start?.getTime() ?? 0)) / 86400000 + 1;

    // Taken from https://github.com/d3/d3-scale/issues/150#issuecomment-561304239
    function padLinear(
      [x0, x1]: [number, number],
      padding: number,
      endPadding?: number
    ): [number, number] {
      const d1 = ((x1 - x0) * padding) / 2;
      const d2 = ((x1 - x0) * (endPadding ?? padding)) / 2;
      return [x0 - d1, x1 + d2];
    }

    const numBarCharts = allDatasets.filter(
      (d) => d.plotType === "barchart"
    ).length;

    let xScale:
      | d3.ScaleLinear<number, number, never>
      | d3.ScaleTime<number, number, never>;
    const linearPadding = padLinear(
      start !== undefined && end !== undefined && horizontalAxisType === "time"
        ? [start!.getTime(), end!.getTime()]
        : (d3.extent(
            allDatasets.flatMap((d) => d.points),
            (d) => d.x
          ) as [number, number]),
      Math.max(
        padding,
        numBarCharts > 0 ? (numDays < 10 ? 0.2 : 0.05) : padding,
        hasScatter ? scatterPadding : padding
      )
    );
    if (horizontalAxisType === "time") {
      xScale = d3
        .scaleTime()
        .domain(xScaleDomain?.current ?? linearPadding)
        .range([marginLeft, width - marginRight]);
    } else {
      // Displacement
      xScale = d3
        .scaleLinear()
        .domain(xScaleDomain?.current ?? linearPadding)
        .range([marginLeft, width - marginRight]);
    }

    const tooltipAnchorLine = svg.append("line");
    const rulerLine = svg.append("line");
    function rotateTicks(
      axis: d3.Selection<SVGGElement, unknown, null, undefined>
    ) {
      if (horizontalAxisType === "time") {
        axis
          .selectAll("text")
          .style("text-anchor", "end")
          .attr("dx", "-.8em")
          .attr("dy", ".15em")
          .attr("transform", "rotate(-65)");
      }
    }

    // Generate unique mask ID to avoid collision
    const maskId = `mask-${Math.random().toString(36).substr(2, 9)}`;

    // Reference: https://stackoverflow.com/a/38059546/10993299
    svg
      .append("defs")
      .append("clipPath")
      .attr("id", maskId)
      .style("pointer-events", "none")
      .append("rect")
      .attr("x", marginLeft)
      .attr("y", marginTop)
      .attr("width", width - marginLeft - marginRight)
      .attr("height", height + marginBottom);

    const masked = svg.append("g").attr("clip-path", `url(#${maskId})`);

    const xAxis = svg
      .append("g")
      .attr("class", "x axis")
      .attr("transform", `translate(0,${height - marginBottom})`)
      .call(d3.axisBottom(xScale));
    rotateTicks(xAxis);

    // Add brushing
    const brush = d3
      .brushX()
      .extent([
        [0, 0],
        [width, height],
      ])
      .on("end", updateChart);

    masked.append("g").attr("class", "brush").call(brush);
    // Double click to reset scaling
    svg.on("dblclick", updateChart);

    // A function that set idleTimeOut to null
    let idleTimeout: NodeJS.Timeout | null;
    function idled() {
      idleTimeout = null;
    }

    function updateChart(event: D3BrushEvent<DataPoint> | MouseEvent) {
      // Handles the double click event to reset scaling
      if (event.type === "dblclick") {
        xScaleDomain.current = null;
        xScale.domain(linearPadding);
      } else {
        const extent = (event as D3BrushEvent<DataPoint>).selection;
        if (!extent) {
          // Sets scaling cooldown
          if (!idleTimeout) return (idleTimeout = setTimeout(idled, 350)); // This allows to wait a little bit
        } else {
          // Handles selection end event
          xScaleDomain.current = [
            xScale.invert(extent[0] as number),
            xScale.invert(extent[1] as number),
          ];
          xScale.domain(xScaleDomain.current);
          brush.clear(d3.select(".brush"));
        }
      }

      // Update axis and line position
      xAxis.transition().duration(1000).call(d3.axisBottom(xScale));
      rotateTicks(xAxis);

      unitDatasets.forEach((datasets) => {
        const thresholdValues = thresholds
          .filter((thresh) => datasets.some((d) => d.id === thresh.parameter))
          .map((thresh) => thresh.value);
        const yScale = d3
          .scaleLinear()
          .domain(
            padLinear(
              d3.extent(
                datasets
                  .flatMap((d) => d.points)
                  .concat([
                    ...(datasets.length > 1
                      ? [{ x: 0, y: calBuffer(datasets) }]
                      : autoscaleY
                      ? []
                      : [{ x: 0, y: 0 }]),
                    ...thresholdValues.map((v) => ({ x: 0, y: v })),
                  ]),
                (d) => d.y
              ) as [number, number],
              padding,
              padding + scatterPadding
            )
          )
          .range([height - marginBottom, marginTop]);

        datasets.forEach((dataset) => {
          if (dataset.plotType === "linegraph") {
            const line = d3
              .line<DataPoint>()
              .x((d) => xScale(d.x))
              .y((d) => yScale(d.y ?? 0));
            // Supress the eslint warning for using explicit any,
            // otherwise, d3 cannot infer the types and the compiler will throw errors
            /* eslint-disable  @typescript-eslint/no-explicit-any */
            (
              svg
                .select(`.line-${dataset.id}`)
                .transition()
                .duration(1000) as any
            ).attr("d", line);
          } else if (dataset.plotType === "barchart") {
            const xDomain =
              horizontalAxisType === "time"
                ? xScale.domain().map((d) => (d as Date).getTime())
                : (xScale.domain() as [number, number]);
            /// There is an edge case when the chart is zoomed in, adding/removing the data sets and then zooms out again,
            /// it will lead to missing lines as the filter only runs when data sets change but not the zooming level changes.
            /// To get it work with the preserved zooming level, the best way is to render the entire array when data sets are added/removed.
            /// Since the default view is without zooming, this shouldn't have performance difference compared to the previous implementation.
            /// Keeping the previous filter condition but adding a always true condition to nullify it and avoiding changing too much code.
            const datapoints = dataset.points.filter(
              (p) => p.x >= xDomain[0] && p.x <= xDomain[1]
            );
            const totalBarWidth =
              ((width - marginLeft - marginRight) / (datapoints.length + 2)) *
              0.4;
            const currentBarWidth = totalBarWidth / numBarCharts;
            (
              svg
                .selectAll(`.bar-${dataset.id}`)
                .transition()
                .duration(1000) as any
            )
              .attr(
                "x",
                (d: { x: d3.NumberValue }) =>
                  (xScale(d.x) ?? marginLeft) - totalBarWidth / 2
              )
              .attr("y", (d: { y: any }) => yScale(d.y ?? 0))
              .attr("width", currentBarWidth);
          } else if (dataset.plotType === "scatterplot") {
            (
              svg
                .selectAll(`.scatter-${dataset.id}`)
                .transition()
                .duration(1000) as any
            )
              .attr(
                "cx",
                (d: { x: d3.NumberValue }) => xScale(d.x) ?? marginLeft
              )
              .attr("cy", (d: { y: any }) => yScale(d.y ?? 0));
          }
        });
      });
    }

    function calBuffer(datasets: TimeSeriesDataSet[]) {
      // Getting all the y values from datasets
      const yValues = datasets
        .flatMap((d) => d.points.map((p) => p.y))
        .filter((y): y is number => y !== null);

      const yMin = Math.min(...yValues);
      const yMax = Math.max(...yValues);
      const yRangeDiff = yMax - yMin;
      // create a buffer that is 5% of the range difference lower than the bottom of y-axis
      const buffer = yMin - yRangeDiff * 0.05;
      return buffer;
    }

    let unitNum = 0;
    unitDatasets.forEach((datasets, unit) => {
      // Get the y scale for the current unit
      const thresholdValues = thresholds
        .filter((thresh) => datasets.some((d) => d.id === thresh.parameter))
        .map((thresh) => thresh.value);
      const yScale = d3
        .scaleLinear()
        .domain(
          padLinear(
            d3.extent(
              datasets
                .flatMap((d) => d.points)
                .concat([
                  ...(datasets.length > 1
                    ? [{ x: 0, y: calBuffer(datasets) }]
                    : autoscaleY
                    ? []
                    : [{ x: 0, y: 0 }]),
                  ...thresholdValues.map((v) => ({ x: 0, y: v })),
                ]),
              (d) => d.y
            ) as [number, number],
            padding,
            padding + scatterPadding
          )
        )
        .range([height - marginBottom, marginTop]);

      const isLeftAxis = unitNum % 2 === 0;
      const axisNumOnSide = Math.ceil((unitNum + 1) / 2);
      const axisLoc = isLeftAxis
        ? baseMargin + marginWidth * axisNumOnSide
        : width - baseMargin - marginWidth * axisNumOnSide;

      svg
        .append("g")
        .attr("transform", `translate(${axisLoc},0)`)
        .call(isLeftAxis ? d3.axisLeft(yScale) : d3.axisRight(yScale));

      // Unit label
      svg
        .append("text")
        .attr("x", axisLoc + (isLeftAxis ? -marginWidth / 2 : marginWidth / 2))
        .attr("y", 15)
        .attr("text-anchor", "middle")
        .text(unit);

      // Draw the lines for the current unit
      datasets.forEach((dataset) => {
        const xDomain =
          horizontalAxisType === "time"
            ? xScale.domain().map((d) => (d as Date).getTime())
            : (xScale.domain() as [number, number]);
        /// For calculating the bar width only
        const datapoints = dataset.points.filter(
          (p) => p.x >= xDomain[0] && p.x <= xDomain[1]
        );

        if (dataset.plotType === "barchart") {
          const totalBarWidth =
            ((width - marginLeft - marginRight) / (datapoints.length + 2)) *
            0.4;
          const currentBarWidth = totalBarWidth / numBarCharts;
          masked
            .selectAll(`rect-${unitNum}`)
            .data(dataset.points)
            .enter()
            .append("rect")
            .attr("x", (d) => xScale(d.x) - totalBarWidth / 2)
            .attr("y", (d) => yScale(d.y ?? 0))
            .attr("width", currentBarWidth)
            .attr("height", (d) => height - marginBottom - yScale(d.y ?? 0))
            .attr("fill", dataset.color ?? d3.schemeCategory10[unitNum])
            .attr("fill-opacity", 0.5)
            .attr("z-index", 1)
            .attr("position", "relative")
            .attr("class", `bar-${dataset.id}`);
        }

        if (dataset.plotType === "linegraph") {
          // Split line into segments for missing data
          const lineData = [];
          let currentSegment = [];
          for (const point of dataset.points) {
            if (point.y === null) {
              if (currentSegment.length > 0) {
                lineData.push(currentSegment);
                currentSegment = [];
              }
            } else {
              currentSegment.push(point);
            }
          }
          if (currentSegment.length > 0) {
            lineData.push(currentSegment);
          }

          const line = d3
            .line<DataPoint>()
            .x((d) => xScale(d.x))
            .y((d) => yScale(d.y ?? 0));

          lineData.forEach((segment) => {
            masked
              .append("path")
              .datum(segment)
              .attr("class", `line-${dataset.id}`)
              .attr("fill", "none")
              .attr("stroke", dataset.color ?? d3.schemeCategory10[unitNum])
              .attr("stroke-width", 1.5)
              .attr("d", line)
              .attr("z-index", 3)
              .attr("position", "relative");
          });
        }

        if (dataset.plotType === "scatterplot") {
          masked
            .selectAll(`circle-${unitNum}`)
            .data(dataset.points)
            .enter()
            .append("circle")
            .attr("cx", (d) => xScale(d.x) ?? marginLeft)
            .attr("cy", (d) => yScale(d.y ?? 0))
            .attr("r", 3)
            .attr("fill", dataset.color ?? d3.schemeCategory10[unitNum])
            .attr("z-index", 1)
            .attr("position", "relative")
            .attr("class", `scatter-${dataset.id}`);
        }

        // Draw any thresholds associated with the parameter
        thresholds
          .filter((thresh) => thresh.parameter === dataset.id)
          .forEach((thresh) => {
            masked
              .append("line")
              .attr("x1", marginLeft)
              .attr("y1", yScale(thresh.value))
              .attr("x2", width - marginRight)
              .attr("y2", yScale(thresh.value))
              .attr("stroke", thresh.alertType.statusColour)
              .attr("stroke-width", 1)
              .attr("stroke-dasharray", "5,5")
              .attr("z-index", 0);
          });
      });

      unitNum++;
    });

    // Create the tooltip container.
    const tooltip = d3.select(domRef.current).append("div");

    // Add the event listeners that show or hide the tooltip.
    const bisect = d3.bisector((d) => d).center;
    function pointermoved(event: PointerEvent) {
      if (unitDatasets.size === 0) {
        // No data points, nothing to do
        return;
      }

      const xLoc = xScale.invert(d3.pointer(event)[0]);
      const yLoc = d3.pointer(event)[1];
      const xDomain =
        horizontalAxisType === "time"
          ? xScale.domain().map((d) => (d as Date).getTime())
          : (xScale.domain() as [number, number]);
      const sortedXs = allPoints
        .filter((p) => p.x >= xDomain[0] && p.x <= xDomain[1])
        .map((d) => d.x)
        .sort((a, b) => a - b);
      const idx = bisect(sortedXs, xLoc);

      tooltipAnchorLine
        .attr("x1", xScale(sortedXs[idx]))
        .attr("y1", 0)
        .attr("x2", xScale(sortedXs[idx]))
        .attr("y2", height - marginTop)
        .style("stroke-width", 1)
        .style("stroke", cursorColor)
        .style("fill", "none")
        .style("display", null);

      rulerLine
        .attr("x1", baseMargin + marginWidth)
        .attr("y1", yLoc)
        .attr(
          "x2",
          width - baseMargin - (unitDatasets.size > 1 ? marginWidth : 0)
        )
        .attr("y2", yLoc)
        .style("stroke-width", 1)
        .style("stroke", cursorColor)
        .style("fill", "none")
        .style("display", null);

      tooltip
        .html(
          renderToStaticMarkup(
            <GraphTooltip datasets={allDatasets} x={sortedXs[idx]} />
          )
        )
        .style("display", null)
        .style("position", "absolute")
        .style(
          "right",
          `${document.documentElement.clientWidth - event.layerX + 5}px`
        )
        .style(
          "bottom",
          `${document.documentElement.clientHeight - event.layerY + 5}px`
        )
        .style("z-index", 9999);
    }

    function pointerleft() {
      if (unitDatasets.size === 0) {
        // No data points, nothing to do
        return;
      }

      tooltip.style("display", "none");
      tooltipAnchorLine.style("display", "none");
      rulerLine.style("display", "none");
    }
  }, [
    unitDatasets,
    width,
    height,
    marginBottom,
    marginTop,
    padding,
    horizontalAxisType,
    start,
    end,
    thresholds,
    autoscaleY,
  ]);

  useEffect(() => {
    if (svgRef.current !== null) {
      const observer = new ResizeObserver(() => {
        setWidth(svgRef.current?.clientWidth ?? 500);
      });
      observer.observe(svgRef.current);
      return () => observer.disconnect();
    }
  }, [svgRef]);

  return (
    <div className="grid">
      <svg
        ref={svgRef}
        className="w-full rounded-md border border-border col-start-1 row-start-1 pt-2"
      />
      <div
        ref={domRef}
        className="col-start-1 row-start-1 pointer-events-none"
      />
    </div>
  );
};

type TimeSeriesGraphProps = {
  datasets: TimeSeriesDataSet[];
  thresholds: Threshold[];
  height: number;
  padding?: number;
  axisType: AxisType;
  start?: Date;
  end?: Date;
};

export const TimeSeriesGraph: React.FC<TimeSeriesGraphProps> = ({
  datasets,
  thresholds,
  height,
  padding = 0.0,
  axisType,
  start,
  end,
}) => {
  // Start here: It is a quick hardcoded fix to change the default parameter of the visualisation page.
  const [shownDatasets, setShownDatasets] = useState<string[]>([]);

  useEffect(() => {
    if (
      datasets.some((dataset) => dataset.id === "waterLevelRl") &&
      datasets.some((dataset) => dataset.id === "waterLevel")
    ) {
      setShownDatasets(["waterLevelRl"]);
    } else if (datasets.some((dataset) => dataset.id === "waterLevel")) {
      setShownDatasets(["waterLevel"]);
    } else if (datasets.some((dataset) => dataset.id === "flowRate")) {
      setShownDatasets(["flowRate"]);
    } else if (
      datasets.some((dataset) => dataset.id === "electricalConductivity")
    ) {
      setShownDatasets(["electricalConductivity"]);
    } else if (
      datasets.some((dataset) => dataset.id === "deltaEast") &&
      datasets.some((dataset) => dataset.id === "deltaNorth") &&
      datasets.some((dataset) => dataset.id === "deltaUp")
    ) {
      setShownDatasets(["deltaEast", "deltaNorth", "deltaUp"]);
    } else {
      setShownDatasets(datasets.map((d) => d.id));
    }
  }, [datasets]);

  // End here.

  const isExporting = useAppSelector(selectIsExporting);

  const updateShownDatasets = (value: string[]) => {
    const baseShownParams = value.filter((data) => !data.includes("##"));
    const reducedSets = value.filter(
      (param: string) =>
        !param.includes("##") || baseShownParams.includes(param.split("##")[0])
    );
    setShownDatasets(reducedSets);
  };

  datasets.forEach((dataset, i) => {
    if (dataset.color === undefined) {
      dataset.color = graphColors[i % graphColors.length];
    }
  });

  // Aggregate datasets by the units used
  const unitDatasetMapping: Map<string, TimeSeriesDataSet[]> = new Map();
  datasets
    .filter((dataset) => shownDatasets.includes(dataset.id))
    .forEach((dataset) => {
      const existingList = unitDatasetMapping.get(dataset.units);
      if (existingList === undefined) {
        unitDatasetMapping.set(dataset.units, [dataset]);
      } else {
        existingList.push(dataset);
        unitDatasetMapping.set(dataset.units, existingList);
      }
    });

  const shownThresholds = thresholds.filter((thresh) =>
    shownDatasets.includes(`${thresh.parameter}##Threshold`)
  );

  return (
    <div className="flex flex-col gap-1 w-full min-w-[500px]">
      <ToggleGroup
        type="multiple"
        className="justify-start flex-wrap"
        value={shownDatasets}
        onValueChange={(value) => updateShownDatasets(value)}
      >
        {datasets.map((dataset) => (
          <Fragment key={`${dataset.id}-fragment`}>
            {(!isExporting || shownDatasets.includes(dataset.id)) && (
              <>
                <ToggleGroupItem
                  key={`${dataset.id}-toggle`}
                  value={dataset.id}
                  variant="outline"
                  className="h-8"
                >
                  <div className="flex flex-row gap-2">
                    <div
                      className="h-0.5 w-[14px] my-auto"
                      style={{ backgroundColor: dataset.color }}
                    />
                    <span>{`${dataset.name} (${dataset.units})`}</span>
                  </div>
                </ToggleGroupItem>
                {thresholds.some((t) => t.parameter === dataset.parameter) && (
                  <ToggleGroupItem
                    key={`${dataset.id}##Threshold-toggle`}
                    value={`${dataset.id}##Threshold`}
                    variant="outline"
                    className="h-8"
                    disabled={!shownDatasets.includes(dataset.id)}
                  >
                    <div className="flex flex-row gap-2">
                      <div
                        className="h-0.5 w-[14px] my-auto"
                        style={{
                          borderWidth: 1,
                          borderStyle: "dashed",
                        }}
                      />
                      <span>{`${dataset.name}`}</span>
                    </div>
                  </ToggleGroupItem>
                )}
              </>
            )}
          </Fragment>
        ))}
      </ToggleGroup>
      <MultiLineGraph
        unitDatasets={unitDatasetMapping}
        thresholds={shownThresholds}
        height={height}
        padding={padding}
        horizontalAxisType={axisType}
        autoscaleY={true}
        start={start}
        end={end}
      />
    </div>
  );
};
