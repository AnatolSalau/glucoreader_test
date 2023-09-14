package by.delfihealth.salov.glucoreader_test.web.handler;

import by.delfihealth.salov.glucoreader_test.comport.dto.SerialPortDTO;
import by.delfihealth.salov.glucoreader_test.comport.model.HexByteData;
import by.delfihealth.salov.glucoreader_test.comport.services.ComPortService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.time.LocalTime;
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
            String serialPortsStr = objectMapper.writeValueAsString(serialPorts);
            TextMessage message = new TextMessage(serialPortsStr);
            logger.info("Server sends: {}", message);
            session.sendMessage(message);
      }

      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            logger.info("Server connection closed: {}", status);
            sessions.remove(session);
      }

      @Scheduled(fixedRate = 5000)
      void sendPeriodicMessages() throws IOException {
            for (WebSocketSession session : sessions) {
                  if (session.isOpen()) {
                        String broadcast = "server periodic message " + LocalTime.now();
                        List<SerialPortDTO> currentSerialPortsDTO = comPortService.getCurrentSerialPortsDTO();
                        List<SerialPortDTO> newSerialPortsDTO = comPortService.findAllSerialPortsDTO();
                        if (currentSerialPortsDTO.equals(newSerialPortsDTO)) {
                              System.out.println("ComPortList are equals");
                              System.out.println("currentSerialPortsDTO : " + currentSerialPortsDTO);
                              System.out.println("newSerialPortsDTO : " + newSerialPortsDTO);
                              System.out.println();
                              return;
                        }
                        System.out.println("ComPortList are different");
                        System.out.println("currentSerialPortsDTO : " + currentSerialPortsDTO);
                        System.out.println("newSerialPortsDTO : " + newSerialPortsDTO);
                        System.out.println();
                        String serialPortsStr = objectMapper.writeValueAsString(newSerialPortsDTO);
                        //System.out.println("SerialPorts : " + serialPortsStr);
                        //logger.info("Server sends: {}", serialPortsStr);
                        session.sendMessage(new TextMessage(serialPortsStr));
                  }
            }
      }

      @Override
      public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String request = message.getPayload();
            logger.info("Server received: {}", request);

            comPortService.openComPort(request, 19200, 8,
                  1, 2);
            List<HexByteData> protocolVersion = comPortService.getProtocolVersion();
            System.out.println("-------------------------------------");
            System.out.println(protocolVersion);
            System.out.println("-------------------------------------");

/*            String response = String.format("response from server to '%s'", HtmlUtils.htmlEscape(request));
            logger.info("Server sends: {}", response);
            session.sendMessage(new TextMessage(response));*/
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
