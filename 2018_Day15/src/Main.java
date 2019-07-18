import java.util.List;

public class Main {

    public static void main(String args[]){

    }

    private class Character{

        private final int attack, initHealth, initX, initY;
        private final String faction;
        private int x, y, health;

        public Character(int x, int y, int attack, int health, String faction){
            this.initHealth = health;
            this.initX = x;
            this.initY = y;
            this.attack = attack;
            this.faction = faction;
        }

        public boolean move(){
            
        }

        public void attack(){

        }

        public int x(){
            return x;
        }

        public int y(){
            return y;
        }
    }

}
