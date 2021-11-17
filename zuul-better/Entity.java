
class Entity
{   
    boolean IS_ENEMY;
    String name;
    Room can_go;
    Game game;
    int combat_power;
    boolean isEquipment;
    Entity(boolean is_enemy,Game G,int combatPower,String name,boolean isEquipment)
    {
        this.IS_ENEMY = is_enemy;
        this.game = G;
        this.combat_power = combatPower;
        this.name = name;
        this.isEquipment = isEquipment;
    }
    

    public void attack()
    {   
        if(IS_ENEMY)
        {
            System.out.println("You have been attacked by " + this.name + " who has combat power of " + this.combat_power + " ! ");
            System.out.println("You are succesful in fighting him off!");
            game.hp = game.hp - this.combat_power;
            System.out.println("You have " + game.hp + " health remaining");
        }
    }
    public void get_found()
    {   
        if(isEquipment)
        {
            System.out.println("You have found a new item !");
            System.out.print("Name : " + this.name + "Combat Power :" + this.combat_power);
            System.out.print("Do you want to take it?");
        }
    }
}