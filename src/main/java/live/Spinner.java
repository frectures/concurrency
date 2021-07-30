package live;

import java.util.stream.IntStream;

public class Spinner {
    public static void main(String[] args) {
        Spinner spinner = new Spinner();
        new Thread(spinner::calculate).start();
        spinner.spin();
        spinner.print();
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
