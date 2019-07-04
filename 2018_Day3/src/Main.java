import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){

        List<String> input = readFile("Day3");
        int maxFloorWidth = 0, maxFloorHeight = 0;
        for (String s : input){
//            System.out.println(s);
            int[] params = splitInputString(s);
            int floorWidth = params[1] + params[2];
            int floorHeight = params[3] + params[4];
            if (floorWidth > maxFloorWidth){
                maxFloorWidth = floorWidth;
            }
            if (floorHeight > maxFloorHeight) {
                maxFloorHeight = floorHeight;
            }
        }
        int[][] floorSpace = new int[maxFloorHeight][maxFloorWidth];
        int sharedSpace = 0;
        for (String s : input){
            int[] params = splitInputString(s);
            for (int y = params[3]; y < params[3] + params[4]; ++y){
                for (int x = params[1]; x < params[1] + params[2]; x++) {
                    if (floorSpace[y][x] == 0) {
                        floorSpace[y][x] = params[0];
                    }
                    else if (floorSpace[y][x] != -1){
                        floorSpace[y][x] = -1;
                        sharedSpace++;
                    }
                }
            }
        }
        System.out.println(sharedSpace);
        for (String s : input){
            int[] params = splitInputString(s);
            boolean unClaimed = true;
            for (int y = params[3]; y < params[3] + params[4]; ++y){
                for (int x = params[1]; x < params[1] + params[2]; x++) {
                    if (floorSpace[y][x] != params[0])
                        unClaimed = false;
                }
            }
            if (unClaimed){
                System.out.println(params[0]);
            }
        }
    }

    /**
     *
     * @param s string being split
     * @return id, startX, width, startY, height
     */
    public static int[] splitInputString(String s){
        String[] split = s.split(" ");
        int id = Integer.parseInt(split[0].substring(1));
        String[] startPoint = split[2].split(",");
        //remove the :
        startPoint[1] = startPoint[1].substring(0, startPoint[1].length() -1);
        int startX = Integer.parseInt(startPoint[0]);
        int startY = Integer.parseInt(startPoint[1]);
        String[] size = split[3].split("x");
        int width = Integer.parseInt(size[0]);
        int height = Integer.parseInt(size[1]);
        return new int[]{id, startX, width, startY, height};
    }

    public static List<String> readFile(String fileName){

        List<String> output = new ArrayList<String>();

        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while (s.hasNextLine()){
                output.add(s.nextLine());
            }
        }catch(FileNotFoundException e){
            System.out.println(e.getStackTrace());
        }
        return output;
    }

}
