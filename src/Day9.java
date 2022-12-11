import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day9 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day9-input.txt")).useDelimiter(" |\\r\\n");
        Set<String> visitedCoordinates = new HashSet<>();
        int[][] rope = new int[10][];
        for (int i = 0; i < 10; i++) {
            int[] c = new int[2];
            rope[i] = c;
        }

        while (scanner.hasNextLine()) {
            String direction = scanner.next();
            int steps = scanner.nextInt();
            for (int i = 0; i < steps; i++) {
                if (direction.equals("U")) {
                    rope[0][1]++;
                } else if (direction.equals("D")) {
                    rope[0][1]--;
                } else if (direction.equals("R")) {
                    rope[0][0]++;
                } else if (direction.equals("L")) {
                    rope[0][0]--;
                }
                for (int j = 1; j < 10; j++) {
                    int[] coord = getTailCoordinate(rope[j - 1][0], rope[j - 1][1], rope[j][0], rope[j][1]);
                    rope[j][0] = coord[0];
                    rope[j][1] = coord[1];
                    if (j == 9) {
                        visitedCoordinates.add("" + rope[j][0] + "," + rope[j][1]);
                    }
                }
            }
        }

        System.out.println(Arrays.toString(visitedCoordinates.toArray()));
        int totalVisited = visitedCoordinates.size();
        System.out.println("Answer part 1: " + totalVisited);
    }

    private static int[] getTailCoordinate(int x, int y, int tx, int ty) {
        int xDiff = x - tx;
        int yDiff = y - ty;
        int dirX = Integer.signum(xDiff);
        int dirY = Integer.signum(yDiff);

        while (!(Math.abs(xDiff) <= 1 && Math.abs(yDiff) <= 1)) {
            tx += dirX;
            ty += dirY;
            xDiff = x - tx;
            yDiff = y - ty;
            dirX = Integer.signum(xDiff);
            dirY = Integer.signum(yDiff);
        }

        return new int[]{tx, ty};
    }

}
