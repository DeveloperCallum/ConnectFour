package uk.co.paidsoftware.connectfour.grids;

public class Grid {
    private Cell[][] grid;

    public Grid(Cell[][] grid) {
        this.grid = grid;
    }

    public Grid(int row, int column) {
        grid = new Cell[row][column];
    }

    public Cell getHighestCell(int x) {
        int lastY = getHighestY(x);
        return getValueOrSet(lastY, x);
    }

    public int getHighestY(int x) {
        int lastY = (grid.length - 1);

        for (int i = 0; i < grid.length; i++) {
            Cell currentCell = grid[i][x];

            if (currentCell == null || currentCell.getPlayer() == null) {
                lastY = i;
                continue;
            }

            break;
        }

        return lastY;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setValue(int y, int x, Cell value) {
        grid[y][x] = value;
    }

    public Cell getValueOrSet(int y, int x) {
        Cell cell = grid[y][x];

        if (cell == null) {
            Cell created = new Cell();
            grid[y][x] = created;
            cell = created;
        }

        return cell;
    }

    public Cell getValue(int y, int x) {
        return grid[y][x];
    }

    public static String stringify(Cell[][] grid) {
        String data = toInteger(grid);

        return data;
    }

    public static String toInteger(Cell[][] grid) {
        StringBuilder str = new StringBuilder();

        for (int y = 0; y < grid.length; y++) {
            Cell[] xArray = grid[y];

            for (int x = 0; x < xArray.length; x++) {
                Cell cell = grid[y][x];

                if (cell == null || cell.getPlayer() == null) {
                    str.append(0).append(", ");
                    continue;
                }

                if (x == xArray.length - 1) {
                    str.append(cell.getPlayer().getIdentity());
                    continue;
                }

                str.append(cell.getPlayer().getIdentity()).append(", ");
            }

            str.append("\n");
        }

        return str.toString();
    }
}
