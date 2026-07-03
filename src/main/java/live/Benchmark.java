package live;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class Benchmark {
    private static final int REQUIRED_MEASUREMENTS = 7;
    private static final long MILLISECONDS_PER_NANOSECOND = 1_000_000;

    private final String description;
    private final long[] measurements;
    private int countMeasurements;

    public Benchmark(String description) {
        this.description = description;
        this.measurements = new long[REQUIRED_MEASUREMENTS];
        this.countMeasurements = 0;
    }

    public boolean requiresMoreMeasurements() {
        return countMeasurements < REQUIRED_MEASUREMENTS;
    }

    public void measure(Runnable job) {
        long before = System.nanoTime();
        job.run();
        long after = System.nanoTime();
        storeMeasurement(before, after, "");
    }

    public void measure(Callable<?> job) {
        Object result;
        long before = System.nanoTime();
        try {
            result = job.call();
        } catch (Exception ex) {
            result = ex;
        }
        long after = System.nanoTime();
        storeMeasurement(before, after, result.toString());
    }

    private void storeMeasurement(long before, long after, String result) {
        long measurement = after - before;
        measurements[countMeasurements++] = measurement;
        long ms = measurement / MILLISECONDS_PER_NANOSECOND;
        System.out.println(description + ": " + ms + " ms   " + result);

        if (countMeasurements == REQUIRED_MEASUREMENTS) {
            summary();
        }
    }

    private void summary() {
        Arrays.sort(measurements);
        int middle = measurements.length / 2;
        long a = measurements[middle - 1];
        long b = measurements[middle];
        long c = measurements[middle + 1];
        long average = (a + b + c) / 3;
        System.out.println(average / MILLISECONDS_PER_NANOSECOND + " ms (average of median 3)");
    }
}
