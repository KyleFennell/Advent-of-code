import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){
        try {
            System.out.println(parseFile("Day1Input.txt"));
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static int parseFile(String fileName)throws Exception{
        Scanner s = new Scanner(new File(fileName));
        String concatenation = "";
        int number = 0;
        HashMap<Integer, Boolean> map = new HashMap<>();
        while (true) {
            while (s.hasNextLine()) {
                concatenation += s.nextLine() + "_";
            }
            String[] numberStrings = concatenation.split("_");
            for (String n : numberStrings) {
                number += Integer.parseInt(n);
                if (map.get(number) != null) {
                    return number;
                }
                map.put(number, true);
//            System.out.println(number);
            }
            s = new Scanner(new File(fileName));
        }
//        return number;
    }
}
