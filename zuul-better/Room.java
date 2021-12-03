import java.util.Set;
import java.util.HashMap;
import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "Insanity" application. 
 * "Insanity" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes and Ronen Raj Roy(K21086768)
 * @version 2021.12.01
 */

public class Room 
{
    private String name;
    private HashMap<String, Room> exits;
    private ArrayList<Entity> entities;// stores exits of this room.
    private ArrayList<Character>characters;
    private Game game;
    private boolean visited;
    private boolean is_locked;
    private Entity key;
    private HashMap<Room,String> room_descriptions;
    private boolean end_room;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String name) 
    {
        this.name = name;
        exits = new HashMap<>();
        entities = new ArrayList<>();
        characters = new ArrayList<>();
        visited = false;
        room_descriptions = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    /**
     * mutator method to set the room as a locked room with a certain key
     * @param Entity key
     */
    public void is_locked(Entity key)
    {
        is_locked = true;
        this.key = key;
    }
    /**
     * mutator method the set the current Room
     */
    public void is_end()
    {
        is_locked = true;
        this.end_room = true;
    }
    /**
     * function which opens the current room if the user has the correct key
     */
    public void open_room(Entity key)
    {
        if(this.key == key)
        {
            is_locked = false;
            System.out.println("Congratulations! You have unlocked the door.");
            System.out.println("You can use the go command now!");
        }
    }
    /**
     * function which opens the end room if the user has all the three keys
     */
    public void open_end_room(Entity key1,Entity key2,Entity key3)
    {
            is_locked = false;
            System.out.println("You unlocked the door leading to the throne room...");
            System.out.println("You can use the go command now!");
    }
    /**
     * accesor method to know if the room is locked
     * @return boolean b
     */
    public boolean locked()
    {
        return is_locked;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public void setRoomDescription(String str)
    {
        room_descriptions.put(this,str);
    }
    public String getShortDescription()
    {
        return "You are in" + name + ".\n" + getExitString();
    }
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return room_descriptions.get(this);
    }
    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    /**
     * function to print all the items in the current room
     */
    public void show_items()
    {   if(this.entities.size() == 0)
        {
            System.out.println("This room has no items.");
            return;
        }
        System.out.println("The room has the following items.");
        for(int i = 0;i<this.entities.size();i++)
        {
            System.out.println((i+1) + ". " + this.entities.get(i).getName() + "(Combat Power - " + this.entities.get(i).getCombat_power() + ", Weight - " + this.entities.get(i).getWeight() + " )");
        }
    }
    /**
     * function to print all the characters in the current room
     */
    public void show_characters()
    {   
        if(this.characters.size() == 0)
        {
            System.out.println("This room has no characters.");
        }
        else{
            System.out.println("This room has the following characters.");
            for(int i = 0;i<this.characters.size();i++)
            {   
                System.out.println((i+1 + ". " + this.characters.get(i).getName() + " (" + this.characters.get(i).getCombat_power() + " )"));
            }
        }
    }
    /**
     * acccesor method to get if the room has been visited
     * @return visited
     */
    public boolean is_visited()
    {
        return visited;
    }
    /**
     * sets the current room as visited 
     * @param boolean b
     */
    public void has_been_visited(boolean b)
    {
        this.visited = true;
    }
    /**
     * accesor method to know if the room is the end room.
     * @return boolean end_room;
     */
    public boolean is_end_room()
    {
        return this.end_room;
    }
    /**
     * accesor method to get all the characters present in this room
     * @return ArrayList<Character> characters
     */
    public ArrayList<Character> getCharacters()
    {
        return this.characters;
    }
    /**
     * accesor method to get all the items present in the current Room
     * @return ArrayList<Entity> items
     */
    public ArrayList<Entity> getEntities()
    {
        return this.entities;
    }
}

