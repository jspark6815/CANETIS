#define TRIGGER 5
#define ECHO    4
#include <ESP8266WiFi.h>
#include <WebSocketClient.h>

const char* ssid     = "KDW";
const char* password = "kimdaewoon";

char path[] = "/4";
char host[] = "s.canetis.tk:8080";

WebSocketClient webSocket;

WiFiClient client;

void setup() {
  Serial.begin(115200);
  
  pinMode(12, OUTPUT);
  pinMode(TRIGGER, OUTPUT);
  pinMode(ECHO, INPUT);
  delay(10);
  
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());
  delay(5000);
  
  if (client.connect("s.canetis.tk", 8080)) {
    Serial.println("Connected");
  } else {
    Serial.println("Connection failed.");
    while(1) {
      // Hang on failure
    }
  }

  webSocket.path = path;
  webSocket.host = host;
  if (webSocket.handshake(client)) {
    Serial.println("Handshake successful");
  } else {
    Serial.println("Handshake failed.");
    while(1) {
      // Hang on failure
    }  
  }
  
  String data;
  
  while (client.connected()) {
    webSocket.getData(data);
    if (data.length() > 0) {
      Serial.print("Received data: ");
      Serial.println(data);
      if (data.equals("1")){
        break;
      }
    }
  }
}

void loop() {
    long duration, distance;
    digitalWrite(TRIGGER, LOW);
    delayMicroseconds(2); 
    
    digitalWrite(TRIGGER, HIGH);
    delayMicroseconds(10); 
    
    digitalWrite(TRIGGER, LOW);
    duration = pulseIn(ECHO, HIGH);
    distance = (duration/2) / 29.1;
    
    if(distance < 100) {
      tone(12, 200);
      delay(100);
      noTone(12);
      delay(100);
    }
}
