import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PropertyDescriptionController implements Initializable {
    @FXML
    private Label name;
    @FXML
    private Label price;
    @FXML
    private Label desc;
    @FXML
    private Label type;
    @FXML
    private Label available;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AirbnbListing a = ClickedOn.getCurrentListing();
        name.setText("Host name: " + a.getHost_name());
        price.setText("Price: " + a.getPrice());
        desc.setText("Description: " + a.getName());
        type.setText("Room type: " + a.getRoom_type());
        available.setText("Availability: " + a.getAvailability365() + "/365");
    }
}
