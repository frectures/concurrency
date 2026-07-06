package live;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class MobyDick {
    private final String mobyString;
    private final List<String> mobyLines;
    private final Pattern WORDS = Pattern.compile("\\p{IsAlphabetic}+");
    private final int expectedSize;

    public MobyDick() {
        try {
            Path path = Path.of(System.getProperty("user.home"), "Downloads", "2701-0.txt");
            mobyString = Files.readString(path);
            mobyLines = Files.readAllLines(path);

            expectedSize = (int) WORDS.matcher(mobyString).results()
                    .map(MatchResult::group)
                    .map(String::toLowerCase)
                    .distinct()
                    .count();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Set<String> checkSize(Set<String> vocabulary) {
        int size = vocabulary.size();
        if (size != expectedSize) throw new RuntimeException(size + " != " + expectedSize);
        return vocabulary;
    }

    @Benchmark
    public Set<String> serialString() {

        Set<String> vocabulary = WORDS.matcher(mobyString).results()
                .map(MatchResult::group)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return checkSize(vocabulary);
    }

    @Benchmark
    public Set<String> serialLines() {

        Set<String> vocabulary = mobyLines.stream()
                .flatMap(line -> WORDS.matcher(line).results())
                .map(MatchResult::group)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return checkSize(vocabulary);
    }

    @Benchmark
    public Set<String> parallelString() {

        Set<String> vocabulary = WORDS.matcher(mobyString).results()
                .parallel()
                .map(MatchResult::group)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return checkSize(vocabulary);
    }

    @Benchmark
    public Set<String> parallelLines() {

        Set<String> vocabulary = mobyLines.stream()
                .parallel()
                .flatMap(line -> WORDS.matcher(line).results())
                .map(MatchResult::group)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return checkSize(vocabulary);
    }

    @Benchmark
    public Set<String> concurrentHashMap() {

        // - ConcurrentHashSet does not exist
        // - ConcurrentHashMap exists
        // - Just use any dummy value :)
        var vocabulary = new ConcurrentHashMap<String, String>();

        // TODO parallel-forEach-put into vocabulary

        return checkSize(vocabulary.keySet());
    }

    @Benchmark
    public FredSet fredSet() {

        var vocabulary = new FredSet();

        // TODO parallel-forEach-add into vocabulary

        // TODO fix concurrency bugs in FredSet

        // TODO Remove all OTHER @Benchmark annotations
        // TODO Configure iterations=300 in line 19; Still no deviations?

        if (vocabulary.size() == 0) throw new RuntimeException("TODO");
        long delta = vocabulary.size() - expectedSize;
        if (delta < 0) {
            System.out.println(-delta + " missing words");
        } else if (delta > 0) {
            System.out.println(delta + " too many words");
        }
        return vocabulary;
    }

    static void main() throws IOException {
        org.openjdk.jmh.Main.main(new String[]{MobyDick.class.getName()});
    }
}
