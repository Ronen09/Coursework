
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StatsController extends GenericController implements Initializable {

    int count;
    static StatsLogic[] items;

    //sets the fields for all labels
    @FXML
    private Label statTitle1;

    @FXML
    private Label statTitle2;

    @FXML
    private Label statTitle3;

    @FXML
    private Label statTitle4;

    @FXML
    private Label statistic1;

    @FXML
    private Label statistic2;

    @FXML
    private Label statistic3;

    @FXML
    private Label statistic4;

    @FXML
    private VBox vbox;

    @FXML
    private Button gen;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StatsController.generateStatsController();

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

                StatsController.generateStatsController();
                generate();
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
                StatsController.generateStatsController();
                generate();
            }

        });

        setPriceBoxes();
        generate();
    }


    // ======== GETS THE INFOO ========
    private static double avgRevProp(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        double totalrevs = 0;
        for(String s: holder.keySet()){
            for(AirbnbListing a: holder.get(s)){
                totalrevs += a.getNumberOfReviews();
            }
        }
        return totalrevs/FilteredAvailableProps.returnTotalListings();
    }

    private static int totalHomesAps(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        int totalhomes = 0;
        for(String s: holder.keySet()){
            for(AirbnbListing a: holder.get(s)){
                if(a.getRoom_type().equalsIgnoreCase("Entire home/apt")){
                    totalhomes++;
                }
            }
        }
        return totalhomes;
    }

    private static String expBorough(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        int highest = 0;
        String currentBorough = "";
        for(String s : holder.keySet()){
            for(AirbnbListing a : holder.get(s)){
                if(a.getPrice() * a.getMinimumNights() > highest){
                    highest = a.getPrice() * a.getMinimumNights();
                    currentBorough = a.getNeighbourhood();
                }
            }
        }
        return currentBorough;
    }

    private static String mostRevBor(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        int largest = 0;
        int boroughsum = 0;
        String neigh = "";
        for(String s: holder.keySet()){
            for(AirbnbListing a : holder.get(s)){
                boroughsum += a.getNumberOfReviews();
            }
            if(boroughsum > largest){
                largest = boroughsum;
                neigh = s;
            }
        }
        return neigh;
    }

    private static String highestAvgAvai(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        double highestavg = 0;
        double temp = 0;
        String neigh = "";

        for(String s: holder.keySet()){
            for(AirbnbListing a : holder.get(s)){
                temp += a.getNumberOfReviews();
            }
            if(temp/holder.get(s).size() > highestavg){
                highestavg = temp/holder.get(s).size();
                neigh = s;
            }
        }
        return neigh;
    }
    
    private static String popRoomType(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        int homesapt = 0;
        int privrooms = 0;

        for(String s: holder.keySet()){
            for(AirbnbListing a : holder.get(s)){
                if(a.getRoom_type().equalsIgnoreCase("Entire home/apt")){
                    homesapt++;
                }
                else{
                    privrooms++;
                }
            }

        }
        if(homesapt > privrooms){
            return "Entire home/apt";
        }
        else{
            return "Private room";
        }
    }

    private static String cheapBorough(){
        HashMap<String, ArrayList<AirbnbListing>> holder = FilteredAvailableProps.returnFullFilteredProps();
        double highest = 10000000;
        double temp = 0;
        String currentBorough = "";
        for(String s : holder.keySet()){
            for(AirbnbListing a : holder.get(s)){
                temp += a.getPrice();
            }
            if(temp / holder.get(s).size() < highest){
                highest = temp / holder.get(s).size();
                currentBorough = s;
            }
        }
        return currentBorough;
    }


    // ======== END ========

    public static void generateStatsController() {
        items = new StatsLogic[8];

        StatsLogic item1 = new StatsLogic("Average number of reviews per property", Float.toString(Math.round(avgRevProp()*100f)/100f));
        StatsLogic item2 = new StatsLogic("Total number of available properties", Integer.toString(FilteredAvailableProps.returnTotalListings()));
        StatsLogic item3 = new StatsLogic("No. of homes/apartments", Integer.toString(totalHomesAps()));
        StatsLogic item4 = new StatsLogic("The most expensive borough", expBorough());

        StatsLogic item5 = new StatsLogic("Most reviewed borough", mostRevBor());
        StatsLogic item6 = new StatsLogic("Highest average availability borough", highestAvgAvai());
        StatsLogic item7 = new StatsLogic("Most popular room type", popRoomType());
        StatsLogic item8 = new StatsLogic("Most affordable borough",cheapBorough());

        items[0] = item1;
        items[1] = item2;
        items[2] = item3;
        items[3] = item4;
        items[4] = item5;
        items[5] = item6;
        items[6] = item7;
        items[7] = item8;
    }

    //sets the methods used when each button is clicked

    @FXML
    void generate() {
        statTitle1.setText(items[0].returnTitle());
        statTitle2.setText(items[1].returnTitle());
        statTitle3.setText(items[2].returnTitle());
        statTitle4.setText(items[3].returnTitle());

        statistic1.setText(items[0].returnString());
        statistic2.setText(items[1].returnString());
        statistic3.setText(items[2].returnString());
        statistic4.setText(items[3].returnString());
    }

    @FXML
    void clear(){
        statTitle1.setText("");
        statTitle2.setText("");
        statTitle3.setText("");
        statTitle4.setText("");

        statistic1.setText("");
        statistic2.setText("");
        statistic3.setText("");
        statistic4.setText("");
    }

    @FXML
    void leftButton1(ActionEvent event) {
        statTitle1.setText(items[0].returnTitle());
        statistic1.setText(items[0].returnString());
    }

    @FXML
    void leftButton2(ActionEvent event) {
        statTitle2.setText(items[1].returnTitle());
        statistic2.setText(items[1].returnString());
    }

    @FXML
    void leftButton3(ActionEvent event) {

        statTitle3.setText(items[2].returnTitle());
        statistic3.setText(items[2].returnString());

    }

    @FXML
    void leftButton4(ActionEvent event) {
        statTitle4.setText(items[3].returnTitle());
        statistic4.setText(items[3].returnString());
    }

    @FXML
    void rightButton1(ActionEvent event) {
        statTitle1.setText(items[4].returnTitle());
        statistic1.setText(items[4].returnString());
    }

    @FXML
    void rightButton2(ActionEvent event) {
        statTitle2.setText(items[5].returnTitle());
        statistic2.setText(items[5].returnString());
    }

    @FXML
    void rightButton3(ActionEvent event) {
        statTitle3.setText(items[6].returnTitle());
        statistic3.setText(items[6].returnString());
    }

    @FXML
    void rightButton4(ActionEvent event) {
        statTitle4.setText(items[7].returnTitle());
        statistic4.setText(items[7].returnString());

    }



}
