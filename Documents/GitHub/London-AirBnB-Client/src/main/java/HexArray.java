import javafx.scene.shape.Polygon;

import java.util.ArrayList;

/**
 * This class is designed to hold the array of hexagons generated.
 */

public class HexArray {
    // Tracks all polygons in a row
    private static ArrayList<Polygon> hexRow = new ArrayList<>();

    // Tracks all rows of polygons
    private static ArrayList<ArrayList> hexArray = new ArrayList<>();

    // Number of polygons in a row
    private static int rowLimit = 7;

    // Number of polygons in a column
    private static int heightLimit = 7;

    /**
     * Adds the polygon to a static array for access.
     * @param p The polygon to add.
     */
    public static void addPolygonToArray(Polygon p){
        if(hexArray.size() >= 7){
            System.out.println("Array is full!");
        }
        else {
            if (hexRow.size() + 1 > rowLimit) {
                System.out.println("Must clear array!");
                hexRow = new ArrayList<>();
            }
            hexRow.add(p);
            if(hexRow.size() >= rowLimit){
                hexArray.add(hexRow);
            }
        }
    }

    /**
     * Clears the whole array.
     */
    public static void clearArray(){
        hexArray.clear();
    }

    /**
     * Shows the x-y coords of every shape in the array
     */
    public static void showArrayStates(){
        for(ArrayList a : hexArray){
            for (Polygon p : hexRow){
                System.out.print("X: " + hexRow.indexOf(p) + " |");
                System.out.println("Y: " + hexArray.indexOf(a));
            }
        }
    }

    /**
     * Gets a specific polygon in the 2D array
     * @param x The x coord (no. in the row)
     * @param y The y coord (no. in the column)
     */
    public static void getPolygonAt(int x, int y){
        System.out.println(hexArray.get(x).get(y));
    }

    /**
     * Gets the location of a specific polygon.
     * @param poly The polygon whose location we want.
     */
    public static void getLocationOfPolygon(Polygon poly){
        boolean found = false;
        int storedx = 0;
        int storedy = 0;

        for(int i = 0; i < heightLimit; i++){
            for (int j = 0; j < rowLimit; j++){
                if(hexArray.get(i).get(j).equals(poly)){
                    found = true;
                    storedx = j;
                    storedy = i;
                }
            }
        }
        if(found){
            System.out.println("X: " + storedx + " | Y: " + storedy);
        }
        else{
            System.out.println("Nothing");
        }

    }

}
