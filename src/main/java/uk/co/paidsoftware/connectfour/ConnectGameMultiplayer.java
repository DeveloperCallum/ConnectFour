package uk.co.paidsoftware.connectfour;

import uk.co.paidsoftware.connectfour.exceptions.Exceptions;
import uk.co.paidsoftware.connectfour.exceptions.PlayerCantMoveException;
import uk.co.paidsoftware.connectfour.grids.Cell;
import uk.co.paidsoftware.connectfour.grids.Point;
import uk.co.paidsoftware.connectfour.players.Player;
import uk.co.paidsoftware.connectfour.structure.CellNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class ConnectGameMultiplayer implements ConnectFour {
    private Cell[][] grid;
    private final List<Player> playQueue = new LinkedList<>();

    private final int neededToWin;

    public ConnectGameMultiplayer(Player player, Cell[][] grid) {
        this.grid = grid;
        addPlayer(player);
        this.neededToWin = 4;
    }

    public int getNeededToWin() {
        return neededToWin;
    }

    @Override
    public synchronized void makeMove(Player player, int x) throws PlayerCantMoveException {
        Player current = playQueue.get(0);

        if (!current.equals(player)) {
            throw new PlayerCantMoveException("Not " + player.getName() + "'s turn", Exceptions.WRONG_PLAYER_WENT);
        }

        if (grid[0].length <= x || x < 0) {
            throw new PlayerCantMoveException("Position doesn't exist", Exceptions.INVALID_POSITION);
        }

        int lastY = (grid.length - 1);

        for (int i = 0; i < grid.length; i++) {
            Cell currentCell = grid[i][x];

            if (currentCell == null || currentCell.getPlayer() == null) {
                lastY = i;
                continue;
            }

            break;
        }

        //Get the highest cell.
        Cell lastFree = grid[lastY][x];

        if (lastFree == null) {
            Cell created = new Cell();
            grid[lastY][x] = created;
            lastFree = created;
        }

        lastFree.setPlayer(player);
        System.out.println("Placed Marker: " + player.getIdentity() + " | (" + x + ", " + lastY + ")");
        checkWin(player, lastY, x);

        playQueue.remove(0);
        playQueue.add(current);
    }

    public void checkWin(Player player, int y, int x) {
        CellNode rootNode = new CellNode(player, x, y);

        int bottomX = 0;
        int rightX = 0;
        int leftX = 0;
        int topRightX = 0;
        int bottomRightX = 0;
        int topLeftX = 0;
        int bottomLeftX = 0;

        for (int i = 1; i <= neededToWin; i++) {
            int minY = Math.max(y - i, 0);
            int maxY = Math.min(y + i, grid.length - 1);
            int minX = Math.max(x - i, 0);
            int maxX = Math.min(x + i, grid.length - 1);

            System.out.print("-X: " + minX + ", ");
            System.out.print(" X: " + maxX + ", ");
            System.out.print(" -Y: " + minY + ", ");
            System.out.print(" Y: " + maxY + ", ");
            System.out.print("| PX: " + x + ", PY: " + y);
            System.out.println();

            //Don't Check TOP
            Cell bottom = grid[maxY][x];

            if (bottom != null) {
                CellNode farNode = rootNode.getFarBottomRecursive();
                Point point = new Point(x, maxY);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setBottom(new CellNode(player, point));

                    if (player.getIdentity() == bottom.getPlayer().getIdentity()) {
                        bottomX++;
                    }
                }
            }

            Cell right = grid[y][maxX];

            if (right != null) {
                CellNode farNode = rootNode.getFarRightRecursive();
                Point point = new Point(maxX, y);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setRight(new CellNode(player, point));

                    if (player.getIdentity() == right.getPlayer().getIdentity()) {
                        rightX++;
                    }
                }
            }

            Cell left = grid[y][minX];

            if (left != null) {
                CellNode farNode = rootNode.getFarLeftRecursive();
                Point point = new Point(minX, y);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setLeft(new CellNode(player, point));

                    if (player.getIdentity() == left.getPlayer().getIdentity()) {
                        leftX++;
                    }
                }
            }

            Cell topRight = grid[minY][maxX];

            if (topRight != null) {
                CellNode farNode = rootNode.getFarTopRightRecursive();
                Point point = new Point(maxX, minY);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setTopRight(new CellNode(player, point));

                    if (player.getIdentity() == topRight.getPlayer().getIdentity()) {
                        topRightX++;
                    }
                }
            }

            Cell bottomRight = grid[maxY][maxX];

            if (bottomRight != null) {
                CellNode farNode = rootNode.getFarBottomRightRecursive();
                Point point = new Point(maxX, maxY);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setBottomRight(new CellNode(player, point));

                    if (player.getIdentity() == bottomRight.getPlayer().getIdentity()) {
                        bottomRightX++;
                    }
                }
            }

            Cell topLeft = grid[minY][minX];

            if (topLeft != null) {
                CellNode farNode = rootNode.getFarTopLeftRecursive();
                Point point = new Point(minX, minY);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setTopLeft(new CellNode(player, point));

                    if (player.getIdentity() == topLeft.getPlayer().getIdentity()) {
                        topLeftX++;
                    }
                }
            }

            Cell bottomLeft = grid[maxY][minX];

            if (bottomLeft != null) {
                CellNode farNode = rootNode.getFarBottomLeftRecursive();
                Point point = new Point(minX, maxY);

                if (!farNode.getPoint().equals(point)) {
                    farNode.setBottomLeft(new CellNode(player, point));

                    if (player.getIdentity() == bottomLeft.getPlayer().getIdentity()) {
                        bottomLeftX++;
                    }
                }
            }
        }

        System.out.println();

        boolean winner = ((bottomX >= neededToWin - 1) ||
                (rightX >= neededToWin - 1) ||
                (leftX >= neededToWin - 1) ||
                (topRightX >= neededToWin - 1) ||
                (bottomRightX >= neededToWin - 1) ||
                (topLeftX >= neededToWin - 1) ||
                (bottomLeftX >= neededToWin - 1));

        if (winner){
            System.out.println("Winner: " + player.getIdentity());
        }

        //Event Listener
    }

    @Override
    public void reset() {

    }

    @Override
    public void reset(Player winner) {

    }

    public void addPlayer(Player player) {
        playQueue.add(player);
    }

    private Set<Player> getPlayers(Player player) {
        return new HashSet<>(playQueue);
    }
}
