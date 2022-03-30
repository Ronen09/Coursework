/**
 * Stores the details of things that have been clicked on.
 */
public class ClickedOn {
    // borough name
    private static String currentClickedString = "";
    // listing clicked on in table
    private static AirbnbListing currentListing;

    public static String getCurrentClickedString() {
        return currentClickedString;
    }

    public static AirbnbListing getCurrentListing(){
        return currentListing;
    }

    public static void setCurrentClickedString(String currentClickedString) {
        ClickedOn.currentClickedString = currentClickedString;
    }

    public static void setCurrentListing(AirbnbListing l){
        ClickedOn.currentListing = l;
    }
}
