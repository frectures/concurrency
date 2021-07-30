package live;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MobyDickTest {
    @Test
    public void top12() throws URISyntaxException, IOException {
        Map<String, Long> frequencies = MobyDick.countWords();
        assertEquals(1805L, frequencies.get("but"));
        assertEquals(1876L, frequencies.get("he"));
        assertEquals(2114L, frequencies.get("i"));
        assertEquals(2495L, frequencies.get("his"));
        assertEquals(2497L, frequencies.get("it"));
        assertEquals(3045L, frequencies.get("that"));
        assertEquals(4077L, frequencies.get("in"));
        assertEquals(4539L, frequencies.get("to"));
        assertEquals(4636L, frequencies.get("a"));
        assertEquals(6325L, frequencies.get("and"));
        assertEquals(6469L, frequencies.get("of"));
        assertEquals(14175L, frequencies.get("the"));
    }
}
