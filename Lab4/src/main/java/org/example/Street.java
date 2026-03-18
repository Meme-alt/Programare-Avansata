package org.example;

public class Street {
    private String name;
    private int length;
    private Intersection I1;
    private Intersection I2;
    public Street(){ }
    public Street(String name, int length, Intersection I1, Intersection I2){
        if(length < I1.distance(I2)){
            System.out.println("Error: length shorther than distance");
        }
        this.name = name;
        this.length = length;
        this.I1 = I1;
        this.I2 = I2;
    }
    public String getName(){
        return name;
    }
    public int getLength(){
        return length;
    }
    public Intersection getI1(){
        return I1;
    }
    public Intersection getI2() {
        return I2;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setI1(Intersection I1) {
        this.I1 = I1;
    }
    public void setI2(Intersection I2) {
        this.I2 = I2;
    }
    @Override
    public String toString(){
        return "Street: " + name + " Length: " + length + " Intersections: " + I1.getName() + I2.getName();
    }
}
