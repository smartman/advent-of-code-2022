import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Day2RockPaperScissors {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("inputs/day2-input.txt"));

        long totalScore = 0;
        long totalScore2 = 0;
        while (scanner.hasNext()) {
            String opp = scanner.next();
            String mine = scanner.next();
            totalScore += roundScore(opp, mine) + itemScore(mine);

            String mine2 = chosenItem(opp, mine);
            totalScore2 += roundScore(opp, mine2) + itemScore(mine2);
        }

        System.out.println("Total score strategy 1: " + totalScore);
        System.out.println("Total score strategy 2: " + totalScore2);

        FileWriter fw = new FileWriter("output.txt");
        fw.write(String.valueOf(totalScore));
    }

    private static String chosenItem(String opp, String mine) {
        switch (opp + mine) {
            // I need draw
            case "AY":
                return "X";
            case "BY":
                return "Y";
            case "CY":
                return "Z";
            // I need to win
            case "AZ":
                return "Y";
            case "BZ":
                return "Z";
            case "CZ":
                return "X";
            // I need to lose
            case "AX":
                return "Z";
            case "BX":
                return "X";
            default: // CX
                return "Y";
        }
    }

    /**
     * Rocks A, X
     * Papers B, Y
     * Scissors C, Z
     */
    private static int roundScore(String opp, String mine) {
        switch (opp + mine) {
            // I win
            case "AY":
            case "BZ":
            case "CX":
                return 6;
            // tie
            case "AX":
            case "BY":
            case "CZ":
                return 3;
            // I lose
            default:
                return 0;
        }
    }

    private static int itemScore(String mine) {
        switch (mine) {
            case "X":
                return 1;
            case "Y":
                return 2;
            default: // Z
                return 3;
        }
    }
}
