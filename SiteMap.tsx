"use client";

import {
  DivIcon,
  LeafletEventHandlerFnMap,
  point,
  MarkerCluster,
} from "leaflet";
import {
  LayersControl,
  MapContainer,
  Marker,
  TileLayer,
  useMap,
  useMapEvents,
} from "react-leaflet";
import "./style.css";
import { renderToStaticMarkup } from "react-dom/server";
import { getDeviceSymbol } from "../devices/DeviceType";
import CutomisedMarkerCluster from "./MarkerCluster";
import MarkerClusterGroup from "react-leaflet-cluster";
import "leaflet/dist/leaflet.css";
import { ActivityIcon, MinusIcon, PlusIcon } from "lucide-react";
import { CustomIcon } from "../ui/custom-icons";
import { useEffect, useRef, useState } from "react";
import L from "leaflet";
import { selectIsExporting } from "@/lib/export/exportSlice";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import { Visibility } from "../ui/visiblity";
import { DeviceSymbolIllustration } from "../devices/DeviceSymbol";
import {
  Alert,
  AlertEvent,
  AlertPriorities,
  AlertStatus,
  AlertType,
  compareSeverityOrder,
  normalAlertType,
  unconfiguredAlertType,
} from "@/lib/alerts/alert";
import {
  DisplacementVector,
  DisplacementVectorProps,
  getDisplacementVector,
} from "./DisplacementVector";
import { Toggle } from "../ui/toggle";
import {
  selectIsMultigraph,
  setSelectedDevices,
} from "@/lib/selectedDevicesSlice";
import { useGetAlertTypesQuery } from "@/lib/api/apiSlice";
import { selectCurrentSite } from "@/lib/site/siteSlice";
import { skipToken } from "@reduxjs/toolkit/query";
import { outlineStyle } from "@/lib/utils";
import { Device } from "@/lib/devices/device";
import { Deployment } from "@/lib/deployments/deployment";
import { DeviceData } from "@/lib/devices/device-data";

/**
 * Represents a device within a site.
 */
export interface SiteDevice {
  id: string; // The unique identifier of the device.
  name: string; // The name of the device.
  deviceType: string; // The type of device.
  displacementVector?: DisplacementVectorProps; // The displacement vector of the device, if applicable
  lat: number; // The latitude coordinate of the device.
  lng: number; // The longitude coordinate of the device.
  priority?: AlertPriorities; // The priority level of the device.
  alertType?: AlertType; // The label of the alert level.
}

export const getSiteDevice = (
  deployment: Deployment,
  device: Device,
  alerts: Alert[],
  alertEvents: AlertEvent[],
  latestMeasurements: DeviceData[] | undefined
): SiteDevice => {
  const alertConfigs: Alert[] = alerts!.filter(
    (alert) => alert.deploymentId === deployment.deploymentId
  );

  /// get all active alerts for the deployment, sort by severity, take the first (highest severity)
  const alertEvent =
    alertEvents!
      .filter((alertEvent) => alertEvent.status !== AlertStatus.Resolved)
      .map((alertEvent) => {
        const alert = alertConfigs.find(
          (alert) => alert.id === alertEvent.alertId
        );
        return {
          event: alertEvent,
          alert,
        };
      })
      .filter((e) => e.alert)
      .sort((a, b) =>
        compareSeverityOrder(
          b.alert!.alertType.severity,
          a.alert!.alertType.severity
        )
      )[0] ?? null;
  const hasConfigs =
    alertConfigs.filter((conf) => conf.observationParameter !== null).length >
    0;

  return {
    id: device!.deviceInfo.deviceId,
    name: device!.deviceInfo.displayName ?? device!.deviceInfo.name,
    lat: deployment.positionCoordinates[0],
    lng: deployment.positionCoordinates[1],
    deviceType: device!.deviceTypeName,
    priority:
      alertEvent?.alert?.priority ??
      (hasConfigs ? undefined : AlertPriorities.Unknown),
    alertType:
      alertEvent?.alert?.alertType ??
      (hasConfigs ? normalAlertType : undefined),
    displacementVector: getDisplacementVector(
      deployment,
      latestMeasurements,
      alerts,
      alertEvents
    ),
  } as SiteDevice;
};

