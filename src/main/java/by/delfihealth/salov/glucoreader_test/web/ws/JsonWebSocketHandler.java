package by.delfihealth.salov.glucoreader_test.web.ws;

import by.delfihealth.salov.glucoreader_test.comport.dto.ComPortDto;
import by.delfihealth.salov.glucoreader_test.comport.services.DeviceDTOService;
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

      @Autowired
      private DeviceDTOService deviceDTOService;

      List<ComPortDto> comPortDtoList = new ArrayList<>();
      
      @Override
      public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
            String newData = deviceDTOService.getDataJsonByPortDescription("ELTIMA");
            comPortDtoList = deviceDTOService.getComPortDtoListByPortDescription("ELTIMA");
            session.sendMessage(new TextMessage(newData));
      }
      /*

       */
      @Override
      public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
            logger.info("Server connection closed: {}", status);
            sessions.remove(session);
      }
      /*

       */
      @Scheduled(fixedRate = 1000)
      void sendPeriodicMessages() throws IOException {
            List<ComPortDto> newComPortDtoList = deviceDTOService
                  .getComPortDtoListByPortDescription("ELTIMA");
            for (WebSocketSession session : sessions) {
                  if (session.isOpen()) {
                        boolean equals = comPortDtoList.equals(newComPortDtoList);
                        if ( equals == false) {
                              String data = deviceDTOService.getDataJsonByPortDescription("ELTIMA");
                              session.sendMessage(new TextMessage(data));
                              System.out.println("Session - data was sent");
                        }
                  }
            }
            comPortDtoList = newComPortDtoList;
      }
      /*

       */
      @Override
      public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String request = message.getPayload();

            if(request.equals("getDeviceList")) {
                  String data = deviceDTOService.getDataJsonByPortDescription("ELTIMA");
                  session.sendMessage(new TextMessage(data));
            }
            if(request.equals("setCurrentDateTime")) {
                  deviceDTOService.setCurrentDateTime("ELTIMA", 19200, 8, 1, 2);
                  String data = deviceDTOService.getDataJsonByPortDescription("ELTIMA");
                  session.sendMessage(new TextMessage(data));
                  System.out.println("setCurrentDateTime");
            }
            if(request.startsWith("setConverterType")) {
                  String dataRequest = request.substring(24);
                  /*
                        deviceDTOService.setConverterType("ELTIMA", 19200, 8, 1, 2);
                        String data = deviceDTOService.getDataJsonByPortDescription("ELTIMA");
                        session.sendMessage(new TextMessage(data));
                  */
                  System.out.println("setConverterType : " + dataRequest);
            }
      }
      /*
                   
       */
      @Override
      public void handleTransportError(WebSocketSession session, Throwable exception) {
            logger.info("Server transport error: {}", exception.getMessage());
      }
      /*

       */
      @Override
      public List<String> getSubProtocols() {
            return Collections.singletonList("subprotocol.glucoreader.websocket");
      }
}
