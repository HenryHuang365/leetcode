#include <WiFiS3.h>       // Include the WiFi library for connecting to WiFi networks
#include <PubSubClient.h> // Include the PubSubClient library for MQTT communication
#include <ArduinoJson.h>  // Include the ArduinoJson library for handling JSON data
#include "sparkfun.h"     // Include the SparkFun library for the AS7265x spectral sensor

const int LED_PIN = 13; // Define the pin number for the LED

// Uncomment and define topics for MQTT communication
// char subTopic[] = "/v1.6/devices/mqttwizard/button/lv"; // Topic for button input
// char pubTopic[] = "/v1.6/devices/mqttwizard/received_text"; // Topic for publishing LED state
// char cmdTopic[] = "/v1.6/devices/mqttwizard/command"; // Topic for receiving commands
// char rTopic[] = "/v1.6/devices/mqttwizard/R";
// char gTopic[] = "/v1.6/devices/mqttwizard/G";
// char bTopic[] = "/v1.6/devices/mqttwizard/B";
// char lTopic[] = "/v1.6/devices/mqttwizard/L";
// char vTopic[] = "/v1.6/devices/mqttwizard/V";

WiFiClient wifiClient;           // Create a WiFi client object
PubSubClient client(wifiClient); // Create an MQTT client object using the WiFi client
char msg[50];                    // Buffer to store outgoing messages
int value = 0;                   // Integer to store sensor values
int ledState = 0;                // Variable to store the LED state
int status = WL_IDLE_STATUS;     // Variable to store WiFi connection status

void setup()
{
    pinMode(LED_PIN, OUTPUT);           // Set the LED pin as an output
    Serial.begin(9600);                 // Initialize serial communication at 9600 baud
    std::cout << "hi" << std::endl;     // Print a message to the console
    setup_wifi();                       // Call the function to set up the WiFi connection
    Serial.println("WIFI SUCCESSFUL");  // Print a message indicating WiFi setup success
    client.setServer(mqttServer, 1883); // Set the MQTT server and port
    client.setCallback(callback);       // Set the callback function for MQTT messages
    setup_device();                     // Call the function to set up the device (sensor)
}

void loop()
{
    // Main loop
    if (!client.connected())
    {                // Check if the MQTT client is connected
        reconnect(); // If not, attempt to reconnect
    }
    client.loop(); // Process incoming MQTT messages
}

void setup_device()
{
    // Setup function for the device (sensor)
    // pinMode(redPin, OUTPUT);
    // pinMode(greenPin, OUTPUT);
    // pinMode(bluePin, OUTPUT);
    Serial.println("AS7265x Spectral Triad Test"); // Print a message indicating the sensor test

    while (sensor.begin() == false)
    { // Check if the sensor is detected
        Serial.println("AS7265x not detected. Please check wiring. Freezing...");
        delay(1000); // Wait for 1 second before checking again
    }
    Serial.println("AS7265x detected!"); // Print a message indicating the sensor is detected
    sensor.disableIndicator();           // Disable the sensor's indicator LED
}

void setup_wifi()
{
    // Initialize serial and wait for port to open
    Serial.println();
    Serial.print("Connecting to ");
    Serial.println(ssid); // Print the SSID of the network to connect to

    // Check for the WiFi module
    if (WiFi.status() == WL_NO_MODULE)
    {
        Serial.println("Communication with WiFi module failed!");
        while (true)
            ; // If the module is not found, halt execution
    }

    String fv = WiFi.firmwareVersion();
    if (fv < WIFI_FIRMWARE_LATEST_VERSION)
    {
        Serial.println("Please upgrade the firmware"); // Suggest firmware upgrade if needed
    }

    // Attempt to connect to WiFi network
    while (status != WL_CONNECTED)
    {
        Serial.print("Attempting to connect to SSID: ");
        Serial.println(ssid);
        status = WiFi.begin(ssid, pass); // Connect to the network using SSID and password
        delay(10000);                    // Wait for 10 seconds for connection
    }

    printWifiStatus(); // Call the function to print WiFi status information
}

void printWifiStatus()
{
    // Print the SSID of the network you're attached to
    Serial.print("SSID: ");
    Serial.println(WiFi.SSID());

    // Print your board's IP address
    IPAddress ip = WiFi.localIP();
    Serial.print("IP Address: ");
    Serial.println(ip);

    // Print the received signal strength
    long rssi = WiFi.RSSI();
    Serial.print("signal strength (RSSI):");
    Serial.print(rssi);
    Serial.println(" dBm");
}

void callback(char *topic, byte *payload, unsigned int length)
{
    // Callback function to handle incoming MQTT messages
    Serial.print("Message arrived [");
    Serial.print(topic);
    Serial.print("] ");
    for (int i = 0; i < length; i++)
    {
        Serial.print((char)payload[i]); // Print the incoming message
    }
    Serial.println();

    if (strcmp(topic, subTopic) == 0)
    {
        // Handle button trigger
        if ((char)payload[0] == '1')
        {
            digitalWrite(LED_PIN, HIGH); // Turn on the LED if payload is '1'
            ledState = 1;                // Update LED state
        }
        else
        {
            digitalWrite(LED_PIN, LOW); // Turn off the LED if payload is not '1'
            ledState = 0;               // Update LED state
        }
        client.publish(pubTopic, ledState == 1 ? "1" : "0"); // Publish the LED state
    }
    else if (strcmp(topic, cmdTopic) == 0)
    {
        // Handle manual input command
        Serial.println("RECEIVED FROM MANUAL INPUT");
        StaticJsonDocument<200> doc;                                // Create a JSON document
        DeserializationError error = deserializeJson(doc, payload); // Deserialize JSON payload

        if (error)
        {
            Serial.print("deserializeJson() failed: ");
            Serial.println(error.c_str()); // Print error message if deserialization fails
            return;
        }

        // Extract the 'cmd' key from the JSON payload
        const char *cmd = doc["context"]["cmd"];

        // Execute based on the cmd value
        if (cmd != nullptr)
        {
            if (strcmp(cmd, "t") == 0)
            {
                Serial.println("Do the test");
                for (int i = 0; i < 1; i++)
                {
                    test('r'); // Perform the test function with 'r' parameter
                               // test('g');
                               // test('b');
                               // test('0');
                }
                Serial.println("FINISH");
            }
            else if (strcmp(cmd, "h") == 0)
            {
                Serial.println("Do the h");
            }
            // Add more commands as needed
        }
    }
}

void reconnect()
{
    // Function to handle MQTT reconnection
    while (!client.connected())
    {
        Serial.print("Attempting MQTT connection...");
        if (client.connect("ArduinoClient", mqttUsername, mqttPassword))
        {
            Serial.println("connected"); // Print message if connected
            client.subscribe(subTopic);  // Subscribe to the button topic
            client.subscribe(cmdTopic);  // Subscribe to the command topic
        }
        else
        {
            Serial.print("failed, rc=");
            Serial.print(client.state()); // Print the client state if connection fails
            Serial.println(" try again in 5 seconds");
            delay(5000); // Wait for 5 seconds before trying again
        }
    }
}