/**
 * Props for the SiteMap component.
 */
interface SiteMapProps {
  lat: number; // The latitude coordinate of the site.
  lng: number; // The longitude coordinate of the site.
  devices: SiteDevice[]; // The list of devices within the site.
}

const azureMapKey = process.env.NEXT_PUBLIC_AZURE_MAP_KEY;

/**
 * Renders a React Leaflet map component for a site using Nearmap.
 * @param lat - The latitude coordinate of the site.
 * @param lng - The longitude coordinate of the site.
 * @param devices - The list of devices within the site.
 * @param alertLevels - The list of colors for alert levels, low to high.
 */
export function SiteMap({ lat, lng, devices }: SiteMapProps) {
  const maxZoom = 23;
  const [showDisplacement, setShowDisplacement] = useState<boolean | null>(
    null
  );
  const [clustersUpdated, setClustersUpdated] = useState(0);
  const isExporting = useAppSelector(selectIsExporting);
  const dispatch = useAppDispatch();

  // Necessary to prevent callbacks attempting to be added before marker rendered
  const delayClusterUpdate = async () => {
    await new Promise((resolve) => setTimeout(resolve, 300));
    setClustersUpdated((v) => v + 1);
  };

  useEffect(() => {
    delayClusterUpdate();
    if (
      showDisplacement === null &&
      devices.some((device) => device.displacementVector)
    ) {
      setShowDisplacement(false);
    }
  }, [devices, showDisplacement]);

  // Add onclick listeners to markers
  useEffect(() => {
    devices.forEach((device) => {
      const marker = document.getElementById(`marker-${device.id}`);
      if (marker) {
        marker.addEventListener("click", () =>
          dispatch(setSelectedDevices([[device.id]]))
        );
      }
    });

    // Don't need cleanup as repeated eventlisteners are deduplicated
    // and the event listeners are removed when the component is unmounted
  }, [devices, clustersUpdated, dispatch]);

  function hideLayerControl() {
    const controlElement = document.querySelector(
      ".leaflet-control-layers-toggle"
    );
    if (controlElement && controlElement instanceof HTMLElement) {
      controlElement.style.display = "none";
    }
  }
  function showLayerControl() {
    const controlElement = document.querySelector(
      ".leaflet-control-layers-toggle"
    );
    if (controlElement && controlElement instanceof HTMLElement) {
      controlElement.style.display = "block";
    }
  }

  useEffect(() => {
    if (isExporting) {
      hideLayerControl();
    } else {
      showLayerControl();
    }
    return () => {
      showLayerControl();
    };
  }, [isExporting]);

  const [zoomLevel, setZoomLevel] = useState(15);

  const createClusterCustomIcon = function (cluster: MarkerCluster) {
    return new DivIcon({
      html: `<span class="cluster-icon">${cluster.getChildCount()}</span>`,
      className: "custom-marker-cluster",
      iconSize: point(33, 33, true),
    });
  };

  return (
    <MapContainer
      id={"map-container"}
      zoomControl={false}
      center={[lat, lng]}
      zoom={15}
      maxZoom={maxZoom}
      className="w-full h-full rounded-lg border border-gray-400 p-0 z-0"
    >
      <MapAlertKey />
      <ResizeMapEvents />
      <MapEventHook
        handlers={{
          zoomend: () => delayClusterUpdate(),
        }}
      />
      <MapControls
        showDisplacement={showDisplacement}
        setShowDisplacement={setShowDisplacement}
        setZoomLevel={setZoomLevel}
      />
      <ScaleControl />
      <LayersControl position="topright">
        <LayersControl.BaseLayer checked name="Satellite">
          <TileLayer
            url={`https://atlas.microsoft.com/map/tile?subscription-key=${azureMapKey}&api-version=2024-04-01&tilesetId=microsoft.imagery&zoom={z}&x={x}&y={y}`}
            attribution={`© ${new Date().getFullYear()} TomTom, Microsoft`}
            maxNativeZoom={19}
            maxZoom={maxZoom}
          />
        </LayersControl.BaseLayer>
        <LayersControl.BaseLayer name="Topographic">
          <TileLayer
            url={`https://atlas.microsoft.com/map/tile?subscription-key=${azureMapKey}&api-version=2024-04-01&tilesetId=microsoft.base.road&zoom={z}&x={x}&y={y}`}
            attribution={`© ${new Date().getFullYear()} TomTom, Microsoft`}
            maxNativeZoom={22}
            maxZoom={maxZoom}
          />
        </LayersControl.BaseLayer>
      </LayersControl>

      {showDisplacement !== true ? (
        zoomLevel > 16 ? (
          <CutomisedMarkerCluster
            zoomToBoundsOnClick={false}
            spiderfyOnMaxZoom={false}
            showCoverageOnHover={false}
            onClusterAnimationend={() => delayClusterUpdate()}
            iconCreateFunction={(cluster) => {
              const clusterSize = cluster.getChildCount();
              const height = 18 * clusterSize + 8;

              return new DivIcon({
                className: `bg-transparent`,
                iconAnchor: [54, height / 2],
                html: renderToStaticMarkup(
                  <div
                    id={`cluster-${cluster
                      .getAllChildMarkers()
                      .map((m) => m.options.title)
                      .join("-")}`}
                    className="flex flex-col bg-white bg-opacity-100 p-1 w-fit gap-1 font-bold border border-dashed border-black rounded-lg"
                  >
                    {cluster.getAllChildMarkers().map((marker, idx) => (
                      <div
                        key={idx}
                        id={`marker-${marker.options.title}`}
                        dangerouslySetInnerHTML={{
                          __html: marker.getIcon().createIcon().innerHTML,
                        }}
                      />
                    ))}
                  </div>
                ),
              });
            }}
          >
            {devices.map((device) => (
              <DeviceMarker
                key={device.id}
                device={device}
                showDisplacement={showDisplacement}
                inCluster={true}
              />
            ))}
          </CutomisedMarkerCluster>
        ) : (
          <MarkerClusterGroup
            chunkedLoading
            iconCreateFunction={createClusterCustomIcon}
          >
            {devices.map((device) => (
              <DeviceMarker
                key={device.id}
                device={device}
                showDisplacement={showDisplacement}
                inCluster={true}
              />
            ))}
          </MarkerClusterGroup>
        )
      ) : (
        devices
          .filter((device) => device.displacementVector)
          .map((device) => (
            <DeviceMarker
              key={device.id}
              device={device}
              showDisplacement={showDisplacement}
              inCluster={false}
            />
          ))
      )}
    </MapContainer>
  );
}

