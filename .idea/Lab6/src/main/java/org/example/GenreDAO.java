package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class GenreDAO {
    public void create(String name) throws SQLException {
        Connection con = DBconnection.getConnection();
        try(PreparedStatement pstmt = con.prepareStatement("insert into genres (name) values (?)")){
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }
    public Integer findByName(String name) throws SQLException {
        Connection con = DBconnection.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select id from genres where name = ?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
    }
    public String findById(int id) throws SQLException {
        Connection con = DBconnection.getConnection();
        try (PreparedStatement pstmt = con.prepareStatement("select name from genres where id = ?")) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? rs.getString(1) : null;
            }
        }
    }
}
