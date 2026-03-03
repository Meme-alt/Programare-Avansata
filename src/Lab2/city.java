package Lab2;

public final class city extends Location {
    private int population;
    public city(){
        super();
    };
    public city(String name, float x, float y, int population){
        super(name, x, y);
        this.population = population;
    }
    public int getPopulation(){
        return population;
    }
    public void setPopulation(int population){
        this.population = population;
    }
}
