import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
public class Controller extends GenericController implements Initializable {
    @FXML
    private boolean hasFinished = false;
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML 
    private ComboBox<String> preference;



    @FXML 
    private DatePicker From_Date_Picker;
    @FXML
    private DatePicker To_Date_Picker;


    @FXML
    private Button submit_Button;
    private String[] preferences = {"Entire home", "One Room","Either"};
    @FXML 
    public void loginButtonClicked()
    {
        System.out.println("It works.");
    }

    private boolean enteredDate = false;
    private boolean enteredRoomType = false;
    private boolean enteredPrice = false;

    Alert a = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL arg0,ResourceBundle arg1)
    {
        // THIS MUST ONLY BE CALLED EXACTLY ONCE!
        try {
            BoroughAvailableProps.BoroughAvailableProps();
        }catch(Exception e){
            e.printStackTrace();
        }


        // Must be done for in
        From_Price_Range.getItems().addAll(generatePrices());
        To_Price_Range.getItems().addAll(generatePrices());

        preference.getItems().addAll(preferences);
    }

    // CHECKERS FOR EACH ENTRY
    public void checkDate(){
        if(From_Date_Picker.getValue() == null || To_Date_Picker.getValue() == null || From_Date_Picker.getValue().isAfter(To_Date_Picker.getValue())){
            System.out.println("You have not entered a valid date!");
            enteredDate = false;
        }
        else{
            enteredDate = true;
        }
    }

    public void checkRoomType(){
        if(preference.getValue() == null){
            System.out.println("You have not picked a valid room type!");
            enteredRoomType = false;
        }
        else{
            enteredRoomType = true;
        }
    }

    public void checkPrice(){
        if(From_Price_Range.getValue() == null || To_Price_Range.getValue() == null || From_Price_Range.getValue() > To_Price_Range.getValue()){
            System.out.println("Invalid prices chosen!");
            enteredPrice = false;
        }
        else{
            enteredPrice = true;
        }
    }
    @FXML
    public void checkAll(){
        checkDate();
        checkRoomType();
        checkPrice();

        if(!enteredDate || !enteredRoomType || !enteredPrice){
            String s1 = "The following must be done:";
            if (!enteredDate) {
                s1 = s1 + "\n Enter a valid date.";
            }
            if (!enteredRoomType) {
                s1 = s1 + "\n Enter a valid room type.";
            }
            if (!enteredPrice) {
                s1 = s1 + "\n Enter a valid price range.";
            }
            a.setContentText(s1);
            a.show();
        }

        if(enteredDate && enteredRoomType && enteredPrice){
            b2.setDisable(false);
            submitPreferences();
            FilteredAvailableProps.FilteredAvailableProps();
        }

    }

    public void submitPreferences()
    {
        Preferences.setFrom_Date(From_Date_Picker.getValue());
        Preferences.setTo_Date(To_Date_Picker.getValue());
        Preferences.setRoom_Preference(preference.getValue());
        Preferences.setFrom_Price(From_Price_Range.getValue());
        Preferences.setTo_Price(To_Price_Range.getValue());

    }


}
