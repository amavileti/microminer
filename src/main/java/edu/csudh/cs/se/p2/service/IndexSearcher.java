package edu.csudh.cs.se.p2.service;

import java.util.Map;

public interface IndexSearcher {

    Map<String, String> search(String description);
    
    void reload();
    
    void reload(Map<String, String> values);
}
