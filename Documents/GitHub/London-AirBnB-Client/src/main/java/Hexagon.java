import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;

public class Hexagon{
    // ==== CONSTANTS ====
    private final static double v = Math.sqrt(3);

    // radius from centre to corner
    private final static int r = 60;

    // diameter of hexagon (non-corner)
    private final static double c = v * r;

    // vertical distance from peak to non-peak
    private final static double d = r * 0.5;

    private Text boroughName = new Text();

    private Text availableProperties = new Text();

    private static BoroughFullNameMap fullnames = new BoroughFullNameMap();


    /**
     * Draws a hexagon. Needed to fill the hexagon array.
     * @param root The pane to attach the hexagon to.
     * @param x The starting x coord (top-left corner).
     * @param y Starting y coord (top-left corner).
     */
    public void drawHexagon(Pane root, double x, double y){

        Polygon hex = new Polygon();


        hex.getPoints().setAll(
                x, y,
                x+(c/2), y-d, // top peak
                x+c, y,

                x+c, y+r,
                x + (c/2), y+r+d, // bottom peak
                x, y+r

        );

        // Add hexagon to array
        HexArray.addPolygonToArray(hex);

        hex.setFill(Color.TRANSPARENT);
        hex.setStroke(Color.TRANSPARENT);

        root.getChildren().add(hex);
    }

    /**
     * Overloaded method of previous to include strings.
     * @param root The pane to attach the hexagon to.
     * @param x The starting x coord (top-left corner).
     * @param y Starting y coord (top-left corner).
     * @param s The string you want to enter.
     */
    public void drawHexagon(Pane root, double x, double y, String s, int p, Color color){

        // Create 2 layers of hexagons - 1 for visual, 1 for interaction
        Polygon hex = new Polygon(); // visual
        Polygon upperlayerhex = new Polygon(); // interaction

        hex.getPoints().setAll(
                x, y,
                x+(c/2), y-d, // top peak
                x+c, y,

                x+c, y+r,
                x + (c/2), y+r+d, // bottom peak
                x, y+r

        );
        upperlayerhex.getPoints().setAll(
                x, y,
                x+(c/2), y-d, // top peak
                x+c, y,

                x+c, y+r,
                x + (c/2), y+r+d, // bottom peak
                x, y+r

        );

        // Add hexagon to array
        HexArray.addPolygonToArray(hex);

        hex.setFill(color);
        hex.setStroke(Color.TRANSPARENT);

        upperlayerhex.setFill(Color.TRANSPARENT);
        upperlayerhex.setStroke(Color.TRANSPARENT);

        // ==== INTERACTION STARTS HERE ====
        // Change color on user click
        EventHandler<MouseEvent> enterHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HexArray.getLocationOfPolygon(hex);

                availableProperties.setStroke(Color.BLACK);
                availableProperties.setFill(Color.BLACK);

                // Move boroughname up
                TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.seconds(0.1));
                t.setNode(boroughName);
                t.setToY(-15);
                t.play();

                // Move availableprop down
                TranslateTransition t2 = new TranslateTransition();
                t2.setDuration(Duration.seconds(0.1));
                t2.setNode(availableProperties);
                t2.setToY(15);
                t2.play();

                // Fade in availableprop
                FadeTransition t3 = new FadeTransition();
                t3.setDuration(Duration.seconds(0.1));
                t3.setNode(availableProperties);
                t3.setToValue(1);
                t3.play();

            }
        };

        EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boroughName.setStroke(Color.BLACK);
                boroughName.setFill(Color.BLACK);

                // Move boroughname back to original pos
                TranslateTransition t = new TranslateTransition();
                t.setDuration(Duration.seconds(0.1));
                t.setNode(boroughName);
                t.setToY(0);
                t.play();

                // Ditto with availableprops
                TranslateTransition t2 = new TranslateTransition();
                t2.setDuration(Duration.seconds(0.1));
                t2.setNode(availableProperties);
                t2.setToY(0);
                t2.play();

                // Fade out props
                FadeTransition t3 = new FadeTransition();
                t3.setDuration(Duration.seconds(0.1));
                t3.setNode(availableProperties);
                t3.setToValue(0);
                t3.play();

//                availableProperties.setStroke(Color.TRANSPARENT);
//                availableProperties.setFill(Color.TRANSPARENT);

            }
        };

        EventHandler<MouseEvent> clickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    // Set ClickedOn stuff
                    ClickedOn.setCurrentClickedString(fullnames.getFullNameFromInit(s));


                    URL url = new File("src/main/resources/fxml/PropertyList.fxml").toURI().toURL();
                    FXMLLoader loader = new FXMLLoader();
                    Parent root = loader.load(url);
                    Stage stage = new Stage();

                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.setTitle("Properties available in: " + fullnames.getFullNameFromInit(s));
                    stage.setScene(new Scene(root, 800, 500));

                    stage.setResizable(false);
                    stage.setX(200);
                    stage.setY(200);

                    stage.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        // ==== INTERACTION ENDS HERE ====


        // ==== HANDLERS ====
        upperlayerhex.addEventFilter(MouseEvent.MOUSE_ENTERED_TARGET, enterHandler);
        upperlayerhex.addEventFilter(MouseEvent.MOUSE_EXITED_TARGET, exitHandler);
        upperlayerhex.addEventFilter(MouseEvent.MOUSE_CLICKED, clickedHandler);

        root.getChildren().add(addText(s, p, hex, x, y));

        root.getChildren().add(upperlayerhex);

        FadeTransition t = new FadeTransition();
        t.setNode(hex);
        t.setDuration(Duration.seconds(1));
        t.setFromValue(0);
        t.setToValue(1);
        t.play();
    }

    /**
     * Returns a stackpane object that can be added as a child to a root pane.
     * @param text The text that should be displayed
     * @param shape The hexagon to add text to.
     * @param x The x coord of the shape.
     * @param y The y coord of the shape.
     * @return StackPane to be added to the root scene in the main method.
     */
    public StackPane addText(String text, int p, Polygon shape, double x, double y){
        StackPane s = new StackPane();
        s.getChildren().add(shape);


        boroughName.setText(text);
        boroughName.setStyle("-fx-font: 24 arial;");
        boroughName.setStroke(Color.BLACK);


        availableProperties.setText(Integer.toString(p));
        availableProperties.setStyle("-fx-font: 24 arial;");
        availableProperties.setStroke(Color.TRANSPARENT);
        availableProperties.setFill(Color.TRANSPARENT);



        s.getChildren().add(boroughName);
        s.getChildren().add(availableProperties);
        s.setLayoutX(x);
        s.setLayoutY(y-d);

        return s;
    }

    /**
     * Returns the radius
     * @return The radius.
     */
    public static int getRadius(){
        return r;
    }

    /**
     * Returns the diameter (distance from flat side to flat side)
     * @return The diameter.
     */
    public static double getDiameter(){
        return c;
    }

}