function MapEventHook({ handlers }: { handlers: LeafletEventHandlerFnMap }) {
  useMapEvents(handlers);
  return null;
}

function DeviceMarker({
  device,
  showDisplacement,
  inCluster,
}: {
  device: SiteDevice;
  showDisplacement: boolean | null;
  inCluster: boolean;
}) {
  const isMultigraphActive = useAppSelector(selectIsMultigraph);
  const dispatch = useAppDispatch();

  return (
    <Marker
      key={device.id}
      position={[device.lat, device.lng]}
      eventHandlers={{
        click: () =>
          isMultigraphActive
            ? null
            : dispatch(setSelectedDevices([[device.id]])),
      }}
      icon={
        new DivIcon({
          className: "bg-transparent border-none font-bold h-[18px]",
          iconAnchor: [9, 9],
          html: renderToStaticMarkup(
            <DeviceSummary
              device={device}
              showDisplacement={inCluster ? null : showDisplacement}
              isClickable={!isMultigraphActive}
            />
          ),
        })
      }
      title={device.id}
    />
  );
}

function DeviceSummary({
  device,
  showDisplacement,
  isClickable,
}: {
  device: SiteDevice;
  showDisplacement: boolean | null;
  isClickable: boolean;
}) {
  const deformationToEast =
    showDisplacement && (device.displacementVector?.deltaEast ?? 0) > 0;

  return (
    <div
      data-is-right={deformationToEast}
      data-is-clickable={isClickable}
      data-device-id={device.id}
      data-is-displacement={showDisplacement}
      className="flex data-[is-right=true]:flex-row-reverse data-[is-right=false]:flex-row bg-gray-300 bg-opacity-0 w-fit rounded-[2px] data-[is-clickable=true]:hover:bg-opacity-50 data-[is-displacement=true]:data-[is-right=true]:-translate-x-[calc(100%-9px)]"
      style={
        showDisplacement
          ? {
              marginLeft: deformationToEast ? "auto" : 0,
              marginRight: deformationToEast ? 0 : "auto",
            }
          : {}
      }
    >
      {showDisplacement && device.displacementVector && (
        <div
          data-is-right={deformationToEast}
          className="absolute pointer-events-none top-[calc(50%+1px)] data-[is-right=true]:translate-x-[calc(50%-8.5px)] data-[is-right=false]:left-1/2 data-[is-right=false]:-translate-x-[calc(50%-3px)] -translate-y-[calc(50%)]"
        >
          <DisplacementVector {...device.displacementVector} />
        </div>
      )}
      <div data-device-id={device.id}>
        <DeviceSymbolIllustration type={getDeviceSymbol(device.deviceType)} />
      </div>
      <div
        className="h-[18px] w-[18px] rounded-[2px] text-center"
        data-device-id={device.id}
        style={{
          color: "#F2F2F2",
          backgroundColor: device.alertType?.statusColour ?? "pink",
        }}
      >
        {device.priority !== AlertPriorities.Unknown && device.priority}
      </div>
      <span
        className="px-1 text-shadow overflow-clip whitespace-nowrap"
        data-device-id={device.id}
        style={outlineStyle}
      >
        {device.name}
      </span>
    </div>
  );
}

