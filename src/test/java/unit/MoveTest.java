package unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.grids.Grid;
import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.players.GamePlayer;
import uk.co.paidsoftware.connectfour.players.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class MoveTest {

    @Test
    public void placeTest() throws InterruptedException {
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.setStarted(true);

        String test1 = Grid.toInteger(grid);
        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        String test2 = Grid.toInteger(grid);

        Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 0, 0, \n", test1);
        Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 1, 0, \n", test2);
    }

    @Test
    public void secondPlayerTest() throws InterruptedException {
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);

        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);
        gameMultiplayer.setStarted(true);

        String test1 = Grid.toInteger(grid);

        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer) {
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer) {
            gameMultiplayer.wait(1000);
        }

        String test2 = Grid.toInteger(grid);

        gameMultiplayer.makeMove(player2, 1, () -> {
            synchronized (gameMultiplayer) {
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer) {
            gameMultiplayer.wait(1000);
        }

        String test3 = Grid.toInteger(grid);

        Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 0, 0, \n", test1);
        Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 1, 0, \n", test2);
        Assertions.assertEquals("0, 0, 0, \n0, 2, 0, \n0, 1, 0, \n", test3);
    }

    @Test
    public void NotPlayersTurn() throws InterruptedException {
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);

        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);
        gameMultiplayer.setStarted(true);

        String test1 = Grid.toInteger(grid);
        AtomicBoolean moved = new AtomicBoolean(false);

        gameMultiplayer.makeMove(player2, 1, () -> {

        }, exception -> {
            System.out.println(exception.toString());

            synchronized (gameMultiplayer) {
                gameMultiplayer.notify();
                moved.set(true);
            }
        });

        synchronized (gameMultiplayer) {
            gameMultiplayer.wait(1000);

            Assertions.assertTrue(moved.get());
        }
    }

    @Test
    public void winTest() throws InterruptedException {
        Cell[][] grid = new Cell[6][7];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);
        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);
        gameMultiplayer.setStarted(true);

        String test1 = Grid.toInteger(grid);

        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player2, 2, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player2, 2, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player2, 2, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
        }

        gameMultiplayer.makeMove(player1, 1, () -> {
            synchronized (gameMultiplayer){
                gameMultiplayer.notify();
            }
        });

        synchronized (gameMultiplayer){
            gameMultiplayer.wait(1000);
            System.out.println(Grid.toInteger(grid));
        }
    }
}