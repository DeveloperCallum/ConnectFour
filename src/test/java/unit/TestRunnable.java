package unit;

public class TestRunnable implements Runnable {
    private boolean wasRan = false;
    private Thread threadUsed;

    @Override
    public void run() {
        wasRan = true;
        threadUsed = Thread.currentThread();

        synchronized (this){
            this.notifyAll();
        }
    }

    public boolean wasRan() {
        return wasRan;
    }

    public Thread getThreadUsed() {
        return threadUsed;
    }
}