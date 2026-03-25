package org.example;

public class Main {
    public static void main(String[] args){
        Catalog catalog = new Catalog("Test");
        Resource resource = new Resource("123", "desktop", "https://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html", 2026, "oracle");
        Resource resource1 = new Resource("888", "template", "https://en.wikipedia.org/wiki/Web_template_system", 2026, "wikipedia");
        resource.addConcepts(Resource.Concept.OBJECT_ORIENTED_PROGRAMMING);
        resource.addConcepts(Resource.Concept.ALGORITHM_DESIGN_TECHNIQUES);
        resource1.addConcepts(Resource.Concept.WEB_DEVELOPMENT);
        resource1.addConcepts(Resource.Concept.ARCHITECTURES);
        catalog.addResource(resource);
        catalog.addResource(resource1);
        try{
            new Save(catalog, "catalog.dat").execute();
            new Load(catalog, "catalog.dat").execute();
            System.out.println(catalog.getResources().get(0).getTitle());
            new List(catalog).execute();
            new Report(catalog).execute();
        }catch(Exception e){
            System.err.println("There has been an error: " + e.getMessage());
        }
        RandomRun.run(1000);
    }
}
