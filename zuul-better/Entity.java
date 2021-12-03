import java.util.*;
/**
 * Main Class created to implement item functionality
 * 
 * @author Ronen Raj Roy(K21086768)
 * @version 2021-12-01
 */
class Entity
{   
    private String name;
    private int combat_power;
    private boolean isEquipment; 
    boolean is_opened;
    private double weight;
    Entity(int combatPower,String name,double weight)
    {
        this.combat_power = combatPower;
        this.name = name;
        this.is_opened = false;
        this.weight = weight;
        this.isEquipment = true;
    }
    /**
     * accesor method to get the name of the item
     * @return String name
     */
    public String getName() {
        return name;
    }
    /**
    * setter method the set the name of the item
    * @param String  name
    */
    public void setName(String name) {
        this.name = name;
    }
    /**
    * accesor method to get the combat power of the item.
    * @return
    */
    public int getCombat_power() {
        return combat_power;
    }
    /**
     * setter method to set the combat power of the item.
     * @param int combat_power
     */
    public void setCombat_power(int combat_power) {
        this.combat_power = combat_power;
    }
    /**
     * accesor method to get the weight of the item
     * @return double weight
     */
    public double getWeight()
    {
        return weight;
    }
   /**
    * accesor method to get if the item is equipment or not.
    * @return boolean isEquipment
    */
   public boolean is_equipment()
   {
       return this.isEquipment;
   }
   public boolean is_chest()
   {
       return false;
   }
   public void show_chest_items()
   {
       System.out.println(name + " is not a chest.");
   }
   public ArrayList<Entity> getItems()
   {
       return null;
   }
   
}