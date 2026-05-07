package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private static void removeWallBetween(Cell current, Cell next){
        int r1 = current.getRow();
        int c1 = current.getCol();
        int r2 = next.getRow();
        int c2 = next.getCol();
        if (r1 == r2 + 1) { current.setTop(false); next.setBottom(false); }
        else if (r1 == r2 - 1) { current.setBottom(false); next.setTop(false); }
        else if (c1 == c2 + 1) { current.setLeft(false); next.setRight(false); }
        else if (c1 == c2 - 1) { current.setRight(false); next.setLeft(false); }
    }

    private static List<Cell> getUnvisitedNeighbors(Cell cell, boolean[][] visited, Cell[][] grid) {
        List<Cell> neighbors = new ArrayList<>();
        int r = cell.getRow();
        int c = cell.getCol();
        if (r > 0 && !visited[r - 1][c]) neighbors.add(grid[r - 1][c]);
        if (r < grid.length - 1 && !visited[r + 1][c]) neighbors.add(grid[r + 1][c]);
        if (c > 0 && !visited[r][c - 1]) neighbors.add(grid[r][c - 1]);
        if (c < grid[0].length - 1 && !visited[r][c + 1]) neighbors.add(grid[r][c + 1]);
        return neighbors;
    }

    public static void generate(Cell[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Stack<Cell> stack = new Stack<>();
        Random rand = new Random();
        Cell current = grid[0][0];
        visited[0][0] = true;
        stack.push(current);

        while(!stack.isEmpty()){
            current = stack.pop();
            List<Cell> neighbours = getUnvisitedNeighbors(current, visited, grid);
            if(!neighbours.isEmpty()){
                stack.push(current);
                Cell next = neighbours.get(rand.nextInt(neighbours.size()));
                removeWallBetween(current, next);
                visited[next.getRow()][next.getCol()] = true;
                stack.push(next);
            }
        }
    }
}
