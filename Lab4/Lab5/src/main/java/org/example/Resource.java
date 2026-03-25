package org.example;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Resource implements Serializable {
    private String id;
    private String title;
    private String location;
    private int year;
    private String author;
    private Set<Concept> concepts;
    public Resource(){ }
    public Resource(String id, String title, String location, int year, String author){
        this.id = id;
        this.title = title;
        this.location = location;
        this.year = year;
        this.author = author;
        this.concepts = new HashSet<>();
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
    public Set<Concept> getConcepts(){ return concepts; }
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
    public void SetConcepts(Set<Concept> concepts){ this.concepts = concepts; }
    public void addConcepts(Concept concept){ this.concepts.add(concept); }
    @Override
    public String toString(){
        return "id: " + id + " title: " + title + " location: " + location + " year: " + year + "  author: " + author + " concepts: " + concepts + "\n";
    }
    public enum Concept{
        GRAPH_THEORY,
        NEURAL_NETWORKS,
        ALGORITHM_DESIGN_TECHNIQUES,
        OBJECT_ORIENTED_PROGRAMMING,
        ARCHITECTURES,
        COMPILERS,
        CRYPTOGRAPHY,
        WEB_DEVELOPMENT
    }
}
