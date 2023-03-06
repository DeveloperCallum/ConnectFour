package uk.co.paidsoftware.connectfour.scheduling;

import java.util.LinkedList;
import java.util.List;

public class ConnectRunner implements Runnable {
    private List<Runnable> queue = new LinkedList<>();
    private boolean isActive = true;

    @Override
    public void run() {
        while (isActive){
            while (!queue.isEmpty()) {
                queue.get(0).run();
                queue.remove(0);
            }

            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void submitTask(Runnable runnable) {
        queue.add(runnable);
    }

    public void shutdown(){
        isActive = false;
    }
}
