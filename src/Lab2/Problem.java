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
            result += locations[i].toString() + "\n";
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
}
