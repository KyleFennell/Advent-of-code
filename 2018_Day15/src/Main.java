import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static int WIDTH, HEIGHT;
    public static List<Character> characters = new ArrayList<>();
    public static List<Character> elves = new ArrayList<>();
    public static List<Character> goblins = new ArrayList<>();
    public static char[][] map;
    public static final boolean COMMENTARY = false, DEBUG = false;

    public static void main(String args[]) throws InterruptedException {
        long time = System.currentTimeMillis();

        readFile("Day15");
        parseMap(3);

        elves.forEach(c -> characters.add(c));
        goblins.forEach(c -> characters.add(c));
        int turn;

        game:
        for  (turn = 0;; turn++){
//            Thread.sleep(400);
//            System.out.println("\nTurn " + turn);
            characters.sort(Character::compareTo);
//            printMap();
            for (Character c : characters){
                if (c.dead){
                    continue;
                }
                c.takeTurn();
                if (allDead(c.enemy)){
                    break game;
                }
            }
        }

        System.out.println("Turn " + turn);
        printMap();
        System.out.println("battle finished " + turn + "*" + Math.max(sumHealth(elves), sumHealth(goblins)) +"="+ Math.max(sumHealth(elves), sumHealth(goblins))*turn);
        System.out.println(System.currentTimeMillis()-time);

        int minAtkVal = 3;
        sim:
        while (true){
            characters.forEach(Character::reset);
            for (Character e : elves){
                e.attack = minAtkVal;
            }
            game:
            for  (turn = 0;; turn++){
//                Thread.sleep(400);
//                System.out.println("\nTurn " + turn);
                characters.sort(Character::compareTo);
//                printMap();
                for (Character c : characters){
                    if (c.dead){
                        continue;
                    }
                    c.takeTurn();
                    if (anyDead(elves)){
                        System.out.println("elf died on turn " + turn + " with atk " + minAtkVal);
                        break game;
                    }
                    if (c.faction == "E" && allDead(goblins)){
                        break sim;
                    }
                }
            }
            if (!anyDead(elves)){
                break;
            }
            minAtkVal++;
        }
        System.out.println("Turn " + turn);
        printMap();
        System.out.println("battle finished " + turn + "*" + Math.max(sumHealth(elves), sumHealth(goblins)) +"="+ Math.max(sumHealth(elves), sumHealth(goblins))*turn);
        System.out.println(System.currentTimeMillis()-time);
    }

    private static void printMap(){
        char[][] tempout = new char[HEIGHT][WIDTH];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                tempout[i][j] = map[i][j];
            }
        }
        String[] out = new String[HEIGHT];
        for (Character c : characters){
            if (c.dead){
                continue;
            }
            tempout[c.y][c.x] = c.faction.charAt(0);
        }
        for (int i = 0; i < map.length; i++){
            out[i] = "";
            for (int j = 0; j < map[i].length; j++){
                out[i] += tempout[i][j];
            }
        }
        for (Character c : characters){
            if (c.dead){
                continue;
            }
            out[c.y] += " "+c.faction + "("+c.health+")";
        }
        String outS = "";
        for (String s : out){
            outS += s + "\n";
        }
        System.out.println(outS);
    }

    public static boolean allDead(List<Character> chars){
        for (Character c : chars){
            if (!c.dead){
                return false;
            }
        }
        return true;
    }

    public static boolean anyDead(List<Character> chars){
        for (Character c : chars){
            if (c.dead){
                return true;
            }
        }
        return false;
    }

    public static int sumHealth(List<Character> chars){
        int sum = 0;
        for (Character c : chars){
            if (!c.dead){
                sum += c.health;
            }
        }
        return sum;
    }

    public static void commentary(Object s){
        if (COMMENTARY)
            System.out.println(s);
    }
    public static void debug(Object s){
        if (DEBUG)
            System.out.println(s);
    }

    public static void readFile(String fileName){
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            int lines = 0;
            String line = "";
            while(s.hasNextLine()){
                lines++;
                line = s.nextLine();
            }
            HEIGHT = lines;
            WIDTH = line.length();
            map = new char[HEIGHT][WIDTH];
            s = new Scanner(new File(fileName+".txt"));
            for (int i = 0; i < lines; i++){
                map[i] = s.nextLine().toCharArray();
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
    }

    private static void parseMap(int elfAtk){
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                if (map[i][j] == 'E'){
                    elves.add(new Character(j, i, elfAtk, 200, "E"));
                    map[i][j] = '.';
                }
                else if (map[i][j] == 'G'){
                    goblins.add(new Character(j, i, 3, 200, "G"));
                    map[i][j] = '.';
                }
            }
        }
    }

    private static class Point implements Comparable<Point>{
        public final int x, y;
        public Point from = null;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
        }
        public Point(Point p, Point from){
            this.x = p.x;
            this.y = p.y;
            this.from = from;
        }
        @Override
        public boolean equals(Object p){
            return this.x == ((Point)p).x && this.y == ((Point)p).y;
        }
        @Override
        public int compareTo(Point p){
            return p.y == this.y ? Integer.compare(this.x, p.x) : Integer.compare(this.y, p.y);
        }
        private Point add(Point p){
            return new Point(this.x + p.x, this.y + p.y);
        }
        @Override
        public String toString(){
            return "("+x+","+y+")";
        }
    }

    private static class Character implements Comparable<Character>{

        private final int initHealth, initX, initY;
        private int attack;
        private final String faction;
        public int x, y, health;
        public boolean dead = false;
        private List<Character> enemy;
        private static Point[] readingOrder = {
                new Point(0, -1),
                new Point(-1, 0),
                new Point(1, 0),
                new Point(0, 1)};

        public Character(int x, int y, int attack, int health, String faction){
            this.initHealth = health;
            this.initX = x;
            this.initY = y;
            this.attack = attack;
            this.faction = faction;
            enemy = faction == "E" ? goblins : elves;
            reset();
        }

        public void reset(){
            dead = false;
            this.x = this.initX;
            this.y = this.initY;
            this.health = this.initHealth;
        }

        public void takeTurn(){
            debug("NEW CHARACTER");
            if (dead){
                return;
            }
            Point p = findNextAction();
//            System.out.println(p);
            if (posEquals(p)){
                return;
            }
            for (Character e : enemy){
                if (e.posEquals(p) && !e.dead){
                    commentary(faction+"["+health+"]"+getPoint() + " attacks " + e.faction+"["+e.health+"]"+e.getPoint());
                    e.defend(this.attack);
                    return;
                }
            }
            commentary(faction+getPoint()+" moves to " + p);
            this.x = p.x;
            this.y = p.y;
            p = findNextAction();
            if (posEquals(p)){
                return;
            }
            for (Character e : enemy){
                if (e.posEquals(p)){
                    commentary("and attacks " + e.faction+"["+e.health+"]"+e.getPoint());
                    e.defend(this.attack);
                    return;
                }
            }
        }

        private Point findNextAction(){
            List<Point> adjacent = new ArrayList<>();
            List<Point> checked = new ArrayList<>();
            adjacent.add(new Point(this.x, this.y));
            debug("added " + adjacent);
            while (!adjacent.isEmpty()){
                debug("adjacent: " + adjacent);
                Point p = adjacent.remove(0);
                Point lowest = p.from;
                int minHealth = Integer.MAX_VALUE;
                outer:
                for (int i = 0; i < readingOrder.length; i++){
                    Point q = p.add(readingOrder[i]);
                    if (map[q.y][q.x] == '#'){
                        continue;
                    }
                    for (Character c : characters){
                        if(c.posEquals(q) && !c.dead) {
                            debug("found character: " + c.faction+c.health+c.getPoint());
                            if (!c.faction.equals(faction) ){
                                debug("  is enemy");
                                if (c.health < minHealth){
                                    debug("  new lowest: " + c.health);
                                    lowest = p.from == null ? c.getPoint() : p.from;
                                    minHealth = c.health;
                                }
                            }
                            continue outer;
                        }
                    }
                    Point toAdd = new Point(q, p.from == null ? q : p.from);
                    if (!adjacent.contains(toAdd) && !checked.contains(toAdd)){
                        adjacent.add(toAdd);
                    }
                }
                if (minHealth < Integer.MAX_VALUE){
                    debug("RETURNING " + lowest);
                    return lowest;
                }
                checked.add(p);
            }
            debug("POINT NOT MOVING");
            return getPoint();
        }

        public void defend(int dmg){
            this.health -= dmg;
            if (this.health <= 0){
                commentary(faction+getPoint()+" died");
                dead = true;
            }
        }

        public Point getPoint(){
            return new Point(this.x, this.y);
        }

        public boolean posEquals(Point p){
            return p.y == this.y && p.x == this.x;
        }

        public int compareTo(Character c){
            return c.y == this.y ? Integer.compare(this.x, c.x) : Integer.compare(this.y, c.y);
        }
    }
}
