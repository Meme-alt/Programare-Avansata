package org.example;

import java.util.HashMap;
import java.util.Map;

public class Maze {
    private final int rows;
    private final int cols;
    private final int exitRow, exitCol;
    private final Cell[][] matrix;
    private int bunnyRow = 0;
    private int bunnyCol = 0;
    private boolean gameOver = false;
    private final Map<Robot, Cell> robotPositions = new HashMap<>();

    public Maze(Cell[][] generatedMatrix, int exitRow, int exitCol) {
        this.matrix = generatedMatrix;
        this.rows = generatedMatrix.length;
        this.cols = generatedMatrix[0].length;
        this.exitRow = exitRow;
        this.exitCol = exitCol;
    }

    public Cell getCell(int r, int c) {
        return matrix[r][c];
    }
    public synchronized java.util.List<Cell> getRobotLocations() {
        return new java.util.ArrayList<>(robotPositions.values());
    }
    public synchronized int getBunnyRow() { return bunnyRow; }
    public synchronized int getBunnyCol() { return bunnyCol; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public synchronized boolean isGameOver() {
        return gameOver;
    }

    public synchronized void setGameOver(boolean status) {
        this.gameOver = status;
    }

    public boolean isExit(int r, int c) {
        return r == exitRow && c == exitCol;
    }

    public synchronized void updateBunny(int r, int c) {
        this.bunnyRow = r;
        this.bunnyCol = c;
    }

    public boolean visit(int row, int col, Robot robot) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return false;
        synchronized (matrix[row][col]) {
            robotPositions.put(robot, matrix[row][col]);
            return true;
        }
    }
    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append("+");
                sb.append(matrix[i][j].hasTop() ? "---" : "   ");
            }
            sb.append("+\n");
            for (int j = 0; j < cols; j++) {
                sb.append(matrix[i][j].hasLeft() ? "|" : " ");

                boolean hasRobot = false;
                for (Cell c : robotPositions.values()) {
                    if (c != null && c.getRow() == i && c.getCol() == j) {
                        hasRobot = true;
                        break;
                    }
                }

                if (i == bunnyRow && j == bunnyCol) sb.append(" B ");
                else if (hasRobot) sb.append(" R ");
                else if (i == exitRow && j == exitCol) sb.append(" E ");
                else sb.append("   ");
            }
            sb.append(matrix[i][cols - 1].hasRight() ? "|\n" : " \n");
        }
        for (int j = 0; j < cols; j++) {
            sb.append("+");
            sb.append(matrix[rows - 1][j].hasBottom() ? "---" : "   ");
        }
        sb.append("+\n");
        return sb.toString();
    }
}
