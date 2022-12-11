import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Day11 {
    static List<Monkey> monkeyList = new LinkedList<>();
    static BigInteger multiples;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day11-input.txt"));

        readMonkeyList(scanner);

        // We will multiply all number together that will be used to calculate modulus
        // Especially in part 2 we need to keep the numbers at the reasonable level.
        // This multiplication allows us to calculate remainder that will give exactly same modulus values
        multiples = monkeyList.stream().map(m -> m.divisibleTest).reduce(BigInteger.ONE, BigInteger::multiply);

        // part 1
//        for (int i = 0; i < 20; i++) {
//            System.out.println("Round " + i);
//            for (Monkey next : monkeyList) {
//                System.out.println(next);
//                throwItems(next, false);
//
//            }
//        }

        //part 2
        for (int i = 0; i < 10000; i++) {
            for (Monkey next : monkeyList) {
                throwItems(next, true);
            }
        }

        List<Integer> inspectCounts = new LinkedList<>();
        for (Monkey monkey : monkeyList) {
            inspectCounts.add(monkey.getItemsInspected());
        }

        Collections.sort(inspectCounts);
        Collections.reverse(inspectCounts);
        System.out.println((long) inspectCounts.get(0) * (long) inspectCounts.get(1));
    }


    public static void throwItems(Monkey monkey, boolean part2) {
        Queue<BigInteger> monkeyItems = monkey.getItems();
        while (!monkeyItems.isEmpty()) {
            BigInteger item = monkeyItems.poll();
            monkey.inspectItem();
            // calculate new worry level
            BigInteger newWorryLevel;
            if (monkey.isOldOperator()) {
                monkey.setOperator(item);
            }
            if (monkey.getOperation() == '+') {
                newWorryLevel = item.add(monkey.getOperator());
            } else {
                newWorryLevel = item.multiply(monkey.getOperator());
            }
            if (!part2) {
                long divisionResult=Math.floorDiv(newWorryLevel.longValue(), 3);
                newWorryLevel = new BigInteger(String.valueOf(divisionResult));
            }

            // decide which monkey to throw items
            int nextMonkey;
            newWorryLevel = newWorryLevel.divideAndRemainder(multiples)[1]; // reduce the huge number
            boolean testMatches = newWorryLevel.mod(monkey.getDivisibleTest()).equals(BigInteger.ZERO);
            if (testMatches) {
                nextMonkey = monkey.getTrueMonkey();
            } else {
                nextMonkey = monkey.getFalseMonkey();
            }

//            System.out.printf("Orig %d, new %d, next %d\n", item, newWorryLevel, nextMonkey);
            monkeyList.get(nextMonkey).catchItem(newWorryLevel);
        }
    }

    public static void readMonkeyList(Scanner scanner) {
        String[] parts;
        Monkey monkey = new Monkey("-");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Monkey")) {
                monkey = new Monkey(line.substring(7, 8));
            } else if (line.startsWith("  Starting items")) {
                parts = line.substring(18).split(", ");
                for (String part : parts) {
                    monkey.catchItem(new BigInteger(part));
                }
            } else if (line.startsWith("  Operation")) {
                parts = line.substring(23).split(" ");
                monkey.setOperation(parts[0].toCharArray()[0]);
                if ("old".equals(parts[1])) {
                    monkey.setOldOperator(true);
                } else {
                    monkey.setOperator(new BigInteger(parts[1]));
                }
            } else if (line.startsWith("  Test")) {
                monkey.setDivisibleTest(new BigInteger(line.substring(21)));
            } else if (line.startsWith("    If true")) {
                monkey.setTrueMonkey(Integer.parseInt(line.substring(29)));
            } else if (line.startsWith("    If false")) {
                monkey.setFalseMonkey(Integer.parseInt(line.substring(30)));
                monkeyList.add(monkey);
            }
        }
    }
}

class Monkey {

    String id;
    Queue<BigInteger> items = new LinkedList<>();
    char operation;
    BigInteger operator;
    boolean isOldOperator;
    BigInteger divisibleTest;
    int trueMonkey;
    int falseMonkey;
    int itemsInspected;

    public Monkey(String id) {
        this.id = id;
    }

    public void inspectItem() {
        itemsInspected++;
    }

    public void catchItem(BigInteger worryLevel) {
        items.offer(worryLevel);
    }

    public int getItemsInspected() {
        return itemsInspected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Queue<BigInteger> getItems() {
        return items;
    }

    public char getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    public BigInteger getOperator() {
        return operator;
    }

    public void setOperator(BigInteger operator) {
        this.operator = operator;
    }

    public boolean isOldOperator() {
        return isOldOperator;
    }

    public void setOldOperator(boolean oldOperator) {
        isOldOperator = oldOperator;
    }

    public BigInteger getDivisibleTest() {
        return divisibleTest;
    }

    public void setDivisibleTest(BigInteger divisibleTest) {
        this.divisibleTest = divisibleTest;
    }

    public int getTrueMonkey() {
        return trueMonkey;
    }

    public void setTrueMonkey(int trueMonkey) {
        this.trueMonkey = trueMonkey;
    }

    public int getFalseMonkey() {
        return falseMonkey;
    }

    public void setFalseMonkey(int falseMonkey) {
        this.falseMonkey = falseMonkey;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "id='" + id + '\'' +
                ", items=" + Arrays.toString(items.toArray()) +
                ", itemsInspected=" + itemsInspected +
                '}';
    }
}
