//Name - Ronen Raj Roy
//Student ID - K21086768
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * This project implements a simple application. Properties from a fixed
 * file can be displayed. 
 * 
 * 
 * @author Michael Kölling and Josh Murphy,edited by Ronen Raj Roy (K21086768)
 * @version 1.0
 */
public class PropertyViewer
{    
    private PropertyViewerGUI gui;     // the Graphical User Interface
    private Portfolio portfolio;
    private int property_no;            // stores the property number which is currently being viewed by the user.
    private int number_of_properties;   // stores the number of properties viewed by the user.
    private Property current_property;  // stores the property currently being viewed by the user
    private int total_sum;              // stores the sum of the amounts of the properties that the user views.
    private StatsViewerGUI stats_gui;       
    /**
     * Create a PropertyViewer and display its GUI on screen.
     */
    public PropertyViewer()
    {   
        number_of_properties = 1;   //stores the number of properties viewed.
        property_no = 0;            //stores current property number.
        gui = new PropertyViewerGUI(this);
        portfolio = new Portfolio("airbnb-london.csv");
        displayProperty(property_no);
        total_sum = total_sum + current_property.getPrice();  //stores the price of the first property viewed(we don't want to miss out on that!)
    }
    
    /**
     * Function used to display new Properties when the user 
     * wants to go next or back.
     */
    public void displayProperty(int prop_no)
    {   
        current_property = (portfolio).getProperty(prop_no);
        gui.showProperty(current_property);
        gui.showID(current_property);
        gui.showFavourite(current_property);
    }
    /**
     *  Function which is used to move next in the airbnb list with a rollover included so that it goes to the first property after clicking next on the last property.
     */
    public void nextProperty()
    {   
        property_no = (property_no + 1) % (portfolio.numberOfProperties());
        number_of_properties++;
        displayProperty(property_no);
        total_sum = total_sum + (current_property).getPrice();
    }

    /**
     * Function which is used to move back in the airbnb list with a rollover included so that it goes to the last property after clicking previous on the first property.
     */
    public void previousProperty()
    {   
        property_no = (property_no - 1) % (portfolio.numberOfProperties());
        number_of_properties++;
        displayProperty(property_no);
        total_sum = total_sum + (current_property).getPrice();
    }

    /**
     *  Function used to set the current property as the user's favourite.
     */
    public void toggleFavourite()
    {
        current_property.toggleFavourite();
        displayProperty(property_no);
    }
    

    //----- methods for challenge tasks -----
    
    /**
     * This method opens the system's default internet browser
     * The Google maps page should show the current properties location on the map.
     */
    public void viewMap() throws Exception
    {
       double latitude = current_property.getLatitude();    // gets the latitude of the current property being showed.
       double longitude = current_property.getLongitude();  // gets the longitiude of the current property being showerd
       
       URI uri = new URI("https://www.google.com/maps/place/" + latitude + "," + longitude);
       java.awt.Desktop.getDesktop().browse(uri); 
    }
    
    /**
     * Function used to return the number of properties that the user has seen till now.
     */
    public int getNumberOfPropertiesViewed()
    {   
        return number_of_properties;
    }
    
    /**
     * Function which computes and returns the average price of the properties viewed by the user till now.
     */
    public int averagePropertyPrice()
    {   
        return(total_sum / number_of_properties);
    }
    /**
     * Function which is used to show the above mentioned statistics in a graphical interface.
     */
    public void Stats()
    {   
            stats_gui.showStats();
    }
}

