#include <ESP8266WiFi.h>
#include <WebSocketClient.h>

const char* ssid     = "KDW";
const char* password = "kimdaewoon";

char path[] = "/2";
char host[] = "s.canetis.tk:8080";

WebSocketClient webSocket;

WiFiClient client;

void setup() {
  Serial.begin(115200);
  pinMode(12, OUTPUT);
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
      if (data.equals("0")){
        break;
      }
    }
  }

}

void loop() {
  String data;
  
  if (client.connected()) {
    webSocket.getData(data);
    if (data.length() > 0) {
      Serial.print("Received data: ");
      Serial.println(data);
      if (data.equals("16")){
        tone(12, 300);
        delay(1000);
        noTone(12);
      }
    }
    
  }
  else {
    Serial.println("Client disconneted.");
    while(1){
    }
  }
  delay(50);
}
