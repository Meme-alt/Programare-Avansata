package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            var genreDao = new GenreDAO();
            genreDao.create("Action");
            genreDao.create("Thriller");
            DBconnection.getConnection().commit();
            var genreId = genreDao.findByName("Action");
            System.out.println(genreId);
            DBconnection.getConnection().close();
        }catch(SQLException e){
            System.err.println(e);
        }
    }
}
