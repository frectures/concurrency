package live;

public class Philosopher implements Runnable {
    private final Object leftFork;
    private final Object rightFork;

    public Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(this + " thinking");
            synchronized (leftFork) {
                System.out.println(this + " grabbing");
                synchronized (rightFork) {
                    System.out.println(this + " eating");
                }
            }
        }
    }

    public static void main(String[] args) {
        Object[] forks = {
                new Object(),
                new Object(),
                new Object(),
                new Object(),
                new Object(),
        };

        Philosopher[] philosophers = {
                new Philosopher(forks[0], forks[1]),
                new Philosopher(forks[1], forks[2]),
                new Philosopher(forks[2], forks[3]),
                new Philosopher(forks[3], forks[4]),
                new Philosopher(forks[4], forks[0]),
        };

        for (Philosopher philosopher : philosophers) {
            new Thread(philosopher).start();
        }
    }
}
