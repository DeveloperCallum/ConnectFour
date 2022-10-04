package uk.co.paidsoftware.connectfour.grids;

import uk.co.paidsoftware.connectfour.players.Player;

public class Cell {
    private Player player = null;
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the top cell at the position.
     *
     * @param x    Position.
     * @param y    Position.
     * @param grid The entire grid.
     * @return The cell if it exists.
     */
    public static Cell getTop(int x, int y, Cell[][] grid) {
        int newY = y - 1;

        if (y < 0) {
            return null;
        }

        return getCell(x, newY, grid);
    }

    /**
     * Gets the top cell at the position.
     *
     * @param x    Position.
     * @param y    Position.
     * @param grid The entire grid.
     * @return The cell if it exists.
     */
    public static Cell getBottom(int x, int y, Cell[][] grid) {
        int newY = y + 1;

        if (y < grid.length) {
            return getCell(x, newY, grid);
        }

        return null;
    }

    /**
     * Gets the top cell at the position.
     *
     * @param x    Position.
     * @param y    Position.
     * @param grid The entire grid.
     * @return The cell if it exists.
     */
    public static Cell getRight(int x, int y, Cell[][] grid) {
        int newX = x - 1;

        if (x < grid.length) {
            return getCell(newX, y, grid);
        }

        return null;
    }

    /**
     * Gets the top cell at the position.
     *
     * @param x    Position.
     * @param y    Position.
     * @param grid The entire grid.
     * @return The cell if it exists.
     */
    public static Cell getLeft(int x, int y, Cell[][] grid) {
        int newX = x + 1;

        if (x < grid.length) {
            return getCell(newX, y, grid);
        }

        return null;
    }

    private static Cell getCell(int x, int y, Cell[][] grid) {

        if (x < 0){
            return null;
        }

        if (y < 0){
            return null;
        }

        Cell cell = grid[y][x];

        if (cell == null) {
            grid[y][x] = new Cell();
        }

        return grid[y][x];
    }
}
