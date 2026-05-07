package org.example;

import java.util.*;

public class Robot implements Runnable {
    private String name;
    private Game game;
    private int row, col;
    private Stack<Cell> pathStack = new Stack<>();
    private int delay = 500;
    private boolean paused = false;

    public Robot(String name, int startR, int startC) {
        this.name = name;
        this.row = startR;
        this.col = startC;
    }

    public void setGame(Game game) { this.game = game; }
    public String getName(){ return name; }
    public void speedUp() { if (delay > 100) delay -= 100; }
    public void slowDown() { delay += 100; }
    public synchronized void pauseEntity() { paused = true; }
    public synchronized void resumeEntity() {
        paused = false;
        notifyAll();
    }
    public int getRow() { return row; }
    public int getCol() { return col; }

    private synchronized void checkPaused(){
        while(paused && !game.getMaze().isGameOver()){
            try{
                wait();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isValidMove(Cell currentCell, int dir) {
        if (dir == 0 && !currentCell.hasTop()) return true;
        if (dir == 1 && !currentCell.hasBottom()) return true;
        if (dir == 2 && !currentCell.hasLeft()) return true;
        if (dir == 3 && !currentCell.hasRight()) return true;
        return false;
    }
    private List<Integer> getUnvisitedDirections(Cell currentCell, SharedMemory mem) {
        List<Integer> validDirs = new ArrayList<>();
        if (isValidMove(currentCell, 0) && !mem.isVisited(row - 1, col)) validDirs.add(0);
        if (isValidMove(currentCell, 1) && !mem.isVisited(row + 1, col)) validDirs.add(1);
        if (isValidMove(currentCell, 2) && !mem.isVisited(row, col - 1)) validDirs.add(2);
        if (isValidMove(currentCell, 3) && !mem.isVisited(row, col + 1)) validDirs.add(3);
        return validDirs;
    }

    private boolean canSeeBunny(int bRow, int bCol, Maze maze) {
        if (Math.abs(this.row - bRow) > 2 || Math.abs(this.col - bCol) > 2) return false;
        if (this.row == bRow) {
            int step = (bCol > this.col) ? 1 : -1;
            int c = this.col;
            while (c != bCol) {

                Cell current = maze.getCell(this.row, c);
                if (step == 1 && current.hasRight()) return false;
                if (step == -1 && current.hasLeft()) return false;
                c += step;
            }
            return true;
        }
        else if (this.col == bCol) {
            int step = (bRow > this.row) ? 1 : -1;
            int r = this.row;
            while (r != bRow) {
                Cell current = maze.getCell(r, this.col);
                if (step == 1 && current.hasBottom()) return false;
                if (step == -1 && current.hasTop()) return false;
                r += step;
            }
            return true;
        }
        return false;
    }
    private boolean canCatchBunny(Bunny b, Maze maze) {
        if (this.row == b.getRow() && this.col == b.getCol()) return true;
        if (this.row == b.getRow() && Math.abs(this.col - b.getCol()) == 1) {
            Cell myCell = maze.getCell(this.row, this.col);
            if (this.col > b.getCol() && !myCell.hasLeft()) return true;
            if (this.col < b.getCol() && !myCell.hasRight()) return true;
        }
        if (this.col == b.getCol() && Math.abs(this.row - b.getRow()) == 1) {
            Cell myCell = maze.getCell(this.row, this.col);
            if (this.row > b.getRow() && !myCell.hasTop()) return true;
            if (this.row < b.getRow() && !myCell.hasBottom()) return true;
        }
        return false;
    }
    private void checkAndCatchBunnies() {
        for (Bunny b : game.getBunnies()) {
            if (b.isCaught()) continue;
            if (canCatchBunny(b, game.getMaze())) {
                System.out.println("🤖 " + name + " CAUGHT " + b.getName() + "!");
                b.catchBunny();
                boolean anyAlive = false;
                for (Bunny checkB : game.getBunnies()) {
                    if (!checkB.isCaught()) {
                        anyAlive = true;
                        break;
                    }
                }
                if (!anyAlive) {
                    System.out.println("🤖 ALL BUNNIES CAUGHT! Robots win!");
                    game.getMaze().setGameOver(true);
                    game.resumeAll();
                }
            }
        }
    }
    private Cell getNextStepToTarget(int targetR, int targetC, Maze maze){
        int rows = maze.getRows();
        int cols = maze.getCols();
        boolean[][] visitedBFS = new boolean[rows][cols];
        Queue<Cell> queue = new LinkedList<>();
        Map<Cell, Cell> parentMap = new HashMap<>();
        Cell start = maze.getCell(this.row, this.col);
        Cell target = maze.getCell(targetR, targetC);
        if(start == target){
            return start;
        }
        while(!queue.isEmpty()){
            Cell curr = queue.poll();
            if(curr == target){
                Cell step = curr;
                while(parentMap.get(step) != start){
                    step = parentMap.get(step);
                }
                return step;
            }
            int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for(int i = 0; i < 4; i++){
                if(isValidMove(curr, i)){
                    int nr = curr.getRow() + dirs[i][0];
                    int nc = curr.getCol() + dirs[i][1];
                    if(nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visitedBFS[nr][nc]){
                        Cell next = maze.getCell(nr, nc);
                        visitedBFS[nr][nc] = true;
                        parentMap.put(next, curr);
                        queue.add(next);
                    }
                }
            }
        }
        return null;
    }
    @Override
    public void run() {
        game.getMem().markVisited(row, col);
        pathStack.push(game.getMaze().getCell(row, col));
        while (!game.getMaze().isGameOver()) {
            checkPaused();
            SharedMemory mem = game.getMem();
            checkAndCatchBunnies();
            if (game.getMaze().isGameOver()) break;
            for (Bunny b : game.getBunnies()) {
                if (!b.isCaught() && canSeeBunny(b.getRow(), b.getCol(), game.getMaze())) {
                    mem.reportBunny(b.getRow(), b.getCol(), name);
                    break;
                }
            }
            int nextRow = row, nextCol = col;
            boolean isBacktracking = false;
            if (mem.isBunnySpotted()) {
                Cell nextStep = getNextStepToTarget(mem.getTargetRow(), mem.getTargetCol(), game.getMaze());
                if (nextStep != null) {
                    nextRow = nextStep.getRow();
                    nextCol = nextStep.getCol();
                } else {
                    mem.clearBunnyReport();
                }
            } else {
                Cell currentCell = game.getMaze().getCell(row, col);
                List<Integer> unvisited = getUnvisitedDirections(currentCell, mem);
                if (!unvisited.isEmpty()) {
                    int dir = unvisited.get(0);
                    if (dir == 0) nextRow--; else if (dir == 1) nextRow++; else if (dir == 2) nextCol--; else nextCol++;
                    pathStack.push(currentCell);
                } else {
                    if (!pathStack.isEmpty()) {
                        Cell prevCell = pathStack.pop();
                        nextRow = prevCell.getRow();
                        nextCol = prevCell.getCol();
                        isBacktracking = true;
                    }
                }
            }
            if (game.getMaze().visit(nextRow, nextCol, this)) {
                row = nextRow;
                col = nextCol;
                if (!isBacktracking && !mem.isBunnySpotted()) mem.markVisited(row, col);
                checkAndCatchBunnies();
                if (mem.isBunnySpotted() && this.row == mem.getTargetRow() && this.col == mem.getTargetCol()) {
                    boolean stillSeeBunny = false;
                    for (Bunny b : game.getBunnies()) {
                        if (!b.isCaught() && canSeeBunny(b.getRow(), b.getCol(), game.getMaze())) {
                            stillSeeBunny = true;
                            break;
                        }
                    }
                    if (!stillSeeBunny) {
                        mem.clearBunnyReport();
                        pathStack.clear();
                        pathStack.push(game.getMaze().getCell(row, col));
                    }
                }
            } else {
                if (mem.isBunnySpotted()) mem.clearBunnyReport();
                else if (!isBacktracking && !getUnvisitedDirections(game.getMaze().getCell(row, col), mem).isEmpty()) pathStack.pop();
                else if (isBacktracking) pathStack.push(game.getMaze().getCell(row, col));
            }
            try { Thread.sleep(delay); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }
}