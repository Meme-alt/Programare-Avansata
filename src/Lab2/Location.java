package Lab2;

/**
 * represents a location that has a name and a space location that is determined by the x and y coordinates
 */
public abstract sealed class Location permits city, airport, GasStation {
    private String name;
    private float x;
    private float y;

    /**
     * constructors getters and setters
     */
    public Location(){ };
    public Location(String name, float x, float y){
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public String getName(){
        return name;
    }
    public float getXcord(){
        return x;
    }
    public float getYcord(){
        return y;
    }
    public void setName(String name){ this.name = name; }
    public void setXcord(float x){ this.x = x; }
    public void setYcord(float y){ this.y = y; }
    @Override
    public String toString(){
        return name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Location)) {
            return false;
        }
        Location other = (Location) obj;
        return name.equals(other.name);
    }
}
