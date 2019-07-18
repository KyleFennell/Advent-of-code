import java.util.ArrayList;

public class Main {

    public static ArrayList<Integer> scores = new ArrayList<>();
    public static final int STOP_POINT = 640441;
    public static final int[] ARR_STOP_POINT = {6, 4, 0, 4, 4, 1};

    public static void main(String args[]){
        scores.add(3);
        scores.add(7);
        int chef1 = 0;
        int chef2 = 1;
        boolean found = false;
        while (!found){
            int sum = scores.get(chef1) + scores.get(chef2);
            if (sum/10 != 0){
                scores.add((sum/10));
            }
            scores.add((sum%10));
            chef1 = (chef1+scores.get(chef1)+1)%scores.size();
            chef2 = (chef2+scores.get(chef2)+1)%scores.size();
            if (scores.size() == STOP_POINT+10){
                String finalScore = "";
                for (int i = scores.size()-10; i < scores.size(); i++){
                    finalScore += scores.get(i);
                }
                System.out.println(finalScore);
            }
            if (scores.size() > ARR_STOP_POINT.length){
                found = true;
                for (int j = ARR_STOP_POINT.length-1; j >= 0; j--){
                    if (scores.get(scores.size()-1-(ARR_STOP_POINT.length-j)) != ARR_STOP_POINT[j]){
                        found = false;
                        break;
                    }
                }
                if (found){
                    System.out.println(scores.size()-ARR_STOP_POINT.length-1);
                    break;
                }
            }
        }
    }
}
