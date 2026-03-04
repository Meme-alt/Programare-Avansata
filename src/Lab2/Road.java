package Lab2;

/**
 * represents a road a link between 2 different locations that has a name, length, speedLimit and endPoint
 */
public class Road{
    private String name;
    private Roadtype type;
    private float length;
    private int speedLimit;
    private Location startingPoint;
    private Location endPoint;
    public Road(){ }
    public Road(String name, Roadtype type, float length, int speedLimit, Location startingPoint, Location endPoint){
        this.name = name;
        this.type = type;
        this.length = length;
        this.speedLimit = speedLimit;
        this.startingPoint = startingPoint;
        this.endPoint = endPoint;
    }

    /**
     * getters and setters
     */
    public String getName(){ return name; }
    public Roadtype getType(){ return type; }
    public float getLength(){ return length; }
    public int getSpeedLimit(){ return speedLimit; }
    public Location getStartingPoint(){ return startingPoint; }
    public Location getEndPoint(){ return endPoint; }
    public void setName(String name){ this.name = name; }
    public void setType(Roadtype type){ this.type = type; }
    public void setLength(float length){ this.length = length; }
    public void setSpeedLimit(int speedLimit){ this.speedLimit = speedLimit; }
    public void setStartingPoint(Location startingPoint){ this.startingPoint = startingPoint; }
    public void setEndPoint(Location endPoint){ this.endPoint = endPoint; }
    public enum Roadtype{
        FREEWAY,
        HIGHWAY,
        URBAN,
        RESIDENTIAL;
    }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Road)) {
            return false;
        }
        Road other = (Road) obj;
        return name.equals(other.name);
    }
}
