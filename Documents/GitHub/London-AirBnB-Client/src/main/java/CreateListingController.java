import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateListingController extends GenericController implements Initializable {

    private String[] rtypes = {"Entire home/apt", "Private room"};
    private String[] boroughs = generateBoroughNames();

    private Alert alert = new Alert(Alert.AlertType.WARNING);
    private String alertbegin;

    private Alert succ = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    protected TextField hostname_entry;
    @FXML
    private TextField desc_entry;
    @FXML
    private ComboBox<String> borough_entry;
    @FXML
    private ComboBox<String> roomtype;
    @FXML
    private TextField price_entry;
    @FXML
    private TextField minnights_entry;
    @FXML
    private TextField avai_entry;

    /**
     * Need to autogenerate the followed:
     * - id
     * - host_id
     * - latitude
     * - longitude
     *
     * Set to 0:
     * - no of reviews
     * - last review
     * - reviews per month
     * - calculated host listings
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        borough_entry.getItems().addAll(boroughs);
        roomtype.getItems().addAll(rtypes);
    }

    private int randomIdGen(){
        Random r = new Random();
        return r.nextInt(10000000);
    }

    @FXML
    public void submit(ActionEvent event){
        if(finalCheck()){
            AirbnbListing newone = new AirbnbListing(
                    Integer.toString(randomIdGen()),
                    desc_entry.getText(),
                    Integer.toString(randomIdGen()),
                    hostname_entry.getText(),
                    borough_entry.getValue(),
                    0, 0,
                    roomtype.getValue(),
                    Integer.parseInt(price_entry.getText()),
                    Integer.parseInt(minnights_entry.getText()),
                    0,
                    "",
                    0,
                    0,
                    Integer.parseInt(avai_entry.getText())
                    );
            BoroughAvailableProps.insertNewProp(newone);
            succ.setContentText("New listing successfully added!");
            succ.show();
        }
    }

    /**
     * Final check to ensure everything is good.
     */
    @FXML
    public boolean finalCheck(){
        String newline = "\n ";
        if(checkForErrors()){
            System.out.println("YAY!");
            return true;
        }
        else{
            alertbegin = "The following issues must be resolved:";
            if(!validTextField(hostname_entry)){
                alertbegin = alertbegin + newline + "Invalid host name entry";
            }
            if(!validTextField(desc_entry)){
                alertbegin = alertbegin + newline + "Invalid description entry";
            }
            if(!validSelector(borough_entry)){
                alertbegin = alertbegin + newline + "Invalid borough selected";
            }
            if(!validSelector(roomtype)){
                alertbegin = alertbegin + newline + "Invalid room type selected";
            }
            if(!validPrice(price_entry)){
                alertbegin = alertbegin + newline + "Invalid price entry";
            }
            if(!validPrice(minnights_entry)){
                alertbegin = alertbegin + newline + "Invalid minimum nights entry";
            }
            if(!validAvai(avai_entry)){
                alertbegin = alertbegin + newline + "Invalid availability entry";
            }
            alert.setContentText(alertbegin);
            alert.show();
        }
        return false;
    }

    /**
     * Validation for price, minnights, and availability
     */
    public boolean checkForErrors(){
        if(validTextField(hostname_entry) && validTextField(desc_entry) &&
        validSelector(borough_entry) && validSelector(roomtype)
        && validPrice(price_entry) && validPrice(minnights_entry)
        && validAvai(avai_entry)){
            return true;
        }
        return false;
    }

    // ====== CHECKERS ======
    public boolean validTextField(TextField h){
        if(h.getText().equalsIgnoreCase("")){
            return false;
        }
        return true;
    }

    public boolean validSelector(ComboBox c){
        if(c.getValue() == null){
            return false;
        }
        return true;
    }
    /**
     * Check price entered is valid
     * @return Ditto.
     */
    public boolean validPrice(TextField f){
        try{
            if(Integer.parseInt(f.getText()) < 0){
                return false;
            }
        }
        catch (NumberFormatException e){
            System.out.println("Invalid number format!");
            return false;
        }
        return true;
    }

    /**
     * Check availability entered is valid
     * @return Ditto.
     */
    public boolean validAvai(TextField a){
        try{
            if(Integer.parseInt(a.getText()) < 0 || Integer.parseInt(a.getText()) > 366){
                return false;
            }
        }
        catch (NumberFormatException e){
            System.out.println("Invalid number format!");
            return false;
        }
        return true;
    }

    // ====== END CHECKERS ======

    /**
     * Debug command
     */
    public void printFieldInfo(){
        System.out.println(hostname_entry.getText());
        System.out.println(desc_entry.getText());
        System.out.println(borough_entry.getValue());
        System.out.println(roomtype.getValue());

        System.out.println(price_entry.getText());
        System.out.println(minnights_entry.getText());
        System.out.println(avai_entry.getText());
    }

    /**
     * Generates the full names of boroughs to display in the combobox
     * @return Array containing the names.
     */
    private String[] generateBoroughNames(){
        HashMap<String, String> mappy = BoroughFullNameMap.getBoroughNames();
        String[] holder = new String[33];
        int i = 0;
        for(String s: mappy.keySet()){
            holder[i] = mappy.get(s);
            i++;
        }
        return holder;
    }
}


