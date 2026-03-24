package org.example;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Load extends Command {
    private String path;
    public Load(Catalog catalog, String path){
        super(catalog);
        this.path = path;
    }
    @Override
    public void execute() throws Exception{
        try(var in = new ObjectInputStream(new FileInputStream(path))){
            Catalog loaded_catalog = (Catalog) in.readObject();
            this.catalog.getResources().clear();
            this.catalog.getResources().addAll(loaded_catalog.getResources());
        }
    }
}
