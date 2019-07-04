import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String args[]){
        Pattern p = Pattern.compile("([0-9])\\1*");
        String initialString = "1113122113";
        String itteration = initialString;
        for(int i = 0; i < 50; i++){
            String next = "";
            Matcher m = p.matcher(itteration);
            while(m.find()) {
//                System.out.println(m.start()+" "+m.end()+" "+m.group());
                next += m.group().length() +""+ m.group().charAt(0);
            }
//            System.out.println(next);
            itteration = next;
        }
        System.out.println(itteration.length());
    }
}

//287148
//374214