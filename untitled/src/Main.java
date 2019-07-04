import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        ArrayList<Character> file = parseFile("code.txt", '|');
        int codeCount = 0, textCount = 0;
        State state = State.DEFAULT;
            for (Character c : file){
                codeCount++;
                textCount++;
                switch (state) {
                    case DEFAULT:
                        switch (c) {
                            case '"': textCount--;
                                break;
                            case '\\': state = State.ESCAPED;
                        }
                        break;
                    case ESCAPED:
                        switch (c){
                            case 'x': state = State.HEXx;
                                break;
                            case '"': textCount--;          //removing the counted escape;
                                state = State.DEFAULT;
                                break;
                            default: state = State.DEFAULT;
                        }
                        break;
                    case HEXx:
                        if ((""+c).matches("[0-9a-f]")){
                            state = State.HEXd1;
                        }
                        else {
                            System.out.println("unknown sequence: \\x"+c);
                            state = State.DEFAULT;
                        }
                        break;
                    case HEXd1:
                        if ((""+c).matches("[0-9a-f]")){
                            state = State.DEFAULT;
                            textCount -= 3;
                        }
                        else {
                            System.out.println("unknown sequence: \\x?"+c);
                            state = State.DEFAULT;
                        }
                        break;
                }
            }
        System.out.println(codeCount+" "+textCount+" "+(codeCount-textCount));
    }
    public static ArrayList<Character> parseFile(String filename, char delimeter){
        BufferedReader reader;
        ArrayList<Character> line = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), Charset.forName("UTF-8")));

            int c;
            while ((c = reader.read()) != -1) {
                line.add((char) c);
            }
        } catch (Exception e) {}
        return line;
    }

    enum State{
        DEFAULT, ESCAPED, HEXx, HEXd1
    }
}
