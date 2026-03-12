package Lab3;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        socialNetwork network = new socialNetwork();
        network.addProfile(new Person("Alice", 451, "24 May"));
        network.addProfile(new Company("Bit", 277, "IT"));
        network.addProfile(new Person("Tony", 102, "17 April"));
        network.addProfile(new Company("Hoyo", 72, "Games"));
        network.getProfile(0).addRelationship(network.getProfile(2), "Friend");
        network.getProfile(3).addRelationship(network.getProfile(1), "Employee");
        network.getProfile(2).addRelationship(network.getProfile(3), "Contractor");
        network.getProfile(2).addRelationship(network.getProfile(0), "Employer");
        System.out.println(network.toString());
        List<Profile> disconectPoints = network.findDisconectPoint();
        System.out.println("Disconect Points: ");
        for(Profile p : disconectPoints){
            System.out.println(p.getName());
        }
        System.out.println("\nMaximal Parts: ");
        List<Set<Profile>> maximalParts = network.findMaximalParts();
        for(Set<Profile> comp : maximalParts){
            for(Profile p : comp){
                System.out.println(p.getName() + " ");
            }
            System.out.println();
        }
    }
}
