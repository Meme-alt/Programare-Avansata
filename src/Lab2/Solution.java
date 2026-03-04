package Lab2;

public class Solution {
    private Location[] path;
    private int size;
    private float distance;
    private boolean reachable;
    public Solution(){
        this.reachable = false;
        this.distance = -1;
    };
    public Solution(Location[] path, int size, float distance){
        this.path = path;
        this.size = size;
        this.distance = distance;
        this.reachable = true;
    }
    public boolean isReachable(){
        return reachable;
    }
    public Location[] getPath(){
        return path;
    }
    @Override
    public String toString(){
        if(!reachable){
            return "Unreachable";
        }
        String res = "Path found!\n";
        res += "Distance: " + distance + "\n";
        res += "Route: ";
        for(int i = size - 1; i >= 0; i--){
            res += path[i].getName();
            if(i > 0){
                res += " -> ";
            }
        }
        return res;
    }
}
