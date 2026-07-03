package live;

import java.util.stream.IntStream;

public class UniqueIdGenerator {

    private static long id = 0L;

    public static long next() {
        return id++;
    }

    static void main() {
        IntStream.range(0, 10).forEach(i -> {
            IntStream.range(0, 1_000_000_000).forEach(j -> {
                next();
            });
        });
        System.out.println(id);
    }
}
