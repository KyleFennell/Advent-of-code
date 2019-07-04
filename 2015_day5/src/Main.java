import java.io.File;
import java.util.regex.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        String fileString = readFile("strings.txt");
        String[] strings = fileString.split(" ");
        Pattern[] patterns1 = {
                Pattern.compile("([\\w])\\1"),
                Pattern.compile("([aeiou])"),
                Pattern.compile("(ab|cd|pq|xy)")};
        int niceStrings = 0;
        for (String s : strings){
            int[] counters = countPatterns(s, patterns1);
            if (counters[0] > 0 && counters[1] >= 3 && counters[2] == 0){
                niceStrings++;
            }
        }
        System.out.println(niceStrings);
        Pattern[] patterns2 = {
                Pattern.compile("(\\w\\w)[\\w]*\\1"),
                Pattern.compile("(\\w)\\w\\1")};
        niceStrings = 0;
        for (String s : strings){
            int[] counters = countPatterns(s, patterns2);
            if (counters[0] > 0 && counters[1] > 0){
                niceStrings++;
            }
        }
        System.out.println(niceStrings);
    }

    public static int[] countPatterns(String s, Pattern[] patterns){
        int[] counters = new int[patterns.length];
        for (int i = 0; i < patterns.length; i++){
            Matcher m = patterns[i].matcher(s);
            while(m.find()){
//                System.out.println(s+" "+patterns[i].pattern()+" "+s.substring(m.start(), m.end()));
                counters[i]++;
            }
        }
        return counters;
    }

    public static String readFile(String filename){
        Scanner in = null;
        String output = "";
        try{
            in = new Scanner(new File(filename));
            output += in.nextLine();
            while(in.hasNextLine()){
                output += " "+in.nextLine();
            }
        }
        catch (Exception e){}
        return output;
    }
}
