public class Main {
    private final static int SIZE = 300, SERIAL = 4172;
    private static int[][] grid = new int[SIZE][SIZE];
    public static void main(String args[]){
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                grid[i][j] = calcPower(i+1, j+1);
            }
        }
        int maxX = -1, maxY = -1, maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < SIZE-5; i++){
            for (int j = 0; j < SIZE-5; j++) {
                int sum = 0;
                for (int x = 0; x < 3; x++){
                    for (int y = 0; y < 3; y++){
                        sum += grid[i+x][j+y];
                    }
                }
                if (sum > maxVal){
                    maxX = i+1;
                    maxY = j+1;
                    maxVal = sum;
                }
            }
        }
        System.out.println("("+maxX+","+maxY+","+maxVal+")");

        maxX = -1;
        maxY = -1;
        maxVal = Integer.MIN_VALUE;
        int maxSize = 0;
        for (int s = 1; s < SIZE; s++) {
            if (s%10 == 0){
                System.out.println(s);
            }
            for (int i = 0; i < SIZE - s; i++) {
                for (int j = 0; j < SIZE - s; j++) {
                    int sum = 0;
                    for (int x = 0; x < s; x++) {
                        for (int y = 0; y < s; y++) {
                            sum += grid[i + x][j + y];
                        }
                    }
                    if (sum > maxVal) {
                        maxX = i + 1;
                        maxY = j + 1;
                        maxVal = sum;
                        maxSize = s;
                    }
                }
            }
        }
        System.out.println("("+maxX+","+maxY+","+maxVal+","+maxSize+")");
    }

    private static int calcPower(int x, int y){
        int rackID = x+10;
        int val = rackID;
        val *= y;
        val += SERIAL;
        val *= rackID;
        val /= 100;
        val %= 10;
        val -= 5;
        return val;
    }
}
