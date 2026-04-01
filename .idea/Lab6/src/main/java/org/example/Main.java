package org.example;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            var genreDao = new GenreDAO();
            var actorDao = new ActorDAO();
            var movieDao = new MovieDAO();
            genreDao.create("Sci-Fi");
            Integer sciFiId = genreDao.findByName("Sci-Fi");
            System.out.println(sciFiId);
            actorDao.create("Keanu Reeves");
            Integer keanuId = actorDao.findByName("Keanu Reeves");
            System.out.println(keanuId);
            movieDao.create("The Matrix", Date.valueOf("1999-03-31"), 136, 8.7, sciFiId);
            Integer matrixId = movieDao.findByName("The Matrix");
            System.out.println(matrixId);
            movieDao.addActorToMovie(matrixId, keanuId);
            ReportGenerator.generateHTMLReport();
        }catch(SQLException e){
            System.err.println(e);
            }finally{
                DBconnection.closePool();
            }
    }
}
