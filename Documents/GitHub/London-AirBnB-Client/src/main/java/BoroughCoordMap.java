import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class BoroughCoordMap {
    private String FileName = "src\\main\\resources\\csv\\boroughnames.csv";
    private static HashMap<String, int[]> BoroughCoords = new HashMap<>();

    /**
     * Assigns each borough to a coordinate.
     */
    public BoroughCoordMap() {
        CoordMap c = new CoordMap();
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(FileName));
            while((line = reader.readLine()) != null){
                String[] rows = line.split(",");
                BoroughCoords.put(rows[0], c.obtainValueFromArray(Integer.parseInt(rows[1])));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //printAllShit();
    }

    /**
     * Prints out the boroughs and their coords.
     */
    public void printAll(){
        for(String s : BoroughCoords.keySet()){
            System.out.println(s + ": " + Arrays.toString(BoroughCoords.get(s)));
        }
    }

    /**
     * Returns the borough's name from its coords.
     * @param x X coord.
     * @param y Y coord.
     * @return Borough name.
     */
    public String getNameFromCoords(int x, int y){
        for(String key : BoroughCoordMap.BoroughCoords.keySet()){
            if(x == BoroughCoords.get(key)[0] && y == BoroughCoords.get(key)[1]){
                return key;
            }
        }
        return null;
    }
}
