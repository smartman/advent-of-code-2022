import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Day1CalorieCounting {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day1-input.txt"));

        long[] top3 = new long[3];
        String line;
        long calories = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if ("".equals(line)) {
                for (long top : top3) {
                    if (top < calories) {
                        top3[0] = calories;
                        break;
                    }
                }

                calories = 0;
                Arrays.sort(top3);
                continue;
            }
            calories = calories + Long.parseLong(line);
        }

        long totalSum = 0;
        for (long top : top3) {
            totalSum += top;
        }

        System.out.println("Total calories for top 3 " + totalSum);
        System.out.println("Total calories for top 1 " + top3[2]);
    }
}