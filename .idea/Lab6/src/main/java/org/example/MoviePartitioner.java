package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class MoviePartitioner {
    static class MovieNode{
        int id;
        String title;
        Set<String> actors = new HashSet<>();
        public MovieNode(int id, String title){
            this.id = id;
            this.title = title;
        }
    }
    public static List<MovieNode> loadMoviesAndActors() throws Exception{
        List<MovieNode> nodes = new ArrayList<>();
        String sql = "SELECT m.id, m.title, v.actors FROM movies m JOIN movie_report_view v ON m.title = v.title";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery()){
            while(rs.next()){
                MovieNode node = new MovieNode(rs.getInt("id"), rs.getString("title"));
                String actorString = rs.getString("actors");
                if(!actorString.equals("No actors listed")){
                    node.actors.addAll(Arrays.asList(actorString.split(", ")));
                }
                nodes.add(node);
            }
        }
        return nodes;
    }
    public static boolean hasConflict(MovieNode newMovie, List<MovieNode> list){
        for(MovieNode Movie : list){
            for(String actor : newMovie.actors){
                if(Movie.actors.contains(actor)){
                    return true;
                }
            }
        }
        return false;
    }
    private static List<List<MovieNode>> attemptPartition(List<MovieNode> movies, int k) {
        List<List<MovieNode>> lists = new ArrayList<>();
        for(int i = 0; i < k; i++){
            lists.add(new ArrayList<>());
        }
            int maxCapacity = (int) Math.ceil((double) movies.size() / k);
            for(MovieNode movie : movies){
                boolean placed = false;
                for(List<MovieNode> list : lists){
                    if(list.size() < maxCapacity && !hasConflict(movie, list)){
                        list.add(movie);
                        placed = true;
                        break;
                    }
                }
                if(!placed){
                    return null;
                }
            }
        return lists;
    }
    public static List<List<MovieNode>> partitionMovies() throws Exception {
        List<MovieNode> movies = loadMoviesAndActors();
        int totalMovies = movies.size();
        for(int k = 1; k <= totalMovies; k++){
            List<List<MovieNode>> partitions = attemptPartition(movies, k);
            if(partitions != null){
                return partitions;
            }
        }
        return new ArrayList<>();
    }
}
