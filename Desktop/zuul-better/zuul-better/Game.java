import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    public Room currentRoom;
    private HashMap<String,Entity> equipment;
    public int hp;
    private Entity hillichurl;
    public int combat_power;
    Room start, monster_camp1, monster_camp2, monster_camp4, monster_camp3,weapon_room,treasure_room1,treasure_room2,companion_room,misc_room,mini_boss,boss_room;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {   
        equipment = new HashMap<>();
        equipment.put("sword",(new Entity(false,this,100,"Sword",true)));
        equipment.put("armor",(new Entity(false,this,100,"Shield",true)));
        equipment.put("misc",(new Entity(false,this,0,"Medicine",false)));
        createRooms();
        hp = 1000;
        combat_power = 200;
        parser = new Parser();
        Room start, monster_camp1, monster_camp2, monster_camp4, monster_camp3,weapon_room,treasure_room1,treasure_room2,companion_room,misc_room,mini_boss,boss_room;
    }
    public void updateCombatPower()
    {
        combat_power = equipment.get("sword").combat_power + equipment.get("misc").combat_power + equipment.get("armor").combat_power;
    }
    public void showCombatPower()
    {
        System.out.println("Your combat power is : " + combat_power);
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
          
        // create the rooms
        start = new Room("outside the main entrance of the university");
        monster_camp1 = new Room("in a lecture theater");
        monster_camp4 = new Room("in the campus pub");
        monster_camp2= new Room("in a computing lab");
        monster_camp3 = new Room("in the computing admin office");
        weapon_room = new Room("in the computing admin office");
        companion_room = new Room("in the computing admin office");
        mini_boss = new Room("in the computing admin office");
        boss_room = new Room("in the computing admin office");
        treasure_room1 = new Room("in the computing admin office");
        treasure_room2 = new Room("in the computing admin office");
        misc_room = new Room("A room left in the wake of destruction.");
        // initialise room exits
        start.setExit("east", weapon_room);
        start.setExit("south",monster_camp1);

        weapon_room.setExit("west",start);
        weapon_room.entities.add(new Entity(false,this,150,"Silver Sword",true));
        weapon_room.entities.add(new Entity(false,this,200,"Spartan Shield",true));
        
        monster_camp1.setExit("north",start);
        monster_camp1.setExit("west",monster_camp2);
        monster_camp1.setExit("south",companion_room);
        monster_camp1.entities.add(new Entity(true,this,300,"Hillichurl Pack",true));
        monster_camp1.entities.add(new Entity(false,this,200,"Treasure Key",false));
        
        monster_camp2.setExit("south",treasure_room1);
        monster_camp2.setExit("east",monster_camp1);

        companion_room.setExit("north",monster_camp1);
        companion_room.setExit("west",monster_camp4);
        companion_room.setExit("south",mini_boss);

        monster_camp4.setExit("east",companion_room);
        
        mini_boss.setExit("north",companion_room);
        mini_boss.setExit("west",treasure_room2);
        mini_boss.setExit("south",monster_camp3);
        
        monster_camp3.setExit("north",mini_boss);
        monster_camp3.setExit("east",misc_room);
        monster_camp3.setExit("south",boss_room);

        misc_room.setExit("west",monster_camp3);
        
        boss_room.setExit("north",monster_camp3);
        

        currentRoom = start;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("inventory")){
            inventory();
        }
        else if(commandWord.equals("take")){
            take_item(command);
            updateCombatPower();
            showCombatPower();
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
        if(currentRoom == start)
        {
            this.hp = 1000;
            System.out.println("You have successfully restored your hp!");
        }
        if(!currentRoom.entities.isEmpty())
        {   
            if(currentRoom.entities.get(0).IS_ENEMY)
            {
                currentRoom.entities.get(0).attack();
                currentRoom.entities.remove(0);
            }
            show_items();
        }
    }
    private void show_items()
    {
        System.out.println("The room has the following items.");
        for(int i = 0;i<currentRoom.entities.size();i++)
        {
            System.out.println((i+1) + ". " + currentRoom.entities.get(i).name);
        }
    }
    private void take_item(Command command)
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
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    private void inventory()
    {   
        System.out.println("Inventory");
        int i = 1;
        for(Entity key:equipment.values())
        {   
            System.out.println(i+". " + key.name);
            i++;
        }
    }
}
