package edu.csudh.cs.se.p2.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import edu.csudh.cs.se.p2.repository.UrlRepository;

public class IndexSearchMapImpl implements IndexSearcher {
    

    private Map<String, String> content;
    
    
    public IndexSearchMapImpl(UrlRepository repository){
        content = repository.loadUrls();
    }

    public Map<String, String> search(final String description) {
        Iterable<String> descriptions = Splitter.on(Pattern.compile("\\s+")).split(description);
        List<String> searchString = ImmutableList.copyOf(descriptions);
        Map<String, String> filteredKeys = Maps.filterKeys(content, Predicates.in(searchString));
        return filteredKeys;
    }

    
}
