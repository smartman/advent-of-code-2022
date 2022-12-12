import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.*;

public class Day12 {
    static char[][] map;
    static int[][] shortestPath;
    static Position startPosition;
    static Position endPosition;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day12-input.txt"));
        int height = 0;
        int width = 0;
        while (scanner.hasNextLine()) {
            width = scanner.nextLine().toCharArray().length;
            height++;
        }

        scanner = new Scanner(new File("inputs/day12-input.txt")).useDelimiter("");

        map = new char[width][height];
        shortestPath = new int[width][height];
        char[][] visited = new char[width][height];

        startPosition = new Position(0, 0);
        endPosition = new Position(0, 0);

        List<Position> startingPoints = new LinkedList<>();

        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                char next = scanner.next().toCharArray()[0];
                while (next == '\r' || next == '\n') {
                    next = scanner.next().toCharArray()[0];
                }
                map[j][i] = next;
                shortestPath[j][i] = Integer.MAX_VALUE;
                if (map[j][i] == 'S') {
                    startPosition = new Position(j, i);
                    map[j][i] = 'a';
                } else if (map[j][i] == 'E') {
                    endPosition = new Position(j, i);
                    map[j][i] = 'z';
                }
                if (map[j][i] == 'a') {
                    startingPoints.add(new Position(j, i));
                }
            }
        }

        System.out.println("Start: " + startPosition);
        System.out.println("End: " + endPosition);
        printMap(map);

        int minSteps = nextSquare(0, startPosition, visited);


        System.out.println("Part 1 answer: " + minSteps);

        int shortestHike = Integer.MAX_VALUE;
        int count = 0;
        for (Position nextA : startingPoints) {
            for (int i = 0; i < shortestPath.length; i++) {
                for (int j = 0; j < shortestPath[i].length; j++) {
                    shortestPath[i][j] = Integer.MAX_VALUE;
                    visited[i][j] = 0;
                }
            }
            try {
                minSteps = nextSquare(0, nextA, visited);
                if (shortestHike > minSteps) {
                    shortestHike = minSteps;
                }
                System.out.println("Min steps here: " + minSteps+ " count "+count);
            } catch (Exception e) {
                System.out.println("Nothing to see here");
            } finally {
                count++;
            }
        }

        System.out.println("Part 2 answer: " + shortestHike);
    }

    static int nextSquare(int previousSteps, Position currentPosition, char[][] visitedMap) {
        if (currentPosition.equals(endPosition)) {
//            System.out.println("Found path of: " + previousSteps);
            return previousSteps;
        }

        if (previousSteps < shortestPath[currentPosition.getX()][currentPosition.getY()]) {
            shortestPath[currentPosition.getX()][currentPosition.getY()] = previousSteps;
        }

        Position right = currentPosition.moveRight(map, visitedMap);
        Position left = currentPosition.moveLeft(map, visitedMap);
        Position up = currentPosition.moveUp(map, visitedMap);
        Position down = currentPosition.moveDown(map, visitedMap);

        if (right == null && left == null && up == null && down == null) {
//            System.out.println("Stuck, here is the map: " + currentPosition + " level=" + map[currentPosition.getX()][currentPosition.getY()]);
//            printMap(visitedMap);
            throw new RuntimeException("Stuck");
        }

        int minStepsThere = Integer.MAX_VALUE;

        visitedMap[currentPosition.getX()][currentPosition.getY()] = '#';
        for (Position direction : new Position[]{right, down, left, up}) {
            try {
                if (direction == null) {
                    continue;
                }
                if (shortestPath[direction.getX()][direction.getY()] <= previousSteps + 1) {
                    continue;
                }

//                visitedMap[currentPosition.getX()][currentPosition.getY()] = (char) ('a' + previousSteps);

                int distance = nextSquare(previousSteps + 1, direction, visitedMap);

                if (distance < minStepsThere) {
                    minStepsThere = distance;
                }
//                System.out.println(currentPosition + " direction " + i + " steps " + distance);
            } catch (RuntimeException e) {
                // Nothing to handle
            }
        }

        visitedMap[currentPosition.getX()][currentPosition.getY()] = 0;
        return minStepsThere;
    }


    static int printMap(char[][] map) {
        int visitedSquares = 0;
        for (int x = map[0].length - 1; x >= 0; x--) {
            for (int y = 0; y < map.length; y++) {
                System.out.printf("%s", map[y][x] == 0 ? '.' : map[y][x]);
                if (map[y][x] > 0) {
                    visitedSquares++;
                }
            }
            System.out.println();
        }
        return visitedSquares;
    }

}

class Position {
    private final int x;
    private final int y;

    public Position moveRight(char[][] map, char[][] visitedMap) {
        int newX = x + 1;
        int newY = y;
        Position newPosition = new Position(newX, newY);

        char currentHeight = map[x][y];

        if (!canStepThere(currentHeight, newX, newY, map, visitedMap)) {
            return null;
        }

        return newPosition;
    }

    public Position moveLeft(char[][] map, char[][] visitedMap) {
        int newX = x - 1;
        int newY = y;
        Position newPosition = new Position(newX, newY);

        char currentHeight = map[x][y];

        if (!canStepThere(currentHeight, newX, newY, map, visitedMap)) {
            return null;
        }

        return newPosition;
    }

    public Position moveUp(char[][] map, char[][] visitedMap) {
        int newX = x;
        int newY = y + 1;
        Position newPosition = new Position(newX, newY);

        char currentHeight = map[x][y];

        if (!canStepThere(currentHeight, newX, newY, map, visitedMap)) {
            return null;
        }

        return newPosition;
    }

    public Position moveDown(char[][] map, char[][] visitedMap) {
        int newX = x;
        int newY = y - 1;
        Position newPosition = new Position(newX, newY);

        char currentHeight = map[x][y];

        if (!canStepThere(currentHeight, newX, newY, map, visitedMap)) {
            return null;
        }

        return newPosition;
    }

    public boolean canStepThere(char currentHeight, int newX, int newY, char[][] map, char[][] visitedMap) {

        if (newX < 0 || newY < 0) {
            return false;
        } else if (newX >= map.length || newY >= map[0].length) {
            return false;
        }

        char newHeight = map[newX][newY];
        char visited = visitedMap[newX][newY];

        if (newHeight > (currentHeight + 1)) {
            return false;
        } else if (visited > 0) {
            return false;
        }

        return true;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }
}
