package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class DataImporter {
    public static void ImportMovies(String path){
        GenreDAO genreDAO = new GenreDAO();
        MovieDAO movieDAO = new MovieDAO();
        ActorDAO actorDAO = new ActorDAO();
        try(CSVReader reader = new CSVReader(new FileReader(path))){
            String[] line;
            reader.readNext();
            while((line = reader.readNext()) != null){
                String title = line[0];
                Date releaseDate = Date.valueOf(line[1]);
                Integer duration = Integer.parseInt(line[2]);
                Double score = Double.parseDouble(line[3]);
                String genres = line[4];
                String genre = genres.split("\\|")[0];
                Integer genreId = genreDAO.findByName(genre);
                if(genreId == null){
                    genreDAO.create(genre);
                    genreId = genreDAO.findByName(genre);
                }
                movieDAO.create(title, releaseDate, duration, score, genreId);
                Integer movieId = movieDAO.findByName(title);
                if(line.length > 5 && !line[5].isEmpty()){
                    String[] actors = line[5].split("\\|");
                    for(String actorName : actors){
                        Integer actorId = actorDAO.findByName(actorName);
                        if(actorId == null){
                            actorDAO.create(actorName);
                            actorId = actorDAO.findByName(actorName);
                        }
                        movieDAO.addActorToMovie(movieId, actorId);
                    }
                }
            }
            System.out.println("Imported movies");
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
