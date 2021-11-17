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
    Entity(boolean is_chest,Game G,int combatPower,String name,boolean isEquipment,ArrayList<Entity>items)
    {
        this.is_chest = is_chest;
        this.game = G;
        this.combat_power = combatPower;
        this.name = name;
        this.isEquipment = isEquipment;
        this.items = items;
        this.is_opened = false;
    }
    
    public void chest()
    {   
        if(!is_opened)
        {
            System.out.println("The chest has the following items :");
            for(int i = 0;i < items.size();i++)
            {
                System.out.println((i+1) + ". " + items.get(i).name);
                game.currentRoom.entities.add(items.get(i));
            }
        }
        else
        {
            System.out.println("Chest has already been opened.");
        }
    }
}