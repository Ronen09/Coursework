import java.util.*;

class Player
{
    boolean is_player;
    Game game;
    public ArrayList<Entity> equipment;
    public int hp;
    public int combat_power;
    public boolean is_enemy;
    private String name;
    private double weight;
    private double maxWeight;
    public Player(Game game,ArrayList<Entity> equipment,int hp,int combat_power,boolean is_enemy,String name)
    {
        this.game = game;
        this.equipment = equipment;
        this.hp = hp;
        this.combat_power = combat_power;
        this.is_enemy = is_enemy;
        this.name = name;
        updateWeight();
    }
    // public void initialize_player()
    // {
        // equipment.put("sword",(new Entity(false,game,100,"Sword",true,null)));
        // equipment.put("armor",(new Entity(false,game,100,"Shield",true,null)));
        // equipment.put("key",(new Entity(false,game,0,"Treasure Key1",false,null)));
    // }
    public void attack(String name)
    {   
        int hp_loss = 0;
        int i = 0;
        for(Character chr : game.currentRoom.characters)
        {
            if(chr.getName().equalsIgnoreCase(name))
            {       
                if(chr.combat_power < this.combat_power)
                {
                    hp_loss = 2000 - (this.combat_power - chr.combat_power);
                    System.out.println(chr.getName() + " has dropped the following items!");
                    for(Entity item:chr.equipment)
                    {   
                        game.currentRoom.entities.add(item);
                        System.out.println((i+1) + ". " + item);
                        i++;
                    }
                }
                else
                {
                    System.out.println("You were defeated!");
                }
            }
        }
        this.hp -= hp_loss;
    }
    public void unlockRoom(String direction)
    {
        Room nextRoom = game.currentRoom.getExit(direction);
        if(nextRoom.locked())
        {
            for(Entity ent:this.equipment)
            {
                if(ent.name.contains("key"))
                {
                        nextRoom.open_room(ent);
                        break;
                }
            }
        }
    }
    public void goRoom(Command command) 
    {   
            if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Go where?");
                return;
            }
        

            String direction = command.getSecondWord();
            
            // Try to leave current room.
            Room nextRoom = game.currentRoom.getExit(direction);
            if(!nextRoom.locked())
            {
                if (nextRoom == null) {
                System.out.println("There is no door!");
                }
                else {
                    game.currentRoom = nextRoom;
                }
            }
            else
            {
                System.out.println("The door is locked.Maybe theres a key lying somewhere.");
            }
    }
    public void goBack()
    {   
        if(game.visited.size() >= 2)
        {
            game.currentRoom = game.visited.get(game.visited.size() - 2);
            game.visited.remove(game.visited.size() - 1);
        }
        else
        {
            System.out.println("You cannot go back further!");
        }
    }
    public void take_item(String item,ArrayList<Entity> entities)
    {   
        for(int i = 0;i<entities.size();i++)
        {
            if(entities.get(i).name.equalsIgnoreCase(item))
            {   
                    if(entities.get(i).isEquipment){
                    	
                        if(entities.get(i).name.toLowerCase().contains("sword"))
                        {   
                        	if(((this.weight + entities.get(i).weight) - this.equipment.get(0).weight) < this.getMaxWeight())
                        	{
                        		equipment.set(0,entities.get(i));
                        	}
                        	else
                        	{
                        		System.out.println(entities.get(i).name +" is too heavy to pick up!");
                        	}
                        }
                        else if(entities.get(i).name.toLowerCase().contains("shield"))
                        {	
                        	if(((this.weight + entities.get(i).weight) - this.equipment.get(1).weight) < this.getMaxWeight())
                    		{
                            	equipment.set(1,entities.get(i));
                    		}
                        	else
                        	{
                        		System.out.println(entities.get(i)+" is too heavy to pick up!");
                        	}
                        }
                        else
                        {   
                        	if(this.weight + entities.get(i).weight < this.getMaxWeight())
                        	{
                        		equipment.add(entities.get(i));
                        	}
                        	else
                        	{
                        		System.out.println(entities.get(i)+" is too heavy to pick up!");
                        	}
                        }   
                            entities.remove(i);
                        }
                    else
                    {   
                        System.out.println("You have to open the chest first before taking its items.");
                    }
                } 
            }
        updateWeight();
        }
    public void open_chest()
    {
        for(int i = 0;i<game.currentRoom.entities.size();i++)
        {
            if(game.currentRoom.entities.get(i).is_chest)
            {   
                game.currentRoom.entities.get(i).show_chest_items();
            }
        }
    }
    public void take_item_from_chest(Command command)
    {    
        String take_item = (command.getSecondWord() + " " + command.getThirdWord()).toLowerCase();
        Entity temp = null;
        String chest = command.getFifthWord();
        for(int i = 0;i<game.currentRoom.entities.size();i++)
        {
            if(game.currentRoom.entities.get(i).is_chest)
            {   
                temp = game.currentRoom.entities.get(i);
                temp.chest(take_item);
            }
        }
    }
    public void give(String who_gets,Entity entity)
    {
        for(int i = 0;i<game.currentRoom.characters.size();i++)
        {
            if(game.currentRoom.characters.get(i).getName().equalsIgnoreCase(who_gets))
            {   
                game.currentRoom.characters.get(i).equipment.add(entity);
            }
        }
    }
    public void updateWeight()
    {   
        weight = 0;
        for(Entity ent:this.equipment)
        {
            weight += ent.weight;
        }
    }
    public void showWeight()
    {
        System.out.println("You are carrying " + weight + " kgs of items.");
    }
    public void setMaxWeight(double w)
    {
        maxWeight = w;
    }
    public double getMaxWeight()
    {
        return maxWeight;
    }
}