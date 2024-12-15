"use client";

import {
  NextIntlClientProvider,
  useLocale,
  useMessages,
  useTranslations,
} from "next-intl";
import { Button } from "../ui/button";
import { TranslationKeys } from "@/gen/translations.gen";
import { useContext, useState } from "react";
import { Loaders } from "../ui/loaders";
import { ImageHandler } from "./utils/image-handler";
import {
  getLoaderElement,
  getSiteMapElement,
  getVisualisationsElement,
} from "./utils/export-element-getter";
import { ZipHandler } from "../../lib/files/zip-handler";
import { pdf } from "@react-pdf/renderer";
import saveAs from "file-saver";
import { MonitorReportPDF } from "./monitor-report-pdf";
import { useAppDispatch, useAppSelector } from "@/store/hooks";
import {
  GraphLoadingStage,
  selectCsvExportData,
  selectExportSelectedDeviceNames,
  selectExportDeviceGroup,
  selectExportEndDate,
  selectExportStartDate,
  selectGraphLoadingStage,
  setIsExporting,
  selectExportAllDeviceNames,
} from "@/lib/export/exportSlice";
import CSVHandler from "./utils/csv-handler";
import { selectAuthUser } from "@/lib/auth/authSlice";
import { AuthUser, getUserFullName } from "@/lib/auth/auth";
import { pdfDateFormatter } from "@/lib/utils";
import { selectCurrentSite } from "@/lib/site/siteSlice";
import { Site } from "@/lib/site/site";
import { DialogContext } from "../dialog/dialog-context";
import { AlertDialog } from "../ui/alert-dialog";
import { selectInterval } from "@/lib/intervalRangeSlice";

