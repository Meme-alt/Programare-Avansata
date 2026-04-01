package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class MovieDAO {
    public void create(String name, Date releaseDate, Integer duration, Double score, Integer genreId) throws SQLException{
        String sql = "insert into movies (title, release_date, duration, score, genre_id) values (?, ?, ?, ?, ?)";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setString(1, name);
            pstmt.setDate(2, releaseDate);
            pstmt.setInt(3, duration);
            pstmt.setDouble(4, score);
            pstmt.setInt(5, genreId);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException{
        String sql = "select id from movies where title = ?";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setString(1, name);
            try(ResultSet rs = pstmt.executeQuery()){
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }
    public void addActorToMovie(int movieId, int actorId) throws SQLException{
        String sql = "insert into movie_actors (movie_id, actor_id) values (?, ?) ON CONFLICT DO NOTHING";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1,movieId);
            pstmt.setInt(2,actorId);
            pstmt.executeUpdate();
        }
    }
}
