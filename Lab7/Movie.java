package com.example.Lab7_Springboot;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.sql.Date;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @Column(name = "release_date")
    private Date releaseDate;
    private Integer duration;
    private Double score;
    public Integer getId(){ return id; }
    public String getTitle(){ return title; }
    public Date getReleaseDate(){ return releaseDate; }
    public Integer getDuration(){ return duration; }
    public Double getScore(){ return score; }
    public void setId(Integer id){ this.id = id; }
    public void setTitle(String title){ this.title = title; }
    public void setReleaseDate(Date releaseDate){ this.releaseDate = releaseDate; }
    public void setDuration(Integer duration){ this.duration = duration; }
    public void setScore(Double score){ this.score = score; }
}
