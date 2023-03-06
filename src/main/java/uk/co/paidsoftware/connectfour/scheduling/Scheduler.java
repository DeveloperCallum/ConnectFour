package uk.co.paidsoftware.connectfour.scheduling;

public interface Scheduler {
    public void submitTask(Runnable runnable);
}
