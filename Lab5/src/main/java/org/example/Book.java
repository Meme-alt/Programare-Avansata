package org.example;

public class Book extends Resource{
    public Book(){ }
    public Book(String id, String title, String location, int year, String author){
        super(id, title, location, year, author);
    }
}
