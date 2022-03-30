import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Table data for the map.
 */
public class PropertyModel {
    private SimpleStringProperty id;
    private SimpleStringProperty host_name;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty num_reviews;
    private SimpleIntegerProperty min_nights;

    public PropertyModel(String id, String hostname, Integer price, Integer reviews, Integer minnights){
        this.id = new SimpleStringProperty(id);
        host_name = new SimpleStringProperty(hostname);
        this.price = new SimpleIntegerProperty(price);
        num_reviews = new SimpleIntegerProperty(reviews);
        min_nights = new SimpleIntegerProperty(minnights);
    }

    // GETTERS

    public String getId() {
        return id.get();
    }

    public String getHost_Name(){
        return host_name.get();
    }

    public int getPrice(){
        return price.get();
    }

    public int getReviews(){
        return num_reviews.get();
    }

    public int getMinNights(){
        return min_nights.get();
    }

    // Setters

    public void setHost_Name(String n){
        host_name = new SimpleStringProperty(n);
    }

    public void setPrice(int x){
        this.price = new SimpleIntegerProperty(x);
    }

    public void setReviews(int x){
        num_reviews = new SimpleIntegerProperty(x);
    }

    public void setMinNights(int x){
        min_nights = new SimpleIntegerProperty(x);
    }
}
