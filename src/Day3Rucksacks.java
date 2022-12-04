import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day3Rucksacks {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day3-input.txt"));

        long prioritiesSum = 0;
        while (scanner.hasNextLine()) {
            String line1 = scanner.nextLine();
            String line2 = scanner.nextLine();
            String line3 = scanner.nextLine();
            char common = findBadge(line1.toCharArray(), line2.toCharArray(), line3.toCharArray());

            // Part 1
//            String firstBag = line.substring(0, line.length() / 2);
//            String secondBag = line.substring(line.length() / 2);
//            char common = findCommonItems(firstBag.toCharArray(), secondBag.toCharArray());
            if (common >= 97) {
                prioritiesSum += common - 96;
            } else {
                prioritiesSum += common - 38;
            }
        }
        System.out.println("Priorities sum= " + prioritiesSum);
    }

    private static char findBadge(char[] first, char[] second, char[] third) {
        for (char i : first) {
            for (char j : second) {
                for (char k : third) {
                    if (i == j && j == k) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    private static char findCommonItems(char[] first, char[] second) {
        char common = 0;
        for (char i : first) {
            for (char j : second) {
                if (i == j) {
                    return i;
                }
            }
        }

        return common;
    }
}
