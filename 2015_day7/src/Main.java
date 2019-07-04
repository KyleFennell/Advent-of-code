import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static HashMap<String, String> instructions = new HashMap<>();
    public static HashMap<String, Integer> values = new HashMap<>();

    public static void main(String args[]){
        String file = parseFile("circuit.txt", '_');
        String[] lines = file.split("_");
        for (String s : lines){
            String[] sections = s.split(" -> ");
            instructions.put(sections[1], sections[0]);
        }
        int a = evaluate("a");
        System.out.println(a);
        // instructions.put("b", ""+a);
        // values = new HashMap<>();
        // System.out.println(evaluate("a"));
    }

    public static int evaluate(String value){
//        System.out.println("evaluating "+value);
        if (values.containsKey(value)){
            return values.get(value);
        }
        if (value.matches("^[\\d]*$")){
//            System.out.println("int found");
            return Integer.parseInt(value);
        }
        int output = 0;
        String instruction = instructions.get(value);
//        System.out.println("instruction found "+instruction);
        String[] words = instruction.split(" ");
        switch(words.length){
            case 1:
                output = evaluate(words[0]);
                break;
            case 2:
                output = 25565 - evaluate(words[1]);
                break;
            case 3:
                switch (words[1]){
                    case "AND": output = evaluate(words[0]) & evaluate(words[2]);
                        break;
                    case "OR": output = evaluate(words[0]) | evaluate(words[2]);
                        break;
                    case "LSHIFT": output = (evaluate(words[0]) << evaluate(words[2]));
                        break;
                    case "RSHIFT": output = evaluate(words[0]) >> evaluate(words[2]);
                        break;
                    default:
                        System.out.println("unknown word \'"+words[1]+"\' in instruction \'"+instruction+"\'");
                }
                break;
            default:
                System.out.println("unknown instruction \'"+instruction+"\'");
                return 0;
        }
        values.put(value, output);
        return output;
    }

    public static String parseFile(String filename, char delimeter){
        Scanner scanner;
        String output = "";
        try {
            scanner = new Scanner(new File(filename));
            output += scanner.nextLine();
            while (scanner.hasNextLine()){
                output += delimeter + scanner.nextLine();
            }
        }
        catch (Exception e){}
        return output;
    }
}
