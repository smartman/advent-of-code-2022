import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day4Cleaning {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day4-input.txt")).useDelimiter("-|,|\\s");
        int[] coords= new int[4];

        int fullyOverLaps = 0;
        int partlyOverlaps =0;

        while (scanner.hasNextLine()) {
            String line=scanner.nextLine();
            String[] parts = line.split("-|,");
            for (int i=0;i<4;i++) {
                coords[i]=Integer.parseInt(parts[i]);
            }

            if (isFullyOverlaps(coords[0], coords[1], coords[2], coords[3])) {
                fullyOverLaps++;
            }

            if (isPartlyOverlaps(coords[0], coords[1], coords[2], coords[3])) {
                partlyOverlaps++;
            }
        }

        System.out.println("Total overlapping: " + fullyOverLaps);
        System.out.println("Partly overlapping: " + partlyOverlaps);
    }

    private static boolean isPartlyOverlaps(int x1, int x2, int y1, int y2) {
        return !((x1 < y1 && x2 < y1) || (x1 > y2 && x2 > y1));
    }
    private static boolean isFullyOverlaps(int x1, int x2, int y1, int y2) {
        return (x1 <= y1 && x2 >= y2) || (x1 >= y1 && x2 <= y2);
    }
}