function MapControls({
  showDisplacement,
  setShowDisplacement,
  setZoomLevel,
}: {
  showDisplacement: boolean | null;
  setShowDisplacement: (show: boolean | null) => void;
  setZoomLevel: (zoom: number) => void;
}) {
  const map = useMap();
  setZoomLevel(map.getZoom());
  const isExporting = useAppSelector(selectIsExporting);
  const [isMaxZoom, setIsMaxZoom] = useState(
    map.getZoom() === map.getMaxZoom()
  );
  const [isMinZoom, setIsMinZoom] = useState(
    map.getZoom() === map.getMinZoom()
  );

  const handleZoomIn = () => {
    map.zoomIn();
  };

  const handleZoomOut = () => {
    map.zoomOut();
  };

  useEffect(() => {
    const checkZoom = () => {
      setIsMaxZoom(map.getZoom() === map.getMaxZoom());
      setIsMinZoom(map.getZoom() === map.getMinZoom());
    };

    map.addEventListener("zoomend", checkZoom);
    return () => {
      map.removeEventListener("zoomend", checkZoom);
    };
  }, [map]);

  return (
    <div>
      <div style={{ position: "absolute", top: 12, left: 12, zIndex: 1000 }}>
        <div className="flex flex-col gap-1.5">
          <Visibility isVisible={!isExporting}>
            <div className="flex flex-col">
              <button
                className="transition-colors duration-300 border rounded-t-md border-border bg-card disabled:bg-gray-300 p-1.5 hover:bg-accent hover:text-accent-foreground"
                onClick={handleZoomIn}
                disabled={isMaxZoom}
              >
                <PlusIcon className="h-4 w-4" />
              </button>
              <button
                className="transition-colors duration-300 border-b border-l border-r rounded-b-md border-border bg-card disabled:bg-gray-300 p-1.5 hover:bg-accent hover:text-accent-foreground"
                onClick={handleZoomOut}
                disabled={isMinZoom}
              >
                <MinusIcon className="h-4 w-4" />
              </button>
            </div>
          </Visibility>
          <Visibility isVisible={showDisplacement !== null && !isExporting}>
            <Toggle
              className="border rounded-md border-border bg-card p-1.5 hover:bg-accent hover:text-accent-foreground h-[30px]"
              pressed={showDisplacement!}
              onPressedChange={setShowDisplacement}
            >
              <ActivityIcon size={16} />
            </Toggle>
          </Visibility>
        </div>
      </div>
      <div
        style={{ zIndex: 1000 }}
        className="p-1.5 flex flex-col items-center absolute right-2 bottom-16"
      >
        <span className="font-semibold text-lg" style={outlineStyle}>
          N
        </span>
        <CustomIcon.NorthArrow
          size={16}
          className="stroke-white stroke-[0.75px] overflow-visible"
        />
      </div>
    </div>
  );
}

