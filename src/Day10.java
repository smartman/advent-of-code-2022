import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day10 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("inputs/day10-input.txt"));
        int cycle = 0;
        int signal = 1;
        int signalSum = 0;
        char[] pixels = new char[240];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
//            System.out.println("Cycle " + cycle + ", signal : " + signal + ", " + line);
            if (line.startsWith("noop")) {
                pixels[cycle] = getPixel(cycle, signal);
                cycle++;
                signalSum += doAddSignl(cycle, signal);

            } else {
                String[] parts = line.split(" ");
                pixels[cycle] = getPixel(cycle, signal);
                cycle++;
                signalSum += doAddSignl(cycle, signal);
                pixels[cycle] = getPixel(cycle, signal);
                cycle++;
                signalSum += doAddSignl(cycle, signal);


                signal += Integer.parseInt(parts[1]);
            }
        }

        System.out.println(signalSum);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 40; j++) {
                System.out.print(pixels[i * 40 + j]);
            }
            System.out.println();
        }
    }

    public static char getPixel(int cycle, int signal) {
        char newPixel = '#';
        cycle = cycle % 40;
        if ((signal > (cycle + 1)) || (signal < (cycle - 1))) {
            newPixel = '.';
        }

        System.out.println("Cycle " + cycle + " signal " + signal + ", pixel: " + newPixel);
        return newPixel;
    }

    public static int doAddSignl(int cycle, int signal) {
        if ((cycle - 20) % 40 == 0) {
            return signal * cycle;
        } else {
            return 0;
        }
    }
}
