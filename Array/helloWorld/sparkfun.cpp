#include "SparkFun_AS7265X.h" // Include the SparkFun AS7265X spectral sensor library
#include <Wire.h>             // Include the Wire library for I2C communication
#include "sparkfun.h"         // Include the custom header file

// Function to test the spectral sensor under different colored lights
void test(char color)
{
    char rStr[8], gStr[8], bStr[8], vStr[8];     // Buffers to store string representations of the values
    StaticJsonDocument<128> doc;                 // Create a JSON document for general data
    StaticJsonDocument<128> vol;                 // Create a JSON document for voltage data
    StaticJsonDocument<128> dRed, dGreen, dBlue; // Create JSON documents for individual color data

    // Set the color of the LED and update the JSON document accordingly
    if (color == 'r')
    {
        setColor(255, 0, 0);               // Set LED to red
        doc["context"]["L"] = "Red Light"; // Update JSON document
    }
    else if (color == 'g')
    {
        setColor(0, 255, 0);                 // Set LED to green
        doc["context"]["L"] = "Green Light"; // Update JSON document
    }
    else if (color == 'b')
    {
        setColor(0, 0, 255);                // Set LED to blue
        doc["context"]["L"] = "Blue Light"; // Update JSON document
    }
    else if (color == '0')
    {
        setColor(0, 0, 0);                  // Turn off LED
        doc["context"]["L"] = "Dark Light"; // Update JSON document
    }
    delay(1000); // Wait for 1 second

    // Take measurements from the spectral sensor
    sensor.takeMeasurements();
    uint16_t red_s[arraySize] = {0};   // Array to store red values
    uint16_t blue_s[arraySize] = {0};  // Array to store blue values
    uint16_t green_s[arraySize] = {0}; // Array to store green values

    // Fill arrays with sensor readings
    for (int i = 0; i < arraySize; i++)
    {
        red_s[i] = sensor.getR();   // Get red value
        blue_s[i] = sensor.getB();  // Get blue value
        green_s[i] = sensor.getG(); // Get green value
        delay(100);                 // Wait for 100 ms between readings
    }

    // Calculate the sum of the sensor readings
    uint16_t r_sum = 0, b_sum = 0, g_sum = 0;
    for (int i = 0; i < arraySize; i++)
    {
        r_sum += red_s[i];
        b_sum += blue_s[i];
        g_sum += green_s[i];
    }

    // Calculate the average values
    float r_v = (float)r_sum / arraySize;
    float b_v = (float)b_sum / arraySize;
    float g_v = (float)g_sum / arraySize;

    // Measure the voltage
    float v_sum = 0.0;
    for (int i = 0; i < arraySize; i++)
    {
        int sensorValue = analogRead(analogPin);      // Read analog value
        float voltage = sensorValue * (Vcc / 1023.0); // Convert to voltage
        v_sum += voltage * ((R1 + R2) / R2);          // Adjust voltage based on resistor values
        delay(100);                                   // Wait for 100 ms between readings
    }
    float v = v_sum / arraySize; // Calculate average voltage

    // Convert the float values to strings
    dtostrf(r_v, 6, 2, rStr); // Convert red value to string
    dtostrf(g_v, 6, 2, gStr); // Convert green value to string
    dtostrf(b_v, 6, 2, bStr); // Convert blue value to string
    dtostrf(v, 6, 2, vStr);   // Convert voltage value to string

    // Update the JSON documents with the measured values and timestamp
    long timestamp = millis(); // Get the current time in milliseconds
    doc["value"] = "0";
    doc["context"]["R"] = r_v;
    doc["context"]["G"] = g_v;
    doc["context"]["B"] = b_v;
    doc["context"]["V"] = v;
    doc["context"]["timestamp"] = timestamp;
    vol["value"] = v;
    vol["context"]["timestamp"] = timestamp;
    dRed["value"] = r_v;
    dRed["context"]["timestamp"] = timestamp;
    dGreen["value"] = g_v;
    dGreen["context"]["timestamp"] = timestamp;
    dBlue["value"] = b_v;
    dBlue["context"]["timestamp"] = timestamp;

    // Serialize JSON and publish it
    char jsonBuffer[128], jsonV[64], jsonRed[64], jsonGreen[64], jsonBlue[64];
    serializeJson(doc, jsonBuffer);
    serializeJson(vol, jsonV);
    serializeJson(dRed, jsonRed);
    serializeJson(dGreen, jsonGreen);
    serializeJson(dBlue, jsonBlue);

    Serial.println(jsonBuffer); // Print the main JSON data

    // Publish all the data
    publishWithRetry(combined, jsonBuffer);
    delay(1000); // Wait for 1 second between publishes
    publishWithRetry(voltage, jsonV);
    delay(1000);
    publishWithRetry(red, jsonRed);
    delay(1000);
    publishWithRetry(green, jsonGreen);
    delay(1000);
    publishWithRetry(blue, jsonBlue);

    // Print the test results
    Serial.print("Test under ");
    Serial.print(color);
    Serial.println(" light");
    Serial.print("R: ");
    Serial.print(rStr);
    Serial.print(" G: ");
    Serial.print(gStr);
    Serial.print(" B: ");
    Serial.print(bStr);
    Serial.print(" V: ");
    Serial.println(vStr);
}

// Function to set the color of the LED
void setColor(int red, int green, int blue)
{
    analogWrite(redPin, red);     // Set red intensity
    analogWrite(greenPin, green); // Set green intensity
    analogWrite(bluePin, blue);   // Set blue intensity
}

// Function to publish data with retries
void publishWithRetry(const char *topic, const char *payload)
{
    while (!client.publish(topic, payload))
    {
        Serial.print("Failed to publish to ");
        Serial.print(topic);
        Serial.println(", retrying...");
        delay(500); // Wait for 500 ms before retrying
    }
    Serial.print("Successfully published to ");
    Serial.println(topic);
}

// Additional commented-out code and examples of other functions were removed for clarity