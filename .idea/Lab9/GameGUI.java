package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameGUI extends JFrame {
    private Game game;
    private final JLabel statusLabel;

    private class MazePanel extends JPanel {
        private final int CELL_SIZE = 40;
        private void drawWalls(Graphics g, Cell cell, int x, int y) {
            g.setColor(Color.BLACK);
            if (cell.hasTop()) g.drawLine(x, y, x + CELL_SIZE, y);
            if (cell.hasBottom()) g.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);
            if (cell.hasLeft()) g.drawLine(x, y, x, y + CELL_SIZE);
            if (cell.hasRight()) g.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);
        }
        private void drawEntity(Graphics g, int r, int c, Color color, String text) {
            int x = c * CELL_SIZE;
            int y = r * CELL_SIZE;
            g.setColor(color);
            g.fillOval(x + 4, y + 4, CELL_SIZE - 8, CELL_SIZE - 8);
            g.setColor(Color.WHITE);
            g.drawString(text, x + CELL_SIZE / 2 - 4, y + CELL_SIZE / 2 + 4);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (game == null || game.getMaze() == null) return;
            Maze maze = game.getMaze();
            for (int i = 0; i < maze.getRows(); i++) {
                for (int j = 0; j < maze.getCols(); j++) {
                    int x = j * CELL_SIZE;
                    int y = i * CELL_SIZE;
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    drawWalls(g, maze.getCell(i, j), x, y);
                }
            }
            drawEntity(g, 9, 9, Color.GREEN, "E");
            for (Bunny b : game.getBunnies()) {
                if(!b.isCaught()) {
                    drawEntity(g, b.getRow(), b.getCol(), Color.BLUE, "B");
                }
            }
            List<Cell> robotCells = maze.getRobotLocations();
            for (Cell c : robotCells) {
                if (c != null) drawEntity(g, c.getRow(), c.getCol(), Color.RED, "R");
            }
        }
    }

    public GameGUI() {
        setTitle("Maze Game UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel setupPanel = new JPanel();
        setupPanel.add(new JLabel("Bunnies:"));
        JSpinner bunnySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        setupPanel.add(bunnySpinner);
        setupPanel.add(new JLabel("Robots:"));
        JSpinner robotSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        setupPanel.add(robotSpinner);
        JButton startBtn = new JButton("Start Game");
        startBtn.addActionListener(e -> {
            startBtn.setEnabled(false); // Prevent clicking twice
            int bCount = (int) bunnySpinner.getValue();
            int rCount = (int) robotSpinner.getValue();
            game.setupAndStart(bCount, rCount);
        });
        setupPanel.add(startBtn);
        add(setupPanel, BorderLayout.NORTH);
        MazePanel mazePanel = new MazePanel();
        mazePanel.setPreferredSize(new Dimension(400, 400));
        add(mazePanel, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel(" Memory: Waiting to start...");
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        JTextField commandInput = new JTextField();
        commandInput.addActionListener(e -> {
            if (game != null) game.processCommand(commandInput.getText());
            commandInput.setText("");
        });
        bottomPanel.add(commandInput, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void setGame(Game game) { this.game = game; }

    public void updateGUI(String status) {
        statusLabel.setText(" " + status);
        repaint();
    }
}
