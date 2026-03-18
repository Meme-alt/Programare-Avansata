package org.example;

public class Intersection {
    private String name;
    private int x;
    private int y;
    public Intersection(){ }
    public Intersection(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public double distance(Intersection other){
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
    @Override
    public boolean equals(Object a){
        if(this == a){
            return true;
        }
        if(a == null || getClass() != a.getClass()){
            return false;
        }
        Intersection that = (Intersection) a;
        return java.util.Objects.equals(name, that.name);
    }
    @Override
    public int hashCode(){
        return java.util.Objects.hash(name);
    }
}
