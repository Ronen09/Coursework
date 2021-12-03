import java.util.*;
/**
 * Class which implements many of the functionalities of the player or the user(combat,taking items,giving them,etc.)
 * @author Ronen Raj Roy(K21086768)
 * @version 2021.12.01
 */
class Player
{
    Game game;
    private ArrayList<Entity> equipment;
    private int hp;
    private int combat_power;
    private String name;
    private double weight;
    private double maxWeight;
    public Player(Game game,ArrayList<Entity> equipment,int hp,int combat_power,boolean is_enemy,String name)
    {
        this.game = game;
        this.equipment = equipment;
        this.hp = hp;
        this.combat_power = combat_power;
        this.name = name;
        updateWeight();
    }
    public int getCombatPower()
    {
        return this.combat_power;
    }
    public void setCombatPower(int c)
    {   
        this.combat_power = c;
    }
    /**
     * function for attacking enemies and adding their drops to the items in the room.
     * @param name
     */
    public void attack(String name)
    {
        int i = 0;
        for(Character chr : game.getCurrentRoom().getCharacters())
        {
            if(chr.getName().equalsIgnoreCase(name))
            {   
                if(chr.isEnemy())
                {
                    if(chr.getCombat_power() < this.combat_power)
                    {
                        this.hp = this.hp - chr.getCombat_power();
                        System.out.println(chr.getName() + " has dropped the following items!");
                        for(Entity item:chr.getEquipment())
                        {
                            game.getCurrentRoom().getEntities().add(chr.getEquipment().get(i));
                            System.out.println((i+1) + ". " + chr.getEquipment().get(i).getName());
                            i++;
                        }
                    }
                    else
                    {
                        System.out.println("You were defeated!");
                        System.out.println("GAME OVER!");
                        System.exit(0);
                    }
                }
                else
                {
                    System.out.println( name + " is not an enemy!" );
                }
            }
        }
    }
    /**
     * unlocks the room which lies in that direciton.
     * @param direction
     */
    public void unlockRoom(String direction)
    {
        Room nextRoom = game.getCurrentRoom().getExit(direction);
        if(nextRoom.is_end_room())
        {   
            if(this.equipment.contains(game.forbidden_key1) && this.equipment.contains(game.forbidden_key2) && this.equipment.contains(game.forbidden_key3))
            {
                nextRoom.open_end_room(game.forbidden_key1,game.forbidden_key2,game.forbidden_key3);
                this.equipment.remove(game.forbidden_key1);
                this.equipment.remove(game.forbidden_key2);
                this.equipment.remove(game.forbidden_key3);
            }
        }
        if(nextRoom.locked())
        {
            for(Entity ent:this.equipment)
            {
                if(ent.getName().contains("Key"))
                {
                    nextRoom.open_room(ent);
                }
            }
        }
    }
    /**
     * changes tthe players current Room to one of the adjacent ones depending upon the direction entered.
     * @param command
     */
    public void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }


        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = game.getCurrentRoom().getExit(direction);
        if (nextRoom != null)
        {
            if (!nextRoom.locked()) {
                game.setCurrentRoom(nextRoom);
            }
            else {
                System.out.println("The door is locked.Maybe theres a key lying somewhere.");
            }
        }
        else
        {
            System.out.println("There is no door there!");
        }
    }
    /**
     * function which uses an arraylist like a stack data structure to go back one room.
     * 
     */
    public void goBack()
    {   
        if(game.visited.size() < 2)
        {
            System.out.println("You cant go back further.");
        }
        else
        {
            game.setCurrentRoom(game.visited.get(game.visited.size() - 2)) ;
            game.visited.remove(game.visited.size() - 1);
        }
        System.out.println(game.getCurrentRoom().getShortDescription());
    }
    /**
     * function which is used to take an item from the a certain list of items and add it to the player's inventory.
     * @param item
     * @param getEntities()
     */
    public void take_item(String item,ArrayList<Entity> entities)
    {   
        boolean found = false;
        for(int i = 0;i<entities.size();i++)
        {
            if(entities.get(i).getName().equalsIgnoreCase(item))
            {
                if(entities.get(i).is_equipment()){
                    found = true;
                    if(entities.get(i).getName().toLowerCase().contains("word"))
                    {
                        if((this.weight + entities.get(i).getWeight() - equipment.get(0).getWeight()) < this.getMaxWeight())
                        {
                                equipment.set(0,entities.get(i));
                        }
                        else
                        {
                            System.out.println("You cannot pick up this item as it exceeds your current weight limit.");
                            break;
                        }
                    }
                    else if(entities.get(i).getName().toLowerCase().contains("hield"))
                    {
                        if(((this.weight + entities.get(i).getWeight()) - equipment.get(1).getWeight()) < this.getMaxWeight())
                        {
                            equipment.set(1,entities.get(i));
                        }
                        else
                        {
                            System.out.println("You cannot pick up this item as it exceeds your current weight limit.");
                            break;
                        }
                    }
                    else
                    {
                        if(((this.weight + entities.get(i).getWeight()) < this.getMaxWeight()))
                        {
                            equipment.add(entities.get(i));
                        }
                        else
                        {
                            System.out.println("You cannot pick up this item as it exceeds your current weight limit.");
                            break;
                        }
                    }
                    System.out.println("Obtained: " + entities.get(i).getName());
                    entities.remove(i);
                }
                else
                {
                    System.out.println("You have to open the chest first before taking its items.");
                }
            }
        }
        if(!found)
        {
            System.out.println(item + " not present.");
        }
        updateWeight();
    }
        /**
         * function used to show the contents of a chest.
         */
    public void open_chest()
    {
        for(int i = 0;i<game.getCurrentRoom().getEntities().size();i++)
        {
            if(game.getCurrentRoom().getEntities().get(i).is_chest())
            {
                game.getCurrentRoom().getEntities().get(i).show_chest_items();
            }
        }
    }
    /**
     * function which is used to take an item from a chest.
     * @param Command  command
     */
    public void take_item_from_chest(Command command)
    {
        String take_item = (command.getSecondWord() + " " + command.getThirdWord()).toLowerCase();
        for(int i = 0;i<game.getCurrentRoom().getEntities().size();i++)
        {
            if(game.getCurrentRoom().getEntities().get(i).is_chest())
            {
                take_item = take_item.toLowerCase();
                this.take_item(take_item,game.getCurrentRoom().getEntities().get(i).getItems());
            }
        }
    }
    /**
     * function which is used to give an item to a certain character in the room.
     * @param String who_gets
     * @param Entity entity
     */
    public void give(String who_gets,Entity entity)
    {  
        for(int i = 0;i<game.getCurrentRoom().getCharacters().size();i++)
        {
            if(game.getCurrentRoom().getCharacters().get(i).getName().equalsIgnoreCase(who_gets))
            {   
                if(game.getCurrentRoom().getCharacters().get(i).isEnemy())
                {
                    System.out.println(who_gets + " is an enemy. Don't give items to enemies. XD");
                    return;
                }
                if(game.getCurrentRoom().getCharacters().get(i).getName().equals("ghost"))
                {   
                    if(game.getCurrentRoom().getCharacters().get(i).given())
                    {
                        return;
                    }
                    game.getCurrentRoom().getCharacters().get(i).getEquipment().add(entity);
                    game.getCurrentRoom().getCharacters().get(i).give_key();
                    this.getEquipment().remove(entity);
                }
            }
        }
    }
    /**
     * function which updates the weight of equipment the player is currently carrying(called when the player drops,takes or gives items.)
     */
    public void updateWeight()
    {   
        weight = 0;
        for(Entity ent:this.equipment)
        {
            weight += ent.getWeight();
        }
    }
    /**
     * function which prints the weight of the items the player is carrying.
     */
    public void showWeight()
    {
        System.out.println("You are carrying " + weight + " kgs of items.");
    }
    /**
     * setter method to set the max weight the player can carry.
     * @param w
     */
    public void setMaxWeight(double w)
    {
        maxWeight = w;
    }
    /**
     * accessor method to get the max weight the player can carry.
     * @return maxWeight
     */
    public double getMaxWeight()
    {
        return maxWeight;
    }
    /**
     * accessor method to get the weight the player is currently carrying.
     * @return weight
     */
    public double getWeight()
    {
        return weight;
    }
    /**
     * function which is called when the player uses medicine.Heals the player for 2000 hp.
     */
    public void heal()
    {
        for(Entity ent:equipment)
        {
            if(ent.getName().equalsIgnoreCase("medicine"))
            {
                this.hp += 2000;
                equipment.remove(ent);
                return;
            }
        }
        System.out.println("You don't have any medicine!");
    }
    /**
     * accesor method to get the current HP of the player.
     * @return hp
     */
    public int getHp()
    {
        return this.hp;
    }
    /**
     * accessor method to get the equipment arraylist of the player.
     * @return ArrayList<> equipment
     */
    public ArrayList<Entity> getEquipment(){
        return this.equipment;
    }
    /**
     * function which is used to implement the player being able to drop items (except for swords or shields.)
     * @param command
     */
    public void drop_item(Command command)
    {
        String item = command.getSecondWord() + command.getSecondWord();
                for(int i = 0;i<this.equipment.size();i++)
                {   
                    if(this.equipment.get(i).getName().equalsIgnoreCase(item))
                    {   
                        if(i > 1)
                        {
                            game.getCurrentRoom().getEntities().add(this.equipment.get(i));
                            this.equipment.remove(i);
                            break;
                        }
                        else
                        {
                            System.out.println("You cant drop ur equipment!");
                        }
                    }
                }
    }
    /**
     * function used to display the dialogues of getCharacters().
     * @param Character chr
     */
    public void talk(Command command)
    {
        if(command.getThirdWord() == null)
        {   
            String chr = command.getSecondWord();
            for(Character ch:game.getCurrentRoom().getCharacters())
            {
                if(ch.getName().equalsIgnoreCase(chr))
                {
                    System.out.println(ch.getDialogue());
                    return;
                }
            }
            System.out.println(chr + " not found in current room.(You can check getCharacters() in the current room through the inspect command.)");
        }
        else
        {
            String chr = command.getSecondWord() + " " + command.getThirdWord();
            for(Character ch:game.getCurrentRoom().getCharacters())
            {
                if(ch.getName().equalsIgnoreCase(chr))
                {
                    System.out.println(ch.getDialogue());
                    return;
                }
            }
            System.out.println(chr + " not found in current room.(You can check getCharacters() in the current room through the inspect command.)");
        }
    }
    /**
     * shows the inventory of the player.
     */
    public void inventory()
    {   
        this.updateWeight();
        System.out.println("Inventory");
        int i = 1;
        for(Entity key: this.getEquipment())
        {   
            System.out.println(i+". " + key.getName());
            i++;
        }
        game.showCombatPower();
        this.showWeight();
        game.getMoreWeight();
        System.out.println("You have " + this.getHp() + " hp remaining.");
    }
}