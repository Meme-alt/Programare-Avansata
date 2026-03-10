package Lab3;

public class Designer extends Person {
    int nr_original_designs;
    String favouriteDesign;
    public Designer(){ }
    public Designer(int nr_original_designs, String name, int id, String birthdate, String favouriteDesign){
        super(name, id, birthdate);
        this.nr_original_designs = nr_original_designs;
        this.favouriteDesign = favouriteDesign;
    }
    public void setNr_original_designs(int nr_original_designs){
        this.nr_original_designs = nr_original_designs;
    }
    public int getNr_original_designs(){
        return nr_original_designs;
    }
    public String getFavouriteDesign(){
        return favouriteDesign;
    }
    public void setFavouriteDesign(String favouriteDesign){
        this.favouriteDesign = favouriteDesign;
    }
    @Override
    public String toString(){
        return "Designer: " + getName() + " (ID: " + getID() + ", Designs: " + nr_original_designs + ", Favorite Design: " + favouriteDesign + ")";
    }
}
