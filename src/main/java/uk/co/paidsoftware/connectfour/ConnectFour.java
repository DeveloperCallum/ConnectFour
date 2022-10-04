package uk.co.paidsoftware.connectfour;

import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.players.Player;

/**
 * Interface for the connect four game.
 */
public interface ConnectFour {

    /**
     * Allow the player to take their move.
     *
     * @param player The player who makes the move.
     * @param column The column where the drop was made.
     */
    void makeMove(Player player, int column) throws PlayerCantMoveException;

    void reset();

    void reset(Player winner);
}
