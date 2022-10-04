import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.paidsoftware.connectfour.ConnectGameMultiplayer;
import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.grids.Grid;
import uk.co.paidsoftware.connectfour.players.GamePlayer;
import uk.co.paidsoftware.connectfour.players.Player;

public class MoveTest {

    @Test
    public void placeTest(){
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);

        try {
            String test1 = Grid.toInteger(grid);
            gameMultiplayer.makeMove(player1, 1);
            String test2 = Grid.toInteger(grid);

            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 0, 0, \n", test1);
            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 1, 0, \n", test2);
        } catch (PlayerCantMoveException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void secondPlayerTest(){
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);

        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);

        try {
            String test1 = Grid.toInteger(grid);
            gameMultiplayer.makeMove(player1, 1);
            String test2 = Grid.toInteger(grid);
            gameMultiplayer.makeMove(player2, 1);
            String test3 = Grid.toInteger(grid);

            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 0, 0, \n", test1);
            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 1, 0, \n", test2);
            Assertions.assertEquals("0, 0, 0, \n0, 2, 0, \n0, 1, 0, \n", test3);
        } catch (PlayerCantMoveException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void NotPlayersTurn(){
        Cell[][] grid = new Cell[3][3];
        Player player1 = new GamePlayer("Bob", 1);
        Player player2 = new GamePlayer("Finn", 2);

        ConnectGameMultiplayer gameMultiplayer = new ConnectGameMultiplayer(player1, grid);
        gameMultiplayer.addPlayer(player2);

        try {
            String test1 = Grid.toInteger(grid);
            gameMultiplayer.makeMove(player1, 1);
            String test2 = Grid.toInteger(grid);

            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 0, 0, \n", test1);
            Assertions.assertEquals("0, 0, 0, \n0, 0, 0, \n0, 1, 0, \n", test2);

            Assertions.assertThrows(PlayerCantMoveException.class, () -> {
                gameMultiplayer.makeMove(player1, 1);
            });
        } catch (PlayerCantMoveException e) {
            throw new RuntimeException(e);
        }
    }
}
