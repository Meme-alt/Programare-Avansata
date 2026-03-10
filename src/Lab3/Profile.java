package Lab3;

public interface Profile extends Comparable<Profile> {
    String getName();
    void setName(String name);
    int getID();
    void setID(int id);
    void addRelationship(Profile profile, String type);
    int importance();
}
