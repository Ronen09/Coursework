import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Class for generating the tiling using Hexagon objects
 */
public class HexTiling {

    // height of hexagon
    private final static double e = 1.5 * Hexagon.getRadius();

    private static final BoroughCoordMap b = new BoroughCoordMap();

    private static final ColorInterpolator colourin = new ColorInterpolator(0, FilteredAvailableProps.returnBoroughMostProps() + 2);

    /**
     * Draws tiling on the panel.
     * @param root Root node/panel to link tiling to.
     * @param sx Starting x coordinate for tiling.
     * @param sy Starting x coordinate for tiling.
     * @param spc Spacing between tiles.
     * @param rws No. of hexes per row
     * @param clms No. of hexes per column
     */
    public void drawTiling(Pane root, double sx, double sy, double spc, int rws, int clms){
        HexArray.clearArray();

        // Changing offset
        double startx = sx;
        double starty = sy;

        // Dimensions
        for(int i = 0; i < rws; i++) {
            for(int j = 0; j < clms; j++) {
                Hexagon h = new Hexagon();
                // Initials
                String name = b.getNameFromCoords(j,i);

                String fullname = BoroughAvailableProps.getFullNameFromInit(name);

                if(name != null){
                    h.drawHexagon(root, startx, starty, name, FilteredAvailableProps.getPropertiesFromName(fullname), colourin.evaluateValueToColor(FilteredAvailableProps.getPropertiesFromName(fullname)));

                }
                else{
                    h.drawHexagon(root, startx, starty);
                }
                startx += (Hexagon.getDiameter() + spc);
            }
            if(i%2 == 1){
                startx = sx;
            }
            else{
                startx = sx - Hexagon.getDiameter()/2 -5;
            }
            starty += (e + spc);
        }
    }


}
