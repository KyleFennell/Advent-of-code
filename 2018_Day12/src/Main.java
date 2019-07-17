import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static int offset = 0;
    private static HashMap<String, String> dict = new HashMap<>();
    public static void main(String args[]){
        String initialState = readFile("Day12");
        String state = initialState;

        for (int i = 0; i < 20; i++){
            state = pad(state);
            state = update(state);
//            System.out.println(state+" "+getScore(state));
        }
//        System.out.println(state);
        System.out.println("Part1 answer: "+getScore(state));

        state = initialState;
        int timeSinceScoreChange = Integer.MIN_VALUE;
        int lastChangedAt = 0, lastScore = 0, lastChangedScore=0, lastDif = 0, gen = 0;
        offset = 0;

        while(timeSinceScoreChange < 3){
            gen++;
            state = pad(state);
            state = update(state);
//            System.out.println(state+" "+getScore(state));
            int score = getScore(state);
            int scoreDif = score-lastScore;
            if (scoreDif != lastDif){
                lastChangedAt = gen;
                lastChangedScore = score;
                timeSinceScoreChange = 0;
            }
            else {
                timeSinceScoreChange++;
            }
//            System.out.println("Gen: "+gen+" LastDif:"+lastDif+" scoreDif: "+scoreDif+" score:"+score);
            lastDif = scoreDif;
            lastScore = score;
        }
        System.out.println("converged at gen: " + lastChangedAt+" with score of: " + lastChangedScore +
                " and change: "+lastDif);
        System.out.println("Part2 answer: " + (lastChangedScore + (50000000000L - lastChangedAt)*lastDif));

    }

    private static String pad(String state){
        while(state.indexOf("#") < 4){
            state = "." + state;
            offset++;
        }
        while(state.lastIndexOf("#") > state.length() -5){
            state += ".";
        }
        return state;
    }

    private static String update(String state){
        String out = "..";
        for (int i = 0; i < state.length()-4; i++){
            String key = state.substring(i, i+5);
            if (dict.containsKey(key)){
                out += dict.get(key);
//                System.out.println(i+":"+key+":"+dict.get(key));
            }
            else{
                out += ".";
//                System.out.println(i+":"+key+":.");
            }
        }
        out += "..";
        return out;
    }

    private static int getScore(String state){
        int score = 0;
        for (int i = 0; i < state.length(); i++){
            if (state.charAt(i) == '#'){
                score += i-offset;
            }
        }
        return score;
    }

    public static String readFile(String fileName){
        String initialState = "";
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            String line = s.nextLine();
            initialState = line.split(": ")[1];
            s.nextLine();
            while(s.hasNextLine()){
                String[] split = s.nextLine().split(" => ");
                dict.put(split[0], split[1]);
//                System.out.println("found: " +split[0]+" : "+split[1]);
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        return initialState;
    }
}
