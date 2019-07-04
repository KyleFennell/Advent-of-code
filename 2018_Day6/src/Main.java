import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String args[]){

        List<String> inputs = readFile("Day6");
        List<int[]> coords = new ArrayList<>();
        for (String s : inputs){
            coords.add(getCoords(s));
        }

        //read in coords and decide size of the board

        int maxX = 0, maxY = 0, minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (int[] coord : coords){
            if (coord[0] < minX){
                minX = coord[0];
            }
            else if (coord[0] > maxX){
                maxX = coord[0];
            }
            if (coord[1] < minY){
                minY = coord[1];
            }
            else if (coord[1] > maxY){
                maxY = coord[1];
            }
        }

        //create and fill board with id of closest coord as values for each element

        int[][] grid = new int[maxY+minY][maxX+minX];
        for (int y = 0; y < grid.length; ++y){
            for (int x = 0; x < grid[y].length; ++x){
                int closestDistance = Integer.MAX_VALUE;
                for (int i = 0; i < coords.size(); i++){
                    if (distance(x, y, coords.get(i)) < closestDistance){
                        closestDistance = distance(x, y, coords.get(i));
                        grid[y][x] = i+1;
                    }
                    else if (distance(x, y, coords.get(i)) == closestDistance){
                        grid[y][x] = 0;
                    }
                }
            }
        }

        //disqualify ids on the edges

        List<Integer> disqualified = new ArrayList<>();
        for (int y = 0; y < grid.length; y++){
            if (!disqualified.contains(grid[y][0])){
                disqualified.add(grid[y][0]);
            }
            if (!disqualified.contains(grid[y][grid[y].length-1])){
                disqualified.add(grid[y][grid[y].length-1]);
            }
        }
        for (int x = 0; x < grid[0].length; ++x){
            if (!disqualified.contains(grid[0][x])){
                disqualified.add(grid[0][x]);
            }
            if (!disqualified.contains(grid[grid.length-1][x])){
                disqualified.add(grid[grid.length-1][x]);
            }
        }

        //count the size of the non disqualified ids

        HashMap<Integer, Integer> counters = new HashMap<>();
        for (int y = 0; y < grid.length; ++y){
            for (int x = 0; x < grid[y].length; ++x){
                if(!disqualified.contains(grid[y][x])) {
                    if (counters.containsKey(grid[y][x])) {
                        counters.put(grid[y][x], counters.get(grid[y][x]) + 1);
                    } else {
                        counters.put(grid[y][x], 1);
                    }
                }
            }
        }

        //find max value of the counts

        Iterator<Integer> valuesIt = counters.values().iterator();
        int maxSize = 0;
        while (valuesIt.hasNext()){
            int value = valuesIt.next();
            if(value > maxSize){
                maxSize = value;
            }
        }

        System.out.println(maxSize);

        //part 2

        int reigionCount = 0;
        for (int y = 0; y < grid.length; ++y){
            for (int x = 0; x < grid[y].length; ++x){
                int sumDistance = 0;
                for (int i = 0; i < coords.size(); i++){
                    sumDistance += distance(x, y, coords.get(i));
                }
                if (sumDistance < 10000){
                    reigionCount++;
                }
            }
        }

        System.out.println(reigionCount);

    }

    public static List<String> readFile(String fileName){

        List<String> output = new ArrayList<>();
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                output.add(s.nextLine());
            }
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
        return output;
    }

    public static int distance(int x, int y, int[] coord){
        return Math.abs(coord[0]-x) + Math.abs(coord[1]-y);
    }

    public static int[] getCoords(String s){
        String[] coords = s.split(", ");
        return new int[]{Integer.parseInt(coords[0]), Integer.parseInt(coords[1])};
    }

}