const ScaleControl = () => {
  const map = useMap();
  const scaleContainerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const scaleControl = L.control
      .scale({
        position: "bottomright", // initialization, will change later
        maxWidth: 100,
        metric: true,
        imperial: false,
      })
      .addTo(map);

    if (scaleContainerRef.current) {
      const scaleElement = scaleControl.getContainer();
      if (scaleElement && scaleElement instanceof HTMLElement) {
        scaleContainerRef.current.insertBefore(
          scaleElement,
          scaleContainerRef.current.firstChild
        );
        const scaleLine = scaleElement.querySelector(
          ".leaflet-control-scale-line"
        );

        if (scaleLine && scaleLine instanceof HTMLElement) {
          scaleLine.style.borderBottom = "1px solid";
          scaleLine.style.borderLeft = "1px solid";
          scaleLine.style.borderRight = "1px solid";
          scaleLine.style.borderColor = "#020617";
          scaleLine.style.height = "4px";
          scaleLine.style.background = "none";
          scaleLine.style.padding = "0 0 10px 0";
          scaleLine.style.display = "flex";
          scaleLine.style.flexDirection = "row";
          scaleLine.style.justifyContent = "center";
          scaleLine.style.alignItems = "center";
        }
      }
    }

    return () => {
      scaleControl.remove();
    };
  }, [map]);

  // TODO: Change bottom tp 11 when we get rid of bottom indicator.
  return (
    <div
      style={{ position: "absolute", bottom: 32, right: 10, zIndex: 1000 }}
      ref={scaleContainerRef}
    />
  );
};

function ResizeMapEvents() {
  const map = useMap();
  useEffect(() => {
    const mapElement = document.getElementById("map-container");
    if (!mapElement) return;
    const resizeObserver = new ResizeObserver(() => {
      map.invalidateSize();
    });
    resizeObserver.observe(mapElement as HTMLElement);
    return () => resizeObserver.disconnect();
  }, [map]);

  return null;
}

function MapAlertKey() {
  const currentSite = useAppSelector(selectCurrentSite);
  const { data: alertTypes } = useGetAlertTypesQuery(
    !currentSite ? skipToken : { siteId: currentSite.siteId }
  );
  const sortedAlerts = [
    ...[...(alertTypes || [])].sort(
      (a, b) => compareSeverityOrder(b.severity, a.severity) // S1 -> S10
    ),
    normalAlertType,
    unconfiguredAlertType,
  ];

  return !alertTypes || alertTypes.length === 0 ? null : (
    <div
      style={{ zIndex: 1000, bottom: 12, left: 12 }}
      className="absolute bg-white shadow-md p-2 rounded-md"
    >
      <div className="flex flex-col gap-2">
        {sortedAlerts.map((alert) => (
          <div
            key={`${alert.name}-${alert.statusColour}`}
            className="flex items-center gap-2 px-1.5"
          >
            <div
              className="w-3 h-3 rounded-[2px]"
              style={{ backgroundColor: alert.statusColour }}
            ></div>
            <span>{alert.name}</span>
          </div>
        ))}
      </div>
    </div>
  );
}
