package org.example;

import java.util.*;
import org.graph4j.Graph;
import org.graph4j.Edge;
import org.graph4j.GraphBuilder;
import org.graph4j.spanning.WeightedSpanningTreeIterator;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;

public class City {
    private String name;
    private Map<Intersection, List<Street>> cityMap;
    public City(){ }
    public City(String name){
        this.name = name;
        this.cityMap = new HashMap<>();
    }
    public void addIntersection(Intersection intersection){
        cityMap.putIfAbsent(intersection, new LinkedList<>());
    }
    public void addStreet(Street street){
        Intersection i1 = street.getI1();
        Intersection i2 = street.getI2();
        addIntersection(i1);
        addIntersection(i2);
        cityMap.get(i1).add(street);
        cityMap.get(i2).add(street);
    }
    public Map<Intersection, List<Street>> getCityMap() {
        return cityMap;
    }
    public void findCertainStreets(int minLength){
        cityMap.keySet().stream()
                .flatMap(v -> cityMap.get(v).stream())
                .distinct()
                .filter(e -> e.getLength() > minLength)
                .filter(e -> { int joins = cityMap.get(e.getI1()).size() + cityMap.get(e.getI2()).size() - 2;
                    return joins >= 3; })
                .forEach(System.out::println);
    }
    @Override
    public String toString(){
        return "City: " + name;
    }
    public void solution(int nr){
        List<Intersection> nodes = new ArrayList<>(cityMap.keySet());
        Map<Intersection, Integer> nodeToId = new HashMap<>();
        for(int i = 0; i < nodes.size(); i++){
            nodeToId.put(nodes.get(i), i);
        }
        Graph g = GraphBuilder.numVertices(nodes.size()).buildGraph();
        cityMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .forEach(s -> {
                    int u = nodeToId.get(s.getI1());
                    int v = nodeToId.get(s.getI2());
                    g.addEdge(u, v, (double) s.getLength());
                });
        WeightedSpanningTreeIterator iterator = new WeightedSpanningTreeIterator(g);
        int count = 0;
        while(iterator.hasNext() && count < nr){
            var tree = iterator.next();
            count++;
            System.out.println("\nSolution " + count + ":");
            System.out.println(tree.toString());
        }
    }
    public void CarRoute(){
        List<Intersection> nodes = new ArrayList<>(cityMap.keySet());
        var graph = new SimpleWeightedGraph<Intersection, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        nodes.forEach(graph::addVertex);
        for(int i = 0; i < nodes.size(); i++){
            for(int j = i + 1; j < nodes.size(); j++){
                Intersection a = nodes.get(i);
                Intersection b = nodes.get(j);
                DefaultWeightedEdge edge = graph.addEdge(a, b);
                graph.setEdgeWeight(edge, a.distance(b));
            }
        }
        var t = new TwoApproxMetricTSP<Intersection, DefaultWeightedEdge>();
        var route = t.getTour(graph);
        for(Intersection inter : route.getVertexList()) {
            System.out.println(inter.getName() + " -> ");
        }
    }
}
