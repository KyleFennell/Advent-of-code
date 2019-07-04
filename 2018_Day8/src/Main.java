import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String args[]){
        List<Integer> input = readFile("Day8");

        System.out.println(treeRecurseP1(input.iterator()));
        System.out.println(treeRecurseP2(input.iterator()));
    }

    public static int treeRecurseP1(Iterator<Integer> it){
        int children = it.next();
        int dataPoints = it.next();
        int sum = 0;
        for (int i = 0; i < children; i++){
            sum += treeRecurseP1(it);
        }
        for (int i = 0; i < dataPoints; i++){
            sum += it.next();
        }
        return sum;
    }

    public static int treeRecurseP2(Iterator<Integer> it){
        int children = it.next();
        int[] childrenSums = new int[children];
        int dataPoints = it.next();
        int sum = 0;
        for (int i = 0; i < children; i++){
            childrenSums[i] = treeRecurseP2(it);
        }
        for (int i = 0; i < dataPoints; i++){
            int child = it.next();
            if (children == 0){
                sum += child;   //datapoint
            }
            else if (child <= children && child != 0){
                sum += childrenSums[child-1];
            }
        }
        return sum;
    }

    public static List<Integer> readFile(String fileName){

        List<Integer> output = new ArrayList<>();

        try{
            Scanner s = new Scanner(new File(fileName+".txt"));
            while(s.hasNextInt()){
                output.add(s.nextInt());
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }

        return output;
    }

}
