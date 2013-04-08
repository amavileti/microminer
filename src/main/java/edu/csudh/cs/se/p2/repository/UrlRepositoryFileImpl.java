package edu.csudh.cs.se.p2.repository;

import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;

public class UrlRepositoryFileImpl implements UrlRepository {

    private String content;
    private Pattern csvPattern = Pattern.compile("(\\s*)?,(\\s+)?");
    
    private static final String NEW_LINE = System.getProperty("line.separator");
    
    public UrlRepositoryFileImpl(String s){
        setContent(s);
    }
    
    public Map<String, String> loadUrls() {
        return Splitter.on(NEW_LINE).withKeyValueSeparator(
                Splitter.on(csvPattern)).split(content.toLowerCase());
    }
    
    public void setContent(String s){
        this.content = s;
    }
}
