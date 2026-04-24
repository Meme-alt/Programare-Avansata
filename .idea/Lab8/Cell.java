package org.example;

public class Cell {
    private int row, col;
    private boolean top = true, right = true, bottom = true, left = true;
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }
    public boolean hasTop() { return top; }
    public void setTop(boolean top) { this.top = top; }
    public boolean hasRight() { return right; }
    public void setRight(boolean right) { this.right = right; }
    public boolean hasBottom() { return bottom; }
    public void setBottom(boolean bottom) { this.bottom = bottom; }
    public boolean hasLeft() { return left; }
    public void setLeft(boolean left) { this.left = left; }
}
