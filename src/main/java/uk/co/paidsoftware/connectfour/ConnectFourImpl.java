package uk.co.paidsoftware.connectfour;

import uk.co.paidsoftware.connectfour.exceptions.Exceptions;
import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.grids.Grid;
import uk.co.paidsoftware.connectfour.players.Player;
import uk.co.paidsoftware.connectfour.structure.CellNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class ConnectFourImpl implements ConnectFour {

    /** The default value for the sequential tiles needed to win. */
    public static final int DEFAULT_SEQUENTIAL_TILES_NEEDED_TO_WIN = 4;

    /** The player queue. */
    private final List<Player> playQueue = new LinkedList<>();

    /** The stored game state. */
    private final Grid gridData;

    /** The needed sequential tiles needed to win. */
    private final int tilesNeeded;


    /**
     * Create an instance of a game.
     * @param player The first player
     * @param grid The game tiles matrix
     */
    @Deprecated
    protected ConnectFourImpl(final Player player, final Cell[][] grid) {
        this.gridData = new Grid(grid);
        this.tilesNeeded = DEFAULT_SEQUENTIAL_TILES_NEEDED_TO_WIN;
        addPlayer(player);
    }

    /**
     * Create an instance of a game.
     * @param player The first player
     * @param cells The game tiles matrix
     * @param sequentialTilesNeeded The tiles needed in a row to win.
     */
    @Deprecated
    protected ConnectFourImpl(final Player player, final Cell[][] cells, final int sequentialTilesNeeded) {
        this.gridData = new Grid(cells);
        addPlayer(player);
        this.tilesNeeded = sequentialTilesNeeded;
    }

    /**
     * Create an instance of a game.
     * @param player The first player.
     * @param grid The game tiles matrix.
     */
    protected ConnectFourImpl(final Player player, final Grid grid) {
        this.gridData = grid;
        addPlayer(player);
        this.tilesNeeded = DEFAULT_SEQUENTIAL_TILES_NEEDED_TO_WIN;
    }

    /**
     * Create an instance of a game.
     * @param player The first player.
     * @param grid The class for handling the game tiles matrix.
     * @param sequentialTilesNeeded The tiles needed in a row to win.
     */
    protected ConnectFourImpl(final Player player, final Grid grid, final int sequentialTilesNeeded) {
        this.gridData = grid;
        addPlayer(player);
        this.tilesNeeded = sequentialTilesNeeded;
    }

    public int getTilesNeeded() {
        return tilesNeeded;
    }

    @Override
    public synchronized void makeMove(final Player player, final int x) throws PlayerCantMoveException {
        Player current = playQueue.get(0);

        if (!current.equals(player)) {
            throw new PlayerCantMoveException("Not " + player.getName() + "'s turn. " + current.getName() + " should have went", Exceptions.WRONG_PLAYER_WENT);
        }

        if (gridData.getGrid()[0].length <= x || x < 0) {
            throw new PlayerCantMoveException("Position doesn't exist", Exceptions.INVALID_POSITION);
        }

        int lastY = gridData.getHighestY(x);
        Cell lastFree = gridData.getValueOrSet(lastY, x);
        lastFree.setPlayer(player);

        boolean win = checkWin(player, lastY, x);

        if (win) {
            System.out.println(player.getName() + " is the winner");
        }

        playQueue.remove(0);
        playQueue.add(current);
    }

    /**
     * Check to see if a player has won.
     * @param player Check if this player has won
     * @param y y position
     * @param x x position
     * @return true, if thw player has won
     */
    protected boolean checkWin(final Player player, final int y, final int x) {
        Cell[][] grid = gridData.getGrid();

        CellNode rootNode = new CellNode(player, x, y);

        int bottomX = 0;
        int rightX = 0;
        int leftX = 0;
        int topRightX = 0;
        int bottomRightX = 0;
        int topLeftX = 0;
        int bottomLeftX = 0;

        for (int i = 1; i <= tilesNeeded; i++) {
            int minY = y - i;
            int maxY = y + i;
            int minX = x - i;
            int maxX = x + i;

            boolean validMinY = minY >= 0;
            boolean validMaxY = maxY < grid.length;
            boolean validMinX = minX >= 0;
            boolean validMaxX = maxX < grid.length;

            //Don't Check TOP
            if (validMaxY) {
                Cell bottom = grid[maxY][x];

                if (bottom != null) {
                    if (player.getIdentity() == bottom.getPlayer().getIdentity()) {
                        bottomX++;
                    }
                }
            }

            if (validMaxX) {
                Cell right = grid[y][maxX];

                if (right != null) {
                    if (player.getIdentity() == right.getPlayer().getIdentity()) {
                        rightX++;
                    }
                }
            }

            if (validMinX) {
                Cell left = grid[y][minX];

                if (left != null) {
                    if (player.getIdentity() == left.getPlayer().getIdentity()) {
                        leftX++;
                    }
                }
            }

            if (validMinY && validMaxX) {
                Cell topRight = grid[minY][maxX];

                if (topRight != null) {
                    if (player.getIdentity() == topRight.getPlayer().getIdentity()) {
                        topRightX++;
                    }
                }
            }

            if (validMaxY && validMaxX) {
                Cell bottomRight = grid[maxY][maxX];

                if (bottomRight != null) {
                    if (player.getIdentity() == bottomRight.getPlayer().getIdentity()) {
                        bottomRightX++;
                    }
                }
            }

            if (validMinY && validMinX) {
                Cell topLeft = grid[minY][minX];

                if (topLeft != null) {
                    if (player.getIdentity() == topLeft.getPlayer().getIdentity()) {
                        topLeftX++;
                    }
                }
            }

            if (validMaxY && validMinX) {
                Cell bottomLeft = grid[maxY][minX];

                if (bottomLeft != null) {
                    if (player.getIdentity() == bottomLeft.getPlayer().getIdentity()) {
                        bottomLeftX++;
                    }
                }
            }
        }

        return ((bottomX >= tilesNeeded - 1) || (rightX >= tilesNeeded - 1) || (leftX >= tilesNeeded - 1) || (topRightX >= tilesNeeded - 1) || (bottomRightX >= tilesNeeded - 1) || (topLeftX >= tilesNeeded - 1) || (bottomLeftX >= tilesNeeded - 1));
    }

    /**
     * Add player to the game.
     * @param player The target.
     */
    public void addPlayer(final Player player) {
        playQueue.add(player);
    }

    /**
     * Remove player from the game.
     * @param player The target.
     */
    public void removePlayer(final Player player) {
        playQueue.remove(player);
    }

    public Set<Player> getPlayers() {
        return new HashSet<>(playQueue);
    }
}
