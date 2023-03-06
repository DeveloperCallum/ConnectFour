package uk.co.paidsoftware.connectfour;

import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.players.Player;

public interface ConnectFour {
  
  /**
   * Allow the player to take their move.
   *
   * @param player The player who makes the move.
   * @param column The column where the drop was made.
   */
  void makeMove(Player player, int column) throws PlayerCantMoveException;
  
  /**
   * Add a player to the game.
   *
   * @param player The target.
   */
  void addPlayer(Player player);
  
  /**
   * Remove a player from the game.
   *
   * @param player The target.
   */
  void removePlayer(Player player);
}
