package edu.csudh.cs.se.p2.repository;

import java.util.Map;

public interface UrlRepository {

    Map<String, String> loadUrls();
    
    void setContent(String s);
}
