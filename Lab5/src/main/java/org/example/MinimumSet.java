package org.example;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class MinimumSet {
    public static List<Resource> findMinimumSet(Catalog catalog){
        Set<Resource.Concept> missingConcepts = EnumSet.allOf(Resource.Concept.class);
        List<Resource> resources = new ArrayList<>(catalog.getResources());
        List<Resource> usedResources = new ArrayList<>();
        while(!missingConcepts.isEmpty()){
            Resource best = null;
            int max = 0;
            for(Resource res : resources){
                int count = 0;
                for(Resource.Concept c : res.getConcepts()){
                    if(missingConcepts.contains(c)){
                        count++;
                    }
                }
                if(count > max){
                    max = count;
                    best = res;
                }
            }
            if(best == null){
                System.out.println("Cannot cover all concepts");
                break;
            }
            usedResources.add(best);
            missingConcepts.removeAll(best.getConcepts());
            resources.remove(best);
        }
        return usedResources;
    }
}
