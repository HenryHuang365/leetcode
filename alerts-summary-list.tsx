"use client";

import {
  Alert,
  AlertEvent,
  AlertStatus,
  alertStatusTranslationKey,
} from "@/lib/alerts/alert";
import { Device } from "@/lib/devices/device";
import { useTranslations } from "next-intl";
import { DataTable } from "../ui/data-table";
import { format } from "date-fns";
import Combobox from "../ui/combobox";
import { TranslationKeys } from "@/gen/translations.gen";
import { RowSelectionState } from "@tanstack/react-table";
import { Button } from "../ui/button";
import { LineChartIcon } from "lucide-react";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import {
  selectIsMultigraph,
  selectSelectedDevices,
  setSelectedDevices,
} from "@/lib/selectedDevicesSlice";
import { useState } from "react";
import { selectDeviceTypeFilter } from "@/lib/overview/overviewSlice";

export interface DeviceSummaryModel {
  device: Device;
  alert: Alert | null;
  alertEvent: AlertEvent | null;
}

const enum DeviceFilter {
  all = "all",
  open = "open",
  monitoring = "monitoring",
  investigating = "investigating",
}

interface DeviceSummaryListProps {
  models: DeviceSummaryModel[];
  loading: boolean;
}

export function DeviceSummaryList({ models, loading }: DeviceSummaryListProps) {
  const [deviceFilter, setDeviceFilter] = useState<DeviceFilter>(
    DeviceFilter.all
  );

  const deviceTypeFilter = useAppSelector(selectDeviceTypeFilter);

  const t = useTranslations();
  const selectedDevices = useAppSelector(selectSelectedDevices);
  const isMultigraphActive = useAppSelector(selectIsMultigraph);
  const dispatch = useAppDispatch();
  const selectedRows = models.reduce((acc, val, idx) => {
    const toCheck = isMultigraphActive
      ? selectedDevices[0]
      : selectedDevices.flat();
    return {
      [idx.toString()]: toCheck.includes(val.device.deviceInfo.deviceId),
      ...acc,
    };
  }, {} as RowSelectionState);

  console.log("Here is the models: ", models);
  console.log("Here is the selectedRows", selectedRows);
  console.log("Here is the selected Devices: ", selectedDevices);

  const toggleRow = (deviceId: string) => {
    const idx = models.findIndex(
      (model) => model.device.deviceInfo.deviceId === deviceId
    );
    setSelectedRows((prev) => ({
      ...prev,
      [idx.toString()]: !prev[idx.toString()],
    }));
  };

  const setSelectedRows: React.Dispatch<
    React.SetStateAction<RowSelectionState>
  > = (updateSelectedRows) => {
    const rows =
      typeof updateSelectedRows === "function"
        ? updateSelectedRows(selectedRows)
        : updateSelectedRows;
    const selectedKeys = Object.entries(rows)
      .filter(([, v]) => v) // Filter to selected entries
      .map(([k]) => k); // Only get key
    dispatch(
      setSelectedDevices(
        selectedKeys.map((key) => [
          models[parseInt(key)].device.deviceInfo.deviceId,
        ])
      )
    );
  };
  const numSelectedRows = Object.values(selectedRows).filter((v) => !!v).length;

  const createChart = () => {
    const selectedKeys = Object.entries(selectedRows)
      .filter(([, v]) => v) // Filter to selected entries
      .map(([k]) => k); // Only get key
    const newSelectedDevices = selectedKeys.map(
      (key) => models[parseInt(key)].device.deviceInfo.deviceId
    );
    dispatch(setSelectedDevices([newSelectedDevices]));
  };
  const multiChartActive =
    selectedDevices.length > 0 && selectedDevices[0].length > 1;

  const getComboBoxVals = (
    options: { label: string; value: DeviceFilter }[]
  ) => {
    return options.map((option) => ({
      label: option.label,
      value: option.value,
    }));
  };

  const deviceFilterOptions = [
    {
      label: t(TranslationKeys.alertsSummaryFilterAll),
      value: DeviceFilter.all,
    },
    {
      label: t(TranslationKeys.alertsSummaryFilterOpen),
      value: DeviceFilter.open,
    },
    {
      label: t(TranslationKeys.alertsSummaryFilterMonitoring),
      value: DeviceFilter.monitoring,
    },
    {
      label: t(TranslationKeys.alertsSummaryFilterInvestigating),
      value: DeviceFilter.investigating,
    },
  ];

  return (
    <div className="flex flex-col gap-y-4 p-2 flex-grow overflow-hidden">
      <div className="flex flex-row justify-between items-center p-2">
        <h1 className="text-lg font-bold mr-2">
          {t(TranslationKeys.alertsSummaryTitle)}
        </h1>
        <div className=" flex flex-row items-center gap-x-2 w-[200px] h-[46px]">
          <Combobox
            key={deviceFilter}
            datas={getComboBoxVals(deviceFilterOptions)}
            value={deviceFilter}
            onChange={(val) => setDeviceFilter(val as DeviceFilter)}
            selectHint={t(TranslationKeys.deviceTableTypeFilterPlaceholder)}
            searchHint={t(
              TranslationKeys.deviceTableTypeFilterSearchPlaceholder
            )}
            noResultsHint={t(TranslationKeys.deviceTableTypeFilterNoResult)}
          />
        </div>
      </div>
      <div className="flex flex-row gap-x-2 justify-between">
        <div />
        <div>
          <Button
            onClick={createChart}
            disabled={numSelectedRows < 2 || multiChartActive}
            className="flex flex-row gap-2"
          >
            <LineChartIcon size={16} />
            <span>{`${t(
              TranslationKeys.alertsSummaryCreateChart
            )} (${numSelectedRows})`}</span>
          </Button>
        </div>
      </div>
      <div className="flex flex-col flex-grow overflow-hidden">
        <DataTable
          rowStyler={(row) =>
            row.alert !== null && row.alert.alertType.statusColour !== undefined
              ? {
                  borderLeft: `3px solid ${row.alert.alertType.statusColour}`,
                }
              : {}
          }
          isLoading={loading}
          enableRowSelection={true}
          disableRowSelectors={multiChartActive}
          disablePagination={true}
          externalRowSelection={selectedRows}
          setExternalRowSelection={setSelectedRows}
          onRowClicked={(row) => toggleRow(row.device.deviceInfo.deviceId)}
          columns={[
            {
              label: t(TranslationKeys.alertsSummaryTableDeviceId),
              key: "device.deviceInfo.displayName",
            },
            {
              label: t(TranslationKeys.alertsSummaryTablePriority),
              key: "alert.priority",
              transformer: (val) => (val !== undefined ? (val as string) : ""),
            },
            {
              label: t(TranslationKeys.alertsSummaryTableAlarmTime),
              key: "alertEvent.startTime",
              transformer: (val) =>
                val !== undefined ? format(val as Date, "HH:mm:ss") : "",
            },
            {
              label: t(TranslationKeys.alertsSummaryTableStatus),
              key: "alertEvent.status",
              transformer: (val) =>
                val !== undefined
                  ? t(alertStatusTranslationKey(val as AlertStatus))
                  : "",
            },
            {
              label: t(TranslationKeys.alertsTableLabelDeviceType),
              key: "device.deviceTypeName",
              transformer: (val) => (val as string) ?? "-",
            },
          ]}
          typeFilter={deviceTypeFilter}
          alertFilter={
            deviceFilter === DeviceFilter.all
              ? ""
              : deviceFilter === DeviceFilter.open
              ? "Open"
              : deviceFilter === DeviceFilter.monitoring
              ? "Monitoring"
              : "Investigating"
          }
          data={models}
        />
      </div>
    </div>
  );
}
