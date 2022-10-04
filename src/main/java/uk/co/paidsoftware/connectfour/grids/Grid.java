package uk.co.paidsoftware.connectfour.grids;

public class Grid {
    public static String stringify(Cell[][] grid){
        String data = toInteger(grid);
        System.out.println(data);

        return data;
    }

    public static String toInteger(Cell[][] grid){
        StringBuilder str = new StringBuilder();

        for (int y = 0; y < grid.length; y++) {
            Cell[] xArray = grid[y];

            for (int x = 0; x < xArray.length; x++) {
                Cell cell = grid[y][x];

                if (cell == null || cell.getPlayer() == null){
                    str.append(0).append(", ");
                    continue;
                }

                if (x == xArray.length - 1){
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
