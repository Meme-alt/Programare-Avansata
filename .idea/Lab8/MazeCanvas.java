package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeCanvas extends JPanel {
    public Cell[][] grid;
    public MazeCanvas(){
        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                if (grid == null) {
                    return;
                }
                int rows = grid.length;
                int cols = grid[0].length;
                int cellSize = Math.min((getWidth() - 20) / cols, (getHeight() - 20) / rows);
                int offsetX = (getWidth() - (cols * cellSize)) / 2;
                int offsetY = (getHeight() - (rows * cellSize)) / 2;
                int mouseX = e.getX();
                int mouseY = e.getY();
                int col = (mouseX - offsetX) / cellSize;
                int row = (mouseY - offsetY) / cellSize;
                if (row >= 0 && row < rows && col >= 0 && col < cols) {
                    int cellX = offsetX + col * cellSize;
                    int cellY = offsetY + row * cellSize;
                    int distTop = mouseY - cellY;
                    int distBottom = (cellY + cellSize) - mouseY;
                    int distLeft = mouseX - cellX;
                    int distRight = (cellX + cellSize) - mouseX;
                    int minDist = Math.min(Math.min(distTop, distBottom), Math.min(distLeft, distRight));
                    int pixels = 10;
                    if (minDist <= pixels) {
                        if (minDist == distTop) {
                            boolean newState = !grid[row][col].hasTop();
                            grid[row][col].setTop(newState);
                            if (row > 0) {
                                grid[row - 1][col].setBottom(newState);
                            }
                        } else if (minDist == distBottom) {
                            boolean newState = !grid[row][col].hasBottom();
                            grid[row][col].setBottom(newState);
                            if (row < rows - 1) {
                                grid[row + 1][col].setTop(newState);
                            }
                        } else if (minDist == distLeft) {
                            boolean newState = !grid[row][col].hasLeft();
                            grid[row][col].setLeft(newState);
                            if (col > 0) {
                                grid[row][col - 1].setRight(newState);
                            }
                        } else if (minDist == distRight) {
                            boolean newState = !grid[row][col].hasRight();
                            grid[row][col].setRight(newState);
                            if (col < cols - 1) {
                                grid[row][col + 1].setLeft(newState);
                            }
                        }
                        repaint();
                    }
                }
            }
        });
    }
    public void setMazeDimensions(int rows, int cols){
        grid = new Cell[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Cell(i, j);
            }
        }
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(grid == null){
            return;
        }
        int rows = grid.length;
        int cols = grid.length;
        Graphics2D g2 = (Graphics2D) g;
        int cellSize = Math.min((getWidth() - 20) / cols, (getHeight() - 20) / rows);
        int offsetX = (getWidth() - (cols * cellSize)) / 2;
        int offsetY = (getHeight() - (rows * cellSize)) / 2;
        g2.setColor(Color.BLACK);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                int x = offsetX + j * cellSize;
                int y = offsetY + i * cellSize;
                g2.setColor(Color.WHITE);
                g2.fillRect(x, y, cellSize, cellSize);
                g2.setColor(Color.BLACK);
                if (grid[i][j].hasTop())    g2.drawLine(x, y, x + cellSize, y);
                if (grid[i][j].hasRight())  g2.drawLine(x + cellSize, y, x + cellSize, y + cellSize);
                if (grid[i][j].hasBottom()) g2.drawLine(x + cellSize, y + cellSize, x, y + cellSize);
                if (grid[i][j].hasLeft())   g2.drawLine(x, y + cellSize, x, y);
            }
        }
    }
}
