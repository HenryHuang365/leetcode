#ifndef SPARKFUN_H
#define SPARKFUN_H

// Include any necessary standard libraries
#include <Arduino.h>          // Include the Arduino core library
#include "SparkFun_AS7265X.h" // Include the SparkFun AS7265X spectral sensor library
#include <Wire.h>             // Include the Wire library for I2C communication
#include <WiFiS3.h>           // Include the WiFi library for connecting to WiFi networks
#include <PubSubClient.h>     // Include the PubSubClient library for MQTT communication
#include <ArduinoJson.h>      // Include the ArduinoJson library for handling JSON data

AS7265X sensor; // Create an instance of the AS7265X sensor

// WiFi and MQTT configuration
const char *ssid = "asus_wifi";                                   // SSID of the WiFi network
const char *pass = "dysgbqnwbb.1";                                // Password of the WiFi network
const char *mqttServer = "169.55.61.243";                         // IP address of the MQTT server
const char *mqttUsername = "BBUS-6ckJkf51394H9W87CVZtUVhdIKl32l"; // Username for MQTT server
const char *mqttPassword = "";                                    // Password for MQTT server (empty in this case)

const int arraySize = 5;  // Size of the sample arrays
int redPin = 11;          // Pin for controlling the red component of the LED
int greenPin = 10;        // Pin for controlling the green component of the LED
int bluePin = 9;          // Pin for controlling the blue component of the LED
const int analogPin = A0; // Analog pin for measuring voltage
const float Vcc = 5.0;    // Supply voltage to the Arduino
const float R1 = 100;     // Value of resistor R1 in ohms
const float R2 = 100;     // Value of resistor R2 in ohms

// MQTT topics for publishing and subscribing
char subTopic[] = "/v1.6/devices/mqttwizard/button/lv"; // Topic for button input
char pubTopic[] = "/v1.6/devices/mqttwizard/health";    // Topic for publishing health status
char cmdTopic[] = "/v1.6/devices/mqttwizard/command";   // Topic for receiving commands
char combined[] = "/v1.6/devices/mqttwizard/combined";  // Topic for combined data
char channels[] = "/v1.6/devices/mqttwizard/channels";  // Topic for channel data
char voltage[] = "/v1.6/devices/mqttwizard/voltage";    // Topic for voltage data
char red[] = "/v1.6/devices/mqttwizard/590nm-750nm";    // Topic for red light data
char green[] = "/v1.6/devices/mqttwizard/500nm-590nm";  // Topic for green light data
char blue[] = "/v1.6/devices/mqttwizard/380nm-500nm";   // Topic for blue light data

WiFiClient wifiClient;           // Create a WiFi client object
PubSubClient client(wifiClient); // Create an MQTT client object using the WiFi client

void test(char color); // Declare the test() function

// Map of channels to wavelengths
String channelMap[] = {"410", "435", "460", "485", "510", "535", "560", "585", "610", "645", "680", "705", "730", "760", "810", "860", "900", "940"};

// Channel sums calculation (commented out)
// channelSum[0] += sensor.getCalibratedA();
// channelSum[1] += sensor.getCalibratedB();
// channelSum[2] += sensor.getCalibratedC();
// channelSum[3] += sensor.getCalibratedD();
// channelSum[4] += sensor.getCalibratedE();
// channelSum[5] += sensor.getCalibratedF();
// channelSum[6] += sensor.getCalibratedG();
// channelSum[7] += sensor.getCalibratedH();
// channelSum[8] += sensor.getCalibratedI();
// channelSum[9] += sensor.getCalibratedJ();
// channelSum[10] += sensor.getCalibratedK();
// channelSum[11] += sensor.getCalibratedL();
// channelSum[12] += sensor.getCalibratedR();  // Red
// channelSum[13] += sensor.getCalibratedS();
// channelSum[14] += sensor.getCalibratedT();
// channelSum[15] += sensor.getCalibratedU();
// channelSum[16] += sensor.getCalibratedV();
// channelSum[17] += sensor.getCalibratedW();  // Blue

#endif // SPARKFUN_H