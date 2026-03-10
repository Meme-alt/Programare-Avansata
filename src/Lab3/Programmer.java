package Lab3;

public class Programmer extends Person {
    int languages;
    String favoriteLanguage;
    public Programmer(){ }
    public Programmer(int languages, String name, int id, String birthdate, String favouriteLanguage){
        super(name, id, birthdate);
        this.languages = languages;
        this.favoriteLanguage = favouriteLanguage;
    }
    public void setLanguages(int languages) {
        this.languages = languages;
    }
    public int getLanguages(){
        return languages;
    }
    public String getFavouriteLanguage(){
        return favoriteLanguage;
    }
    public void setFavouriteLanguage(String favouriteLanguage){
        this.favoriteLanguage = favouriteLanguage;
    }
    @Override
    public String toString(){
        return "Programmer: " + getName() + " (ID: " + getID() + ", Languages: " + languages + ", Main: " + favoriteLanguage + ")";
    }
}
