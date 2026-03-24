package org.example;

public class List extends Command{
    public List(Catalog catalog){
        super(catalog);
    }
    @Override
    public void execute() throws Exception{
        for(Resource res : catalog.getResources()){
            System.out.println(res);
        }
    }
}
