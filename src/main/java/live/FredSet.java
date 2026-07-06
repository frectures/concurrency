package live;

import java.util.Arrays;

public class FredSet {
    private final String[][] table;
    private int size;

    public FredSet() {
        table = new String[32768][];
        Arrays.fill(table, new String[0]);
    }

    public void add(String s) {
        int row = s.hashCode() & 32767;

        String[] line = table[row];
        synchronized (line) {
            for (String string : line) {
                if (string.equals(s)) return;
            }

            line = Arrays.copyOf(line, line.length + 1);
            line[line.length - 1] = s;
            table[row] = line;
            size++;
        }
    }

    public int size() {
        return size;
    }
}
