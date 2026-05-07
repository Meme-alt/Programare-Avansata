package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private final Maze maze;
    private Bunny bunny;
    private final List<Robot> robots = new ArrayList<>();
    private final List<Bunny> bunnies = new ArrayList<>();
    private final SharedMemory mem = new SharedMemory();
    private GameGUI gui;

    private volatile boolean displayPaused = false;
    private volatile int displayDelay = 500;

    public Game(Maze maze) { this.maze = maze; }
    public void setGui(GameGUI gui) { this.gui = gui; }

    public void setBunny(Bunny bunny) {
        this.bunny = bunny;
        this.bunny.setGame(this);
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
        robot.setGame(this);
    }

    public Maze getMaze() { return maze; }
    public SharedMemory getMem() { return mem; }
    public List<Bunny> getBunnies() { return bunnies; }

    public void resumeAll() {
        for (Bunny b : bunnies) b.resumeEntity();
        for (Robot r : robots) r.resumeEntity();
        displayPaused = false;
    }

    public void processCommand(String command) {
        String action = command.trim().toLowerCase();
        if (action.equals("stop")) {
            for(Bunny b : bunnies) {
                b.pauseEntity();
            }
            for (Robot r : robots) r.pauseEntity();
            displayPaused = true;
        } else if (action.equals("resume")) {
            resumeAll();
        } else if (action.equals("speed up") || action.equals("speedup")) {
            for(Bunny b : bunnies) {
                b.speedUp();
            }
            for (Robot r : robots) r.speedUp();
            if (displayDelay > 100) displayDelay -= 100;
        } else if (action.equals("slow down") || action.equals("slowdown")) {
            for(Bunny b : bunnies) {
                b.slowDown();
            }
            for (Robot r : robots) r.slowDown();
            displayDelay += 100;
        }
    }

    private void startManagerThread() {
        Thread managerThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            long timeLimitMs = 60000;
            while (!maze.isGameOver()) {
                long elapsedMs = System.currentTimeMillis() - startTime;
                if (elapsedMs >= timeLimitMs) {
                    maze.setGameOver(true);
                    resumeAll();
                    if (gui != null) gui.updateGUI("⏳ TIME LIMIT EXCEEDED! Game Over.");
                    break;
                }
                if (!displayPaused && gui != null) {
                    gui.updateGUI("Time: " + (elapsedMs / 1000) + "s | Memory: " + mem.getInfo());
                }
                try { Thread.sleep(displayDelay); } catch (InterruptedException e) { break; }
            }
            if (maze.isGameOver() && gui != null) {
                gui.updateGUI("GAME OVER! | Final Memory: " + mem.getInfo());
            }
        });
        managerThread.setDaemon(true);
        managerThread.start();
    }
    public void setupAndStart(int numBunnies, int numRobots) {
        List<Cell> emptyCells = new ArrayList<>();
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                emptyCells.add(maze.getCell(i, j));
            }
        }
        Collections.shuffle(emptyCells);
        for (int i = 0; i < numBunnies; i++) {
            Cell c = emptyCells.get(i);
            Bunny b = new Bunny("Bunny-" + (i + 1), c.getRow(), c.getCol());
            b.setGame(this);
            bunnies.add(b);
            new Thread(b).start();
        }
        for (int i = 0; i < numRobots; i++) {
            Cell c = emptyCells.get(i + numBunnies);
            Robot r = new Robot("Robot-" + (i + 1), c.getRow(), c.getCol());
            r.setGame(this);
            robots.add(r);
            new Thread(r).start();
        }
        startManagerThread();
    }
}
