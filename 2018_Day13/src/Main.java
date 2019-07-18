import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static char[][] map = new char[0][0];
    private static List<Cart> carts = new ArrayList<>();
    public static void main(String args[]) throws InterruptedException{
        map = readFile("Day13");
        init();
        boolean crashed = false;
        while (!crashed) {
            carts.sort(Cart::compareTo);
            for (Cart c : carts){
               c.update();
               if (checkCollision()){
                   printColisions();
                   crashed = true;
               }
            }
//            printMap();
//            Thread.sleep(500);
        }

        for (Cart c : carts){
            c.reset();
        }

        while (carts.size() > 1){
            carts.sort(Cart::compareTo);
            for (int i = 0; i < carts.size(); i++){
                Cart c = carts.get(i);
                c.update();
                checkCollision();
            }
            for (int i = carts.size()-1; i >= 0; i--){
                Cart c = carts.get(i);
                if (c.isDead()){
                    carts.remove(c);
                }
            }
        }
        System.out.println("p2: "+carts.get(0).x+","+carts.get(0).y);
    }

    private static boolean checkCollision(){
        boolean crash = false;
        for (int i = 0; i < carts.size(); i++){
            Cart c1 = carts.get(i);
            for (int j = i+1; j < carts.size(); j++){
                Cart c2 = carts.get(j);
                if (c1.isDead() && c2.isDead()){
                    continue;
                }
                if (c1.x == c2.x && c1.y == c2.y){
                    c1.kill();
                    c2.kill();
                    crash = true;
                }
            }
        }
        return crash;
    }

    private static void printColisions(){
        for (Cart c : carts){
            if (c.isDead()){
                System.out.println("("+c.x+","+c.y+")");
            }
        }
    }

    private static void printMap(){
        String s = "";
        for (int y = 0; y < map.length; y++){
            for (int x = 0; x < map[y].length; x++){
                boolean set = false;
                for (Cart c : carts){
                    if (c.x == x && c.y == y){
                        s += c.getChar();
                        set = true;
                        break;
                    }
                }
                if (!set){
                    s += map[y][x];
                }
            }
            s += "\n";
        }
        System.out.println(s);
    }

    private static class Cart implements Comparable<Cart>{

        private static char[] rotations = {'^', '>','v','<'};
        private final int initX, initY, initRot;
        public int x, y;
        private int rot, lastIntersection = 1;
        private boolean dead = false;

        public Cart(int x, int y, int rot){
//            System.out.println("Cart created: ("+x+","+y+")");
            this.initX = x;
            this.initY = y;
            this.initRot = rot;
            reset();
        }

        public void reset(){
            x = initX;
            y = initY;
            rot = initRot;
            lastIntersection = 1;
            dead = false;
        }

        public void update(){
            move();
            interact();
        }

        public void kill(){
            dead = true;
        }

        private void interact(){
            switch(map[y][x]){
                case '\\': rot += rot%2 == 0 ? -1 : 1;
                    break;
                case '/': rot += rot%2 == 0 ? 1 : -1;
                    break;
                case '+':
                    intersection();
                    break;
                default:
                    return;
            }
            rot = (rot+4)%4;
        }

        private void move(){
            if (dead){
                return;
            }
            if (map[y][x] == 'X'){
                dead = true;
                return;
            }
            x += rot%2 == 0 ? 0 : rot < 2 ? 1 : -1;
            y += rot%2 == 1 ? 0 : rot < 1 ? -1 : 1;
        }

        private void intersection(){
            lastIntersection = (lastIntersection+2)%3-1;
            rot += lastIntersection;
        }

        public char getChar(){
            return dead ? 'X' : rotations[rot];
        }

        public boolean isDead(){
            return dead;
        }

        @Override
        public int compareTo(Cart c){
            return this.y > c.y ? 1 : this.y < c.y ? -1 :
                    this.x > c.x ? 1 : this.x < c.x ? -1 : 0;
        }
    }

    private static void init(){
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                switch(map[y][x]){
                    case '<':
                        map[y][x] = '-';
                        carts.add(new Cart(x, y, 3));
                        break;
                    case '>':
                        map[y][x] = '-';
                        carts.add(new Cart(x, y, 1));
                        break;
                    case '^':
                        map[y][x] = '|';
                        carts.add(new Cart(x, y, 0));
                        break;
                    case 'v':
                        map[y][x] = '|';
                        carts.add(new Cart(x, y, 2));
                        break;
                }
            }
        }
    }


    public static char[][] readFile(String fileName){

        char[][] output = new char[0][0];

        try{
            int lines = 0;
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                s.nextLine();
                lines++;
            }
            output = new char[lines][];
            s = new Scanner(new File(fileName+".txt"));
            lines = 0;
            String line = "";
            while(s.hasNextLine()){
                line = s.nextLine();
                output[lines] = line.toCharArray();
                lines++;
            }

        }catch(FileNotFoundException e){
            System.out.println(e);
        }

        return output;
    }
}
