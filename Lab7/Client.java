package com.example.Lab7_Springboot;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class Client {
    private final RestTemplate restTemplate = new RestTemplate(new org.springframework.http.client.JdkClientHttpRequestFactory());    private final String url = "http://localhost:8080/api/movies";
    private void fetchAndPrintMovies(){
        Movie[] movies = restTemplate.getForObject(url, Movie[].class);
        if(movies != null){
            for(Movie m : movies){
                System.out.println(m.getTitle() + "Score: " + m.getScore());
            }
        }
    }
    private void postNewMovie(Movie movie){
        restTemplate.postForObject(url, movie, Movie.class);
        System.out.println("Added: " + movie.getTitle());
    }
    private void putUpdateMovie(Integer id, Movie movie){
        restTemplate.put(url + "/" + id, movie);
        System.out.println("Updated entire movie with ID: " + id);
    }
    private void patchScore(Integer id, Double score){
        Map<String, Double> request = new HashMap<>();
        request.put("score", score);
        restTemplate.patchForObject(url + "/" + id, request, Void.class);
        System.out.println("Updated score for ID " + id + " to " + score);
    }
    private void deleteMovie(Integer id){
        restTemplate.delete(url + "/" + id);
        System.out.println("Delete Movie with ID: " + id);
    }
    public void runClient(){ }
    private Integer findMovieByTitle(String title){
        Movie[] movies = restTemplate.getForObject(url, Movie[].class);
        if(movies != null){
            for(Movie movie : movies){
                if(movie.getTitle().equalsIgnoreCase(title)){
                    return movie.getId();
                }
            }
        }
        return null;
    }
}
