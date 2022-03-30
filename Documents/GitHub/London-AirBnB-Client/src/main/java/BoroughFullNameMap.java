import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Maps each borough initial to its full name.
 */
public class BoroughFullNameMap {
    private String FileName = "src\\main\\resources\\csv\\boroughnames.csv";
    private static HashMap<String, String> BoroughNames = new HashMap<>();

    public BoroughFullNameMap(){
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(FileName));
            while((line = reader.readLine()) != null){
                String[] rows = line.split(",");
                BoroughNames.put(rows[0], rows[2]);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Return the hashmap.
     * @return The hashmap of names.
     */
    public static HashMap<String, String> getBoroughNames(){
        return BoroughNames;
    }

    /**
     * Prints out the boroughs and their coords.
     */
    public void printAll(){
        for(String s : BoroughNames.keySet()){
            System.out.println(s + ": " + BoroughNames.get(s));
        }
    }

    /**
     * Get the full name of a borough from its initials.
     * @param s The initial 4 letters.
     * @return The full name.
     */
    public String getFullNameFromInit(String s){
        return BoroughNames.get(s);
    }
}
