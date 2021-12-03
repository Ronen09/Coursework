import java.util.*;
/**
 *  This class is the main class of the "Insanity" application. 
 *  "Insanity" is a simple, text based adventure game.  Users 
 *  have to fight through the ruined kings castle to get his blade.
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This class executes the play loop and calls all the necessary functions in other classes.
 * 
 * @author  Michael Kölling and David J. Barnes(edited by Ronen Raj Roy (K21086768))
 * @version 2021.12.01
 */

public class Game extends WorldGen
{
    private Parser parser;
    private Room currentRoom;
    private boolean finished;
    private Character random_ghost;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {   
        super();
        teleporter = new Teleporter(this,transporter_room);
        hp = 10000;
        combat_power = 2100;
        parser = new Parser();
        player = new Player(this,equipment,hp,combat_power,false,"Ronen");
        player.setMaxWeight(6.0);
        addChars();
        currentRoom = start;
    }
    public void updateCombatPower()
    {   
        player.setCombatPower(player.getEquipment().get(0).getCombat_power() + player.getEquipment().get(1).getCombat_power()); 
    }
    public void showCombatPower()
    {
        System.out.println("Your combat power is : " + player.getCombatPower());
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void addChars()
    {
        monster_camp1.getCharacters().add(new Character(this,drop1,2000,2000,true,"Thralls"));
        monster_camp2.getCharacters().add(new Character(this,drop2,2000,3000,true,"Mages"));
        mini_boss.getCharacters().add(new Character(this,drop5,2000,6000,true,"Azdaha"));
        mini_boss1.getCharacters().add(new Character(this,drop4,2000,4000,true,"Thresh"));
        random_ghost = new Character(this,null,2000,0,false,"Wandering Apparition");
        random_ghost.setCurrentRoom(weapon_room);
        random_ghost.setDialogue("Oh do I see another traveller who is trying to brave the horrors of this castle.I have been wandering in the castle halls for more than a century.\n But I have never seen anyone get to the end."
                                + "I will give you one advice,I do know for a fact that one of the forbidden keys are locked in treasure room. Dont forget to explore...");
        treasure_room2.getCharacters().add(random_ghost);
        ghost = new Character(this,drop3,2000,300,false,"ghost");
        ghost.setCurrentRoom(mage_room);
        ghost.setDialogue("As you move through the castle a ghostly apparition comes up to you.\n"
                            +"He says,\"Hello traveler,I am delighted to see you.Just like you, I dared to explore this castle many years ago,but I wasn't strong enough.\"\n"
                            + "\"However I did manage to obtain one of the forbidden keys.If you help me in finding my spellbook,I am willing to give it to you.\"");
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.       
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        if(!checkEnd())
        {
            System.out.println("Thanks for playing!");
        }
        else
        {
            endMsg();
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Insanity!");
        System.out.println("You are King Garen,who has set out to venture ");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.getExitString()); 
    }
    /**
     * Print out the ending message for the player.
     */
    private void endMsg()
    {   
        System.out.println();
        System.out.println("You take the sword and feel its enormous power engulf you.Slowly your mortal skin burns and turns pale.");
        System.out.println("You are not Garen anymore.You are reborn as the next King of Death.But when you meet your queen again,she was sad to see you." );
        System.out.println("\"Why couldn't you just let me rest?\" she said.She vanishes,nowhere to see again.Hearing her harsh words was like blade going through your heart.");
        System.out.println("You cry your heart out,but after a while you just feel empty.Anger and vengenance replace the sadness.You say to yourself,");
        System.out.println("\"If I cannot get my happiness, no one can...\" laughing maniaclly as you raise thousands of thralls to conquer the whole of Earth.");
        System.out.println();
        System.out.println("Thanks,for playing the game,hope you had fun!");
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
        switch(commandWord)
        {
            case "help":    printHelp(); break;            
            case "go":    go(command); break; 
            case "attack":  player.attack(command.getSecondWord()); break;    
            case "unlock": unlock(command); break;
            case "give": give(command); break; 
            case "drop":player.drop_item(command); break; 
            case "back": player.goBack(); break;
            case "use": use(command); break;
            case "take": if(takeItem(command)) return true; break;
            case "inspect": inspect(); break; 
            case "inventory": player.inventory(); break;
            case "quit": wantToQuit = quit(); break;      
            case "open": player.open_chest(); break;
            case "talk": player.talk(command); break;
            default: System.out.println("Unrecognized Command - press help for command info.");
        }
        // else command not recognised.
        return wantToQuit;
    }
    /**
     * setter method to set the current Room to the room specified.
     * @param Room room
     */
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;
    }
    public Entity find_entity(String word)
    {   
        for(Entity entity:player.getEquipment())
        {
            if(entity.getName().equalsIgnoreCase(word))
            {
                return entity;
            }   
        }
        return null;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     */
    private void printHelp() 
    {
        System.out.println("Here are all the commands you can use : ");
        System.out.println("1. go <direction> -- go the room which is present in that direction");
        System.out.println("2. unlock <direction> -- unlock the room which is present in that direction(only if the key in your inventory.)");
        System.out.println("3. attack <character> -- attacks the character specified.");
        System.out.println("4. back -- goes back to the previous room visited.");
        System.out.println("5. inventory -- shows your current inventory.");
        System.out.println("6. take <item> -- take the item from the Current Room");
        System.out.println("7.take <item> from <chest> -- take item from the chest");
        System.out.println("8. talk <character> -- talk to the specified character.");
        System.out.println("9. drop <item> -- drops the item specified.");
        System.out.println("10. give <item> <character> -- gives the item to the character specified");
        System.out.println("11. use <teleporter/medicine> -- use the teleporter or use medicine to heal.");
        System.out.println("12. inspect -- shows all the getCharacters() or items present in the room.");
        System.out.println("12. quit -- Quit the Game.");
    }


    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit() 
    {
        return true;
    }
    /**
     * calls the goRoom function in the player class to change the players current Room.
     * also calls the move commands for the getCharacters().
     * @param Command command 
     */
    private void go(Command command)
    {
        player.goRoom(command);
        ghost.move();
        random_ghost.move();
        if(!currentRoom.is_visited())
        {   
            System.out.println(currentRoom.getLongDescription());
            System.out.println(currentRoom.getShortDescription());
        }
        else
        {
            System.out.println(currentRoom.getShortDescription());
        }
        visited.add(currentRoom);
        currentRoom.has_been_visited(true);
    }
    /**
     * calls the unlock fucntion in the player class to open the room.
     * 
     * @param Command command
     */
    private void unlock(Command command)
    {
    if(direction.contains(command.getSecondWord()))
        {   
            player.unlockRoom(command.getSecondWord());
        }
    }
    /**
     * calls the take item in the player class to take an item from a room or chest.
     * @param command
     * @return boolean(false if the user hasnt taken the final item to end the game,true if the user has taken it.)
     */
    private boolean takeItem(Command command)
    {   String item = "";
        if(command.getThirdWord() == null)
        {
            item = command.getSecondWord();
        }
        else
        {
            item = command.getSecondWord() +" "+  command.getThirdWord();
        }
        if(command.getFifthWord() == null){
            player.take_item(item,this.currentRoom.getEntities());
            updateCombatPower();
        }
        else
        {
            player.take_item_from_chest(command);
        }
        if(checkEnd())
        {
            return true;
        }
        return false;        
    }
    /**
     * calls the give_item function in the player class to give an item from the player's inventory to the specified character.
     * @param Command command
     */
    public void give(Command command)
    {
        if(command.getFourthWord() == null)
        {   
            if(find_entity(command.getSecondWord()) == null)
            {
                System.out.println(command.getSecondWord() + " not found in inventory.");
                return;
            }
            player.give(command.getThirdWord(),find_entity(command.getSecondWord()));
        }
        else
        {   
            String item = command.getSecondWord() + " " + command.getThirdWord();
            if(find_entity(item) == null)
            {
                System.out.println(item + " not found in inventory.");
                return;
            }
            player.give(command.getFourthWord(),find_entity(item));
        }
        
    }
    /**
     * returns the room in which the player is.
     * @return currentRoom
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    /**
     * calls the user_item function in the teleport or player class 
     * @param Command command
     */
    private void use(Command command)
    {
        if(command.getSecondWord().equalsIgnoreCase("teleporter")  && this.currentRoom == teleporter.teleporter_room())
        {
            teleporter.teleport();
        }
        else if(command.getSecondWord().equalsIgnoreCase("medicine"))
        {
            player.heal();
            System.out.println("You have healed yourself.");
         }
        else
        {
            System.out.println(command.getSecondWord() + " cannot be used.");
        }
    }
    
    /**
     * calls the show_items and show_getCharacters() method from the Room class to show all the items and getCharacters() in the room.
     */
    private void inspect()
    {
        currentRoom.show_items();
        currentRoom.show_characters();
    }
    /**
     * checks if the player has taken the end item and terminates the game.
     * @return true  if item has been taken and false if it isnt taken.
     */
    private boolean checkEnd()
    {
        if(player.getEquipment().contains(legendary_sword))
        {
            return true;
        }
        return false;
    }
    /**
     * displays the amount of weight the player can carry more.
     */
    public void getMoreWeight()
    {
        System.out.println("You can carry " + (player.getMaxWeight() - player.getWeight()) + " kgs of items.");
    }
    /**
     * mutator method to add to the players inventory.
     * @param Entity entity
     */
    public void add_to_inventory(Entity entity)
    {
        this.player.getEquipment().add(entity);
    }
    public static void main(String args[])
    {
        Game game = new Game();
        game.play();
    }
}
