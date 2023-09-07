package by.delfihealth.salov.glucoreader_test.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

      //register url path
      //STOMP — Simple Text Oriented Messaging Protocol
      @Override
      public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/ws").withSockJS();
      }

      @Override
      public void configureMessageBroker( MessageBrokerRegistry registry) {
            registry.setApplicationDestinationPrefixes( "/app") ;
            registry.enableSimpleBroker( "/topic") ;
      }
}
