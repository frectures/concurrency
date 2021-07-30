package bonus;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class Fuchur<V> implements Runnable {
    private final Callable<V> callable;
    private V result;
    private Exception exception;

    public Fuchur(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            this.result = callable.call();
        } catch (Exception exception) {
            this.exception = exception;
        }
        synchronized (this) {
            notifyAll();
        }
    }

    public V get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            while (result == null && exception == null) {
                wait();
            }
        }
        if (exception != null) {
            throw new ExecutionException(exception);
        } else {
            return result;
        }
    }
}
