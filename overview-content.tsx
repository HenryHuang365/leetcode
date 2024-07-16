"use client";

import {
  DeviceSummaryModel,
  DeviceSummaryList,
} from "@/components/alerts/alerts-summary-list";
import { VisualisationsSection } from "@/components/charts/visualisations-section";
import { DeviceSidePanel } from "@/components/device-details-side-panel/device-side-panel";
import { OverviewSiteMap } from "@/components/maps/OverviewSiteMap";
import {
  ResizablePanelGroup,
  ResizablePanel,
  ResizableHandle,
} from "@/components/ui/resizable";
import { Visibility } from "@/components/ui/visiblity";
import { AlertStatus } from "@/lib/alerts/alert";
import {
  DeviceAlertMapping,
  compareSeverityForAlert,
  forEachDeploymentAlertEvent,
  mapDeviceAlert,
} from "@/lib/alerts/deviceAlert";
import {
  useGetAlertEventsQuery,
  useGetDevicesQuery,
  useGetDeploymentsQuery,
  useGetGroupByIdQuery,
  useGetAlertsQuery,
} from "@/lib/api/apiSlice";
import { Device } from "@/lib/devices/device";
import {
  setGraphLoadingStage,
  GraphLoadingStage,
  setExportSelectedDeviceNames,
} from "@/lib/export/exportSlice";
import {
  selectIsActiveSelection,
  selectSelectedDevices,
  resetSelectedDevices
} from "@/lib/selectedDevicesSlice";
import { selectCurrentSite } from "@/lib/site/siteSlice";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import { skipToken } from "@reduxjs/toolkit/query";
import { useState, useEffect } from "react";

export interface OverviewContentProps {
  filters?: {
    group: string;
  };
}

