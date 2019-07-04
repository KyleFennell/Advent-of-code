import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String args[]){

        List<char[]> input = readFile("Day7");

        HashMap<Character, Character> dependsOn = new HashMap<>();  //key is dependent of value
        HashMap<Character, Integer> stage = new HashMap<>();        //dependence depth
        for (char[] c : input){
            if (!dependsOn.containsKey(c[1])){
                dependsOn.put(c[1], c[0]);
                stage.put(c[1], 0);
                if (!stage.containsKey(c[0])){
                    stage.put(c[0], 0);
                    stage.put(c[1], 1);
                }
                else {
                    stage.put(c[0], stage.get(c[1]));
                }
            }
            else{
                if (!stage.containsKey(c[0])){
                    stage.put(c[0], 0);
                }
                else if (stage.get(c[1]) <= stage.get(c[0])){
                    stage.put(c[1], stage.get(c[0])+1);
                    dependsOn.put(c[1], c[0]);
                    update(c[1], dependsOn, stage);
                }
            }
        }
        List<Character> concurrentStage = findKeysForValueCI(0, stage);
        String output = "";
        for (int i = 1; !concurrentStage.isEmpty(); i++){
            concurrentStage.sort(Character::compareTo);
            for(char c : concurrentStage){
                output += c;
            }
            concurrentStage = findKeysForValueCI(i, stage);
        }
        System.out.println(output);
    }

    public static List<Character> findKeysForValueCC(char value, HashMap<Character, Character> dependsOn){
        Iterator<Character> keys = dependsOn.keySet().iterator();
        List<Character> dependent = new ArrayList<>();
        while(keys.hasNext()){
            char key = keys.next();
            if (dependsOn.get(key) == value){
                dependent.add(key);
            }
        }
        return dependent;
    }

    public static List<Character> findKeysForValueCI(int value, HashMap<Character, Integer> stage){
        Iterator<Character> keys = stage.keySet().iterator();
        List<Character> dependent = new ArrayList<>();
        while(keys.hasNext()){
            char key = keys.next();
            if (stage.get(key) == value){
                dependent.add(key);
            }
        }
        return dependent;
    }

    public static void update(char value, HashMap<Character, Character> dependsOn, HashMap<Character, Integer> stage){

        List<Character> dependencies = findKeysForValueCC(value, dependsOn);

        for (char c : dependencies){
            stage.put(c, stage.get(value)+1);
            System.out.println(value+" depends on "+c);
            update(c, dependsOn, stage);
        }
    }

    public static List<char[]> readFile(String fileName){

        List<char[]> output = new ArrayList<>();
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                String[] line = s.nextLine().split(" ");
                output.add(new char[]{line[0].charAt(0), line[7].charAt(0)});
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        return output;
    }

}
