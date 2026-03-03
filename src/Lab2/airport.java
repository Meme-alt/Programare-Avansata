package Lab2;

public final class airport extends Location{
    private int nr_terminals;
    public airport(){
        super();
    };
    public airport(String name, float x, float y, int terminals){
        super(name, x, y);
        this.nr_terminals = terminals;
    }
    public int getNr_terminals(){
        return nr_terminals;
    }
    public void setNr_terminals(int terminals){
        this.nr_terminals = terminals;
    }
}
