import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * For functionality that might apply across all controllers.
 */

public class GenericController{

    // FOR ENTERING PRICE RANGE
    @FXML
    protected ComboBox<Integer> From_Price_Range;
    @FXML
    protected ComboBox<Integer> To_Price_Range;

    private PageArray pageArray = new PageArray();
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Parent root;

    @FXML
    protected Alert invalidPrice = new Alert(Alert.AlertType.WARNING);

    private static final int width = 1440;
    private static final int height = 810;

    @FXML
    public void forwardButton(ActionEvent event) throws IOException {
        loadNewScene(event, true);

    }
    @FXML
    public void backButton(ActionEvent event) throws IOException {
        loadNewScene(event, false);
    }

    private void loadNewScene(ActionEvent event, boolean isForward) throws IOException {

        String url1 = "src/main/resources/fxml/";
        String ext = ".fxml";
        URL url;

        if(isForward){
            url = new File(url1 + pageArray.returnNextPage()+ext).toURI().toURL();
        }
        else{
            url = new File(url1 + pageArray.returnLastPage()+ext).toURI().toURL();
        }
        Parent root = FXMLLoader.load(url);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, width, height);

        stage.setScene(scene);

        String temp = "";
        switch(pageArray.getCurrentPage()){
            case 0:
                temp = "Map of London";
                break;
            case 1:
                temp = "Statistics";
                break;
            case 2:
                temp = "Register your own listing";
                break;
            default:
                System.out.println("shouldn't be here");
                break;
        }

        stage.setTitle(temp);
        stage.show();
    }

    protected Integer[] generatePrices(){
        int step = 50;
        int maxprice = 7050;
        //MAX is 7000, step in 100?
        Integer[] holder = new Integer[maxprice/step];
        for(int i = 0; i< maxprice; i += step){
            holder[i/step] = i;
        }
        return holder;
    }

    public void setPriceBoxes(){
        invalidPrice.setContentText("Invalid price range set!");
        // Must be done for in
        From_Price_Range.setValue(Preferences.getFrom_Price());
        To_Price_Range.setValue(Preferences.getTo_Price());

        From_Price_Range.getItems().addAll(generatePrices());
        To_Price_Range.getItems().addAll(generatePrices());

        // Adding listeners to boxes
        From_Price_Range.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(From_Price_Range.getItems().get(newValue.intValue()) > Preferences.getTo_Price()){
                    invalidPrice.show();
                    From_Price_Range.setValue(Preferences.getFrom_Price());
                }
                else {
                    Preferences.setFrom_Price(From_Price_Range.getValue());
                    FilteredAvailableProps.FilteredAvailableProps();
                }
            }
        });

        To_Price_Range.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(To_Price_Range.getItems().get(newValue.intValue()) < Preferences.getFrom_Price()){
                    invalidPrice.show();
                    To_Price_Range.setValue(Preferences.getTo_Price());
                }
                else {
                    Preferences.setTo_Price(To_Price_Range.getValue());
                    FilteredAvailableProps.FilteredAvailableProps();
                }
            }
        });
    }
}
