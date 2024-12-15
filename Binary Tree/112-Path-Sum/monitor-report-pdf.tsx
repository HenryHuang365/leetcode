"use client";

import React from "react";
import {
  Page,
  Text,
  View,
  Document,
  StyleSheet,
  Image as PDFImage,
  Font,
} from "@react-pdf/renderer";
import { useTranslations } from "next-intl";
import { TranslationKeys } from "@/gen/translations.gen";
import { Visibility } from "../ui/visiblity";

type MonitorReportPDFProps = {
  dateStart: string;
  dateEnd: string;
  averageInterval: string;
  mapUrl: string;
  plotUrls: Plot[];
  siteName: string;
  generatedBy: string;
  deviceGroup: string | null;
  deviceList: string[];
  // movingAverage: string; TODO: Add Moving Average
  reportDate: string;
  siteId: string;
};

type Plot = {
  url: string;
  label: string;
};

Font.register({
  family: "Inter",
  fonts: [
    { src: "/src/font/InterTight-Regular.ttf" },
    { src: "/src/font/InterTight-Bold.ttf", fontWeight: 700 },
  ],
});

const styles = StyleSheet.create({
  page: {
    padding: 20,
  },
  headerSection: {
    width: "100%",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "black",
    paddingVertical: 1,
    paddingHorizontal: 20,
  },
  header: {
    fontFamily: "Inter",
    fontSize: 15,
    fontWeight: 700,
    color: "white",
  },
  attributeLable: {
    fontSize: 11,
    fontFamily: "Inter",
    fontWeight: 700,
    color: "black",
  },
  attributeData: {
    fontSize: 11,
    color: "#696969",
    fontFamily: "Inter",
    fontWeight: 400,
  },
  figureLabel: {
    fontSize: 12,
    fontFamily: "Inter",
    fontWeight: 700,
    marginBottom: 10,
  },
});

export const MonitorReportPDF: React.FC<MonitorReportPDFProps> = ({
  dateStart,
  dateEnd,
  averageInterval,
  mapUrl,
  plotUrls,
  siteName,
  generatedBy,
  deviceGroup,
  deviceList,
  reportDate,
}) => {
  const t = useTranslations();
  return (
    <Document>
      <Page size="A4" style={styles.page}>
        <View style={styles.headerSection}>
          <PDFImage src={"/src/GHD-logo.png"} style={{ height: 30 }} />
          <Text style={styles.header}>
            {t(TranslationKeys.pdfHeaderDateLabel, { date: reportDate })}
          </Text>
          <Text style={styles.header}>
            {t(TranslationKeys.pdfHeaderSiteIDLabel, { name: siteName })}
          </Text>
        </View>
        <View style={{ marginTop: 30, flexDirection: "row" }}>
          <View style={{ width: "72%" }}>
            <PDFImage src={mapUrl} />
          </View>
          <View style={{ width: "3.5%" }} />
          <View
            style={{
              width: "24.5%",
              flexDirection: "column",
              gap: 10,
              flexWrap: "wrap",
            }}
          >
            <Attribute
              label={t(TranslationKeys.pdfDateLabel)}
              value={`${dateStart} - ${dateEnd}`}
            />
            <Attribute
              label={"Average Interval"}
              value={`${averageInterval}`}
            />
            <Attribute
              label={t(TranslationKeys.pdfSiteNameLabel)}
              value={siteName}
            />
            <Attribute
              label={t(TranslationKeys.pdfGeneratedByLabel)}
              value={generatedBy}
            />
            <Visibility isVisible={deviceGroup !== null}>
              <Attribute
                label={t(TranslationKeys.pdfDeviceGroupLabel)}
                value={deviceGroup ?? ""}
              />
            </Visibility>
            <Attribute
              label={t(TranslationKeys.pdfDeviceListLabel)}
              value={deviceList.join(", ")}
            />
            {/* <Attribute
                label={t(TranslationKeys.pdfMovingAverageLabel)}
                value={movingAverage}
              /> TODO: Add Moving Average */}
          </View>
        </View>
        <View key={`view-${plotUrls.length}`} style={{ marginTop: 30, flexDirection: "column" }}>
          {plotUrls.map((plot, index) => (
            <>
              <Figure
                key={`Figure-${index}`}
                index={index}
                label={plot.label}
                src={plot.url}
              />
            </>
          ))}
        </View>
      </Page>
    </Document>
  );
};

const Attribute = ({ label, value }: { label: string; value: string }) => (
  <Text style={styles.attributeData}>
    <Text style={styles.attributeLable}>{label}: </Text>
    {value}
  </Text>
);

const Figure = ({
  index,
  label,
  src,
}: {
  index: number;
  label: string;
  src: string;
}) => {
  const t = useTranslations();
  return (
    <View
      style={{ flexDirection: "column", width: "100%", marginBottom: 20 }}
      wrap={false}
    >
      <Text style={styles.figureLabel}>
        {t(TranslationKeys.pdfFigureLabel, { index: index + 1, label: label })}
      </Text>
      <PDFImage src={src} />
    </View>
  );
};
