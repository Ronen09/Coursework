import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Table for displaying the no. of properties
 */
public class PropertyListController implements Initializable {

    private String[] choices = {"Host name", "Price", "No. of reviews"};
    @FXML
    public ChoiceBox sortBy;

    @FXML
    private TableView<PropertyModel> properties;
    @FXML
    public TableColumn<PropertyModel, String> hostName;
    @FXML
    public TableColumn<PropertyModel, Integer> price;
    @FXML
    public TableColumn<PropertyModel, Integer> reviews;
    @FXML
    public TableColumn<PropertyModel, Integer> mnights;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        properties.getItems().clear();

        sortBy.setItems(FXCollections.observableArrayList(choices));

        sortBy.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(choices[newValue.intValue()] + " selected");

                properties.getSortOrder().clear();

                switch(newValue.intValue()){
                    case 0:
                        hostName.setSortType(TableColumn.SortType.ASCENDING);
                        properties.getSortOrder().add(hostName);
                        break;
                    case 1:
                        price.setSortType(TableColumn.SortType.ASCENDING);
                        properties.getSortOrder().add(price);
                        break;
                    case 2:
                        reviews.setSortType(TableColumn.SortType.DESCENDING);
                        properties.getSortOrder().add(reviews);
                        break;
                    default:
                        break;
                }
                properties.sort();

            }
        });

        hostName.setCellValueFactory(new PropertyValueFactory<>("Host_Name"));
        price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        reviews.setCellValueFactory(new PropertyValueFactory<>("Reviews"));
        mnights.setCellValueFactory(new PropertyValueFactory<>("MinNights"));

        properties.setItems(returnProperties());

        /**
         * Obtained from SO
         * https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx
         * 26/03/2022
         */
        properties.setRowFactory( tv -> {
            TableRow<PropertyModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {

                    PropertyModel rowData = row.getItem();
                    String uniqueID = rowData.getId();
                    ClickedOn.setCurrentListing(FilteredAvailableProps.returnSpecificProp(ClickedOn.getCurrentClickedString(), uniqueID));

                    try {
                        URL url = new File("src/main/resources/fxml/PropertyDescription.fxml").toURI().toURL();
                        FXMLLoader loader = new FXMLLoader();
                        Parent root = loader.load(url);
                        Stage stage = new Stage();

                        stage.initModality(Modality.APPLICATION_MODAL);

                        stage.setTitle("Property owned by: " + rowData.getHost_Name());
                        stage.setScene(new Scene(root, 600, 400));

                        stage.setResizable(false);
                        stage.setX(200);
                        stage.setY(200);

                        stage.show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    /**
     * Returns an ObservableList of properties.
     * @return The list of properties we want.
     */
    public ObservableList<PropertyModel> returnProperties(){
        ObservableList<PropertyModel> p = FXCollections.observableArrayList();

        ArrayList<AirbnbListing> l = FilteredAvailableProps.returnPropsFromName(ClickedOn.getCurrentClickedString());
        if(l == null){
            return p;
        }
        for (AirbnbListing a : l){
            p.add(new PropertyModel(a.getId(), a.getHost_name(), a.getPrice(), a.getNumberOfReviews(), a.getMinimumNights()));
        }
        return p;
    }
}
