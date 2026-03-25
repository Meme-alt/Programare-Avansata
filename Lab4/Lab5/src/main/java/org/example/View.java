package org.example;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class View extends Command{
    private Resource resource;
    public View(Catalog catalog, Resource resource){
        super(catalog);
        this.resource = resource;
    }
    @Override
    public void execute() throws Exception{
        if (!Desktop.isDesktopSupported()) {
            throw new RuntimeException("Desktop is not supported");
        }
        Desktop desktop = Desktop.getDesktop();
        String location = resource.getLocation();
        if(location.startsWith("https")){
            desktop.browse(new URI(location));
        }
        else{
            File file = new File(location);
            if(!file.exists()){
                throw new Exception("File not found: " + location);
            }
            desktop.open(file);
        }
    }
}
