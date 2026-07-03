package live;

import java.util.stream.IntStream;

public class Spinner {

    void main() {
        new Thread(this::calculate).start();
        this.spin();
        this.print();
    }

    private int[] squares;
    private boolean done;

    private void calculate() {
        squares = IntStream.range(0, 46341).map(x -> x * x).toArray();
        System.out.println("Squares calculated!");
        done = true;
    }

    private void spin() {
        int spinning = 0;
        while (!done) {
            ++spinning;
        }
        System.out.println("Spinlock span " + spinning + " times!");
    }

    private void print() {
        IntStream.of(squares).limit(11).forEach(System.out::println);
    }
}
