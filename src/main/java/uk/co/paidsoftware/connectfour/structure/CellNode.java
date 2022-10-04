package uk.co.paidsoftware.connectfour.structure;

import uk.co.paidsoftware.connectfour.grids.Point;
import uk.co.paidsoftware.connectfour.players.Player;

public class CellNode {
    private CellNode top = null;
    private CellNode bottom = null;
    private CellNode left = null;
    private CellNode right = null;
    private CellNode topRight = null;
    private CellNode bottomRight = null;
    private CellNode topLeft = null;
    private CellNode bottomLeft = null;
    private final Point nodeLocation;
    private final Player player;

    public CellNode(Player player, Point nodeLocation) {
        this.player = player;
        this.nodeLocation = nodeLocation;
    }

    public CellNode(Player player, int x, int y) {
        this.player = player;
        this.nodeLocation = new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellNode cellNode = (CellNode) o;

        return nodeLocation.equals(cellNode.nodeLocation);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public static CellNode getFarRightRecursive(CellNode node){
        if (node.getRight() == null){
            return node;
        }

        return getFarRightRecursive(node.getRight());
    }


    public static CellNode getFarLeftRecursive(CellNode node){
        if (node.getLeft() == null){
            return node;
        }

        return getFarLeftRecursive(node.getLeft());
    }

    public static CellNode getFarTopRecursive(CellNode node){
        if (node.getTop() == null){
            return node;
        }

        return getFarTopRecursive(node.getTop());
    }

    public static CellNode getFarBottomRecursive(CellNode node){
        if (node.getBottom() == null){
            return node;
        }

        return getFarBottomRecursive(node.getBottom());
    }

    public static CellNode getFarTopRightRecursive(CellNode node){
        if (node.getTopRight() == null){
            return node;
        }

        return getFarTopRightRecursive(node.getTopRight());
    }

    public static CellNode getFarBottomRightRecursive(CellNode node){
        if (node.getBottomRight() == null){
            return node;
        }

        return getFarBottomRightRecursive(node.getBottomRight());
    }

    public static CellNode getFarTopLeftRecursive(CellNode node){
        if (node.getTopLeft() == null){
            return node;
        }

        return getFarTopLeftRecursive(node.getTopLeft());
    }

    public static CellNode getFarBottomLeftRecursive(CellNode node){
        if (node.getBottomLeft() == null){
            return node;
        }

        return getFarBottomLeftRecursive(node.getBottomLeft());
    }

    public CellNode getFarRightRecursive(){
        return getFarRightRecursive(this);
    }


    public CellNode getFarLeftRecursive(){
        return getFarLeftRecursive(this);
    }

    public CellNode getFarTopRecursive(){
        return getFarTopRecursive(this);
    }

    public CellNode getFarBottomRecursive(){
        return getFarBottomRecursive(this);
    }

    public CellNode getFarTopRightRecursive(){
        return getFarTopRightRecursive(this);
    }

    public CellNode getFarBottomRightRecursive(){
        return getFarBottomRightRecursive(this);
    }

    public CellNode getFarTopLeftRecursive(){
        return getFarTopLeftRecursive(this);
    }

    public CellNode getFarBottomLeftRecursive(){
        return getFarBottomLeftRecursive(this);
    }

    public Point getPoint() {
        return nodeLocation;
    }

    public CellNode getTop() {
        return top;
    }

    public void setTop(CellNode top) {
        this.top = top;
        top.bottom = this;
    }

    public CellNode getBottom() {
        return bottom;
    }

    public void setBottom(CellNode bottom) {
        this.bottom = bottom;
        bottom.top = this;
    }

    public CellNode getLeft() {
        return left;
    }

    public void setLeft(CellNode left) {
        this.left = left;
        left.right = this;
    }

    public CellNode getRight() {
        return right;
    }

    public void setRight(CellNode right) {
        this.right = right;
        right.left = this;
    }

    public CellNode getTopRight() {
        return topRight;
    }

    public void setTopRight(CellNode topRight) {
        this.topRight = topRight;
        topRight.bottomRight = this;
    }

    public CellNode getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(CellNode bottomRight) {
        this.bottomRight = bottomRight;
        bottomRight.topRight = this;
    }

    public CellNode getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(CellNode topLeft) {
        this.topLeft = topLeft;
        topLeft.bottomLeft = this;
    }

    public CellNode getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(CellNode bottomLeft) {
        this.bottomLeft = bottomLeft;
        bottomLeft.topLeft = this;
    }
}
