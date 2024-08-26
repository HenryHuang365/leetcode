"use client";

import { ReactNode, useEffect, useState } from "react";
import {
  useGetDeploymentsQuery,
  useGetDeviceQuery,
  useGetLatestMeasurementQuery,
  useUpdateDeviceDetailsMutation,
  useGetFilesByDeviceQuery,
} from "@/lib/api/apiSlice";
import { DataRow } from "@/components/ui/data-rows/data-row";
import { TranslationKeys } from "@/gen/translations.gen";
import { Button } from "@/components/ui/button";
import { useTranslations } from "next-intl";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { useToast } from "@/components/ui/use-toast";
import { Loaders } from "@/components/ui/loaders";
import { useAppSelector } from "@/store/hooks";
import { selectCurrentSite } from "@/lib/site/siteSlice";
import { DeviceStateSelect } from "./device-state-select";
import FormInputField from "../../form-input-field";
import { skipToken } from "@reduxjs/toolkit/query";
import { DeviceStates } from "@/lib/devices/device";
import {
  ACPermissions,
  ControlledAccess,
} from "@/components/access-control/controlled-access";
import * as moment from "moment";
import "moment-timezone";
import { Visibility } from "@/components/ui/visiblity";
import { FileRow } from "@/components/ui/data-rows/file-row";
import { FileData, downloadFile } from "@/lib/files/files";
import { selectToken } from "@/lib/auth/authSlice";
import { downloadBlob } from "@/lib/utils";

export interface DeviceDetailsSectionProps {
  deviceId: string;
  deploymentId: string;
}

export function DeviceDetailsSection({
  deviceId,
  deploymentId,
}: DeviceDetailsSectionProps): ReactNode {
  const t = useTranslations();
  const [editMode, setEditMode] = useState(false);

  return (
    <>
      <div className="flex flex-col gap-4">
        <div className="flex justify-between items-center">
          <span className="text-base font-semibold text-card-foreground">
            {t(TranslationKeys.deviceDetailsSectionLabel)}
          </span>
          <ControlledAccess permissions={[ACPermissions.editDevice]}>
            {!editMode && (
              <Button variant="ghost" onClick={() => setEditMode(true)}>
                {t(TranslationKeys.deviceDetailsEditBtn)}
              </Button>
            )}
          </ControlledAccess>
        </div>
        <DeviceDetailsForm
          deviceId={deviceId}
          deploymentId={deploymentId}
          editMode={editMode}
          setEditMode={setEditMode}
        />
      </div>
    </>
  );
}

const formSchema = z.object({
  state: z.coerce.string(),
  displayName: z.string().min(6).max(999),
});

interface DeviceDetailsFormProps {
  deviceId: string;
  deploymentId: string;
  editMode: boolean;
  setEditMode: (editMode: boolean) => void;
}

