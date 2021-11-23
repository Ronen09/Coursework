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
    public ArrayList<Entity> equipment;
    public int hp;
    private Entity hillichurl;
    public int combat_power;
    public Player player;
    public Room previous_room;
    Room start, monster_camp1, monster_camp2, mini_boss1, monster_camp3,weapon_room,treasure_room1,treasure_room2,mage_room,misc_room,mini_boss,boss_room,transporter_room;
    Character companion;
    ArrayList<Room> visited;
    public Teleporter teleporter;
    ArrayList<String> direction;
    Entity treasure_key;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {      
        visited = new ArrayList<>();
        
        equipment = new ArrayList<>();
        equipment.add((new Entity(false,this,100,"Sword",true,null,0.5)));
        equipment.add((new Entity(false,this,100,"Shield",true,null,0.5)));
        equipment.add((new Entity(false,this,0,"Medicine",false,null,0.5)));
        createRooms();
        teleporter = new Teleporter(this,transporter_room);
        hp = 10000;
        combat_power = 200;
        parser = new Parser();
        player = new Player(this,equipment,hp,combat_power,false,"Ronen");
        player.setMaxWeight(2.0);
        ArrayList<Entity> comp1 = new ArrayList<>();
        companion= new Character(false,this,comp1,2000,200,true,"Zhongli");
        direction = new ArrayList<>();
        direction.add("north");
        direction.add("south");
        direction.add("east");
        direction.add("west");
    }
    public void updateCombatPower()
    {   
        if(companion.is_companion())
        {
            player.combat_power = companion.equipment.get(0).combat_power+player.equipment.get(0).combat_power + player.equipment.get(1).combat_power;
        }
        else
        {
            player.combat_power = player.equipment.get(0).combat_power + player.equipment.get(1).combat_power;
        }
    }
    public void showCombatPower()
    {
        System.out.println("Your combat power is : " + player.combat_power);
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
          
        // create the rooms
        start = new Room("1");
        start.setRoomDescription("You have entered the castle of the ruined king.");
        visited.add(start);
        monster_camp1 = new Room("3");
        monster_camp1.setRoomDescription("As you move through the castle you encounter some thralls lying dormant.\n" +
                                        "However they are suspectible to noise and your heavy armor makes its impossible to move past them stealthily.");
        monster_camp2 = new Room("4");
        monster_camp2.setRoomDescription("Instead of moving deeper down the castle you explore the area around you.\n" +
                                        "You see a room leading to more thralls.");
        monster_camp3= new Room("5");
        monster_camp3.setRoomDescription("You continue on with wounds,and your blood stained sword.");
        mini_boss1 = new Room("9");
        mini_boss1.setRoomDescription("You enter the room.\n Thresh(The warden of death) is there.\n It almost looks like he was waiting for you.\n"
                                        +"\"What brings you here,wandering soul?\",he says \n" + 
                                        "You say to him,with absolute confidence,"+"\"I want to take the Blade Of The Ruined King and bring back my dead queen\""
                                        +"Thresh laughs at you,mockingly \"Only if you get through me.. \"");
        weapon_room = new Room("2");
        weapon_room.setRoomDescription("You enter the armory,containing the equipment of many adventurers who tried to brave the horrors of the castle, only to succumb to them.");
        mage_room = new Room("6");
        mage_room.setRoomDescription("As you go deeper,you are often greeted by the sights of mutilated corpses,who couldnt face the horrors of the castle.");
        mage_room.setRoomDescription("You continue on,fueled by the motivation to bring back your precious queen.....");
        mini_boss = new Room("11");
        mini_boss.setRoomDescription("You can sense that you are close to the legendary Blade.\n But,as you venture into a huge hall,you are interrupted by loud growling noises.\n"
                                        +"A big dragon,black and green,flies down and lands in front of your destination \n" + 
                                        "Its Azdaha,the dragon of Ruined King who fashioned it with the immense power of the blade.\n"+"You shudder at the mere sight"
                                        +"But nothing..nothing would deter you from your goal.");
        boss_room = new Room("12");
        treasure_room1 = new Room("7");
        treasure_room1.setRoomDescription("Your curiosity has led you to a treasure room with some mages trying to guard it");
        treasure_room2 = new Room("10");
        treasure_room2.setRoomDescription("You enter another room,where you see a huge mob of thralls.");
        misc_room = new Room("8");
        misc_room.setRoomDescription("You have finally got all the 3 keys to unlock the locks to the door which lead to the Blade./n You feel happy,hoping to be finally reunited by the woman you loved all your life.");
        transporter_room = new Room("Transporter_Room");
        transporter_room.setRoomDescription("You unlock the door to this room with a strange looking apparatus. \n"
                                            +"The writing on the wall reads\"This is a magical teleporter,which teleports you to any of the rooms of the castle which you have already VISITED.");
        // initialise room exits
        start.setExit("east", weapon_room);
        start.setExit("south",monster_camp1);
        start.setExit("west",transporter_room);
        
        transporter_room.setExit("east",start);
        transporter_room.is_locked(treasure_key);
        transporter_room.is_locked(treasure_key);
        
        weapon_room.setExit("west",start);
        weapon_room.entities.add(new Entity(false,this,150,"silver sword",true,null,1.1));
        weapon_room.entities.add(new Entity(false,this,200,"spartan shield",true,null,0.5));
        treasure_key = new Entity(false,this,0,"iron key",true,null,1.1);
        weapon_room.entities.add(treasure_key);
        ArrayList<Entity> treasure_items1 = new ArrayList<Entity>();
        treasure_items1.add((new Entity(false,this,200,"gold sword",true,null,0.3)));
        treasure_items1.add((new Entity(false,this,200,"bronze shield",true,null,0.2)));
        weapon_room.entities.add((new Entity(true,this,0,"Treasure_Chest",false,treasure_items1,0.1)));

          
        monster_camp1.setExit("north",start);
        monster_camp1.setExit("west",monster_camp2);
        monster_camp1.setExit("south",mage_room);
        ArrayList<Entity> drop1 = new ArrayList<>();
        drop1.add(new Entity(false,this,0,"medicine",true,null,0.3));
        monster_camp1.characters.add(new Character(false,this,drop1,2000,200,true,"Thralls"));
        monster_camp1.entities.add(new Entity(false,this,200,"Treasure Key",false,null,0.5));
        
        monster_camp2.setExit("south",treasure_room1);
        monster_camp2.setExit("east",monster_camp1);

        mage_room.setExit("north",monster_camp1);
        mage_room.setExit("west",monster_camp4);
        mage_room.setExit("south",mini_boss);

        monster_camp4.setExit("east",mage_room);
        
        mini_boss.setExit("north",mage_room);
        mini_boss.setExit("west",treasure_room2);
        mini_boss.setExit("south",monster_camp3);
        
        monster_camp3.setExit("north",mini_boss);
        monster_camp3.setExit("east",misc_room);
        monster_camp3.setExit("south",boss_room);

        misc_room.setExit("west",monster_camp3);
        
        boss_room.setExit("north",monster_camp3);
        

        currentRoom = start;  // start game outside
        previous_room = start;
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
            previous_room = currentRoom;
            player.goRoom(command);
            if(currentRoom != previous_room)
            {
                if(!currentRoom.is_visited())
                {   
                    currentRoom.has_been_visited(true);
                    if(teleporter.teleporter_room() != currentRoom)
                    {
                        visited.add(currentRoom);
                    }
                    System.out.println(currentRoom.getLongDescription());
                    System.out.println(currentRoom.getShortDescription());
                }
                else
                {
                    System.out.println(currentRoom.getShortDescription());
                }
            }
            }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if(commandWord.equals("inventory")){
            inventory();
        }
        else if(commandWord.equals("take")){
            String item = command.getSecondWord() +" "+  command.getThirdWord();
            if(command.getFifthWord() == null){
                player.take_item(item,this.currentRoom.entities);
                updateCombatPower();
                showCombatPower();
            }
            else
            {
                player.take_item_from_chest(command);
                updateCombatPower();
                showCombatPower();
            }
        }
        else if(commandWord.equals("open")){
            player.open_chest();
            }
        else if(commandWord.equals("inspect")){
            currentRoom.show_items();
        }
        else if(commandWord.equals("back")){
            player.goBack();
            System.out.println(currentRoom.getShortDescription());
        }
        else if(commandWord.equals("attack")){
            player.attack(command.getSecondWord());
        }
        else if(commandWord.equals("give")){
            if(command.getFourthWord() == null)
            {
                player.give(command.getThirdWord(),find_entity(command.getSecondWord()));
                player.equipment.remove(player.equipment.indexOf(find_entity(command.getSecondWord().toLowerCase())));
            }
            else
            {   
                String item = command.getSecondWord() + " " + command.getThirdWord();
                player.give(command.getFourthWord(),find_entity(item));
                player.equipment.remove(player.equipment.indexOf(find_entity(item)));
            }
            companion.to_companion();
        }
        else if(commandWord.equals("use"))
        {
            if(command.getSecondWord().equalsIgnoreCase("teleporter")  && this.currentRoom == teleporter.teleporter_room());
            {
                teleporter.teleport();
            }
        }
        else if(commandWord.equals("unlock"))
        {
            if(direction.contains(command.getSecondWord()))
            {
                player.unlockRoom(command.getSecondWord());
            }
        }
        else if(commandWord.equals("drop"))
        {
            String item = command.getSecondWord();
            for(int i = 0;i<player.equipment.size();i++)
            {   
                if(player.equipment.get(i).name.equalsIgnoreCase(item))
                {   
                    if(i > 1)
                    {
                        currentRoom.entities.add(player.equipment.get(i));
                        player.equipment.remove(i);
                        break;
                    }
                    else
                    {
                        System.out.println("You cant drop ur equipment!");
                    }
                }
            }
        }
        // else command not recognised.
        return wantToQuit;
    }
    public Entity find_entity(String word)
    {   
        for(Entity entity:player.equipment)
        {
            if(entity.name.equalsIgnoreCase(word))
            {
                return entity;
            }   
        }
        return null;
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
        for(Entity key:equipment)
        {   
            System.out.println(i+". " + key.name);
            i++;
        }
    }
    public static void main(String args[])
    {
        Game game = new Game();
        game.play();
    }
}
