package org.example;

import java.util.*;
import java.util.stream.IntStream;
import com.github.javafaker.Faker;

public class Main {
    static void main() {
        Random rand = new Random();
        var nodes = IntStream.rangeClosed(0, 9)
                .mapToObj(i -> new Intersection("v" + i, rand.nextInt(1000), rand.nextInt(1000)))
                .toArray(Intersection[]::new);
        for(Intersection i : nodes){
            System.out.println(i.getName());
        }
        List<Street> streets = new LinkedList<>();
        streets.add(new Street("Main St", 500, nodes[0], nodes[1]));
        streets.add(new Street("Broadway", 1200, nodes[1], nodes[2]));
        streets.add(new Street("Oak Ave", 300, nodes[2], nodes[3]));
        Collections.sort(streets, ((u, v) -> u.getName().compareTo(v.getName())));
        for(Street s : streets){
            System.out.println(s.getName() + " length: " + s.getLength());
        }
        Set<Intersection> intersections = new HashSet<>();
        for(var n : nodes){
            intersections.add(n);
        }
        boolean test = intersections.add(new Intersection("v1", 0, 0));
        System.out.println(test);
        Faker faker = new Faker();
        City newCity = new City("Something");
        for(int i = 0; i < nodes.length - 1; i++){
            newCity.addStreet(new Street(faker.address().streetName(),
                    faker.number().numberBetween(300, 5000), nodes[i], nodes[i + 1]));
        }
        newCity.addStreet(new Street("Hub-Road-1", 1000, nodes[1], nodes[5]));
        newCity.addStreet(new Street("Hub-Road-2", 1000, nodes[1], nodes[8]));
        newCity.findCertainStreets(500);
        newCity.addStreet(new Street("Shortcut Alley", 400, nodes[0], nodes[3]));
        newCity.addStreet(new Street("Highway 1", 1500, nodes[2], nodes[7]));
        newCity.addStreet(new Street("Bypass Blvd", 900, nodes[4], nodes[8]));
        newCity.addStreet(new Street("Ring Road", 2500, nodes[0], nodes[9]));
        newCity.solution(3);
        newCity.CarRoute();
        City city = generateCity(10, 15);
        city.findCertainStreets(500);
        city.solution(3);
        city.CarRoute();
    }
    public static City generateCity(int nr_intersections, int nr_streets){
        City city = new City("City");
        Faker faker = new Faker();
        Random rand = new Random();
        List<Intersection> nodes = new ArrayList<>();
        for(int i = 0; i < nr_intersections; i++){
            Intersection node = new Intersection("v" + i, rand.nextInt(1000), rand.nextInt(1000));
            nodes.add(node);
            city.addIntersection(node);
        }
        int nr = 0;
        for(int i = 0; i < nr_intersections - 1; i++){
            Intersection a = nodes.get(i);
            Intersection b = nodes.get(i + 1);
            int length = (int) Math.ceil(a.distance(b));
            city.addStreet(new Street(faker.address().streetName(), length, a, b));
            nr++;
        }
        while(nr < nr_streets){
            Intersection a = nodes.get(rand.nextInt(nr_intersections));
            Intersection b = nodes.get(rand.nextInt(nr_intersections));
            if(!a.equals(b)){
                int length = (int) Math.ceil(a.distance(b));
                city.addStreet(new Street(faker.address().streetName(), length, a, b));
                nr++;
            }
        }
        return city;
    }
}

