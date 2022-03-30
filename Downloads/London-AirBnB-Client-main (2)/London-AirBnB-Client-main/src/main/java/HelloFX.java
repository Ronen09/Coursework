import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class HelloFX extends Application{
    
    AirbnbDataLoader loader = new AirbnbDataLoader();
    @Override
    public void start(Stage stage) throws Exception {

        Controller controller = new Controller();
        URL url = new File("src/main/resources/fxml/WelcomePage.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(url);
        loader.setController(controller);

        Scene scene = new Scene(root, 1440, 810);
        stage.setScene(scene);

        stage.setResizable(false);
        stage.setTitle("Welcome!");
        
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
        

}