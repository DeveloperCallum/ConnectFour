package uk.co.paidsoftware.connectfour.scheduling;

import uk.co.paidsoftware.connectfour.handler.EventHandler;
import uk.co.paidsoftware.connectfour.handler.GameEventHandler;

import java.util.LinkedList;
import java.util.List;

public class GameScheduler implements Scheduler {
    private static Scheduler gameScheduler;
    private int maxThreads = 2;
    private int currentThreads = 0;
    List<ConnectThread> pool = new LinkedList<>();

    private GameScheduler(int maxThreads) {
        this.maxThreads = maxThreads;

        for (int i = 0; i < maxThreads; i++) {
            ConnectThread connectThread = new ConnectThread(new ConnectRunner(), "ConnectThread-" + i);
            connectThread.start();

            pool.add(connectThread);
        }
    }

    private GameScheduler(int maxThreads, String suffix) {
        this.maxThreads = maxThreads;

        for (int i = 0; i < maxThreads; i++) {
            ConnectThread connectThread = new ConnectThread(new ConnectRunner(), suffix + i);
            connectThread.start();

            pool.add(connectThread);
        }
    }

    @Override
    public void submitTask(Runnable runnable) {
        ConnectRunner connectRunner = pool.get(currentThreads++ % maxThreads).getConnectRunner();

        synchronized (connectRunner) {
            connectRunner.submitTask(runnable);
            connectRunner.notify();
        }
    }

    public static Scheduler getInstance(){
        if (gameScheduler == null){
            gameScheduler = new GameScheduler(2);
        }

        return gameScheduler;
    }

    public static void setGameScheduler(Scheduler gameScheduler) {
        if (GameScheduler.gameScheduler == null){
            GameScheduler.gameScheduler = gameScheduler;
        }
    }

    private static class ThreadCache{
        ConnectRunner runner;
        int pool;
    }
}