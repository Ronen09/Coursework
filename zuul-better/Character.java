import java.util.*;
/**
 * Class to implement getCharacters() along with their functionalities
 * 
 * @author Ronen Raj Roy(K21086768)
 * @version 2021-12-01
 */
class Character
{
    boolean is_player;
    Game game;
    private ArrayList<Entity> equipment;
    private int hp;
    private int combat_power;
    private String dialogue;
    private String name;
    private Room currentRoom;
    private boolean is_enemy;
    private boolean gave;
    public Character(Game game, ArrayList<Entity> equipment, int hp, int combat_power, boolean is_enemy, String name)
    {
        this.game = game;
        this.equipment = equipment;
        this.hp = hp;
        this.combat_power = combat_power;
        this.name = name;
        this.is_enemy = is_enemy;
    }
    /**
     * accesor method to get if the current character is an enemy.
     * @return boolean is_enemy
     */
    public boolean isEnemy()
    {
        return this.is_enemy;
    }
    /**
     * accesor method to know if the character has already given his item.
     * @return boolean gave
     */
    public boolean given()
    {   
        System.out.println("I have already given you what I possess,traveller.");
        return this.gave;
    }
    /**
     * accesor to get the name of the character
     * @return String name
     */
    public String getName() {
        return name;
    }
    /**
     * setter method to set the currentRoom of the character
     * @param Room currentRoom
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        this.currentRoom.getCharacters().add(this);
    }
    /**
     * accesor method to get the combat power of the character.
     * @return int combat_power
     */
    public int getCombat_power() {
        return combat_power;
    }
    /**
     * function which moves the character in a random direction with some probablity.
     */
    public void move()
    {
        Random rand = new Random();
        Room nextRoom;
        String directions[] = {"north","south","west","east"};
        if(Math.random() > 0.8)
        {
            nextRoom = currentRoom.getExit(directions[rand.nextInt(4)]);
            if(nextRoom != null)
            {   
                if(!nextRoom.locked())
                {
                    currentRoom.getCharacters().remove(this);
                    currentRoom = nextRoom;
                    currentRoom.getCharacters().add(this);
                }
            }
        }
        check();
    }
    /**
     * setter method to set the dialogue of the character.
     * @param String dialogue
     */
    public void setDialogue(String dialogue)
    {
        this.dialogue = dialogue;
    }
    /**
     * accesor method to get the dialogue of the character.
     * @return String dialogue
     */
    public String getDialogue()
    {
        return dialogue;
    }
    /**
     * function which is used to print a statement letting the player know that the character is in the same room as the player.
     */
    public void meet()
    {
        System.out.println("You see " + this.getName()+ " wandering in the castle.");
    }
    /**
     * function used to check if the character is in the same room as the player.
     */
    private void check()
    {
        if(game.getCurrentRoom() == this.currentRoom)
        {
            meet();
        }
    }
    /**
     * function used to make the character give the certain item to the player provided the player gives it the required item.
     */
    public void give_key()
    {   
        game.player.getEquipment().add(this.equipment.get(0));
        System.out.println("Thanks for finding my spellbook.Now as per my promise,here is the 2nd Forbidden Key.");
        System.out.println("Obtained: " + this.equipment.get(0).getName());
        gave = true;
    }
    /**
     * accesor method to get the getCharacters() equipment.
     * @return ArrayList<Entity> equipment
     */
    public ArrayList<Entity> getEquipment()
    {
        return this.equipment;
    }
}