function replaceSlashes(input: string): string {
  return input.replace(/\//g, "__");
}

export function GlobalDownloadButton() {
  const t = useTranslations();
  const [loading, setLoading] = useState(false);
  const csvExportData = useAppSelector(selectCsvExportData);
  const [downloadProgress, setDownloadProgress] = useState(0);
  const currentSite: Site | null = useAppSelector(selectCurrentSite);
  const graphLoadingStage = useAppSelector(selectGraphLoadingStage);
  const dialogManager = useContext(DialogContext).manager;
  const dispatch = useAppDispatch();

  const downloadAll = async (): Promise<void> => {
    if (getLoaderElement() !== null) return;

    const zipHandler = new ZipHandler();
    const imageHandler = new ImageHandler({
      quality: 0.95,
      backgroundColor: "#F4F4F5",
      skipFonts: true,
    });

    const siteMapFileName = `${t(
      TranslationKeys.exportSiteMapImageFileName
    )}.jpg`;
    const zipFileName = `${t(
      TranslationKeys.exportArchiveFileName
    )} - ${new Date().toLocaleString()}.zip`;

    try {
      // Get Site Map Image
      const siteMapElement = getSiteMapElement(true);
      if (!siteMapElement) {
        dialogManager.openDialog({
          child: (
            <AlertDialog
              title={t(TranslationKeys.globalButtonMapAlertTitle)}
              description={t(TranslationKeys.globalButtonMapAlertDescription)}
            />
          ),
        });
        return;
      }

      // Get Visualisation Images
      const visualisationElements = getVisualisationsElement();
      if (!visualisationElements) {
        dialogManager.openDialog({
          child: (
            <AlertDialog
              title={t(TranslationKeys.globalButtonGraphAlertTitle)}
              description={t(TranslationKeys.globalButtonGraphAlertDescription)}
            />
          ),
        });
        return;
      }

      const totalFiles =
        1 + visualisationElements.length + (csvExportData?.length ?? 0);
      let currentFileGenerated = 0;

      // Add Site Map Image to Zip
      const siteMapDataUrl = await imageHandler.getImageDataUrl(siteMapElement);
      zipHandler.addBase64ToZip(siteMapDataUrl, siteMapFileName);
      currentFileGenerated++;
      setDownloadProgress(currentFileGenerated / totalFiles);

      // Add Visualisation Images to Zip
      for (const elementHolder of visualisationElements) {
        const element = elementHolder.getElement();
        // Change Style
        elementHolder.changeStyleForExport();

        // Get Image
        const dataUrl = await imageHandler.getImageDataUrl(element);
        zipHandler.addBase64ToZip(dataUrl, `${replaceSlashes(element.id)}.jpg`);

        // Change Style Back
        elementHolder.changeStyleBack();

        currentFileGenerated++;
        setDownloadProgress(currentFileGenerated / totalFiles);
      }

      // Generate CSV
      const csvHandler = new CSVHandler(csvExportData ?? []);
      csvHandler.generateCSVFiles().forEach((file) => {
        zipHandler.addFileToZip(file.content, replaceSlashes(file.filename));
        currentFileGenerated++;
        setDownloadProgress(currentFileGenerated / totalFiles);
      });

      zipHandler.triggerDownload(zipFileName);
    } catch (error) {
      console.error("Failed to download images:", error);
      const errorMessage =
        error instanceof Error
          ? error.message
          : t(TranslationKeys.exportUnknownError);
      dialogManager.openDialog({
        child: (
          <AlertDialog
            title={t(TranslationKeys.globalButtonUnknownAlertTitle)}
            description={t(
              TranslationKeys.globalButtonUnknownAlertDescription,
              {
                error: errorMessage,
              }
            )}
          />
        ),
      });
    }
  };

  return (
    <>
      <Button
        variant="tertiary"
        disabled={
          loading ||
          currentSite === null ||
          (graphLoadingStage !== GraphLoadingStage.Loaded &&
            graphLoadingStage !== GraphLoadingStage.NotShown)
        }
        onClick={() => {
          setDownloadProgress(0);
          setLoading(true);
          dispatch(setIsExporting(true));
          downloadAll().finally(() => {
            setLoading(false);
            dispatch(setIsExporting(false));
          });
        }}
      >
        <Loaders.progress
          fractionValue={downloadProgress}
          loading={loading}
          className="mr-2"
        />
        {t(TranslationKeys.navbarDownloadButton)}
      </Button>
    </>
  );
}

export function GlobalReportButton() {
  const t = useTranslations();
  const [loading, setLoading] = useState(false);
  const locale = useLocale();
  const messages = useMessages();
  const [downloadProgress, setDownloadProgress] = useState(0);
  const dispatch = useAppDispatch();

  // Fetch Required Data
  const authUser: AuthUser | null = useAppSelector(selectAuthUser);
  const startDate: string = useAppSelector(selectExportStartDate);
  const endDate: string = useAppSelector(selectExportEndDate);
  const averageInterval = useAppSelector(selectInterval);
  const currentSite: Site | null = useAppSelector(selectCurrentSite);
  const graphLoadingStage = useAppSelector(selectGraphLoadingStage);
  const selectedDeviceNames = useAppSelector(selectExportSelectedDeviceNames);
  const allDeviceNames = useAppSelector(selectExportAllDeviceNames);
  const deviceGroup = useAppSelector(selectExportDeviceGroup);
  const dialogManager = useContext(DialogContext).manager;

  // const movingAverage = useAppSelector(selectMovingAverage); TODO: Add Moving Average

  const handleDownload = async () => {
    if (getLoaderElement() !== null) return;

    const fileName = `${t(
      TranslationKeys.exportReportFileName
    )} - ${new Date().toLocaleString()}.pdf`;

    const imageHandler = new ImageHandler({
      quality: 0.95,
      backgroundColor: "#FFFFFF",
      skipFonts: true,
    });

    // Get Site Map Image
    const siteMapElement = getSiteMapElement(false);
    if (!siteMapElement) {
      return;
    }

    // Get Visualisation Images
    const visualisationElements = getVisualisationsElement();
    if (!visualisationElements) {
      return;
    }

    // Initialize the progress
    const totalFiles = 1 + visualisationElements.length;
    let currentFileGenerated = 0;

    // Get Site Map Image
    const siteMapDataUrl = await imageHandler.getImageDataUrl(siteMapElement);
    if (!siteMapDataUrl) {
      dialogManager.openDialog({
        child: (
          <AlertDialog
            title={t(TranslationKeys.globalButtonMapAlertTitle)}
            description={t(TranslationKeys.globalButtonMapAlertDescription)}
          />
        ),
      });
      return;
    }
    currentFileGenerated++;
    setDownloadProgress(currentFileGenerated / totalFiles);

    // Get Visualisation Images
    const plotUrls = [];
    for (const elementHolder of visualisationElements) {
      if (!elementHolder.isTable()) {
        const element = elementHolder.getElement();
        // Get Image
        const dataUrl = await imageHandler.getImageDataUrl(element);
        plotUrls.push({ label: element.id, url: dataUrl });
      }
      currentFileGenerated++;
      setDownloadProgress(currentFileGenerated / totalFiles);
    }

    if (!plotUrls) {
      dialogManager.openDialog({
        child: (
          <AlertDialog
            title={t(TranslationKeys.globalButtonGraphAlertTitle)}
            description={t(TranslationKeys.globalButtonGraphAlertDescription)}
          />
        ),
      });
      return;
    }

    const pdfInput = {
      dateStart: startDate,
      dateEnd: endDate,
      averageInterval: averageInterval[0],
      mapUrl: siteMapDataUrl,
      plotUrls: plotUrls,
      siteName: currentSite?.siteName ?? "",
      generatedBy: getUserFullName(authUser),
      deviceGroup: deviceGroup,
      deviceList:
        selectedDeviceNames.length > 0 ? selectedDeviceNames : allDeviceNames,
      // movingAverage: t(getMovingAverageTranslationKey(movingAverage)), TODO: Add Moving Average
      reportDate: pdfDateFormatter(new Date()),
      siteId: currentSite?.siteId ?? "",
    };

    const blob = await pdf(
      <NextIntlClientProvider locale={locale} messages={messages}>
        <MonitorReportPDF {...pdfInput} />
      </NextIntlClientProvider>
    ).toBlob();
    saveAs(blob, fileName);
  };

  return (
    <>
      <Button
        variant="tertiary"
        disabled={
          loading ||
          currentSite === null ||
          (graphLoadingStage !== GraphLoadingStage.Loaded &&
            graphLoadingStage !== GraphLoadingStage.NotShown)
        }
        onClick={() => {
          setDownloadProgress(0);
          setLoading(true);
          dispatch(setIsExporting(true));
          handleDownload().finally(() => {
            setLoading(false);
            dispatch(setIsExporting(false));
          });
        }}
      >
        <Loaders.progress
          fractionValue={downloadProgress}
          loading={loading}
          className="mr-2"
        />
        {t(TranslationKeys.navbarReportButton)}
      </Button>
    </>
  );
}
