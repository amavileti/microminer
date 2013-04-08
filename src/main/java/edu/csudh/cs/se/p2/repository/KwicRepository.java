package edu.csudh.cs.se.p2.repository;

import java.util.Map;

public interface KwicRepository {

    void createUrlEntry(String url, String description);
    
    Map<String, String> getAll();
}
