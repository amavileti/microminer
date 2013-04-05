package edu.csudh.cs.se.p2.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

public class TestUrlRepositoryFileImpl {

    String content = "url1,http://www.csudh.edu\n" +
    		"url2,http://www.google.com";
    
    private UrlRepositoryFileImpl repository;
    
    @Before
    public void setUp(){
        repository = new UrlRepositoryFileImpl(content);
    }
   @Test
   public void loadUrls(){
       Map<String, String> result = repository.loadUrls();
       assertNotNull(result);
       assertTrue(result.size() == 2);
       assertEquals(result.keySet(), Sets.newHashSet("url1", "url2"));
   }

}
