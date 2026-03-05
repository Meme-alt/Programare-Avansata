package Lab2;

public class Problem {
    private Location[] locations;
    private Road[] roads;
    private int nr_locations = 0;
    private int nr_roads = 0;
    public Problem(int max_locations, int max_roads){
        this.locations = new Location[max_locations];
        this.roads = new Road[max_roads];
    }

    /**
     * Adds a location to the list
     * @param loc
     */
    public void addLocation(Location loc){
        for(int i = 0; i < nr_locations; i++){
            if(locations[i].equals(loc)){
                System.out.println("Error: location " + loc.getName() + " already exists");
                return;
            }
        }
        if(nr_locations < locations.length){
            locations[nr_locations] = loc;
            nr_locations++;
        }
        else{
            System.out.println("Error: Not enough space in locations");
        }
    }

    /**
     * Adds a road to the list
     * @param road
     */
    public void addRoad(Road road){
        for(int i = 0; i < nr_roads; i++){
            if(roads[i].equals(road)){
                System.out.println("Error: road " + road.getName() + " already exists");
                return;
            }
        }
        if(nr_roads < roads.length){
            roads[nr_roads] = road;
            nr_roads++;
        }
        else{
            System.out.println("Error: Not enough space in roads");
        }
    }
    @Override
    public String toString(){
        String result = "Locations:\n";
        for(int i = 0; i < nr_locations; i++){
            result += locations[i].toString() + "\n";
        }
        result += "Roads:\n";
        for(int i = 0; i < nr_roads; i++){
            result += roads[i].toString() + "\n";
        }
        return result;
    }
    public boolean isValid(){
        if(nr_locations == 0 || nr_roads == 0){
            return false;
        }
        for(int i = 0; i < nr_roads; i++){
            Road r = roads[i];
            if(r.getLength() <= 0){
                return false;
            }
            if(r.getSpeedLimit() <= 0){
                return false;
            }
            if(r.getName() == null){
                return false;
            }
        }
        for(int i = 0; i < nr_locations; i++){
            Location l = locations[i];
            if(l.getName() == null){
                return false;
            }
        }
        return true;
    }
    private int getIndex(Location loc){
        for(int i = 0; i < nr_locations; i++){
            if(locations[i].equals(loc)){
                return i;
            }
        }
        return -1;
    }

    /**
     * DFS algorithm
     * @param start the location you start from
     * @param end the location you want to reach
     * @param visited locations visited in the proccess
     * @return a boolean that shows whether you can reach your destination
     */
    private boolean DFS(Location start, Location end, boolean[] visited){
        if(start.equals(end)){
            return true;
        }
        int index = getIndex(start);
        if(index == -1){
            return false;
        }
        visited[index] = true;
        for(int i = 0; i < nr_roads; i++){
            if(roads[i].getStartingPoint().equals(start)){
                if(DFS(roads[i].getEndPoint(), end, visited)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Through the use of a dfs algorithm determines if a location can be reached from another
     * @param start
     * @param end
     * @return true if it can be reached, false otherwise
     */
    public boolean reachable(Location start, Location end){
        boolean[] visited = new boolean[nr_locations];
        return DFS(start, end, visited);
    }

    /**
     * Dijkastra's algorithm
     * @param start the location you start from
     * @param end the location you want to reach
     * @return the shortest path (by distance) between the 2 locations
     */
    public Solution ShortestPath(Location start, Location end){
        float[] dist = new float[nr_locations];
        int[] parent = new int[nr_locations];
        boolean[] visited = new boolean[nr_locations];

        for (int i = 0; i < nr_locations; i++) {
            dist[i] = Float.MAX_VALUE;
            parent[i] = -1;
        }
        dist[getIndex(start)] = 0;
        for (int count = 0; count < nr_locations; count++) {
            int u = findMinDistance(dist, visited);
            if (u == -1) break;
            visited[u] = true;

            for (int i = 0; i < nr_roads; i++) {
                if (roads[i].getStartingPoint().equals(locations[u])) {
                    int v = getIndex(roads[i].getEndPoint());
                    float len = roads[i].getLength();

                    if (dist[u] + len < dist[v]) {
                        dist[v] = dist[u] + len;
                        parent[v] = u;
                    }
                }
            }
        }
        int final_index = getIndex(end);
        if(dist[final_index] == Float.MAX_VALUE){
            return new Solution();
        }
        Location[] path = new Location[nr_locations];
        int size = 0;
        for(int i = final_index; i != -1; i = parent[i]){
            path[size++] = locations[i];
        }
        return new Solution(path, size, dist[final_index]);
    }
    private int findMinDistance(float[] dist, boolean[] visited){
        float min = Float.MAX_VALUE;
        int minIndex = -1;
        for(int i = 0; i < nr_locations; i++){
            if(!visited[i] && dist[i] <= min){
                min = dist[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    public Location[] getLocations(){
        return locations;
    }
}
