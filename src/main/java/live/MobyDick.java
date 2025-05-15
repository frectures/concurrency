package live;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class MobyDick {
    public static final Pattern WORDS = Pattern.compile("\\p{IsAlphabetic}+");

    public static void main(String[] args) throws IOException {
        Path path = Path.of(System.getProperty("user.home"), "Downloads", "2701-0.txt");
        String content = Files.readString(path);
        List<String> words = WORDS.matcher(content)
                .results()
                .map(MatchResult::group)
                .map(String::toLowerCase)
                .toList();
        // TODO count words
        System.out.println(words.get(10));
    }
}
