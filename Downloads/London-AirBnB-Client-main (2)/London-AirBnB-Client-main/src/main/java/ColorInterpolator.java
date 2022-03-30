import javafx.scene.paint.Color;

public class ColorInterpolator {

    private double effectiveTotal;

    private String leftColorStr = "89CFF0";
    private String rightColorStr = "DCDCDC";


    /**
     * Interpolate between 2 colours (blend them)
     * @param minval The minimum value (red)
     * @param maxval The maximum value (green)
     */
    public ColorInterpolator(double minval, double maxval){
        effectiveTotal = maxval - minval;
    }

    /**
     * Returns a colour.
     * @param enteredval The value we want to be interpolated.
     * @return The color we want.
     */
    public Color evaluateValueToColor(double enteredval){
        double tr = 0;
        double tg = 0;
        double tb = 0;

        double frac = enteredval / effectiveTotal;

        String[] u = leftColorStr.split("");
        String[] v = rightColorStr.split("");

        tr = (Integer.parseInt(u[0] + u[1], 16) - Integer.parseInt(v[0] + v[1], 16)) * frac + Integer.parseInt(v[0] + v[1], 16); tr = snapValue(tr);
        tg = (Integer.parseInt(u[2] + u[3], 16) - Integer.parseInt(v[2] + v[3], 16)) * frac + Integer.parseInt(v[2] + v[3], 16); tg = snapValue(tg);
        tb = (Integer.parseInt(u[4] + u[5], 16) - Integer.parseInt(v[4] + v[5], 16)) * frac + Integer.parseInt(v[4] + v[5], 16); tb = snapValue(tb);

        System.out.println("Red: " + tr);
        System.out.println("Green: " + tg);
        System.out.println("Blue: " + tb);

        return Color.web("#" + Integer.toHexString((int) tr) + Integer.toHexString((int) tg) + Integer.toHexString((int) tb));

    }

    /**
     * Snaps RGB values to avoid colour space errors.
     * @param x The value to snap.
     * @return The snapped value.
     */
    public double snapValue(double x){
        if(x > 255){
            return 254;
        }
        else if(x < 0){
            return 1;
        }
        return x;
    }

}
