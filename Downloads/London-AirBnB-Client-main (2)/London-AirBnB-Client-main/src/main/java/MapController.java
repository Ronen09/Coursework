import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController extends GenericController implements Initializable {
    @FXML
    private Pane p;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPriceBoxes();



        // Adding listeners to boxes
        From_Price_Range.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawNewMap();
            }
        });

        To_Price_Range.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                drawNewMap();
            }
        });

        drawNewMap();


    }

    private void drawNewMap(){
        p.getChildren().clear();
        HexTiling h = new HexTiling();
        h.drawTiling(p, 350, 50, 10, 7, 7);
    }
}
