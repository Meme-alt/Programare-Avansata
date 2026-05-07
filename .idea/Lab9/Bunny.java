package org.example;

import java.util.Random;

public class Bunny implements Runnable{
    private String name;
    private Game game;
    private int r, c;
    private int delay = 500;
    private boolean paused = false;
    private volatile boolean isCaught = false;
    public Bunny(String name, int r, int c) {
        this.name = name;
        this.r = r;
        this.c = c;
    }
    public void setGame(Game game) {
        this.game = game;
    }
    public String getName(){ return name; }
    public void speedUp(){
        if(delay > 100){
            delay -= 100;
        }
    }
    public void slowDown(){ delay += 100; }
    public synchronized void pauseEntity() { paused = true; }
    public synchronized void resumeEntity(){
        paused = false;
        notifyAll();
    }
    public int getRow() { return r; }
    public int getCol() { return c; }
    public boolean isCaught() { return isCaught; }
    public synchronized void catchBunny() {
        isCaught = true;
        notifyAll();
    }
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
    @Override
    public void run(){
        Random rand = new Random();
        while(!game.getMaze().isGameOver() && !isCaught){
            checkPaused();
            if (isCaught || game.getMaze().isGameOver()) break;
            Cell currentCell = game.getMaze().getCell(r, c);
            int dir = rand.nextInt(4);
            if(isValidMove(currentCell, dir)){
                if(dir == 0) r--;
                else if (dir == 1) r++;
                else if (dir == 2) c--;
                else if (dir == 3) c++;
            }
            if (game.getMaze().isExit(r, c)) {
                System.out.println("🐰 " + name + " ESCAPED! Bunnies win!");
                game.getMaze().setGameOver(true);
                game.resumeAll();
            }
            try{ Thread.sleep(300); }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
