package by.delfihealth.salov.glucoreader_test.web.config;

import by.delfihealth.salov.glucoreader_test.web.ws.JsonWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableScheduling
public class WebSocketConfig implements WebSocketConfigurer {

      @Override
      public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry
                  .addHandler(webSocketHandler(), "/websocket")
                  .setAllowedOrigins("*");
      }

      @Bean
      public WebSocketHandler webSocketHandler() {
            return new JsonWebSocketHandler();
      }

      @Bean
      public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                  @Override
                  public void addCorsMappings(CorsRegistry registry){
                        registry.addMapping("/**")
                              .allowedOrigins("*");
                  }
            };
      }
}
