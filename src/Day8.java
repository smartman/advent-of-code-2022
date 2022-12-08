import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day8-input.txt")).useDelimiter("");
        int height = 0;
        int widht = 0;

        // read in dimensions
        while (scanner.hasNextLine()) {
            widht = scanner.nextLine().length();
            height++;
        }

        scanner.close();
        scanner = new Scanner(new File("inputs/day8-input.txt")).useDelimiter("");

        char[][] forest = new char[widht][height];
        boolean[][] visibleMap = new boolean[widht][height];

        int forestRow = 0;
        while (scanner.hasNextLine()) {
            forest[forestRow] = scanner.nextLine().toCharArray();
            forestRow++;
        }

        // top to down
        for (int i = 0; i < widht; i++) {
            char treeHeight = 0;
            for (int j = 0; j < height; j++) {
                if (forest[i][j] > treeHeight) {
                    visibleMap[i][j] = true;
                    treeHeight = forest[i][j];
                }
            }
        }

        // bottom to up
        for (int i = widht - 1; i >= 0; i--) {
            char treeHeight = 0;
            for (int j = widht - 1; j >= 0; j--) {
                if (forest[i][j] > treeHeight) {
                    visibleMap[i][j] = true;
                    treeHeight = forest[i][j];
                }
            }
        }

        // left to right
        for (int j = 0; j < height; j++) {
            char treeHeight = 0;
            for (int i = 0; i < widht; i++) {
                if (forest[i][j] > treeHeight) {
                    visibleMap[i][j] = true;
                    treeHeight = forest[i][j];
                }
            }
        }

        // right to left
        for (int j = widht - 1; j >= 0; j--) {
            char treeHeight = 0;
            for (int i = widht - 1; i >= 0; i--) {
                if (forest[i][j] > treeHeight) {
                    visibleMap[i][j] = true;
                    treeHeight = forest[i][j];
                }
            }
        }

        int totalVisible = 0;

        for (int i = 0; i < widht; i++) {
            for (int j = 0; j < height; j++) {
                if (visibleMap[i][j]) {
                    totalVisible++;
                }
            }
        }


        getScenicScore(3, 2, widht, height, forest);

        //part 2
        int maxScenicScore = 0;
        for (int i = 0; i < widht; i++) {
            for (int j = 0; j < height; j++) {
                int scenicScore = getScenicScore(i, j, widht, height, forest);
                if (scenicScore > maxScenicScore) {
                    System.out.println("Scenic location: " + i + ", " + j + " score=" + scenicScore);
                    maxScenicScore = scenicScore;
                }
            }
        }

        System.out.println("Day 2 part 1 answer: " + totalVisible);
        System.out.println("Day 2 part 2 answer: " + maxScenicScore);
    }

    private static int getScenicScore(int x, int y, int widht, int height, char[][] forest) {
        int top = 0;
        int bottom = 0;
        int left = 0;
        int right = 0;


        char treeHeight = forest[x][y];
        // top to down

        for (int i = x + 1; i < widht; i++) {
            bottom++;
            if (forest[i][y] >= treeHeight) {
                break;
            }
        }

        //left to right
        treeHeight = forest[x][y];
        for (int i = y + 1; i < widht; i++) {
            right++;
            if (forest[x][i] >= treeHeight) {
                break;
            }
        }

        //bottom to up
        treeHeight = forest[x][y];
        for (int i = x - 1; i >= 0; i--) {
            top++;
            if (forest[i][y] >= treeHeight) {
                break;
            }
        }

        //right to left
        treeHeight = forest[x][y];
        for (int i = y - 1; i >= 0; i--) {
            left++;
            if (forest[x][i] >= treeHeight) {
                break;
            }
        }

        return top * bottom * left * right;
    }
}
