package org.example;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Save extends Command {
    private String path;
    public Save(Catalog catalog, String path){
        super(catalog);
        this.path = path;
    }
    @Override
    public void execute() throws Exception{
        try(var out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(this.catalog);
            System.out.println(path);
        }
    }
}
