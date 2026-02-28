package Lab2;

public class Road {
    private String name;
    private Roadtype type;
    private float length;
    private int speedLimit;
    public Road(){ }
    public Road(String name, Roadtype type, float length, int speedLimit){
        this.name = name;
        this.type = type;
        this.length = length;
        this.speedLimit = speedLimit;
    }
    public String getName(){ return name; }
    public Roadtype getType(){ return type; }
    public float getLength(){ return length; }
    public int getSpeedLimit(){ return speedLimit; }
    public void setName(String name){ this.name = name; }
    public void setType(Roadtype type){ this.type = type; }
    public void setLength(float length){ this.length = length; }
    public void setSpeedLimit(int speedLimit){ this.speedLimit = speedLimit; }
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
