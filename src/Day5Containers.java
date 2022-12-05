import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Day5Containers {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day5-input.txt"));

        List<String> configLines = new ArrayList<>();
        List<Stack<Character>> columnns = new ArrayList<>();

        // read all crates configuration
        String line = scanner.nextLine();
        while (!line.startsWith(" 1")) {
            configLines.add(line);
            line = scanner.nextLine();
        }

        int numColumns = (line.length() + 2) / 4;
        System.out.println(numColumns);
        for (int i = 0; i < numColumns; i++) {
            columnns.add(new Stack<>());
        }

        // start from the bottom row, parse crates into Stacks
        for (int i = configLines.size() - 1; i >= 0; i--) {
            int configLineIndex = 0;
            String configLine = configLines.get(i);
            while (configLineIndex <= configLine.length()) {
                Stack<Character> column = columnns.get(configLineIndex / 4);
                String crateInfo = configLine.substring(configLineIndex, Math.min(configLineIndex + 4, configLine.length()));
                configLineIndex += 4;
                if (crateInfo.contains("[")) {
                    column.push(crateInfo.substring(1, 2).toCharArray()[0]);
                }
            }
        }

        // read empty line
        scanner.nextLine();
        // Execute crates moving
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] parts = line.split(" ");
            System.out.println(line);
            int numIterations = Integer.parseInt(parts[1]);
            Stack<Character> sourceStack = columnns.get(Integer.parseInt(parts[3]) - 1);
            Stack<Character> destinationStack = columnns.get(Integer.parseInt(parts[5]) - 1);

            // part 1
//            for (int i = 0; i < numIterations; i++) {
//                Character crate = sourceStack.pop();
//                destinationStack.push(crate);
//            }

            // part 2
            Stack<Character> tempStack = new Stack<>();
            for (int i = 0; i < numIterations; i++) {
                Character crate = sourceStack.pop();
                tempStack.push(crate);
            }

            for (int i = 0; i < numIterations; i++) {
                Character crate = tempStack.pop();
                destinationStack.push(crate);
            }
        }

        StringBuilder answerString = new StringBuilder();
        for (Stack<Character> column : columnns) {
            answerString.append(column.peek());
        }

        System.out.println("Answer is: " + answerString);
    }
}
