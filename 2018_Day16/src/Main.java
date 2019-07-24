import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Sample> samples = new ArrayList<>();
    public static List<Operation> operations = new ArrayList<>();
    public static List<int[]> program = new ArrayList<>();

    public static void main(String args[]){
        initOperations();
        readFile("Day16");
        int count = 0;
        for (Sample s : samples){
            int passes = 0;
            for (Operation o: operations){
                if (s.test(o)){
                    passes++;
                }
                if (passes >= 3){
                    count++;
                    break;
                }
            }
        }
        System.out.println(count);

        // [code][operation]
        // false is potential/valid   true is invalid/checked
        boolean[][] table = new boolean[16][16];
        for (Sample s : samples){
            for (int i = 0; i < operations.size(); i++){
                Operation op = operations.get(i);
                if (!s.test(op)){
                    table[s.op[0]][i] = true;
                    updateTable(table, i);
                }
            }
        }

        int[] map = mapTable(table);
        int[] reg = {0, 0, 0, 0};
        for (int[] i : program){
            reg = operations.get(map[i[0]]).execute(reg, i);
        }
        String s = "";
        for (int i : reg){
            s += i + " ";
        }
        System.out.println(s);


    }

    private static void updateTable(boolean[][] table, int seed){
        updateTable(table, seed, new boolean[16]);
    }
    private static void updateTable(boolean[][] table, int seed, boolean[] checked){
        int count = 0;
        int operation = -1;
        for (int i = 0; i < table.length; i++){
            if (!table[seed][i]){
                count++;
                operation = i;
            }
        }
        if (count == 1){
            checked[seed] = true;
            for (int i = 0; i < checked.length; i++){
                if (i == seed){
                    continue;
                }
                table[i][operation] = true;
                if (!checked[i]){
                    updateTable(table, i, checked);
                }
            }
        }
    }

    private static int[] mapTable(boolean[][] table){
        int[] map = new int[16];
        for (int i = 0; i < table.length; i++){
            for (int j = 0; j < table[i].length; j++){
                if (!table[i][j]){
                    map[i] = j;
                }
            }
        }
        return map;
    }

    public static void readFile(String fileName) {
        String initialState = "";
        try {
            Scanner s = new Scanner(new File(fileName + ".txt"));
            String line;
            int blankCount = 0;
            //read samples;
            while (s.hasNextLine()) {
                line = s.nextLine();
                if (line.isEmpty()){
                    blankCount++;
                    if (blankCount == 3){
                        break;
                    }
                    continue;
                }
                blankCount = 0;
                samples.add(new Sample(
                        stoi(line.substring(9, 19).split(", ")),
                        stoi(s.nextLine().split(" ")),
                        stoi(s.nextLine().substring(9, 19).split(", "))
                ));
            }
            //read program
            while (s.hasNextLine()){
                line = s.nextLine();
                System.out.println(line);
                program.add(stoi(line.split(" ")));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void initOperations(){
        //addr (add register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] + out[op[2]];
            return out;
        });
        //addi (add immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] + op[2];
            return out;
        });
        //mulr (multiply register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] * out[op[2]];
            return out;
        });
        //muli (multiply immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] * op[2];
            return out;
        });
        //banr (bitwise AND register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] & out[op[2]];
            return out;
        });
        //bani (bitwise AND immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] & op[2];
            return out;
        });
        //borr (bitwise OR register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] | out[op[2]];
            return out;
        });
        //bori (bitwise OR immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]] | op[2];
            return out;
        });
        //setr (set register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = out[op[1]];
            return out;
        });
        //seti (set immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = op[1];
            return out;
        });
        //gtir (greater-than immediate/register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = op[1] > reg[op[2]] ? 1 : 0;
            return out;
        });
        //gtri (greater-than register/immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = reg[op[1]] > op[2] ? 1 : 0;
            return out;
        });
        //gtrr (greater-than register/register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = reg[op[1]] > reg[op[2]] ? 1 : 0;
            return out;
        });
        //eqir (equal immediate/register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = op[1] == reg[op[2]] ? 1 : 0;
            return out;
        });
        //eqri (equal register/immediate)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = reg[op[1]] == op[2] ? 1 : 0;
            return out;
        });
        //eqrr (equal register/register)
        operations.add((reg, op) -> {
            int[] out = reg.clone();
            out[op[3]] = reg[op[1]] == reg[op[2]] ? 1 : 0;
            return out;
        });
    }

    private static int[] stoi(String[] in){
        int[] out = new int[in.length];
        for (int i = 0; i < in.length; i++){
            out[i] = Integer.parseInt(in[i]);
        }
        return out;
    }

    private static class Sample{
        public final int[] before, op, after;
        public Sample(int[] before, int[] op, int[] after){
            this.before = before;
            this.op = op;
            this.after = after;
        }

        public boolean test(Operation o){
            int[] result = o.execute(before, op);
            for (int i = 0; i < result.length; i++){
                if (result[i] != after[i]){
                    return false;
                }
            }
            return true;
        }
    }

    public interface Operation{
        int[] execute(int[] registers, int[] op);
    }
}
