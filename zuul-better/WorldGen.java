import java.util.*;
/**
 * Class used to create and initialize the rooms along with the items in them
 * @author Ronen Raj Roy(K21086768)
 * @version 2021.12.01
 */
public class WorldGen
{
    public ArrayList<Entity> equipment = new ArrayList<>();;
    public int hp;
    public Character ghost;
    public int combat_power;
    public Player player;
    public Room previous_room;
    public Room start, monster_camp1, monster_camp2, mini_boss1, monster_camp3,weapon_room,treasure_room1,treasure_room2,mage_room,misc_room,mini_boss,end_room,transporter_room;
    public ArrayList<Room> visited = new ArrayList<>();;
    public Teleporter teleporter;
    public ArrayList<String> direction;
    public ArrayList<Entity> drop1 = new ArrayList<>();
    public ArrayList<Entity> drop2 = new ArrayList<>();
    public ArrayList<Entity> drop3 = new ArrayList<>();
    public ArrayList<Entity> drop4 = new ArrayList<>();
    public ArrayList<Entity> drop5 = new ArrayList<>();
    Entity treasure_key,treasure_key2,treasure_key3,treasure_key4,forbidden_key1,forbidden_key2,forbidden_key3,Thresh_wep,legendary_sword;
    WorldGen()
    {   
        equipment.add((new Entity(1000,"Sword",0.5)));
        equipment.add((new Entity(1100,"Shield",0.5)));
        equipment.add((new Entity(0,"Medicine",0.5)));
        direction = new ArrayList<>();
        direction.add("north");
        direction.add("south");
        direction.add("east");
        direction.add("west");
        createRooms();
        addItems();
    }
    /**
     * creates all the rooms in the game as well as setting the exits.
     */
    private void createRooms()
    {
        start = new Room(" start location.");
        start.setRoomDescription("You have entered the castle of the ruined king.");
        visited.add(start);
        monster_camp1 = new Room(" the castle quarters.");
        monster_camp1.setRoomDescription("As you move through the castle you encounter some thralls lying dormant.\n" +
                                        "However they are suspectible to noise and your heavy armor makes its impossible to move past them stealthily.");
        monster_camp2 = new Room(" a chamber leading to the first treasure room.");
        monster_camp2.setRoomDescription("Instead of moving deeper down the castle you explore the area around you.\n" +
                                        "You see a room leading to more thralls.");
        monster_camp3= new Room(" the chapel.");
        monster_camp3.setRoomDescription("You continue on with wounds,and your blood stained sword.");
        mini_boss1 = new Room(" Thresh's room.");
        mini_boss1.setRoomDescription("You enter the room.\n Thresh(The warden of death) is there.\n It almost looks like he was waiting for you.\n"
                                        +"\"What brings you here,wandering soul?\",he says \n" + 
                                        "You say to him,with absolute confidence,"+"\"I want to take the Blade Of The Ruined King and bring back my dead queen\""
                                        +"Thresh laughs at you,mockingly \"Only if you get through me.. \"");
        weapon_room = new Room(" the weapon room.");
        weapon_room.setRoomDescription("You enter the armory,containing the equipment of many adventurers who tried to brave the horrors of the castle, only to succumb to them.");
        mage_room = new Room(" quadragle of the castle.");
        mage_room.setRoomDescription("As you go deeper,you are often greeted by the sights of mutilated corpses,who couldnt face the horrors of the castle.");
        mage_room.setRoomDescription("You continue on,fueled by the motivation to bring back your precious queen.....");
        mini_boss = new Room(" a massive hall.");
        mini_boss.setRoomDescription("You can sense that you are close to the legendary Blade.\n But,as you venture into a huge hall,you are interrupted by loud growling noises.\n"
                                        +"A big dragon,black and green,flies down and lands in front of your destination \n" + 
                                        "Its Azdaha,the dragon of Ruined King who fashioned it with the immense power of the blade.\n"+"You shudder at the mere sight.."
                                        +"But nothing..nothing would deter you from your goal.");
        end_room = new Room(" the throne room.");
        end_room.setRoomDescription("Finally you enter the room where you can see the Blade floating in its case.You speak the incatantion to remove the barriers and it goes down.\nFinally the legendary Blade Of The Ruined King lies there,waiting for you to take it.");
        treasure_room1 = new Room(" the first treasure room.");
        treasure_room1.setRoomDescription("Your curiosity has led you to a treasure room.");
        treasure_room2 = new Room(" the second treasure room.");
        treasure_room2.setRoomDescription("You encounter another treasure room.");
        misc_room = new Room(" the dining halls.");
        misc_room.setRoomDescription("You venture on.\n You feel happy,hoping to be finally reunited by the woman you loved all your life.");
        transporter_room = new Room(" the Teleporter Room");
        transporter_room.setRoomDescription("You unlock the door to this room with a strange looking apparatus. \n"
                                            +"The writing on the wall reads\"This is a magical teleporter,which teleports you to any of the rooms of the castle which you have already VISITED.");
        // initialise room exits
        start.setExit("east", weapon_room);
        start.setExit("south",monster_camp1);
        start.setExit("west",transporter_room);
        
        weapon_room.setExit("west",start);
        
        transporter_room.setExit("east",start);
        
        monster_camp1.setExit("north",start);
        monster_camp1.setExit("west",monster_camp2);
        monster_camp1.setExit("south",mage_room);
        
        monster_camp2.setExit("south",treasure_room1);
        monster_camp2.setExit("east",monster_camp1);
        
        treasure_room1.setExit("north",monster_camp2);
        
        mage_room.setExit("north",monster_camp1);
        mage_room.setExit("south",mini_boss1);
        
        mini_boss1.setExit("north",mage_room);
        mini_boss1.setExit("west",treasure_room2);
        mini_boss1.setExit("south",misc_room);
        
        treasure_room2.setExit("east",mini_boss1);
        misc_room.setExit("north",mini_boss1);
        misc_room.setExit("south",mini_boss);
        
        mini_boss.setExit("north",misc_room);
        mini_boss.setExit("south",end_room);
        
        end_room.setExit("north",mini_boss);
        end_room.is_end();

    }
    /**
     * Adds all the items to the various rooms.
     */
    private void addItems()
    {
        weapon_room.getEntities().add(new Entity(2100,"Silver Sword",1.1));
        weapon_room.getEntities().add(new Entity(1500,"Spartan Shield",0.5));
        
        treasure_key = new Entity(0,"Iron Key",1.1);
        weapon_room.getEntities().add(treasure_key);
        
        transporter_room.is_locked(treasure_key);
        
        ArrayList<Entity> treasure_items1 = new ArrayList<Entity>();
        treasure_items1.add((new Entity(2500,"Gold Sword",1.0)));
        treasure_items1.add((new Entity(2000,"Bronze Shield",1.2)));
        weapon_room.getEntities().add((new Chest("Treasure Chest",treasure_items1)));
        
        
        drop1.add(new Entity(0,"Mysterious SpellBook",0.3));
        
        treasure_key2 = new Entity(0,"Silver Key",1.1);
        drop2.add(treasure_key2);
        treasure_room1.is_locked(treasure_key2);
        
        ArrayList<Entity> treasure_items2 = new ArrayList<Entity>();
        treasure_items2.add((new Entity(4000,"Sapphire Sword",0.8)));
        treasure_items2.add(new Entity(0,"Medicine",0.5));
        treasure_room1.getEntities().add((new Chest("Treasure Chest",treasure_items2)));
        
        forbidden_key1 = new Entity(0,"Forbidden Key_2",0.3);
        drop3.add(forbidden_key1);
        mage_room.getEntities().add((new Entity(1700,"Pantheon's Shield",0.3)));
        
        Thresh_wep = new Entity(4000,"Thresh's Shield",1.1);
        drop4.add(Thresh_wep);
        drop4.add(new Entity(0,"Medicine",0.5));
        
        treasure_key3 = new Entity(0,"Gold Key",0.2);
        misc_room.getEntities().add(treasure_key3);
        
        ArrayList<Entity> treasure_items3 = new ArrayList<Entity>();
        forbidden_key2 = new Entity(0,"Forbidden Key_1",0.3);
        treasure_items3.add((new Entity(5000,"Diamond Sword",0.9)));
        treasure_items3.add(forbidden_key2);
        treasure_room2.getEntities().add((new Chest("Treasure Chest",treasure_items3)));
        treasure_room2.is_locked(treasure_key3);
        
        forbidden_key3 = new Entity(0,"Forbidden Key_3",0.3);
        drop5.add(forbidden_key3);
        
        legendary_sword = new Entity(10000,"Legendary_Sword_of The_Ruined_King",1.0);
        end_room.getEntities().add(legendary_sword);
    }
}