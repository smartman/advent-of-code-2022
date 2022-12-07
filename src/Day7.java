import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day7-input.txt"));

        List<DirStructure> allDirectories = new ArrayList<>();
        DirStructure currentDirectory = new DirStructure("/");
        DirStructure rootDir = currentDirectory;

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            System.out.println(Arrays.toString(line));
            if ("$cd".equals(line[0] + line[1])) {
                if ("..".equals(line[2])) {
                    currentDirectory = currentDirectory.getParent();
                } else {
                    DirStructure newDir = new DirStructure(line[2]);
                    newDir.setParent(currentDirectory);
                    currentDirectory.getChildren().add(newDir);
                    currentDirectory = newDir;
                    allDirectories.add(newDir);
                }
            } else if (line[0].chars().allMatch(Character::isDigit)) {
                currentDirectory.setSize(currentDirectory.getSize() + Integer.parseInt(line[0]));
            }
        }

        int totalSize = 0;
        for (DirStructure nextDir : allDirectories) {
            int dirSize = nextDir.getTotalSize();
            if (dirSize < 100000) {
                totalSize += dirSize;
            }
        }

        //part2
        int minFree = Integer.MAX_VALUE;
        int freeSpace = 70000000 - rootDir.getTotalSize();
        int requiredSpace = Math.min(30000000 - freeSpace, 30000000);

        for (DirStructure nextDir : allDirectories) {
            int dirSize = nextDir.getTotalSize();
            if (dirSize > requiredSpace && minFree > dirSize) {
                minFree = dirSize;
            }
        }
        //end of part2

        System.out.println("Part 1 answer is: " + totalSize);
        System.out.println("Part 2 answer is: " + minFree);
    }

    static class DirStructure {
        private String name;
        private DirStructure parent;
        private Set<DirStructure> children;
        private int size;

        public DirStructure(String name) {
            this.name = name;
            children = new HashSet<>();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DirStructure getParent() {
            return parent;
        }

        public void setParent(DirStructure parent) {
            this.parent = parent;
        }

        public Set<DirStructure> getChildren() {
            return children;
        }

        public void setChildren(Set<DirStructure> children) {
            this.children = children;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotalSize() {
            int childrenSize = 0;
            for (DirStructure child : this.getChildren()) {
                int childSize = child.getTotalSize();
                childrenSize += childSize;
            }
            return childrenSize + this.getSize();
        }
    }
}