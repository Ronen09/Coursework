import java.util.*;
/**
 * This class is used to implement the teleport functionality in the game.
 * @author Ronen Raj Roy (K21086768)
 * @version 2021.12.01
 */
public class Teleporter
{   
    Game game;
    private Room room;
    private Random rand;
    Teleporter(Game game,Room room)
    {
        this.game = game;
        this.room = room;
        rand = new Random();
    }
    /**
     * teleports the current player to a random room which the player has already visited.
     */
    public void teleport()
    {   
        int rand_num = rand.nextInt(game.visited.size());
        Room currentRoom = game.visited.get(rand_num);
        if(currentRoom != this.room)
        {
            game.setCurrentRoom(currentRoom);
            System.out.println(game.getCurrentRoom().getShortDescription());
        }
        else
        {
            teleport(); 
        }
    }
    /**
     * accesor method to get the room in which the teleporter is present.
     * @return Room room
     */
    public Room teleporter_room()
    {
        return this.room;
    }
    
}