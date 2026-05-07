package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Cell[][] grid = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        MazeGenerator.generate(grid);
        Maze maze = new Maze(grid, 9, 9);
        Game game = new Game(maze);
        GameGUI gui = new GameGUI();
        game.setGui(gui);
        gui.setGame(game);
    }
}
