package Lab3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

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
        List<Profile> importance_profiles = new ArrayList<>(this.profiles);
        importance_profiles.sort(new Comparator<Profile>() {
            @Override
            public int compare(Profile p1, Profile p2) {
                return Integer.compare(p2.importance(), p1.importance());
            }
        });
        String res = "Profiles and companies:\n";
        for(int i = 0; i < importance_profiles.size(); i++){
            res += importance_profiles.get(i).toString() + "(Importance: " + importance_profiles.get(i).importance() + ")\n";
        }
        return res;
    }
    private void DFS(Profile current, Set<Profile> visited, Map<Profile, Integer> discovery, Map<Profile, Integer> small, Map<Profile, Profile> parent, List<Profile> disconectPoints, int[] time){
        int children = 0;
        visited.add(current);
        time[0]++;
        discovery.put(current, time[0]);
        small.put(current, time[0]);
        for(Profile neighbour : current.getRelationships().keySet()){
            if(!visited.contains(neighbour)){
                children++;
                parent.put(neighbour, current);
                DFS(neighbour, visited, discovery, small, parent, disconectPoints, time);
                small.put(current, Math.min(small.get(current), small.get(neighbour)));
                if(parent.get(current) == null && children > 1) {
                    if (!disconectPoints.contains(current)) {
                        disconectPoints.add(current);
                    }
                }
                    if(parent.get(current) != null && small.get(neighbour) >= discovery.get(current)){
                        if(!disconectPoints.contains(current)){
                            disconectPoints.add(current);
                        }
                    }
            } else if(!neighbour.equals(parent.get(current))){
                small.put(current, Math.min(small.get(current), discovery.get(neighbour)));
            }
        }
    }
    public List<Profile> findDisconectPoint(){
        List<Profile> disconectPoints = new ArrayList<>();
        Set<Profile> visited = new HashSet<>();
        Map<Profile, Integer> discovery = new HashMap<>();
        Map<Profile, Integer> small = new HashMap<>();
        Map<Profile, Profile> parent = new HashMap<>();
        int[] time = {0};
        for(Profile p : profiles){
            if(!visited.contains(p)){
                DFS(p, visited, discovery, small, parent, disconectPoints, time);
            }
        }
        return disconectPoints;
    }
}
