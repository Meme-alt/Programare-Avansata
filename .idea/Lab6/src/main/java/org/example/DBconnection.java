package org.example;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBconnection {
    private static HikariDataSource dataSource;
    private DBconnection(){ }
    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:postgresql://localhost:5432/lab_db");
            config.setUsername("postgres");
            config.setPassword("password123");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setAutoCommit(true);
            dataSource = new HikariDataSource(config);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        public static Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }
    public static void closePool(){
        if(dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
    }
}
