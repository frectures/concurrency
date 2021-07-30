package bonus;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class FredPool {
    private final Thread[] threads;
    private final Deque<Runnable> tasks = new LinkedList<>();

    public FredPool(int numThreads) {
        threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; ++i) {
            threads[i] = new Thread(this::infiniteLoop);
            threads[i].start();
        }
    }

    private void infiniteLoop() {
        try {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        tasks.wait();
                    }
                    task = tasks.removeFirst();
                }
                task.run();
            }
        } catch (InterruptedException shutdownRequested) {
            // intentionally left blank
        }
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.addLast(task);
            tasks.notifyAll();
        }
    }

    public <V> Fuchur<V> submit(Callable<V> callable) {
        Fuchur<V> fuchur = new Fuchur<>(callable);
        submit(fuchur);
        return fuchur;
    }
}
