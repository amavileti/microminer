package edu.csudh.cs.se.p2.repository;

import java.util.Map;

import com.google.common.base.Splitter;

public class UrlRepositoryFileImpl implements UrlRepository {

    private String content;
    private static final String NEW_LINE = System.getProperty("line.separator");
    
    public UrlRepositoryFileImpl(String s){
        this.content = s;
    }
    
    public Map<String, String> loadUrls() {
        return Splitter.on(NEW_LINE).withKeyValueSeparator(",").split(content);
    }

    
}
