package org.example;

import javax.swing.*;
import java.awt.*;

import java.util.Random;

public class MazeFrame extends JFrame {
    private MazeCanvas mazeCanvas;
    private void setupUI(){
        setLayout(new BorderLayout());
        add(createControlPanel(), BorderLayout.NORTH);
        createCanvas();
        add(createControlPanelBottom(), BorderLayout.SOUTH);
    }
    public MazeFrame(){
        setTitle("Maze Creator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupUI();
    }
    private void createCanvas(){
        mazeCanvas = new MazeCanvas();
        mazeCanvas.setBackground(Color.LIGHT_GRAY);
        mazeCanvas.setPreferredSize(new Dimension(600, 400));
        add(mazeCanvas, BorderLayout.CENTER);
    }
    private void drawMaze(int rows, int cols){
        mazeCanvas.setMazeDimensions(rows, cols);
    }
    private JPanel createControlPanel(){
        JPanel panel = new JPanel();
        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        JSpinner colSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        JButton drawButton = new JButton("Draw Maze");
        drawButton.addActionListener(e -> drawMaze((int)rowSpinner.getValue(), (int)colSpinner.getValue()));
        panel.add(new JLabel("Rows:")); panel.add(rowSpinner);
        panel.add(new JLabel("Cols:")); panel.add(colSpinner);
        panel.add(drawButton);
        return panel;
    }
    private void createMaze(){
        if(mazeCanvas.grid == null){
            return;
        }
        Random random = new Random();
        for(int i = 0; i < mazeCanvas.grid.length; i++){
            for(int j = 0; j < mazeCanvas.grid[0].length; j++){
                int wall = random.nextInt(4);
                switch(wall){
                    case 0 -> mazeCanvas.grid[i][j].setTop(false);
                    case 1 -> mazeCanvas.grid[i][j].setRight(false);
                    case 2 -> mazeCanvas.grid[i][j].setBottom(false);
                    case 3 -> mazeCanvas.grid[i][j].setLeft(false);
                }
            }
        }
        mazeCanvas.repaint();
    }
    private void resetMaze(){
        int rows = mazeCanvas.grid.length;
        int cols = mazeCanvas.grid[0].length;
        mazeCanvas.setMazeDimensions(rows, cols);
    }
    private JPanel createControlPanelBottom(){
        JPanel panel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton resetButton = new JButton("Reset");
        JButton exitButton = new JButton("Exit");
        createButton.addActionListener(e -> createMaze());
        resetButton.addActionListener(e -> resetMaze());
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(createButton);
        panel.add(resetButton);
        panel.add(exitButton);
        return panel;
    }
}
