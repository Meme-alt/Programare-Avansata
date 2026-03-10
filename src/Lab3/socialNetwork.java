package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class socialNetwork {
    private List<Profile> profiles = new ArrayList<>();
    public socialNetwork(){ }
    public socialNetwork(Profile profile){ profiles.add(profile); }
    public Profile getProfile(int index){ return profiles.get(index); }
    public void changeProfile(int index, Profile profile){ profiles.set(index, profile); }
    public int getLength(){ return profiles.size(); }
    public void addProfile(Profile profile){ profiles.add(profile); }
    public void sortNetwork(){ Collections.sort(this.profiles); }
    @Override
    public String toString(){
        String res = "Profiles and companies:\n";
        for(int i = 0; i < profiles.size(); i++){
            res += profiles.get(i).toString() + "\n";
        }
        return res;
    }
}
