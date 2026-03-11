package Lab3;
import java.util.HashMap;
import java.util.Map;

public class Company implements Profile, Comparable<Profile> {
    public String name;
    private int id;
    private String industry;
    private Map<Profile, String> relationships = new HashMap<>();
    public Company(){ }
    public Company(String name, int id, String industry){
        this.name = name;
        this.id = id;
        this.industry = industry;
    }
    public void addRelationship(Profile profile, String type){
        this.relationships.put(profile, type);
        if(!profile.getRelationships().containsKey(this)){
            profile.addRelationship(this, type);
        }
    }
    @Override
    public String getName(){ return name; }
    @Override
    public int getID(){ return id; }
    @Override
    public void setID(int id){ this.id = id;}
    @Override
    public void setName(String name){ this.name = name; }
    @Override
    public int compareTo(Profile other){ return this.name.compareTo(other.getName());}
    @Override
    public String toString(){
        return "Company: " + name + " (ID: " + id + ", Industry: " + industry + ")";
    }
    @Override
    public int importance(){
        return relationships.size();
    }
    public Map<Profile, String> getRelationships(){
        return this.relationships;
    }
}
