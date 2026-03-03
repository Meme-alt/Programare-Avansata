package Lab2;

public final class GasStation extends Location {
    private int gas_price;
    public GasStation(){
        super();
    };
    public GasStation(String name, float x, float y, int price){
        super(name, x, y);
        this.gas_price = price;
    }
    public int getGas_price(){
        return gas_price;
    }
    public void setGas_price(int price){
        this.gas_price = price;
    }
}
