import java.util.*;

class Entity
{   
    boolean is_chest;
    String name;
    Game game;
    int combat_power;
    boolean isEquipment;
    ArrayList<Entity>items;
    boolean is_opened;
    double weight;
    Entity(boolean is_chest,Game G,int combatPower,String name,boolean isEquipment,ArrayList<Entity>items,double weight)
    {
        this.is_chest = is_chest;
        this.game = G;
        this.combat_power = combatPower;
        this.name = name;
        this.isEquipment = isEquipment;
        this.items = items;
        this.is_opened = false;
        this.weight = weight;
    }
    
    public void chest(String take_item)
    {       
            take_item = take_item.toLowerCase();
            game.player.take_item(take_item,this.items);
    }
    public void show_chest_items()
    {   
        int i = 1;
        System.out.println("The chest has the following items");
        for(Entity entity:items)
        {
            System.out.println(i + ". " + entity.name);
        }
   }
}