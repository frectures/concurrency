package live;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Sum {
    private final int[] a;
    private final int expectedSum;

    public Sum() {
        a = new int[100_000_000];
        System.out.print("RANDOMIZING... ");
        long seed = 0;
        int sum = 0;
        for (int i = 0; i < a.length; ++i) {
            seed = seed * 6364136223846793005L + 1;
            sum += a[i] = (int) (seed >>> 32);
        }
        expectedSum = sum;
    }

    private int check(int sum) {
        if (sum != expectedSum) throw new RuntimeException(sum + " != " + expectedSum);
        return sum;
    }

    @Benchmark
    public int sequentialLoop() {
        int sum = 0;
        for (int x : a) {
            sum += x;
        }
        return check(sum);
    }

    @Benchmark
    public int sequentialStream() {
        return check(Arrays.stream(a).sum());
    }

    @Benchmark
    public int parallelLoop() throws InterruptedException {
        int sum = 0;
        // TODO parallelize
        for (int i = 0; i < a.length / 2; ++i) {
            sum += a[i];
        }
        for (int i = a.length / 2; i < a.length; ++i) {
            sum += a[i];
        }
        return check(sum);
    }

    @Benchmark
    public int parallelStream() {
        return check(Arrays.stream(a).parallel().sum());
    }

    static void main() throws IOException {
        org.openjdk.jmh.Main.main(new String[]{Sum.class.getName()});
    }
}
