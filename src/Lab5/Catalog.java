package Lab5;

import java.awt.Desktop;
import java.util.List;
import java.util.ArrayList;
import java.net.URI;
import java.nio.file.Paths;

public class Catalog {
    private String name;
    List<Resource> resources;
    public Catalog(){ }
    public Catalog(String name){
        this.name = name;
        this.resources = new ArrayList<>();
    }
    public void addResource(Resource resource){
        resources.add(resource);
    }
    public void openResource(Resource resource){
        try{
            if(!Desktop.isDesktopSupported()){
                System.out.println("Desktop isn't supported");
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            if(resource.getLocation().startsWith("http://") || resource.getLocation().startsWith("https://")){
                desktop.browse(new URI(resource.getLocation()));
            }else{
                URI fileURI = Paths.get(resource.getLocation()).toUri();
                desktop.browse(fileURI);
            }
        }catch(Exception e){
            System.err.println("Failed ot open resource: " + resource.getTitle());
        }
    }
}
