import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/resources/fxml/lol.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        stage.setTitle("First Try");
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}