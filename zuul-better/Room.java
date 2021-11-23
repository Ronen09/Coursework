import java.util.Set;
import java.util.HashMap;
import java.util.*;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String name;
    private HashMap<String, Room> exits;
    public ArrayList<Entity> entities;// stores exits of this room.
    public ArrayList<Character>characters;
    private Game game;
    private boolean visited;
    private boolean is_locked;
    private Entity key;
    private HashMap<Room,String> room_descriptions;
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
    public void is_locked(Entity key)
    {
        is_locked = true;
        this.key = key;
    }
    public void open_room(Entity key)
    {
        if(this.key == key)
        {
            is_locked = false;
            System.out.println("Congratulations! You have unlocked the door.");
            System.out.println("You can use the go command now!");
        }
    }
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
    private String getExitString()
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
    public void show_items()
    {   
        System.out.println("The room has the following items.");
        for(int i = 0;i<this.entities.size();i++)
        {
            System.out.println((i+1) + ". " + this.entities.get(i).name);
        }
    }
    public boolean is_visited()
    {
        return visited;
    }
    public void has_been_visited(boolean b)
    {
        this.visited = true;
    }
}

