"use client";

import {
  useGetAlertsQuery,
  useGetDeploymentsQuery,
  useGetDevicesQuery,
  useGetManyMeasurementsQuery,
} from "@/lib/api/apiSlice";
import { TranslationKeys } from "@/gen/translations.gen";
import { selectPeriod } from "@/lib/timePeriodSlice";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import { sub } from "date-fns";
import { TimePeriodValues } from "../time-select/timePeriodType";
import { Deployment } from "@/lib/deployments/deployment";
import {
  DeviceData,
  ObservationParameter,
  VisualisationType,
  visualisationTypes,
} from "@/lib/devices/device-data";
import { Loaders } from "../ui/loaders";
import { TimeSeriesGraph } from "./time-series";
import { FetchBaseQueryError } from "@reduxjs/toolkit/query";
import { SerializedError } from "@reduxjs/toolkit";
import { skipToken } from "@reduxjs/toolkit/query";
import { useEffect, useState, useMemo } from "react";
import { selectLastRefreshedAt } from "@/lib/refresh/refreshSlice";
import {
  GraphLoadingStage,
  selectGraphLoadingStage,
  selectIsExporting,
  selectMovingAverage,
  setCsvExportData,
  setExportDates,
  setGraphLoadingStage,
} from "@/lib/export/exportSlice";
import { Site } from "@/lib/site/site";
import { selectCurrentSite } from "@/lib/site/siteSlice";
import { XIcon } from "lucide-react";
import { Visibility } from "../ui/visiblity";
import { Alert } from "@/lib/alerts/alert";
import { SeismicTable } from "./seismic-table";
import { pdfDateFormatter } from "@/lib/utils";
import {
  selectSelectedDevices,
  setSelectedDevices,
} from "@/lib/selectedDevicesSlice";
import { selectVisualParamsDevices, setVisualParamsDevices } from "@/lib/visualParamsSlice";

import { ToggleGroup, ToggleGroupItem } from "@/components/ui/toggle-group";
import { useTranslations } from "next-intl";

const getPlotType = (param: ObservationParameter): VisualisationType => {
  if (
    param.visualisationType &&
    visualisationTypes.includes(param.visualisationType)
  ) {
    return param.visualisationType as VisualisationType;
  }
  return "linegraph";
};

