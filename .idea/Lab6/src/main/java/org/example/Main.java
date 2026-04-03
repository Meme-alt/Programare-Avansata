package org.example;

import org.flywaydb.core.Flyway;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Flyway flyway = Flyway.configure().dataSource(DBconnection.getDataSource()).baselineOnMigrate(true).load();
            flyway.migrate();
            try (Connection con = DBconnection.getConnection()) {
                con.createStatement().execute("TRUNCATE TABLE movies, genres, actors, movie_lists CASCADE");
            }
            DataImporter.ImportMovies("src/main/java/org/example/movies.csv");
            MovieList myFavorites = new MovieList();
            myFavorites.setName("My All-Time Favorites");
            MovieListDAO listDao = new MovieListDAO();
            Integer listId = listDao.createList(myFavorites.getName());
            if (listId != null) {
                listDao.addMovieToList(listId, 1);
            }
            ReportGenerator.generateHTMLReport();
            List<List<MoviePartitioner.MovieNode>> balancedLists = MoviePartitioner.partitionMovies();
            MovieListDAO listDAO = new MovieListDAO();
            int nr = 1;
            for (List<MoviePartitioner.MovieNode> partition : balancedLists) {
                String listName = "Parition number-" + nr;
                Integer PartitionListId = listDao.createList(listName);
                System.out.println("Created: " + listName + " (Size: " + partition.size() + ")");
                if(PartitionListId != null){
                    for (MoviePartitioner.MovieNode movie : partition) {
                        listDao.addMovieToList(PartitionListId, movie.id);
                        System.out.println("  -> Added: " + movie.title);
                    }
                }
                nr++;
            }
        }catch(Exception e){
            System.err.println(e);
            }finally{
                DBconnection.closePool();
            }
    }
}
