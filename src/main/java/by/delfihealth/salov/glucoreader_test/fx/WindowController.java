package by.delfihealth.salov.glucoreader_test.fx;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class WindowController implements Initializable {

      @FXML
      private WebView webView;

      @FXML
      private Pane splashScreen;

      private String linkWindow = getClass().getResource(
            "/ui/build/index.html"
      ).toExternalForm();

      private static WebEngine engine;

      @Override
      public void initialize(URL location, ResourceBundle resources) {
            webView.setContextMenuEnabled(false);
            engine = webView.getEngine();
            engine.setJavaScriptEnabled(true);
            engine.load(linkWindow);
            playSplashScreenAnimation(splashScreen, 1000,500).play();
      }

      private PauseTransition playSplashScreenAnimation( Pane pane, double pauseDuration, double visibilityChangeDuration) {
            PauseTransition pause = new PauseTransition(Duration.millis(pauseDuration));
            pause.setOnFinished(e -> {
                  // Create the initial and final key frames
                  KeyValue initKeyValue = new KeyValue(pane.opacityProperty(), 1);
                  KeyFrame initFrame = new KeyFrame(Duration.ZERO, initKeyValue);
                  KeyValue endKeyValue = new KeyValue(pane.opacityProperty(), 0);
                  KeyFrame endFrame = new KeyFrame(Duration.millis(visibilityChangeDuration), endKeyValue);
                  // Create a Timeline object
                  Timeline timeline = new Timeline(initFrame, endFrame);
                  // Start the animation
                  timeline.play();
                  timeline.setOnFinished( j -> {
                        splashScreen.setVisible(false);
                  });

            });
            return pause;
      }

      public static WebEngine getEngine() {
            return engine;
      }
}