function DeviceTimeSeries({
  displayName,
  data,
  alerts,
  header,
  start,
  end,
}: {
  displayName?: string;
  data: DeviceData[];
  alerts: Alert[];
  header: boolean;
  start?: Date;
  end?: Date;
}) {
  const selectedDevices = useAppSelector(selectSelectedDevices);
  const isExporting = useAppSelector(selectIsExporting);
  const params = useAppSelector(selectVisualParamsDevices);
  const dispatch = useAppDispatch();

  const uniqueParamsArray = useMemo(() => {
    // Extract all the arrays from the params object
    const allArrays = Object.values(params);
    
    // Flatten the arrays into a single array and remove duplicates
    const mergedArray = [...new Set(allArrays.flat())];
    
    return mergedArray;
  }, [params]);
  
  // console.log("Unique merged array of strings: ", uniqueParamsArray);
  const t = useTranslations();

  // create an array of available parameters to use for
  // filtering parameters when multiple devices are selected
  const chartParams = data.reduce((prev, curr) => {
    curr.parameters.forEach((parameter) => {
      const existingIndex = prev.findIndex(
        (p) => p.value === parameter.name && p.label === parameter.alias
      );

      if (existingIndex === -1)
        prev.push({ label: parameter.alias, value: parameter.name });
    });

    return prev;
  }, [] as Array<{ label: string; value: string }>);

  // chart params for filtering params when multiple devices
  // are selected

  // Start here: It is a quick hardcoded fix to change the default parameter on multi-chart plot.
  const [selectedChartParam, setSelectedChartParam] = useState<Array<string>>(
    uniqueParamsArray
  );
  // useEffect(() => {
  //   const dataDeviceArray: string[] = [];
  //   if (
  //     data.some((data) => data.deviceDisplayName?.startsWith("FM")) ||
  //     data.some((data) => data.deviceDisplayName?.startsWith("VN"))
  //   ) {
  //     dataDeviceArray.push("flowRate");
  //   }

  //   if (data.some((data) => data.deviceDisplayName?.startsWith("REN"))) {
  //     dataDeviceArray.push("batteryLifePercentage");
  //   }

  //   if (data.some((data) => data.deviceDisplayName?.startsWith("SONDE"))) {
  //     dataDeviceArray.push("electricalConductivity");
  //   }

  //   if (data.some((data) => data.deviceDisplayName?.startsWith("PL"))) {
  //     dataDeviceArray.push("waterLevel");
  //   }

  //   if (data.some((data) => data.deviceDisplayName?.startsWith("VWP"))) {
  //     dataDeviceArray.push("waterLevelRl");
  //   }

  //   setSelectedChartParam(dataDeviceArray);
  // }, [data]);

  // End. The hardcode fix ends here.

  // Create separate datasets for the plot data and the table data, where the table
  // data just contains parameters that are set to visualisationType "table" and the
  // plot data contains everything else.
  const tableData = data
    .map((device) => ({
      ...device,
      parameters: device.parameters.filter(
        (param) => getPlotType(param) === "table"
      ),
    }))
    .filter((device) => device.parameters.length > 0);

  let plotData;
  if (data.length === 1) {
    plotData = data
      .map((device) => ({
        ...device,
        parameters: device.parameters.filter(
          (param) => getPlotType(param) !== "table"
        ),
      }))
      .filter((device) => device.parameters.length > 0)
      .flatMap((device) =>
        device.parameters.map((param) => ({
          id: param.name,
          name: param.alias,
          points: device.data.map((point) => ({
            x: new Date(point.timestamp).getTime(),
            y: point[param.name] as number,
          })),
          units: param.unit,
          plotType: getPlotType(param),
          parameter: param.name,
        }))
      );
  } else {
    // multiple devices in data
    plotData = data
      .map((device) => ({
        ...device,
        parameters: device.parameters.filter(
          (param) => getPlotType(param) !== "table"
        ),
      }))
      .filter((device) => device.parameters.length > 0)
      .flatMap((device) =>
        device.parameters
          .map((param) => ({
            id: `${device.deviceId}_${param.name}`,
            name: device.deviceDisplayName as string,
            points: device.data.map((point) => ({
              x: new Date(point.timestamp).getTime(),
              y: point[param.name] as number,
            })),
            units: param.unit,
            plotType: getPlotType(param),
            parameter: param.name,
          }))
          .filter((param) => selectedChartParam.includes(param.parameter))
      );
  }

  return (
    <div
      className="flex flex-col gap-y-2 pb-4"
      id={data.map((device) => device.deviceName ?? "unnamed-device").join("_")}
    >
      <Visibility isVisible={header}>
        <div className="flex flex-row justify-between items-center">
          <Visibility isVisible={!isExporting}>
            <h1 className="text-lg font-bold">
              {data.length > 1
                ? t(TranslationKeys.multiDeviceChart)
                : displayName}
            </h1>
          </Visibility>
          <div className="flex flex-row gap-2 items-center">
            {/* <MovingAverageSelector /> // Removed as not yet used and was causing state issues */}
            <Visibility isVisible={data.length > 1}>
              <div
                className="w-6 h-6 my-auto cursor-pointer hover:bg-muted/50 rounded-sm"
                onClick={() =>
                  dispatch(
                    setSelectedDevices(
                      selectedDevices[0].map((device) => [device])
                    )
                  )
                }
              >
                <XIcon size={16} className="m-auto h-full" />
              </div>
            </Visibility>
          </div>
        </div>
      </Visibility>
      <Visibility isVisible={data.length > 1}>
        <div className="flex">
          <ToggleGroup
            size="sm"
            type="multiple"
            onValueChange={(value) => setSelectedChartParam(value)}
            value={selectedChartParam}
            className="flex flex-wrap justify-start"
          >
            {chartParams.map((param, key) => (
              <ToggleGroupItem
                key={key}
                value={param.value}
                className="flex-shrink-0"
              >
                {param.label}
              </ToggleGroupItem>
            ))}
          </ToggleGroup>
        </div>
      </Visibility>
      <TimeSeriesGraph
        height={400}
        axisType="time"
        start={start}
        end={end}
        datasets={plotData}
        thresholds={alerts
          .filter(
            (alert) =>
              alert.observationParameter !== null &&
              alert.thresholdValue !== null
          )
          .map((alert) => ({
            parameter: alert.observationParameter!,
            value: alert.thresholdValue!,
            alertType: alert.alertType,
          }))}
      />
      {tableData.length > 0 && <SeismicTable data={tableData} />}
    </div>
  );
}

