package org.example;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private Integer id;
    private String name;
    private Timestamp timestamp;
    private List<Movie> movies;
    public MovieList(){ }
    public MovieList(Integer id, String name, Timestamp timestamp){
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.movies = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString(){
        return "name: " + name + "id: " + id + "created: " + timestamp + "number of movies: " + movies.size();
    }
}
