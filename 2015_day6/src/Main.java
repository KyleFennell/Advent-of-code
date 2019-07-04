import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){
        int[][] lights = new int[1000][1000];
        String file = parseFile("commands.txt");
        String[] commands = file.split("\\|");
        for (String s : commands){
            String[] words = s.split(" ");
            String[] startCoords;
            String[] endCoords;
            switch(words[0]) {
                case "turn":
                    startCoords = words[2].split(",");
                    endCoords = words[4].split(",");
                    for (int x = Integer.parseInt(startCoords[0]); x <= Integer.parseInt(endCoords[0]); x++) {
                        for (int y = Integer.parseInt(startCoords[1]); y <= Integer.parseInt(endCoords[1]); y++) {
                            lights[y][x] += words[1].equals("on") ? 1 : lights[y][x] > 0 ? -1 : 0;
                        }
                    }
                    break;
                case "toggle":
                    startCoords = words[1].split(",");
                    endCoords = words[3].split(",");
                    for (int x = Integer.parseInt(startCoords[0]); x <= Integer.parseInt(endCoords[0]); x++) {
                        for (int y = Integer.parseInt(startCoords[1]); y <= Integer.parseInt(endCoords[1]); y++) {
                            lights[y][x] += 2;
                        }
                    }
            }
        }
        int count = 0;
        for (int x = 0; x < 1000; x++){
            for (int y = 0; y < 1000; y++){
                count += lights[y][x];
            }
        }
        System.out.println(count);
    }

    public static String parseFile(String filename){
        Scanner scanner = null;
        String output = "";
        try {
            scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                output += "|" + scanner.nextLine();
            }
        }
        catch (Exception e){}
        return output;
    }
}
