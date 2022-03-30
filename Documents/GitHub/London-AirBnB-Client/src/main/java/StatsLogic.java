/**
 * Is used for displaying all screens with info
 *
 */
public class StatsLogic
{
    private boolean  isVisible;
    String string;
    String title;
    public StatsLogic(String inputTitle, String inputString)
    {
        isVisible = false;
        string = inputString;
        title = inputTitle;
    }

    public void setVisible(){
        isVisible = !isVisible;
    }

    public String returnString()
    {
        return string;
    }

    public String returnTitle()
    {
        return title;
    }

}