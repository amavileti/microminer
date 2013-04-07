package edu.csudh.cs.se.p2.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

import edu.csudh.cs.se.p2.repository.UrlRepository;

@RunWith(MockitoJUnitRunner.class)
public class TestIndexSearchMap {

    private IndexSearchMapImpl impl;
    
    @Mock private UrlRepository urlRepository;
    Map<String, String> sourceData;
    
    @Before
    public void setUp(){        
        sourceData = Maps.newHashMap();
        when(urlRepository.loadUrls()).thenReturn(sourceData);
        impl = new IndexSearchMapImpl(urlRepository);
    }
    
   
    @Test
    public void searchCase1(){
        sourceData.put("searchString", "http://www.csudh.edu");
        impl.reload();
        Map<String, String> result = impl.search("searchString");
        assertNotNull(result);
        assertTrue(result.size() == 1);
        assertTrue(result.containsKey("searchString"));
    }
    
    @Test
    public void noiseWordSearch(){
        sourceData.put("an searchString", "http://www.csudh.edu");
        impl.reload();        
        Map<String, String> result = impl.search("an");
        assertNotNull(result);
        assertTrue(result.size() == 0);
    }
    
    @Test
    public void specialKeywordSearch(){
        sourceData.put("$W searchString", "http://www.csudh.edu");
        impl.reload();
        Map<String, String> result = impl.search("$W");
        assertNotNull(result);
        assertTrue(result.size() == 0);
    }
}
