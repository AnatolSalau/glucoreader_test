package by.delfihealth.salov.glucoreader_test.web.ws;

import by.delfihealth.salov.glucoreader_test.comport.dto.DeviceDto;
import by.delfihealth.salov.glucoreader_test.comport.services.DeviceDTOService;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import com.fazecast.jSerialComm.SerialPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class JsonWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

      private static final Logger logger = LoggerFactory.getLogger(JsonWebSocketHandler.class);

      private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

      private String dataJson = null;

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private DeviceDTOService deviceDTOService;

      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
            String newData = getDataJson();
            dataJson = newData;
            session.sendMessage(new TextMessage(newData));
      }

      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            logger.info("Server connection closed: {}", status);
            sessions.remove(session);
      }

      @Scheduled(fixedRate = 1000)
      void sendPeriodicMessages() throws IOException {
            String newData = getDataJson();
            for (WebSocketSession session : sessions) {
                  if (session.isOpen()) {

                        boolean equals = dataJson.equals(newData);

                        System.out.println(dataJson);
                        System.out.println(newData);
                        System.out.println(equals);
                        if ( equals == false) {
                              session.sendMessage(new TextMessage(newData));
                        }
                  }
            }
            dataJson = newData;
      }

      @Override
      public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String request = message.getPayload();
            logger.info("Server received: {}", request);
            SerialPort serialPortByName = comPortService.findSerialPortByName(request);
            comPortService.openComPort(serialPortByName, 19200, 8,
                  1, 2);

            comPortService.closeComport(serialPortByName);
      }

      @Override
      public void handleTransportError(WebSocketSession session, Throwable exception) {
            logger.info("Server transport error: {}", exception.getMessage());
      }

      @Override
      public List<String> getSubProtocols() {
            return Collections.singletonList("subprotocol.glucoreader.websocket");
      }

      private String getDataJson() {
            List<SerialPort> serialPorts = comPortService.findAllComPortsByDescriptionStartWith("ELTIMA");

            List<DeviceDto> deviceDtoListFromComportList = deviceDTOService.getDeviceDtoListFromComportList(
                  serialPorts, 19200, 8, 1, 2
            );

            String json = deviceDTOService.convertDeviceDtoToJson(deviceDtoListFromComportList);
            return json;
      }
}
