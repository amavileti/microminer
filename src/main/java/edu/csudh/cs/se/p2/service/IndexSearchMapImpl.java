package edu.csudh.cs.se.p2.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import edu.csudh.cs.se.p2.repository.UrlRepository;

public class IndexSearchMapImpl implements IndexSearcher {

    private Map<String, String> content;
    private UrlRepository repository;
    private StringTransformer transformer;

    public IndexSearchMapImpl(UrlRepository repository) {
        this.repository = repository;        
        transformer = new StringTransformer();
        reload();
    }

    public Map<String, String> search(final String description) {
        Map<String, String> filteredKeys = Maps.filterKeys(content, new Predicate<String>() {
            public boolean apply(String key){
                Iterable<String> descriptions = Splitter.on(Pattern.compile("\\s+")).split(description);
                List<String> searchString = ImmutableList.copyOf(descriptions);
                Collection<String> transformedCollection = Collections2.transform(searchString, transformer);
                Collection<String> filteredCollection = Collections2.filter(transformedCollection, Predicates.notNull());
                for(String eachElement : filteredCollection){
                    if(key.contains(eachElement)){
                        return true;
                    }
                }
                return false;
            }
        });
        return filteredKeys;
    }

    public void reload() {
        repository.loadUrls();
    }

    public void reload(Map<String, String> content){
        this.content = content;
    }
    
    public Map<String, String> rotate(Map<String, String> tempContent){
        throw new UnsupportedOperationException();
    }

}
