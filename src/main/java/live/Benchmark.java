package live;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class Benchmark {
    private static final long MILLISECONDS_PER_NANOSECOND = 1_000_000;

    private final String description;
    private int runs;
    private long[] deltas;

    public Benchmark(String description) {
        this(description, 0);
    }
    public Benchmark(String description, int plannedRuns) {
        // enough data for 2 discarded warmups and average of median 3
        plannedRuns = Math.max(2 + 3 + 2, plannedRuns | 1);
        this.description = description;
        this.runs = plannedRuns;
        this.deltas = new long[plannedRuns];
    }

    public boolean done() {
        return runs == 0;
    }

    public void measure(Runnable job) {
        if (done()) throw new IllegalStateException("done!");

        long before = System.nanoTime();
        job.run();
        long after = System.nanoTime();
        enterMeasurement(before, after, "");
    }

    public void measure(Callable<?> job) {
        if (done()) throw new IllegalStateException("done!");

        Object result;
        long before = System.nanoTime();
        try {
            result = job.call();
        } catch (Exception ex) {
            result = ex;
        }
        long after = System.nanoTime();
        enterMeasurement(before, after, result.toString());
    }

    private void enterMeasurement(long before, long after, String result) {
        long delta = after - before;
        deltas[--runs] = delta;
        long ms = delta / MILLISECONDS_PER_NANOSECOND;
        System.out.println(description + ": " + ms + " ms   " + result);

        if (runs == 0) {
            summary();
        }
    }

    private void summary() {
        Arrays.sort(deltas);
        int middle = deltas.length / 2;
        long a = deltas[middle - 1];
        long b = deltas[middle];
        long c = deltas[middle + 1];
        long average = (a + b + c) / 3;
        System.out.println(average / MILLISECONDS_PER_NANOSECOND + " ms average of median 3");
    }
}