export function OverviewContent({ filters }: OverviewContentProps) {
  const dispatch = useAppDispatch();
  useEffect(() => {
    // Reset selected devices when the filter changes
    if (filters?.group) {
      console.log("Filters present, resetting selected devices");
      dispatch(resetSelectedDevices());
    }
  }, [filters, dispatch]);
  const currentSite = useAppSelector(selectCurrentSite);
  const selectedDevices = useAppSelector(selectSelectedDevices);
  const isActiveSelection = useAppSelector(selectIsActiveSelection);
  const [summaryModels, setSummaryModels] = useState<DeviceSummaryModel[]>([]);
  

  const { data: devices, isFetching: isFetchingDevices } = useGetDevicesQuery({
    siteId: currentSite?.siteId,
  });
  const { data: deployments, isFetching: isFetchingDeployments } =
    useGetDeploymentsQuery({ SiteId: currentSite?.siteId });

  const { data: alertsData, isFetching: isFetchingAlerts } = useGetAlertsQuery(
    !currentSite || !deployments
      ? skipToken
      : {
          siteId: currentSite?.siteId,
          deploymentIds: deployments.map((e) => e.deploymentId),
        }
  );
  const { data: alertEventsData, isFetching: isFetchingAlertEvents } =
    useGetAlertEventsQuery(
      !currentSite || !alertsData
        ? skipToken
        : {
            siteId: currentSite?.siteId ?? "",
            alertIds: alertsData.map((alert) => alert.id),
            deploymentIds: alertsData.map((alert) => alert.deploymentId),
          }
    );
  const { data: group, isFetching: isFetchingGroup } = useGetGroupByIdQuery(
    { siteId: currentSite!.siteId, groupId: filters?.group ?? "" },
    {
      skip: !filters?.group,
    }
  );

  useEffect(() => {
    if (
      devices &&
      alertsData &&
      alertEventsData &&
      deployments &&
      (filters?.group ? group : true)
    ) {
      const models: DeviceSummaryModel[] = [];

      const targetDeployments = filters?.group
        ? deployments.filter((deployment) =>
            group?.deployments.some(
              (d) => d.deploymentId === deployment.deploymentId
            )
          )
        : deployments;
      const mapping: DeviceAlertMapping[] = mapDeviceAlert(
        targetDeployments,
        devices,
        alertsData,
        alertEventsData
      );
      forEachDeploymentAlertEvent({
        deviceAlertMappings: mapping,
        callback: (device, _, alert, alertEvent) => {
          models.push({
            device: device!,
            alert,
            alertEvent,
          });
        },
        returnEmptyForNoEvent: true,
        numLimitForAlertEvent: 1,
        alertMappingFilterFn: (alertMapping) =>
          alertMapping.alertEvent?.status !== AlertStatus.Resolved,
        alertMappingCompareFn: compareSeverityForAlert,
      });
      setSummaryModels(models);
    }
  }, [
    alertEventsData,
    devices,
    deployments,
    filters?.group,
    group,
    alertsData,
  ]);

  useEffect(() => {
    if (!isActiveSelection) {
      dispatch(setGraphLoadingStage(GraphLoadingStage.NotShown));
    }

    return () => {
      dispatch(setGraphLoadingStage(GraphLoadingStage.NotStarted));
    };
  }, [dispatch, isActiveSelection]);

  useEffect(() => {
    // Remove data if fecthing data
    if (!devices || (filters?.group && !group)) {
      dispatch(
        setExportSelectedDeviceNames({
          deployments: [],
          deviceGroup: null,
        })
      );
      return;
    }
    // Set to selected device for pdf generation
    const selectedDeviceIds = selectedDevices.flat();
    const selectedDevice: string[] = devices!
      .filter((device: Device) =>
        selectedDeviceIds.includes(device.deviceInfo.deviceId)
      )
      .map((device: Device) => device.deviceInfo.displayName)
      .filter(
        (displayName: string | undefined) => displayName !== undefined
      ) as string[];
    dispatch(
      setExportSelectedDeviceNames({
        deployments: selectedDevice,
        deviceGroup: group?.name ?? null,
      })
    );
  }, [selectedDevices, devices, filters, group, dispatch]);

  return (
    <ResizablePanelGroup
      direction="horizontal"
      className="flex-grow overflow-hidden"
    >
      <ResizablePanel
        minSize={10}
        defaultSize={30}
        id="alerts-summary-panel"
        className="flex flex-col flex-grow overflow-y-hidden min-w-72"
      >
        <DeviceSummaryList
          models={summaryModels}
          loading={
            isFetchingAlerts ||
            isFetchingAlertEvents ||
            isFetchingDevices ||
            isFetchingDeployments ||
            isFetchingGroup
          }
        />
      </ResizablePanel>
      <ResizableHandle className="bg-gray-400" />
      <ResizablePanel id="overview-body-panel">
        <ResizablePanelGroup
          direction="vertical"
          className="flex-grow flex flex-col"
        >
          <ResizablePanel
            defaultSize={isActiveSelection ? 50 : 100}
            className="mt-2"
            id="site-map-panel"
            order={1}
          >
            <OverviewSiteMap filters={filters} />
          </ResizablePanel>
          <Visibility isVisible={isActiveSelection}>
            <ResizableHandle withHandle className="bg-gray-400" />
            <ResizablePanel defaultSize={50} id="visualisation-panel" order={2}>
              <VisualisationsSection
                id="visualisation-section"
                deviceIds={selectedDevices}
              />
            </ResizablePanel>
          </Visibility>
        </ResizablePanelGroup>
      </ResizablePanel>
      {selectedDevices.length === 1 &&
        selectedDevices[0].length === 1 &&
        deployments && (
          <DeviceSidePanel
            deviceId={selectedDevices[0][0]}
            deploymentId={
              deployments.find((d) => d.deviceId === selectedDevices[0][0])!
                .deploymentId
            }
            className="animate-slide-in"
          />
        )}
    </ResizablePanelGroup>
  );
}
