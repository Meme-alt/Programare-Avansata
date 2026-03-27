package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    private static Connection connection = null;
    private static final String url = "jdbc:postgresql://localhost:5432/lab_db";
    private static final String user = "postgres";
    private static final String password = "password123";
    private DBconnection(){ }
    public static void createConnection(){
        try {
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            System.out.println("Connection created");
        }catch(Exception e){
            System.err.println("Failed to create");
        }
    }
    public static Connection getConnection(){
        try{
            if(connection == null || connection.isClosed()){
                createConnection();
            }
        }catch(SQLException e){
            System.err.println("Failed to connect");
        }
        return connection;
    }
    public static void closeConnection(){
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Success closing");
            }
        }catch(SQLException e){
            System.err.println("Error ar closing");
        }
    }
}
