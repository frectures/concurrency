package live;

import java.util.Arrays;
import java.util.Random;

public class Sum {
    private static final int PROBLEM_SIZE = 500_000_000;

    public static void main(String[] args) {
        int[] a = new int[PROBLEM_SIZE];
        randomize(a);
        Benchmark benchmark;

        benchmark = new Benchmark("sequential loop");
        while (benchmark.requiresMoreMeasurements()) {
            benchmark.measure(() -> sequentialLoop(a));
        }

        benchmark = new Benchmark("sequential stream");
        while (benchmark.requiresMoreMeasurements()) {
            benchmark.measure(() -> sequentialStream(a));
        }

        benchmark = new Benchmark("parallel loop");
        while (benchmark.requiresMoreMeasurements()) {
            benchmark.measure(() -> parallelLoop(a));
        }

        benchmark = new Benchmark("parallel stream");
        while (benchmark.requiresMoreMeasurements()) {
            benchmark.measure(() -> parallelStream(a));
        }
    }

    private static void randomize(int[] a) {
        System.out.println("randomizing...");
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

    private static int parallelLoop(int[] a) throws InterruptedException {
        int sum = 0;
        // TODO parallelize
        for (int i = 0; i < PROBLEM_SIZE/2; ++i) {
            sum += a[i];
        }
        for (int i = PROBLEM_SIZE/2; i < PROBLEM_SIZE; ++i) {
            sum += a[i];
        }
        return sum;
    }

    private static int parallelStream(int[] a) {
        return Arrays.stream(a).parallel().sum();
    }
}
