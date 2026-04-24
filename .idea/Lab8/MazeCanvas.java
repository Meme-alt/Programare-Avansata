package org.example;

import javax.swing.*;
import java.awt.*;

public class MazeCanvas extends JPanel {
    public Cell[][] grid;
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
