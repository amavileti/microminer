package edu.csudh.cs.se.p2.service;

import java.util.Map;

public interface IndexSearcher {

    Map<String, String> search(String description);
    
    void reload();
    
    void reload(Map<String, String> values);
    
    Map<String, String> rotate(Map<String, String> tempContent);

    Map<String, String> rotate(String url, String description);
}
