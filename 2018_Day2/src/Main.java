import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]){

        List<String> input = readFile("Day2");
        int doubleLetters = 0, tripleLetters = 0;
        for (String s : input){
            HashMap<Character, Integer> frequency = new HashMap<>();
            for (int i = 0; i < s.length(); i++){
                if (frequency.containsKey(s.charAt(i))){
                    frequency.put(s.charAt(i), frequency.get(s.charAt(i))+1);
                }
                else {
                    frequency.put(s.charAt(i), 1);
                }
            }
            if(frequency.containsValue(2)){
                doubleLetters++;
//                System.out.println("2 "+s);
            }
            if(frequency.containsValue(3)){
                tripleLetters++;
//                System.out.println("3 "+s);
            }
        }
        System.out.println(doubleLetters * tripleLetters);
        for (int i = 0; i < input.size(); i++) {
            for (int j = i+1; j < input.size(); j++){
                if (stringCompare(input.get(i), input.get(j))) {
                    System.out.println(input.get(i)+", "+input.get(j));
                    System.out.println(findString(input.get(i), input.get(j)));
                    return;
                }
            }
        }
    }


    private static List<String> readFile(String fileName){

        List<String> output = new ArrayList<String>();

        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                output.add(s.nextLine());
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }

        return output;
    }

    private static String findString(String s1, String s2){
        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)){
                return s1.substring(0, i-1)+s1.substring(i+1);
            }
        }
        return "";
    }

    private static boolean stringCompare(String s1, String s2){
        int errors = 0;
        for (int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) != s2.charAt(i)){
                System.out.println("error: "+s1+", "+s2+": "+s1.charAt(i)+" != "+s2.charAt(i));
                errors++;
            }
            if (errors > 1){
                return false;
            }
        }
        return true;
    }

}
