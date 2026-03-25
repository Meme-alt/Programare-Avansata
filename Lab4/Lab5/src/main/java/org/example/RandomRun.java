package org.example;

import java.util.Random;

public class RandomRun {
    public static void run(int nr){
        Catalog catalog = new Catalog("Random Catalog");
        Random random = new Random();
        Resource.Concept[] allConcepts = Resource.Concept.values();
        for(int i = 0; i < nr; i++){
            Resource res = new Resource("id" + i, "Resource " + i, "https://test.com", 2026, "Random");
            int nrConcepts = random.nextInt(3) + 1;
            for(int j = 0; j < nrConcepts; j++){
                Resource.Concept randomConcept = allConcepts[random.nextInt(allConcepts.length)];
                res.addConcepts(randomConcept);
            }
            catalog.addResource(res);
        }
        long start = System.currentTimeMillis();
        var solution = MinimumSet.findMinimumSet(catalog);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(solution);
    }
}