// If deviceIds is undefined, it will show all devices
export function VisualisationsSection({
  id,
  deviceIds,
  headers = true,
}: {
  id: string;
  deviceIds?: string[][];
  headers?: boolean;
}) {
  const timePeriod = useAppSelector(selectPeriod);
  const now = new Date(useAppSelector(selectLastRefreshedAt));

  const currentTimePeriod = TimePeriodValues[timePeriod];
  const isDuration = currentTimePeriod.duration !== undefined;

  const start = isDuration
    ? sub(now, currentTimePeriod.duration!)
    : currentTimePeriod.range!.start;
  const end = isDuration ? now : currentTimePeriod.range!.end;

  const [reloading, setReloading] = useState(false);
  const currentSite: Site | null = useAppSelector(selectCurrentSite);
  const [deploymentIds, setDeploymentIds] = useState<string[] | null>(null);
  const graphLoadingStage = useAppSelector(selectGraphLoadingStage);
  const dispatch = useAppDispatch();
  const { data: allDevices } = useGetDevicesQuery(
    !currentSite ? skipToken : { siteId: currentSite.siteId }
  );

  const {
    data: allDeployments,
    isLoading: isLoadingDeployments,
    error: deploymentError,
  }: {
    data?: Deployment[];
    isLoading: boolean;
    isFetching: boolean;
    error?: FetchBaseQueryError | SerializedError;
  } = useGetDeploymentsQuery({
    SiteId: currentSite?.siteId,
  });

  const {
    data: alertData,
    error: alertDataError,
  }: {
    data?: Alert[];
    isLoading: boolean;
    isFetching: boolean;
    error?: FetchBaseQueryError | SerializedError;
  } = useGetAlertsQuery(
    deploymentIds === null
      ? skipToken
      : {
          siteId: currentSite!.siteId,
          deploymentIds,
        }
  );

  const movingAverage = useAppSelector(selectMovingAverage);
  const {
    data: measurements,
    isLoading: isLoadingMeasurements,
    isFetching: isFetchingMeasurements,
    error: measurementsError,
  }: {
    data?: DeviceData[];
    isLoading: boolean;
    isFetching: boolean;
    error?: FetchBaseQueryError | SerializedError;
  } = useGetManyMeasurementsQuery(
    isLoadingDeployments || deploymentIds === null
      ? skipToken
      : {
          siteId: currentSite!.siteId,
          start: start.toISOString(),
          end: end.toISOString(),
          movingAverage: movingAverage,
          deploymentIds,
        }
  );

  useEffect(() => {
    let filteredDeployments: Deployment[] = [];

    if (allDeployments !== undefined) {
      if (deviceIds === undefined) {
        filteredDeployments = allDeployments;
      } else {
        const flatDeviceIds = deviceIds.flat();
        filteredDeployments = allDeployments.filter((deployment) =>
          flatDeviceIds.includes(deployment.deviceId)
        );
      }
    }

    setDeploymentIds(
      filteredDeployments.map((deployment) => deployment.deploymentId)
    );
  }, [deviceIds, allDeployments, allDevices]);

  // Show loader when it is fetching new measurement data

  useEffect(() => {
    if (!isFetchingMeasurements) {
      setReloading(false);
    } else {
      setReloading(true);
    }
  }, [isFetchingMeasurements]);

  useEffect(() => {
    if (deploymentError !== undefined) {
      console.error("Error fetching deployments", deploymentError);
    }
    if (measurementsError !== undefined) {
      console.error("Error fetching measurements", measurementsError);
    }
    if (alertDataError !== undefined) {
      console.error("Error fetching alerts", alertDataError);
    }
  }, [deploymentError, measurementsError, alertDataError]);

  useEffect(() => {
    if (isLoadingDeployments || isLoadingMeasurements || reloading) {
      dispatch(setGraphLoadingStage(GraphLoadingStage.Loading));
    } else {
      dispatch(setGraphLoadingStage(GraphLoadingStage.Loaded));
    }
  }, [isLoadingMeasurements, isLoadingDeployments, reloading, dispatch]);

  useEffect(() => {
    const handleBeforeUnload = () => {
      dispatch(setGraphLoadingStage(GraphLoadingStage.NotStarted));
    };

    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
      dispatch(setGraphLoadingStage(GraphLoadingStage.NotStarted));
    };
  }, [dispatch]);

  

  const deviceIdSets = deviceIds ?? [
    measurements?.map((m) => m.deviceId) ?? [],
  ];
  const shownDatasets = deviceIdSets.map((deviceIdSet) => ({
    key: deviceIdSet.join("-"),
    displayName: allDevices?.find((e) =>
      deviceIdSet.includes(e.deviceInfo.deviceId)
    )?.deviceInfo.displayName,
    data:
      measurements?.filter(
        (meas) => meas !== null && deviceIdSet.includes(meas.deviceId)
      ) ?? [],
  }));

  useEffect(() => {
    if (!isFetchingMeasurements) {
      dispatch(setCsvExportData(measurements!));
      dispatch(
        setExportDates({
          startDate: pdfDateFormatter(start),
          endDate: pdfDateFormatter(end),
        })
      );
      // console.log("shownDatasets: ", shownDatasets)
      // const currentParams = measurements?.reduce((acc, m) => {
      //   if (m.deviceName) { 
      //     acc[m.deviceName] = defaultParams(m.deviceName);
      //   }
      //   return acc;
      // }, {} as Record<string, string[]>);
      // dispatch(setVisualParamsDevices(currentParams ?? {}));
    }

    return () => {
      dispatch(setCsvExportData(null));
    };
  }, [dispatch, end, isFetchingMeasurements, measurements, start]);

  return (
    <div
      id={`${id}`}
      className="flex flex-col h-full pl-8 pr-6 gap-2 pb-8 my-3"
    >
      <Loaders.indicator
        id="is-fetching-for-report"
        loading={graphLoadingStage === GraphLoadingStage.Loading}
        size="large"
        layout="area"
      />
      <Loaders.zone isVisible={graphLoadingStage !== GraphLoadingStage.Loading}>
        <div id={`${id}-children`} className="flex-grow flex flex-col gap-3">
          {measurements?.length === 0 ? (
            <div>No data selected for charting</div>
          ) : (
            shownDatasets.map(
              (dataset) =>
                dataset.data.length > 0 && (
                  <DeviceTimeSeries
                    key={dataset.key}
                    displayName={dataset.displayName}
                    data={dataset.data}
                    alerts={
                      dataset.data.length === 1
                        ? alertData?.filter((alert) =>
                            dataset.data
                              .map((d) => d.deploymentId)
                              .includes(alert.deploymentId)
                          ) ?? []
                        : []
                    }
                    header={headers}
                    start={start}
                    end={end}
                  />
                )
            )
          )}
        </div>
      </Loaders.zone>
    </div>
  );
}

const defaultParams = (displayName: string) => {
  const dataDeviceArray: string[] = [];
    if (displayName.startsWith("FM") || displayName.startsWith("VN")) {
      dataDeviceArray.push("flowRate");
    }

    if (displayName.startsWith("REN")) {
      dataDeviceArray.push("batteryLifePercentage");
    }

    if (displayName.startsWith("SONDE")) {
      dataDeviceArray.push("electricalConductivity");
    }

    if (displayName.startsWith("PL")) {
      dataDeviceArray.push("waterLevel");
    }

    if (displayName.startsWith("VWP")) {
      dataDeviceArray.push("waterLevelRl");
    }

    return dataDeviceArray;
}
