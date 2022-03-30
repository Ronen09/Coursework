import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Holds the coords of each borough in the hex array.
 * Holds an arraylist of size 2 arrays.
 */
public class CoordMap {
    private int[] holder;
    private ArrayList<int[]> arrays = new ArrayList<>();
    private String FileName = "src\\main\\resources\\csv\\coords.csv";

    /**
     * Reads the file and loads in the coordinates into the ArrayList.
     */
    public CoordMap(){
        BufferedReader reader = null;
        String line = "";
        try{
            reader = new BufferedReader(new FileReader(FileName));
            while((line = reader.readLine()) != null){
                String[] row = line.split(",");
                holder = new int[2];

                holder[0] = Integer.parseInt(row[0]);
                holder[1] = Integer.parseInt(row[1]);
                System.out.println(holder);
                arrays.add(holder);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        // printArray();
    }

    /**
     * Prints out the whole array.
     */
    public void printArray(){
        for(int[] i : arrays){
            System.out.print("X: " + Integer.toString(i[0]) + "|");
            System.out.println("Y: " + Integer.toString(i[1]));
        }
    }

    /**
     * Retrieve a value from the array using an index.
     * @param x The index.
     * @return The array at the index.
     */
    public int[] obtainValueFromArray(int x){
        return arrays.get(x);
    }
}
