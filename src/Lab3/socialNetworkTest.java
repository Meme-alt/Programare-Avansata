package Lab3;

import static org.junit.jupiter.api.Assertions.*;

class socialNetworkTest {
    @org.junit.jupiter.api.Test
    void findDisconectPoint() {
        socialNetwork network = new socialNetwork();
        Profile alice = new Person("Alice", 1, "24 May");
        Profile bit = new Company("Bit", 2, "IT");
        Profile tony = new Person("Tony", 3, "17 April");
        Profile hoyo = new Company("Hoyo", 4, "Games");
        network.addProfile(alice);
        network.addProfile(bit);
        network.addProfile(tony);
        network.addProfile(hoyo);
        alice.addRelationship(tony, "Friend");
        tony.addRelationship(hoyo, "Contractor");
        hoyo.addRelationship(bit, "Partner");
        java.util.List<Profile> disconnectPoints = network.findDisconectPoint();
        assertEquals(2, disconnectPoints.size());
        assertTrue(disconnectPoints.contains(tony));
        assertTrue(disconnectPoints.contains(hoyo));
    }
    @org.junit.jupiter.api.Test
    void findMaximalParts() {
        socialNetwork network = new socialNetwork();
        Profile alice = new Person("Alice", 1, "24 May");
        Profile bit = new Company("Bit", 2, "IT");
        Profile tony = new Person("Tony", 3, "17 April");
        network.addProfile(alice);
        network.addProfile(bit);
        network.addProfile(tony);
        alice.addRelationship(tony, "Friend");
        tony.addRelationship(bit, "Contractor");
        bit.addRelationship(alice, "Partner");
        java.util.List<java.util.Set<Profile>> components = network.findMaximalParts();
        assertEquals(1, components.size());
        assertEquals(3, components.get(0).size());
    }
}