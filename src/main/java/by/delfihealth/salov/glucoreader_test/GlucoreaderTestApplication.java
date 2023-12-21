package by.delfihealth.salov.glucoreader_test;

import ch.micheljung.fxwindow.FxStage;
import ch.micheljung.waitomo.WaitomoTheme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

@SpringBootApplication
public class GlucoreaderTestApplication extends Application {

      //Spring
      private ConfigurableApplicationContext configurableApplicationContext;

      //JavaFx
      private Stage primaryStage;

      private Scene scene;

      private BorderPane root;

      //Initialize spring, load resources before start, and run window root
      @Override
      public void init() throws Exception {
            //Run Spring context
            configurableApplicationContext = SpringApplication.run(GlucoreaderTestApplication.class);

            //Get fxml
            FXMLLoader windowFXML = new FXMLLoader(
                  getClass().getResource("/fxml/window.fxml")
            );

            //load JavaFX controllers which load like beans from context
            windowFXML.setControllerFactory(configurableApplicationContext::getBean);

            //Load resources
            //..
      }

      //Set window stage settings and show it
      @Override
      public void start(Stage primaryStage) throws Exception {
            primaryStage.setOpacity(0.0);

            //Fxml resources
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/window.fxml")));

            String windowCss = Objects.requireNonNull(getClass().getResource("/fxml/window.css")).toExternalForm();
            Image titleImage = new Image("/png/iconWindows.png");

            FxStage.configure(primaryStage)
                  .withContent(root)
                  .apply();

            scene = primaryStage.  getScene();

            WaitomoTheme.apply(scene);

            scene.getStylesheets().add(windowCss);

            primaryStage.getIcons().add(titleImage);
            primaryStage.setTitle("GR Test");

            primaryStage.show();
            Thread.sleep(100);;
            primaryStage.setOpacity(1.0);
            this.primaryStage = primaryStage;
      }

      @Override
      public void stop() throws Exception {
            configurableApplicationContext.close();
      }

      public static void main(String[] args) {
            Application.launch(args);
      }
}
