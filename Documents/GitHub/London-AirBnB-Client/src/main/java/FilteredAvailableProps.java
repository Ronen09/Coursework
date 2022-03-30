import java.util.ArrayList;
import java.util.HashMap;

public class FilteredAvailableProps {
    // Stores the original hashmap
    private static HashMap<String, ArrayList<AirbnbListing>> boroughAvaiProps;

    // Stores filtered hashmap
    private static HashMap<String, ArrayList<AirbnbListing>> filteredAvaiProps = new HashMap<>();
    private static ArrayList<AirbnbListing> temp;

    /**
     * Filters properties already in available props
     */
    public static void FilteredAvailableProps(){
        boroughAvaiProps = BoroughAvailableProps.getWholeBoroughAvailableProps();
        filteredAvaiProps.clear();
        for(String key : boroughAvaiProps.keySet()){
            for(AirbnbListing a : boroughAvaiProps.get(key)){
                if(a.getPrice() >= Preferences.getFrom_Price() && a.getPrice() <= Preferences.getTo_Price()){
                    if(!filteredAvaiProps.containsKey(key)){
                        temp = new ArrayList<>();
                    }
                    else{
                        temp = filteredAvaiProps.get(key);
                    }
                    temp.add(a);
                    filteredAvaiProps.put(key, temp);
                }
            }
        }
    }

    public static int getPropertiesFromName(String s){
        if(filteredAvaiProps.get(s) == null){
            return 0;
        }else{
            return filteredAvaiProps.get(s).size();
        }
    }

    /**
     * Returns array of properties under a borough name.
     * @param n Borough name.
     * @return The list of properties.
     */
    public static ArrayList returnPropsFromName(String n){
        return filteredAvaiProps.get(n);
    }

    /**
     * Returns a specific listing.
     * @param bn Borough name.
     * @param id Listing id.
     * @return The listing we want.
     */
    public static AirbnbListing returnSpecificProp(String bn, String id){
        ArrayList<AirbnbListing> temper = filteredAvaiProps.get(bn);
        for(AirbnbListing a : temper){
            if(a.getId().equalsIgnoreCase(id)){
                return a;
            }
        }
        return null;
    }

    /**
     * Returns all listings currently in the map.
     * @return All listings.
     */
    public static int returnTotalListings(){
        int temp = 0;
        for(String k: filteredAvaiProps.keySet()){
            temp += filteredAvaiProps.get(k).size();
        }
        System.out.println(temp);
        return temp;
    }

    /**
     * Return the borough with the most props.
     * @return Ditto.
     */
    public static int returnBoroughMostProps(){
        int largest = 0;
        for(String k: filteredAvaiProps.keySet()){
            if(filteredAvaiProps.get(k).size() > largest){
                largest = filteredAvaiProps.get(k).size();
            }
        }
        return largest;
    }

    /**
     * Returns the filtered map.
     * @return Ditto.
     */
    public static HashMap<String, ArrayList<AirbnbListing>> returnFullFilteredProps(){
        return filteredAvaiProps;
    }

}
