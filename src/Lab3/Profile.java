package Lab3;
import java.util.Map;

public interface Profile extends Comparable<Profile> {
    String getName();
    void setName(String name);
    int getID();
    void setID(int id);
    void addRelationship(Profile profile, String type);
    int importance();
    Map<Profile, String> getRelationships();
}
