package Lab3;
import java.util.HashMap;
import java.util.Map;

public class Person implements Profile, Comparable<Profile>{
    private String name;
    private int id;
    private String birthdate;
    private Map<Profile, String> relationships = new HashMap<>();
    public Person(){ }
    public Person(String name, int id, String birthdate){
        this.name = name;
        this.id = id;
        this.birthdate = birthdate;
    }
    public void addRelationship(Profile profile, String type){
        relationships.put(profile, type);
    }
    @Override
    public String getName(){ return name; }
    @Override
    public int getID(){ return id; }
    @Override
    public void setID(int id){ this.id = id; }
    @Override
    public void setName(String name){ this.name = name; }
    @Override
    public int compareTo(Profile other){ return this.name.compareTo(other.getName());}
    @Override
    public String toString(){
        return "Person: " + name + "(ID: " + id + ", birthday: " + birthdate + ")";
    }
    public int importance(){
        return relationships.size();
    }
}
