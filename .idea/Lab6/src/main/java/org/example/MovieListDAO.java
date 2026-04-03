package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieListDAO {
    public Integer createList(String name) throws SQLException{
        String sql = "INSERT INTO movie_lists (name) VALUES (?) RETURNING id";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }
        return null;
    }
    public void addMovieToList(Integer listId, Integer MovieId){
        String sql = "INSERT INTO list_movies (list_id, movie_id) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try(Connection con = DBconnection.getConnection();
        PreparedStatement pstmt = con.prepareStatement(sql)){
            pstmt.setInt(1, listId);
            pstmt.setInt(2, MovieId);
            pstmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
