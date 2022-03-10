import java.util.*;

class Character
{
    boolean is_player;
    Game game;
    private HashMap<String,Entity> equipment;
    public int hp;
    public int combat_power;
    Room currentRoom;
    private boolean is_enemy;
    private String name;
    public Character(boolean is_player,Game game,HashMap<String,Entity> equipment,int hp,int combat_power,Room room,boolean is_enemy,String name)
    {
        this.is_player = is_player;
        this.game = game;
        this.equipment = equipment;
        this.hp = hp;
        this.combat_power = combat_power;
        this.currentRoom = room;
        this.is_enemy = is_enemy;
        this.name = name;
    }
    private void attack(Character atked)
    {   
        if(atked.is_player)
        {   
            atked.hp -= combat_power;
        }
        if(atked.hp <= 0)
        {
            System.out.println("You died.");
            System.exit(0);
        }
    }
    private void goRoom(Command command) 
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
            Room nextRoom = currentRoom.getExit(direction);
    
            if (nextRoom == null) {
                System.out.println("There is no door!");
            }
            else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            }
            currentRoom.show_items();
        }
    }
    private void take_item(Command command)
    {   if(is_player)
        {
            String item = command.getSecondWord() +" "+  command.getThirdWord();
            for(int i = 0;i<currentRoom.entities.size();i++)
            {
                if(currentRoom.entities.get(i).name.equalsIgnoreCase(item))
                {   
                    if(currentRoom.entities.get(i).isEquipment){
                        if(currentRoom.entities.get(i).name.contains("Sword"))
                        {   
                            equipment.put("sword",currentRoom.entities.get(i));
                        }
                        else
                        {
                            equipment.put("armor",currentRoom.entities.get(i));
                        }
                    }
                    else
                    {
                        equipment.put("misc",currentRoom.entities.get(i));
                    }
                    currentRoom.entities.remove(i);
                }   
            }
        }
    }
}