package edu.csudh.cs.se.p2.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import com.google.common.collect.Maps;

public class KwicRepositoryImpl implements KwicRepository{

    static{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private String insertStatement = "insert into url_desc (url, url_desc) values (?, ?)";
    private String selectAllStatement = "select url, url_desc from url_desc";
    

    public void createUrlEntry(String url, String description){
        Connection conn = getConnection();
        try{
            PreparedStatement pstmt = conn.prepareStatement(insertStatement);
            pstmt.setString(1, url);
            pstmt.setString(2, description);
            pstmt.execute();
        }catch(Exception e){
            throw new RuntimeException("Failed to add record");
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                
            }
        }
    }
    
    public Map<String, String> getAll(){
        Connection conn = getConnection();
        try{
            PreparedStatement pstmt = conn.prepareStatement(selectAllStatement);
            ResultSet rs = pstmt.executeQuery();
            Map<String, String> output = Maps.newHashMap();
            while(rs.next()){
                output.put(rs.getString(2), rs.getString(1));
            }
            return output;
        }catch(Exception e){
            throw new RuntimeException("Failed to add record");
        }finally{
            try{
                conn.close();
            }catch(Exception e){
                
            }
        }
    }
    
    private Connection getConnection(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/kwic", "kwic", "kwic");
            return conn;
        }catch(Exception e){
            throw new RuntimeException("Failed to get connection ", e);
        }
    }
}
