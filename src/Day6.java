import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day6 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day6-input.txt"));
        char[] line = scanner.nextLine().toCharArray();

        System.out.println("Part 1 answer: " + messagePosition(line, 4));
        System.out.println("Part 2 answer: " + messagePosition(line, 14));
    }

    private static int messagePosition(char[] line, int messageLength) {
        for (int i = messageLength - 1; i < line.length; i++) {
            Set<Character> charset = new HashSet<>();

            for (int j = 0; j < messageLength; j++) {
                charset.add(line[i - j]);
            }

            if (charset.size() == messageLength) {
                return i + 1;
            }
        }
        return 0;
    }
}
