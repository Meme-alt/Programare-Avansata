package Lab2;

public abstract sealed class Location permits city, airport, GasStation {
    private String name;
    private float x;
    private float y;
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
