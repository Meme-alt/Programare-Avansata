package org.example;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private Integer id;
    private String name;
    private Date releaseDate;
    private Integer duration;
    private double score;
    private Genre genre;
    private List<Actor> actors;
    public Movie(){
        this.actors = new ArrayList<>();
    }
    public Movie(Integer id, String name, Date releaseDate, Integer duration, double score, Genre genre){
        this.id = id;
        this.name = name;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genre = genre;
        this.actors = new ArrayList<>();
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getReleaseDate() {
        return releaseDate;
    }
    public double getScore() {
        return score;
    }
    public Genre getGenre() {
        return genre;
    }
    public List<Actor> getActors() {
        return actors;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getDuration() {
        return duration;
    }
    public void setDuration(Integer duration) {
        this.duration = duration;
    }
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    public void setScore(double score) {
        this.score = score;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
    public void addActor(Actor actor){
        this.actors.add(actor);
    }
    @Override
    public String toString(){
        return "Movie{title='" + name + "', score=" + score + ", genre=" + (genre != null ? genre.getName() : "None") + "}";
    }
}
