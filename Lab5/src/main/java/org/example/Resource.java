package org.example;

import java.io.Serializable;

public class Resource implements Serializable {
    private String id;
    private String title;
    private String location;
    private int year;
    private String author;
    public Resource(){ }
    public Resource(String id, String title, String location, int year, String author){
        this.id = id;
        this.title = title;
        this.location = location;
        this.year = year;
        this.author = author;
    }
    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getLocation(){
        return location;
    }
    public int getYear(){
        return year;
    }
    public String getAuthor(){
        return author;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setYear(int year){
        this.year = year;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    @Override
    public String toString(){
        return "id: " + id + " title: " + title + " location: " + location + " year: " + year + "  author: " + author;
    }
}
