package org.example;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MazeFrame extends JFrame {
    private MazeCanvas mazeCanvas;
    private JSlider speedSlider = new JSlider(1, 100, 20);

    private void setupUI() {
        setLayout(new BorderLayout());
        add(createControlPanel(), BorderLayout.NORTH);
        createCanvas();
        add(createControlPanelBottom(), BorderLayout.SOUTH);
    }

    public MazeFrame() {
        setTitle("Maze Creator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setupUI();
    }

    private void createCanvas() {
        mazeCanvas = new MazeCanvas();
        mazeCanvas.setBackground(Color.LIGHT_GRAY);
        mazeCanvas.setPreferredSize(new Dimension(600, 400));
        add(mazeCanvas, BorderLayout.CENTER);
    }

    private void drawMaze(int rows, int cols) {
        mazeCanvas.setMazeDimensions(rows, cols);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JSpinner rowSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        JSpinner colSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        JButton drawButton = new JButton("Draw Maze");
        drawButton.addActionListener(e -> drawMaze((int) rowSpinner.getValue(), (int) colSpinner.getValue()));
        panel.add(new JLabel("Rows:"));
        panel.add(rowSpinner);
        panel.add(new JLabel("Cols:"));
        panel.add(colSpinner);
        panel.add(drawButton);
        return panel;
    }

    private void createMaze() {
        resetMaze();
        if (mazeCanvas.grid != null) {
            int delay = speedSlider.getValue();
            new Thread(() -> {
                MazeGenerator.generate(mazeCanvas, delay);
            }).start();
        }
    }

    private void resetMaze() {
        int rows = mazeCanvas.grid.length;
        int cols = mazeCanvas.grid[0].length;
        mazeCanvas.setMazeDimensions(rows, cols);
    }

    private JPanel createControlPanelBottom() {
        JPanel panel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton resetButton = new JButton("Reset");
        JButton validateButton = new JButton("Validate");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton exportButton = new JButton("Export");
        JButton exitButton = new JButton("Exit");
        createButton.addActionListener(e -> createMaze());
        resetButton.addActionListener(e -> resetMaze());
        validateButton.addActionListener(e -> validateMaze());
        saveButton.addActionListener(e -> saveMazeState());
        loadButton.addActionListener(e -> loadMazeState());
        exportButton.addActionListener(e -> ExportMaze());
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(new JLabel("Delay (ms):"));
        panel.add(speedSlider);
        panel.add(createButton);
        panel.add(resetButton);
        panel.add(validateButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(exportButton);
        panel.add(exitButton);
        return panel;
    }

    private void validateMaze() {
        if (mazeCanvas.grid == null) {
            return;
        }
        int rows = mazeCanvas.grid.length;
        int cols = mazeCanvas.grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        boolean isSolvable = dfs(0, 0, rows - 1, cols - 1, visited);
        if (isSolvable) {
            JOptionPane.showMessageDialog(this, "Success: a path exists");
        } else {
            JOptionPane.showMessageDialog(this, "No: there isn't a path");
        }
    }
    private boolean dfs(int r, int c, int targetR, int targetC, boolean[][] visited){
        if(r < 0 || r >= mazeCanvas.grid.length || c < 0 || c >= mazeCanvas.grid[0].length){
            return false;
        }
        if(visited[r][c]){
            return false;
        }
        if(r == targetR && c == targetC){
            return true;
        }
        visited[r][c] = true;
        Cell cell = mazeCanvas.grid[r][c];
        if (!cell.hasTop() && dfs(r - 1, c, targetR, targetC, visited)) return true;
        if (!cell.hasRight() && dfs(r, c + 1, targetR, targetC, visited)) return true;
        if (!cell.hasBottom() && dfs(r + 1, c, targetR, targetC, visited)) return true;
        if (!cell.hasLeft() && dfs(r, c - 1, targetR, targetC, visited)) return true;
        return false;
    }
    private void ExportMaze(){
        if(mazeCanvas == null){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save maze as PNG");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PNG images", "png"));
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            java.io.File file = fileChooser.getSelectedFile();
            if (!file.getAbsolutePath().toLowerCase().endsWith(".png")) {
                file = new java.io.File(file.getAbsolutePath() + ".png");
            }
            try{
                java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(
                        mazeCanvas.getWidth(),
                        mazeCanvas.getHeight(),
                        java.awt.image.BufferedImage.TYPE_INT_RGB);
                java.awt.Graphics2D g2 = image.createGraphics();
                mazeCanvas.paint(g2);
                g2.dispose();
                javax.imageio.ImageIO.write(image, "png", file);
                JOptionPane.showMessageDialog(this, "Maze exported successfully!");
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error:", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void loadMazeState(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Maze State");
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            try (java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(fileChooser.getSelectedFile()))) {
                mazeCanvas.grid = (Cell[][]) in.readObject();
                mazeCanvas.repaint();
                JOptionPane.showMessageDialog(this, "Maze loaded", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error loading maze: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            }
        }
    }
    private void saveMazeState(){
        if(mazeCanvas.grid == null){
            return;
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save new Maze");
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            try (java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.FileOutputStream(fileChooser.getSelectedFile()))) {
                out.writeObject(mazeCanvas.grid);
                JOptionPane.showMessageDialog(this, "Maze saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Error saving maze: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);            }
        }
    }
}

