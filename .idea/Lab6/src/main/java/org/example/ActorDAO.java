package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ActorDAO {
    public void create(String name) throws SQLException{
        try(Connection con = DBconnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement("insert into actors (name) values (?)")){
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException{
        try (Connection con = DBconnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement("select id from actors where name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }
    public String findById(int id) throws SQLException{
        try (Connection con = DBconnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement("select name from actors where id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }
}
