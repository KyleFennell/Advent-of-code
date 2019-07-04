import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String args[]) {
        try {
            String chemicals = (new Scanner(new File("Day5.txt"))).nextLine();
//            chemicals = "eFAbcCdDBaEf";
            String part1 = react(chemicals);
            System.out.println(part1);
            System.out.println(part1.length());

                System.out.println((int)'a'+" "+(int)'A'+" "+((int)'a' - (int)'A'));

            int smallest = Integer.MAX_VALUE;
            for (int i = 0; i < 26; i++){
                char problemLetter = (char)((int)'A' + i);
//                System.out.println("letter : "+problemLetter);
                String part2 = chemicals.replace(""+problemLetter+"", "");
                problemLetter = (char)((int)problemLetter + 32);
//                System.out.println("Letter : "+problemLetter);
                part2 = part2.replace(""+problemLetter+"", "");
                part2 = react(part2);
                if (part2.length() < smallest){
                    smallest = part2.length();
                }
                System.out.println(problemLetter+" "+part2.length());
            }
            System.out.println(smallest);
        }catch (FileNotFoundException e){
            System.out.println(e);
        }
    }

    public static String react(String s){
        boolean modified;
        do {
            modified = false;
            for (int i = 0; i < s.length()-1 && !modified; i++) {
                if (Math.abs((int) s.charAt(i) - (int) s.charAt(i + 1)) == 32) {
                    s = s.substring(0, i) + s.substring(i + 2);
                    modified = true;
                }
            }

        } while (modified);
        return s;
    }
}