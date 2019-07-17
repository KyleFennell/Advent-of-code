import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    private static int[][] edges = new int[26][26];
    private static int[] checked = new int[26];
    private static List<Integer> accessible = new ArrayList<>();
    private static String output = "";
    public static void main(String args[]) {

        List<char[]> input = readFile("Day7");

        //prepare the adjacency matrix
        for (int i = 0; i < 26; i++){
            edges[i] = new int[26];
        }
        //read the input into the matrix
        for (char[] i : input){
            edges[charToInt(i[0])][charToInt(i[1])] = 1;
        }
        System.out.println(getDependencyOrder());
        for (int i = 0; i < checked.length; i++){
            checked[i] = 0;
        }
        System.out.println(getTimeToComplete(5, 60));
    }

    private static String getDependencyOrder(){
        String output = "";
        while(!allChecked()){
            for (int col = 0; col < edges.length; col++){
                if (checked[col] == 1){
                    continue;
                }
                boolean valid = true;
                for (int row = 0; row < edges.length; row++){
                    if (edges[row][col] == 1 && checked[row] != 1){
                        valid = false;
                        break;
                    }
                }
                if (valid){
                    checked[col] = 1;
                    output += intToChar(col, true);
                    break;
                }
            }
        }
        return output;
    }

    private static int getTimeToComplete(int workers, int baseTime){
        PriorityQueue<WorkerEvent> events = new PriorityQueue<>(WorkerEvent::compareTime);
        int time = 0;

        for (int i = 0; i < workers; i++) {
            events.add(new WorkerEvent(i,0, -1));
        }

        while(true){
            System.out.println("\nEvents: " + events);
            if (events.isEmpty()){
                System.out.println("Events empty. Finished!");
                break;
            }
            // if there are any events pop one
            // set as new time
            WorkerEvent e = events.poll();
            System.out.println("Worker:" + e);
            time = e.time;
            // mark step as complete
            if (e.step >= 0){
                checked[e.step] = 2;
            }
            //find a new step;
            boolean noValid = false;
            while (!noValid && events.size() < workers) {
                noValid = true;
                for (int col = 0; col < edges.length; col++) {
                    if (checked[col] > 0) {
                        continue;
                    }
                    boolean valid = true;
                    for (int row = 0; row < edges.length; row++) {
                        if (edges[row][col] == 1 && checked[row] != 2) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        checked[col] = 1;
                        WorkerEvent w = new WorkerEvent(e.worker, time+baseTime, col);
                        events.add(w);
                        System.out.println("Assigning: " + w);
                        noValid = false;
                        break;
                    }
                }
            }
            System.out.println("Finished assigning: " + events.size());
            printChecked(true);
        }
        return time;
    }

    private static class WorkerEvent{
        public final int worker, time, step;
        public WorkerEvent(int worker, int time, int step){
            this.worker = worker;
            this.time = time+(step+1);
            this.step = step;
        }
        public int compareTime(WorkerEvent e){
            return this.time - e.time;
        }
        public String toString(){
            return "(I:" + worker +" T:" + time + " S:(" + intToChar(step, true) + ":" + step +"))";
        }
    }

    private static boolean allChecked(){
        for (int i = 0; i < checked.length; i++){
            if (checked[i] == 0){
                return false;
            }
        }
        return true;
    }

    private static void printChecked(boolean ULCase){
        String checkout = "";
        for (int i = 0; i < checked.length; i++){
            checkout += checked[i] == 1 ? ""+intToChar(i, !ULCase) : checked[i] == 2 ? intToChar(i, ULCase) : ".";
        }
        System.out.println("checked: " + checkout);
    }

    private static int charToInt(char c){
        return (int)c-97 < 0 ? (int)c-65 : (int)c-97;
    }

    private static char intToChar(int i, boolean upper){
        return (char)(i+(upper?65:97));
    }

    public static List<char[]> readFile(String fileName){

        List<char[]> output = new ArrayList<>();
        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextLine()){
                String[] line = s.nextLine().split(" ");
                output.add(new char[]{line[1].charAt(0), line[7].charAt(0)});
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        return output;
    }

    private static void printGrid(){
        System.out.println(" ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        for (int row = 0; row < edges.length; row++){
            String out = "" + intToChar(row, true);
            for (int col = 0; col < edges.length; col++){
                out += edges[row][col] == 1 ? "x" : ".";
            }
            if (checked[row] == 0)
                System.out.println(out);
        }
    }
}
