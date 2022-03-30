import java.util.ArrayList;
import java.util.HashMap;

public class BoroughAvailableProps {
    private static HashMap<String, ArrayList<AirbnbListing>> BoroughAvailableProps = new HashMap<>();
    private static ArrayList<AirbnbListing> plistings;
    private static BoroughFullNameMap fullNameMap = new BoroughFullNameMap();

    public static void BoroughAvailableProps() throws Exception{

        //Load data
        AirbnbDataLoader b = new AirbnbDataLoader();
        b.load();

        // NOTE: USES FULL NAME, NOT INITIALS
        // Maps properties available to names
        for(AirbnbListing c : b.listings){
            if(!BoroughAvailableProps.containsKey(c.getNeighbourhood())){
                plistings = new ArrayList<>();
                plistings.add(c);
                BoroughAvailableProps.put(c.getNeighbourhood(), plistings);
            }
            else{
                ArrayList temp = BoroughAvailableProps.get(c.getNeighbourhood());
                temp.add(c);
                BoroughAvailableProps.put(c.getNeighbourhood(), temp);
            }
        }

    }

    /**
     * Prints everything in the hashmap.
     */
    public void printAll(){
        for(String s : BoroughAvailableProps.keySet()){
            System.out.println(s + ": " + BoroughAvailableProps.get(s));
        }
    }

    /**
     * Gets the number of available properties from the map.
     * @param s The name of the neighbourhood.
     * @return The number of properties available in that neighbourhood.
     */
    public static int getPropertiesFromName(String s){
        return BoroughAvailableProps.get(s).size();
    }

    /**
     * Get the full name of a borough from its initials.
     * @param s The initial 4 letters.
     * @return The full name.
     */
    public static String getFullNameFromInit(String s){
        return fullNameMap.getFullNameFromInit(s);
    }

    /**
     * Gets all available properties from a given borough.
     * @param n Borough name.
     * @return The list of properties.
     */
    public static ArrayList returnPropsFromName(String n){
        return BoroughAvailableProps.get(n);
    }

    /**
     * Return specific property using boroughname and id.
     * @param bn Borough name.
     * @param id Id.
     * @return The AirbnbListing with matching id and borough name.
     */
    public static AirbnbListing returnSpecificProp(String bn, String id){
        ArrayList<AirbnbListing> temper = BoroughAvailableProps.get(bn);
        for(AirbnbListing a : temper){
            if(a.getId().equalsIgnoreCase(id)){
                return a;
            }
        }
        return null;
    }

    /**
     * Return entire hashmap (for filtering)
     * @return The hashmap.
     */
    public static HashMap<String, ArrayList<AirbnbListing>> getWholeBoroughAvailableProps(){
        return BoroughAvailableProps;
    }

    public static void insertNewProp(AirbnbListing l){
        ArrayList temp = BoroughAvailableProps.get(l.getNeighbourhood());
        temp.add(l);
        BoroughAvailableProps.put(l.getNeighbourhood(), temp);
    }

}
