package live;

import java.util.Random;

public class Quicksort {
    private static final int PROBLEM_SIZE = 8_000_000;
    private static final int CHUNK_SIZE = 32;

    public static void main(String[] args) {
        int[] a = new int[PROBLEM_SIZE];
        Benchmark benchmark = new Benchmark("quicksort");
        while (benchmark.requiresMoreMeasurements()) {
            randomize(a);
            benchmark.measure(() -> quicksort(a));
            checkIsSorted(a);
        }
    }

    private static void randomize(int[] a) {
        Random random = new Random(0);
        for (int i = 0; i < a.length; ++i) {
            a[i] = random.nextInt() >>> 1;
        }
    }

    private static void checkIsSorted(int[] a) {
        for (int i = 1; i < a.length; ++i) {
            if (a[i - 1] > a[i]) {
                throw new RuntimeException(a[i - 1] + " > " + a[i] + " @ " + i);
            }
        }
    }

    public static void quicksort(int[] a) {
        quicksort(a, 0, a.length - 1);
        insertionsort(a);
    }

    private static void insertionsort(int[] a) {
        for (int i = 1; i < a.length; ++i) {
            int temp = a[i];
            int k;
            for (k = i; k > 0 && a[k - 1] > temp; --k) {
                a[k] = a[k - 1];
            }
            a[k] = temp;
        }
    }

    private static void quicksort(int[] a, int left, int right) {
        // TODO parallelize
        if (right - left >= CHUNK_SIZE) {
            int middle = (left + right) >>> 1;
            int pivot = a[middle];

            int l = left;
            int r = right;
            while (l <= r) {
                while (a[l] < pivot) ++l;
                while (a[r] > pivot) --r;

                if (l <= r) {
                    int temp = a[l];
                    a[l] = a[r];
                    a[r] = temp;

                    ++l;
                    --r;
                }
            }
            quicksort(a, left, r);
            quicksort(a, l, right);
        }
    }
}
