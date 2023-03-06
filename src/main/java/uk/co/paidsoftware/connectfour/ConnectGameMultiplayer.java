package uk.co.paidsoftware.connectfour;

import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.handler.EventHandler;
import uk.co.paidsoftware.connectfour.handler.GameEventHandler;
import uk.co.paidsoftware.connectfour.multiplayer.ExceptionHandler;
import uk.co.paidsoftware.connectfour.multiplayer.events.GameStartedEvent;
import uk.co.paidsoftware.connectfour.players.Player;
import uk.co.paidsoftware.connectfour.scheduling.GameScheduler;
import uk.co.paidsoftware.connectfour.scheduling.Scheduler;
import uk.co.paidsoftware.connectfour.scheduling.TaskSetSync;

public final class ConnectGameMultiplayer extends ConnectFourImpl {
    /** Instance of the task scheduler. */
    private final TaskSetSync taskSet;

    /** Game state hsa it started yet. */
    private boolean started = false;

    /**
     * Create a multiplayer game.
     *
     * @param player The player starting the game.
     * @param grid   The grid.
     */
    public ConnectGameMultiplayer(final Player player, final Cell[][] grid) {
        super(player, grid);
        this.taskSet = new TaskSetSync();
    }

    /**
     * Create a multiplayer game.
     *
     * @param player      The player starting the game.
     * @param grid        The grid.
     * @param neededToWin The tiles needed in a row to win.
     */
    public ConnectGameMultiplayer(final Player player, final Cell[][] grid, final int neededToWin) {
        super(player, grid, neededToWin);
        this.taskSet = new TaskSetSync();
    }

    /**
     * Let the player make a move.
     *
     * @param player The player who makes the move.
     * @param row    The row where the drop was made.
     */
    @Override
    public synchronized void makeMove(final Player player, final int row) {
        if (!started) {
            throw new RuntimeException("Game has not started");
        }

        taskSet.addTask(() -> {
            try {
                super.makeMove(player, row);
            } catch (PlayerCantMoveException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Let the player make a move.
     *
     * @param player   The player who makes the move.
     * @param row      The row where the drop was made.
     * @param callback callback after the player has moved.
     */
    public synchronized void makeMove(final Player player, final int row, final Runnable callback) {
        if (!started) {
            throw new RuntimeException("Game has not started");
        }

        taskSet.addTask(() -> {
            try {
                super.makeMove(player, row);
                callback.run();
            } catch (PlayerCantMoveException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Let the player make a move.
     *
     * @param player           The player who makes the move.
     * @param row              The row where the drop was made.
     * @param callback         callback after the player has moved.
     * @param exceptionHandler callback after an exception was thrown.
     */
    public synchronized void makeMove(final Player player, final int row, final Runnable callback, final ExceptionHandler<PlayerCantMoveException> exceptionHandler) {
        if (!started) {
            throw new RuntimeException("Game has not started");
        }

        taskSet.addTask(() -> {
            try {
                super.makeMove(player, row);
                callback.run();
            } catch (PlayerCantMoveException e) {
                exceptionHandler.handle(e);
            }
        });
    }

    @Override
    public void addPlayer(final Player player) {
        super.addPlayer(player);

        if (getPlayers().size() >= 2) {
            setStarted(true);
        }
    }

    /**
     * Check if the game has started.
     * @return true if the game has started.
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Set if the game has started.
     * @param isStarted
     */
    public void setStarted(final boolean isStarted) {
        if (!this.started && isStarted) {
            getEventHandler().emitEvent(new GameStartedEvent(this));
        }

        this.started = isStarted;
    }

    public Scheduler getGameScheduler() {
        return GameScheduler.getInstance();
    }

    public EventHandler getEventHandler() {
        return GameEventHandler.getInstance();
    }
}
