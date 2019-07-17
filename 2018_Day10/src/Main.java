import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static List<Star> stars = new ArrayList<>();
    private static final double THRESHOLD = 0.5;
    public static void main(String args[]) throws InterruptedException {

        readFile("Day10");

        Rect lastRect = new Rect(Integer.MIN_VALUE, Integer.MIN_VALUE, (long)Math.sqrt(Long.MAX_VALUE), (long)Math.sqrt(Long.MAX_VALUE));
        int time = 0;
        int sinceLastThreshold = Integer.MIN_VALUE;
        while (sinceLastThreshold < 3){
            for (Star s : stars){
                s.update();
            }
            Rect newRect = getRect();
            double rectDif = newRect.area()/(double)lastRect.area();
            System.out.println("Dif: " + rectDif);
            if (rectDif < THRESHOLD && rectDif > THRESHOLD/2){
                System.out.println("Time: " + time);
                printStars(newRect);
                sinceLastThreshold = 0;
            }
            sinceLastThreshold++;
            time++;
            lastRect = newRect;
        }

    }

    private static Rect getRect(){
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Star s : stars){
            if (s.px > maxX) maxX = s.px;
            if (s.py > maxY) maxY = s.py;
            if (s.px < minX) minX = s.px;
            if (s.py < minY) minY = s.py;
        }
        return new Rect(minX, minY, Math.abs(maxX-minX), Math.abs(maxY-minY));
    }

    private static void printStars(Rect r){
        System.out.println("printing stars: " + r);
        int[][] grid = new int[(int)r.h+1][(int)r.w+1];
        for (Star s : stars){
            grid[s.py-r.y][s.px-r.x] = 1;
        }
        for (int i = 0; i < grid.length; i++){
            String line = "";
            for (int j = 0; j < grid[i].length; j++){
                line += grid[i][j] == 0 ? " ":".";
            }
            System.out.println(line);
        }
    }

    private static class Rect implements Comparable<Rect>{
        public final int x, y;
        public final long w, h;
        public Rect(int x, int y, long w, long h){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
        public long area(){
            return w * h;
        }
        @Override
        public int compareTo(Rect r){
            long areaDif = this.area() - r.area();
            return areaDif > 0 ? 1 : areaDif < 0 ? -1 : 0;
        }
        @Override
        public String toString(){
            return "("+x+","+y+","+w+","+h+")";
        }

    }
    private static class Star{
        public int px, py;
        private final int vx, vy;
        public Star(int px, int py, int vx, int vy){
            this.px = px;
            this.py = py;
            this.vx = vx;
            this.vy = vy;
        }
        public void update(){
            px += vx;
            py += vy;
        }
    }

    public static void readFile(String fileName){
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                String line = s.nextLine();
                line = line.replaceAll(" ", "");
                String[] data = line.split("[<,>]");
                stars.add(new Star(Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[4]),
                        Integer.parseInt(data[5])
                ));
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
    }
}