function DeviceDetailsForm({
  deviceId,
  deploymentId,
  editMode,
  setEditMode,
}: DeviceDetailsFormProps) {
  const t = useTranslations();
  const authToken = useAppSelector(selectToken);
  const currentSite = useAppSelector(selectCurrentSite);
  const { data: device, isFetching: fetchingDevice } =
    useGetDeviceQuery(deviceId);
  const { data: deployment } = useGetDeploymentsQuery(
    !currentSite
      ? skipToken
      : { SiteId: currentSite.siteId, DeploymentId: deploymentId }
  );
  const { data: measurement, isFetching: fetchingMeasurment } =
    useGetLatestMeasurementQuery(
      !currentSite || !deployment || deployment.length === 0
        ? skipToken
        : {
            deploymentId: deployment![0].deploymentId ?? "",
            siteId: currentSite!.siteId,
          }
    );
  const [updateDeviceDetails] = useUpdateDeviceDetailsMutation();

  const { data: calibrationFiles, isFetching: isFetchingCalibrationFiles } =
    useGetFilesByDeviceQuery(
      !currentSite
        ? skipToken
        : {
            siteId: currentSite.siteId,
            deviceId: deviceId,
          }
    );

  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
  });
  const { toast } = useToast();

  // Resets the form's default values on refetch
  useEffect(() => {
    if (device) {
      form.reset({
        state: device?.deviceInfo.state,
        displayName: device?.deviceInfo.displayName,
      });
    }
  }, [device, form, editMode]);

  async function onSubmit(
    data: z.infer<typeof formSchema>,
    e?: React.BaseSyntheticEvent
  ) {
    e?.preventDefault();
    setEditMode(false);
    await updateDeviceDetails({
      displayName: data.displayName,
      state: data.state as DeviceStates,
      deviceId,
    });

    toast({
      description: t(TranslationKeys.alertConfigSucceededDescription),
    });
  }

  return fetchingDevice || fetchingMeasurment ? (
    <Loaders.indicator size="medium" layout="row" />
  ) : (
    <Form {...form}>
      <form
        onSubmit={form.handleSubmit(onSubmit)}
        className="space-y-4 relative"
      >
        <FormInputField
          form={form}
          field="displayName"
          title={t(TranslationKeys.deviceDataRowTitleDisplayName)}
          editMode={editMode}
        />
        <FormField
          control={form.control}
          name="state"
          render={({ field }) => (
            <FormItem>
              <FormControl>
                <DataRow title={t(TranslationKeys.deviceDataRowTitleState)}>
                  {editMode ? (
                    <DeviceStateSelect
                      defaultValue={field.value}
                      onValueChange={(value) => field.onChange(value)}
                    />
                  ) : (
                    <p>{device!.deviceInfo.state}</p>
                  )}
                </DataRow>
              </FormControl>
              <FormDescription />
              <FormMessage />
            </FormItem>
          )}
        />
        <DataRow title={t(TranslationKeys.deviceDataRowTitleLastMeasuredAt)}>
          {formatTimestamp(measurement?.data[0]?.timestamp ?? "-")}
        </DataRow>
        {measurement?.parameters.map((p, index) => (
          <DataRow key={index} title={`${p.alias} (${p.unit})`}>
            {measurement?.data?.[0]?.[p.name] ?? "-"}
          </DataRow>
        ))}
        <DataRow title={t(TranslationKeys.deviceDataRowTitleSerial)}>
          {device?.deviceInfo.serialNumber ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleModel)}>
          {device?.modelInfo.modelName ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleHardwareRevision)}>
          {device?.deviceInfo.hardwareRevision ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleConfiguration)}>
          {device?.deviceInfo.configuration ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleManufacturingDate)}>
          {device?.deviceInfo.manufacturingDate ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleMPN)}>
          {device?.deviceInfo.manufacturePartNumber ?? "-"}
        </DataRow>
        <DataRow title={t(TranslationKeys.deviceDataRowTitleManufacturer)}>
          {device?.modelInfo.manufacturer ?? "-"}
        </DataRow>
        <DataRow
          title={t(TranslationKeys.calibrationFilesTitle)}
          data={
            !isFetchingCalibrationFiles && calibrationFiles?.length === 0
              ? "-"
              : undefined
          }
        >
          <Loaders.indicator
            loading={isFetchingCalibrationFiles}
            size="small"
            layout="row"
          />
          <Visibility isVisible={!isFetchingCalibrationFiles}>
            {calibrationFiles?.map((file: FileData, index) => (
              <FileRow
                key={index}
                fileName={file.fileName}
                download={async () => {
                  const { content } = await downloadFile(
                    currentSite!.siteId,
                    file,
                    authToken!,
                    deviceId
                  );
                  downloadBlob(content, file.fileName);
                }}
              />
            ))}
          </Visibility>
        </DataRow>
        {editMode && (
          <div className="flex my-3 gap-2">
            <Button type="submit">
              {t(TranslationKeys.deviceDetailsSaveBtn)}
            </Button>
            <Button
              type="reset"
              variant="outline"
              onClick={() => setEditMode(false)}
            >
              {t(TranslationKeys.deviceDetailsCancelBtn)}
            </Button>
          </div>
        )}
      </form>
    </Form>
  );
}

export const formatTimestamp = (timestamp: string) => {
  if (timestamp === "-") return timestamp;
  const userTimezone = moment.tz.guess();
  const localTime = moment.utc(timestamp).tz(userTimezone);
  const formattedTime =
    localTime.format("DD/MM/YYYY HH:mm:ss") + ` (GMT${localTime.format("Z")})`;

  return formattedTime;
};
