import java.util.*;
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
    public void teleport()
    {
        int rand_num = rand.nextInt(game.visited.size());
        game.currentRoom = game.visited.get(rand_num);
        System.out.println(game.currentRoom.getLongDescription());
    }
    public Room teleporter_room()
    {
        return this.room;
    }
    
}