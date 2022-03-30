import java.time.LocalDate;

public class Preferences {
    private static LocalDate From_Date;
    private static LocalDate To_Date;
    private static int From_Price;
    private static int To_Price;
    private static String Room_Preference;

    public static LocalDate getFrom_Date() {
        return From_Date;
    }

    public static void setFrom_Date(LocalDate from_Date) {
        From_Date = from_Date;
    }

    public static LocalDate getTo_Date() {
        return To_Date;
    }

    public static void setTo_Date(LocalDate to_Date) {
        To_Date = to_Date;
    }

    public static int getFrom_Price() {
        return From_Price;
    }

    public static void setFrom_Price(int from_Price) {
        From_Price = from_Price;
    }

    public static int getTo_Price() {
        return To_Price;
    }

    public static void setTo_Price(int to_Price) {
        To_Price = to_Price;
    }

    public static String getRoom_Preference() {
        return Room_Preference;
    }

    public static void setRoom_Preference(String room_Preference) {
        Room_Preference = room_Preference;
    }
}
