package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.grids.Grid;
import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.players.GamePlayer;
import uk.co.paidsoftware.connectfour.players.Player;
import uk.co.paidsoftware.connectfour.scheduling.GameScheduler;
import uk.co.paidsoftware.connectfour.scheduling.Scheduler;

public class GameSchedulerTest {

    @Test
    public void testExecutor() throws InterruptedException {
        Scheduler gameScheduler = GameScheduler.getInstance();

        TestRunnable testRunnable1 = new TestRunnable();
        TestRunnable testRunnable2 = new TestRunnable();
        TestRunnable testRunnable3 = new TestRunnable();
        TestRunnable testRunnable4 = new TestRunnable();

        gameScheduler.submitTask(testRunnable1);
        gameScheduler.submitTask(testRunnable2);
        gameScheduler.submitTask(testRunnable3);
        gameScheduler.submitTask(testRunnable4);

        synchronized (testRunnable1){
            testRunnable1.wait(2000);
        }

        Assertions.assertTrue(testRunnable1.wasRan());

        synchronized (testRunnable2){
            testRunnable2.wait(2000);
        }

        Assertions.assertTrue(testRunnable2.wasRan());

        synchronized (testRunnable3){
            testRunnable3.wait(2000);
        }

        Assertions.assertTrue(testRunnable3.wasRan());

        synchronized (testRunnable4){
            testRunnable4.wait(2000);
        }

        Assertions.assertTrue(testRunnable4.wasRan());
    }

    @Test
    public void largeTaskSync() throws InterruptedException {
        Cell[][] grid = new Cell[6][7];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);
        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);
        gameMultiplayer.setStarted(true);

        Scheduler gameSchedulerTest = GameScheduler.getInstance();
        gameSchedulerTest.submitTask(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting");
                Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        gameSchedulerTest.submitTask(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        String test1 = Grid.toInteger(grid);

        gameMultiplayer.makeMove(player1, 1, () -> {
            System.out.println(player1.getName() + " just went in " + Thread.currentThread().getName());
        });

        gameMultiplayer.makeMove(player2, 2, () -> {
            System.out.println( player2.getName() + " just went in " + Thread.currentThread().getName());

            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait();
        }
    }

    @Test
    public void largeMidExecutionTaskSync() throws InterruptedException {
        Cell[][] grid = new Cell[6][7];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);
        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);
        gameMultiplayer.setStarted(true);

        Scheduler gameSchedulerTest = GameScheduler.getInstance();
        gameSchedulerTest.submitTask(() -> {
            try {
                int mills = 5000;
                System.out.println(Thread.currentThread().getName() + " waiting " + (mills / 1000) + "s.");
                Thread.sleep(mills);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        gameSchedulerTest.submitTask(() -> {
            try {
                int mills = 1000;
                System.out.println(Thread.currentThread().getName() + " waiting " + (mills / 1000) + "s.");
                Thread.sleep(mills);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        String test1 = Grid.toInteger(grid);

        gameMultiplayer.makeMove(player1, 1, () -> {
            System.out.println(player1.getName() + " just went in " + Thread.currentThread().getName());
        });

        gameSchedulerTest.submitTask(() -> {
            try {
                int mills = 5000;
                System.out.println(Thread.currentThread().getName() + " waiting " + (mills / 1000) + "s.");
                Thread.sleep(mills);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        gameSchedulerTest.submitTask(() -> {
            try {
                int mills = 1000;
                System.out.println(Thread.currentThread().getName() + " waiting " + (mills / 1000) + "s.");
                Thread.sleep(mills);
                System.out.println(Thread.currentThread().getName() + " stopped waiting");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        gameMultiplayer.makeMove(player2, 2, () -> {
            System.out.println( player2.getName() + " just went in " + Thread.currentThread().getName());

            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait();
        }
    }
}
