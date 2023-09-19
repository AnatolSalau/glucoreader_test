package by.delfihealth.salov.glucoreader_test.web.handler;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class JsonWebSocketHandler extends TextWebSocketHandler implements SubProtocolCapable {

      private static final Logger logger = LoggerFactory.getLogger(JsonWebSocketHandler.class);

      private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

      @Autowired
      private ComPortService comPortService;

      @Autowired
      ObjectMapper objectMapper = new ObjectMapper();

      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            logger.info("Server connection opened");
            sessions.add(session);
            List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDTO();
            JSONArray jsonArray = new JSONArray(serialPorts);
            JSONObject jsonComPorts = new JSONObject();
            jsonComPorts.put("comPortList", jsonArray);
            JSONObject jsonData = new JSONObject();
            jsonData.put("data", jsonComPorts);
            String jsonDataStr = jsonData.toString();
            System.out.println(jsonDataStr);
            session.sendMessage(new TextMessage(jsonDataStr));
            logger.info("Server onOpen message: {}", jsonDataStr);
      }

      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            logger.info("Server connection closed: {}", status);
            sessions.remove(session);
      }

      @Scheduled(fixedRate = 1000)
      void sendPeriodicMessages() throws IOException {
            List<SerialPortDTO> currentSerialPortsDTO = comPortService.getCurrentSerialPortsDTO();
            List<SerialPortDTO> newSerialPortsDTO = comPortService.findAllSerialPortsDTO();
            for (WebSocketSession session : sessions) {
                  if (session.isOpen()) {
                        if (currentSerialPortsDTO.equals(newSerialPortsDTO) == false) {
                              List<SerialPortDTO> serialPorts = comPortService.findAllSerialPortsDTO();
                              JSONArray jsonArray = new JSONArray(serialPorts);
                              JSONObject jsonComPorts = new JSONObject();
                              jsonComPorts.put("comPortList", jsonArray);
                              JSONObject jsonData = new JSONObject();
                              jsonData.put("data", jsonComPorts);
                              String jsonDataStr = jsonData.toString();
                              session.sendMessage(new TextMessage(jsonDataStr));
                        }
                  }
            }
            comPortService.setCurrentSerialPortsDTO(newSerialPortsDTO);
      }

      @Override
      public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String request = message.getPayload();
            logger.info("Server received: {}", request);
            comPortService.openComPort(request, 19200, 8,
                  1, 2);

            comPortService.closeComport();
/*            System.out.println("-------------------------------------");
            System.out.println(protocolVersion);
            System.out.println(protocolVersion);
            System.out.println("-------------------------------------");*/
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
