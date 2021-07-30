package live;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class MobyDick {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Stream<String> lines = linesInResource("/mobydick.txt");
        lines.limit(23).forEach(System.out::println);
    }

    private static Stream<String> linesInResource(String name) throws URISyntaxException, IOException {
        URL url = MobyDick.class.getResource(name);
        URI uri = url.toURI();
        Path path = Path.of(uri);
        return Files.lines(path);
    }

    public static Map<String, Long> countWords() throws URISyntaxException, IOException {
        return new HashMap<>();
    }

    private static void regexTutorial() {
        Matcher matcher = WORDS.matcher("To be or not to be, that is the question...");
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }

    private static final Pattern WORDS = Pattern.compile("\\w+");
}
