package org.example;

public class SharedMemory {
    private String info = "Exploration started.";
    private final boolean[][] visited = new boolean[10][10];
    private boolean bunnySpotted = false;
    private int targetRow = -1;
    private int targetCol = -1;

    public synchronized String getInfo() {
        return info;
    }
    public synchronized void addInfo(String newInfo) {
        this.info = newInfo;
    }
    public synchronized void markVisited(int r, int c){
        visited[r][c] = true;
    }
    public synchronized boolean isVisited(int r, int c){
        return visited[r][c];
    }
    public synchronized void reportBunny(int r, int c, String Name){
        this.bunnySpotted = true;
        this.targetRow = r;
        this.targetCol = c;
        this.info = Name + " SPOTTED BUNNY AT (" + r + "," + c + ")!";
    }
    public synchronized void clearBunnyReport(){
        this.bunnySpotted = false;
        this.info = "Bunny lost";
    }
    public synchronized boolean isBunnySpotted() { return bunnySpotted; }
    public synchronized int getTargetRow() { return targetRow; }
    public synchronized int getTargetCol() { return targetCol; }
}
