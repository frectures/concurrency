package live;

import java.util.Arrays;
import java.util.Random;

public class Sum {
    private static final int PROBLEM_SIZE = 250_000_000;

    public static void main(String[] args) {
        int[] a = new int[PROBLEM_SIZE];
        randomize(a);
        {
            Benchmark benchmark = new Benchmark("sequential loop");
            while (!benchmark.done()) {
                benchmark.measure(() -> sequentialLoop(a));
            }
        }
        {
            Benchmark benchmark = new Benchmark("sequential stream");
            while (!benchmark.done()) {
                benchmark.measure(() -> sequentialStream(a));
            }
        }
    }

    private static void randomize(int[] a) {
        Random random = new Random(0);
        for (int i = 0; i < a.length; ++i) {
            a[i] = random.nextInt(90) + 10;
        }
    }

    private static int sequentialLoop(int[] a) {
        int sum = 0;
        for (int x : a) {
            sum += x;
        }
        return sum;
    }

    private static int sequentialStream(int[] a) {
        return Arrays.stream(a).sum();
    }
}
