package org.example;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    public static void generateHTMLReport(){
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        try{
            cfg.setDirectoryForTemplateLoading(new File("src/main/java/org/example"));
            cfg.setDefaultEncoding("UTF-8");
            List<Map<String, Object>> movieList = new ArrayList<>();
            String sql = "SELECT * FROM movie_report_view";
            try(Connection con = DBconnection.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    Map<String, Object> movieData = new HashMap<>();
                    movieData.put("title", rs.getString("title"));
                    movieData.put("releaseDate", rs.getDate("release_date"));
                    movieData.put("score", rs.getDouble("score"));
                    movieData.put("genre", rs.getString("genre"));
                    movieData.put("actors", rs.getString("actors"));
                    movieList.add(movieData);
                }
            }
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("movies", movieList);
            Template template = cfg.getTemplate("report_template.html");
            try(Writer fileWriter = new FileWriter(new File("movie_report.html"))){
                template.process(templateData, fileWriter);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
