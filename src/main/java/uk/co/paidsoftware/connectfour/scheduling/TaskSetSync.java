package uk.co.paidsoftware.connectfour.scheduling;

import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.players.Player;

import java.util.LinkedList;
import java.util.List;

public class TaskSetSync {
    private final Scheduler gameScheduler;

    public TaskSetSync() {
        this.gameScheduler = GameScheduler.getInstance();
    }

    public TaskSetSync(Scheduler gameScheduler) {
        this.gameScheduler = gameScheduler;
    }

    private final List<Runnable> processing = new LinkedList<>();

    public void addTask(Runnable callback) {
        processing.add(callback);

        if (processing.size() <= 1) {
            gameScheduler.submitTask(() -> {
                synchronized (processing) {
                    while (!processing.isEmpty()) {
                        processing.get(0).run();
                        processing.remove(0);
                    }
                }
            });
        }
    }
}
