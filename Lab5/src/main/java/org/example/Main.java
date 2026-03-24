package org.example;

public class Main {
    public static void main(String[] args){
        Catalog catalog = new Catalog("Test");
        catalog.addResource(new Resource("123", "desktop", "https://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html", 2026, "oracle"));
        catalog.addResource(new Resource("888", "template", "https://en.wikipedia.org/wiki/Web_template_system", 2026, "wikipedia"));
        try{
            new Save(catalog, "catalog.dat").execute();
            new Load(catalog, "catalog.dat").execute();
            System.out.println(catalog.getResources().get(0).getTitle());
            new List(catalog).execute();
            new Report(catalog).execute();
        }catch(Exception e){
            System.err.println("There has been an error: " + e.getMessage());
        }
    }
}
