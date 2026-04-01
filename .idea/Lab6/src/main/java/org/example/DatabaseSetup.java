package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void createTable(){
        String url = "jdbc:postgresql://localhost:5432/lab_db";
        String user = "postgres";
        String password = "password123";
        String sql = """
            DROP TABLE IF EXISTS movie_actors CASCADE;
            DROP TABLE IF EXISTS movies CASCADE;
            DROP TABLE IF EXISTS actors CASCADE;
            DROP TABLE IF EXISTS genres CASCADE;
            DROP VIEW IF EXISTS movie_report_view CASCADE;

            CREATE TABLE genres (
                id SERIAL PRIMARY KEY,
                name VARCHAR(50) NOT NULL UNIQUE
            );

            CREATE TABLE movies (
                id SERIAL PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                release_date DATE,
                duration INTEGER,
                score NUMERIC(3, 1),
                genre_id INTEGER REFERENCES genres(id) 
            );

            CREATE TABLE actors (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL
            );

            CREATE TABLE movie_actors (
                movie_id INTEGER REFERENCES movies(id) ON DELETE CASCADE,
                actor_id INTEGER REFERENCES actors(id) ON DELETE CASCADE,
                PRIMARY KEY (movie_id, actor_id)
            );

            -- THIS IS THE NEW PART! It builds the view Java is looking for.
            CREATE OR REPLACE VIEW movie_report_view AS
            SELECT 
                m.title,
                m.release_date,
                m.score,
                g.name AS genre,
                COALESCE(string_agg(a.name, ', '), 'No actors listed') AS actors
            FROM movies m
            LEFT JOIN genres g ON m.genre_id = g.id
            LEFT JOIN movie_actors ma ON m.id = ma.movie_id
            LEFT JOIN actors a ON ma.actor_id = a.id
            GROUP BY m.id, m.title, m.release_date, m.score, g.name
            ORDER BY m.score DESC;
        """;
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            System.out.println("Success");
            connection.close();
        }catch(SQLException e){
            System.err.println("Failed");
            e.printStackTrace();
        }
    }
}
