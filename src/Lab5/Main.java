package Lab5;

public class Main {
    public static void main(String[] args){
        Catalog catalog = new Catalog("Test");
        Resource test = new Resource("Desktop", "Desktop class documentation", "https://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html", 2026, "oracle");
        catalog.addResource(test);
        catalog.openResource(test);
    }
}
