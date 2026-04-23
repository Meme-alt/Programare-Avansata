package com.example.Lab7_Springboot;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieConstraintService constraintService;
    @GetMapping
    public List<Movie> getMovies(){
        return movieRepository.findAll();
    }
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie){
        Movie saved = movieRepository.save(movie);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<Movie> updateMovie(@PathVariable Integer id, @RequestBody Movie data){
        Optional<Movie> optional = movieRepository.findById(id);
        if(optional.isPresent()){
            Movie movie = optional.get();
            movie.setTitle(data.getTitle());
            movie.setReleaseDate(data.getReleaseDate());
            movie.setDuration(data.getDuration());
            movie.setScore(data.getScore());
            Movie updated = movieRepository.save(movie);
            return ResponseEntity.ok(updated);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> updateMovieScore(@PathVariable Integer id, @RequestBody Map<String, Double> updates){
        Optional<Movie> optional = movieRepository.findById(id);
        if(optional.isPresent()){
            Movie movie = optional.get();
            if(updates.containsKey("score")){
                movie.setScore(updates.get("score"));
                movieRepository.save(movie);
                return ResponseEntity.ok(movie);
            }
            return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id){
        if(movieRepository.existsById(id)){
            movieRepository.existsById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/unrelated")
    public ResponseEntity<List<Movie>> getUnrelatedMovies(@RequestParam int min){
        List<Movie> movies = movieRepository.findAll();
        List<Movie> valid = new ArrayList<>();
        for(Movie m : movies){
            if(m.getActors() != null && !m.getActors().isEmpty()){
                valid.add(m);
            }
        }
        System.out.println("Filtru -> Total filme gasite in DB: " + movies.size());
        System.out.println("Filtru -> Filme cu actori valabili: " + valid.size());
        List<Movie> result = constraintService.findUnrelatedMovies(min, valid);
        if(result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
