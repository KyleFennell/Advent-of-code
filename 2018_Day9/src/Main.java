import java.math.BigInteger;
import java.util.ListIterator;

public class Main {
    final static int PLAYERS = 478, MARBLES = 7124000, SCORING = 23, SCOREBACK = 7;
    public static void main(String args[]){
        CircularLinkedList<Integer> cll = new CircularLinkedList<>();
        ListIterator<Integer> ring = cll.listIterator();
        int crntPlr = 0;
        BigInteger[] playerScores = new BigInteger[PLAYERS];

        for (int i = 0; i < PLAYERS; i++){
            playerScores[i] = BigInteger.ZERO;
        }

        for (int i = 0; i <= MARBLES; i++){
            // if marble is a scoring marble
            if (i != 0 && i % SCORING == 0){
                for (int j = 0; j < SCOREBACK+1; j++){  //+1 to account for the marble were currently on
                    ring.previous();
                }
                int rmvdMarble = ring.previous();
                playerScores[i%PLAYERS] = playerScores[i%PLAYERS].add(new BigInteger(""+(i+rmvdMarble)));
                ring.remove();
                ring.next();
                System.out.println("Score! P:" + i%PLAYERS + " M:" + i + " S:" + rmvdMarble + " = " + (i+rmvdMarble));
            }
            else {
                ring.add(i);
            }
            ring.next();
//            System.out.println((i-1)%PLAYERS+1 + ": " + cll);
        }

        BigInteger max = BigInteger.ZERO;
        int winner = 0;
        for (int i = 0; i < PLAYERS; i++){
//            System.out.println((i+1) + " " + playerScores[i]);
            if (playerScores[i].compareTo(max) > 0){
                max = playerScores[i];
                winner = i+1;
                System.out.println("New Max: " + winner + " " + playerScores[i]);
            }
        }
        System.out.println(winner + ": " + max);
    }
}
