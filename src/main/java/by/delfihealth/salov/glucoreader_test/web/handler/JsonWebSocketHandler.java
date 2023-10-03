package by.delfihealth.salov.glucoreader_test.web.handler;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.services.SerialPortDTOService;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import com.fazecast.jSerialComm.SerialPort;
import org.json.JSONArray;
import org.json.JSONObject;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class JsonWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

      private static final Logger logger = LoggerFactory.getLogger(JsonWebSocketHandler.class);

      private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

      private List<SerialPortDTO> serialPortsAfterConnectionEstablished = new ArrayList<>();

      @Autowired
      private ComPortService comPortService;

      @Autowired
      private SerialPortDTOService serialPortDTOService;

      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
            List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDTO();
            serialPortsAfterConnectionEstablished = serialPorts;
            String jsonDataStr = serialPortDTOService.convertSerialPortToJson(serialPorts);
            session.sendMessage(new TextMessage(jsonDataStr));
      }

      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            logger.info("Server connection closed: {}", status);
            sessions.remove(session);
      }

      @Scheduled(fixedRate = 1000)
      void sendPeriodicMessages() throws IOException {
            List<SerialPortDTO> newSerialPortsDTO = comPortService.findAllSerialPortsDTO();
            for (WebSocketSession session : sessions) {
                  if (session.isOpen()) {
                        if ( serialPortsAfterConnectionEstablished.equals(newSerialPortsDTO) == false) {
                              String jsonDataStr = serialPortDTOService.convertSerialPortToJson(newSerialPortsDTO);
                              session.sendMessage(new TextMessage(jsonDataStr));
                        }
                  }
            }
            serialPortsAfterConnectionEstablished = newSerialPortsDTO;
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
}
