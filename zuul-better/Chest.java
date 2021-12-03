import java.util.ArrayList;
/**
 * SubClass created to implement chest functionality
 * 
 * @author Ronen Raj Roy(K21086768)
 * @version 2021-12-01
 */
public class Chest extends Entity
{   
    private ArrayList<Entity>items;
    private String name;
    Chest(String name,ArrayList<Entity>items)
    {
        super(0,"Treasure Chest",0.0);
        this.name = name;
        this.items = items;
    }
    /**
     * shows all the items present in the chest.
     */
    public void show_chest_items()
    {   
        int i = 1;
        System.out.println("The chest has the following items");
        for(Entity entity:items)
        {
            System.out.println(i + ". " + entity.getName() + "(Combat Power - " + entity.getCombat_power() + ", Weight - " + entity.getWeight() + " )");
        }
   }
   /**
    * method to return true since the current object is a chest.
    * @return boolean true
    */
   public boolean is_chest()
   {
       return true;
   }
   /**
    * accesor method to get the items of the chest.
    * @return ArrayList<Entity> items
    */
   public ArrayList<Entity> getItems()
   {
       return this.items;
   }
}