package org.example;

public abstract class Command {
    protected Catalog catalog;
    public Command(){ }
    public Command(Catalog catalog){
        this.catalog = catalog;
    }
    public abstract void execute() throws Exception;
}
