import java.util.*;

class Character
{
    boolean is_player;
    Game game;
    public HashMap<String,Entity> equipment;
    public int hp;
    public int combat_power;
    public boolean is_enemy;
    private String name;
    public Character(boolean is_player,Game game,HashMap<String,Entity> equipment,int hp,int combat_power,boolean is_enemy,String name)
    {
        this.is_player = is_player;
        this.game = game;
        this.equipment = equipment;
        this.hp = hp;
        this.combat_power = combat_power;
        this.is_enemy = is_enemy;
        this.name = name;
    }
    public void initialize_player()
    {
        equipment.put("sword",(new Entity(false,game,100,"Sword",true,null)));
        equipment.put("armor",(new Entity(false,game,100,"Shield",true,null)));
        equipment.put("key",(new Entity(false,game,0,"Treasure Key1",false,null)));
    }
    public void attack(Character atked)
    {   
        if(atked.is_player)
        {   
            atked.hp -= combat_power;
            if(atked.hp <= 0)
            {
                System.out.println("You died.");
                System.exit(0);
            }
            else
            {
                System.out.println("You were attacked by " + this.name + " .");
                System.out.println("You were successful in defeating them");
                System.out.println("You have " + atked.hp + " hp remaining.");
            }
        }
    }
    public void goRoom(Command command) 
    {   
        if(is_player)
        {
            if(!command.hasSecondWord()) {
                // if there is no second word, we don't know where to go...
                System.out.println("Go where?");
                return;
            }

            String direction = command.getSecondWord();
    
            // Try to leave current room.
            Room nextRoom = game.currentRoom.getExit(direction);
    
            if (nextRoom == null) {
                System.out.println("There is no door!");
            }
            else {
                game.currentRoom = nextRoom;
                System.out.println(game.currentRoom.getLongDescription());
            }
            
        }
    }
    public void take_item(Command command)
    {   
        String item = command.getSecondWord() +" "+  command.getThirdWord();
        for(int i = 0;i<game.currentRoom.entities.size();i++)
        {
            if(game.currentRoom.entities.get(i).name.equalsIgnoreCase(item))
            {   
                if(game.currentRoom.entities.get(i).isEquipment){
                    if(game.currentRoom.entities.get(i).name.contains("Sword"))
                    {   
                        equipment.put("sword",game.currentRoom.entities.get(i));
                    }
                    else
                    {
                        equipment.put("armor",game.currentRoom.entities.get(i));
                    }
                    }
                else
                {   
                    equipment.put(command.getThirdWord(),game.currentRoom.entities.get(i));
                    equipment.put("misc",game.currentRoom.entities.get(i));
                }
                game.currentRoom.entities.remove(i);
            }   
        }
    }
    public void open_chest(Command command)
    {
        String chest = command.getSecondWord() + " " + command.getThirdWord();
        Entity temp = null;
        for(int i = 0;i<game.currentRoom.entities.size();i++)
        {
            if(game.currentRoom.entities.get(i).is_chest)
            {   
                temp = game.currentRoom.entities.get(i);
                game.currentRoom.entities.remove(i);
                temp.chest();
            }
        }
    }
}
