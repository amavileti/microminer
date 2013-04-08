package edu.csudh.cs.se.p2.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import edu.csudh.cs.se.p1.applet.KWRotator;
import edu.csudh.cs.se.p2.repository.UrlRepository;

public class IndexSearchMapImplKw implements IndexSearcher{

    private Map<Collection<String>, String> content;
    private UrlRepository repository;
    private StringTransformer transformer;
    private KWRotator rotator;
    private Pattern spacePattern = Pattern.compile("\\s+");
    private static final String SPACE = " ";
    private Map<String, String> kwicValues;

    public IndexSearchMapImplKw(UrlRepository repository, KWRotator rotator) {
        this.repository = repository;                
        this.rotator = rotator;
        transformer = new StringTransformer();
        kwicValues = Maps.newHashMap();
        reload();
    }

    
    public void reload(){
        Map<String, String> tempContent = repository.loadUrls();
        content = Maps.newHashMap();
        for(Entry<String, String> entry: tempContent.entrySet()){
            String transformedKey = transformer.apply(entry.getKey());
            if(!Strings.isNullOrEmpty(transformedKey)){
                List<String> splitKeys = ImmutableList.copyOf(Splitter.on(spacePattern).split(transformedKey));
                content.put(splitKeys, entry.getValue());
                Collection<String> rotatedValues = rotator.rotate(transformedKey);
                for(String s: rotatedValues){
                    kwicValues.put(s, entry.getValue());
                }
            }
        }
    }
    
    
    public Map<String, String> search(final String description) {
        Map<Collection<String>, String> filteredKeys = Maps.filterKeys(content, new Predicate<Collection<String>>() {
            public boolean apply(Collection<String> key){
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
        Map<String, String> returnMap = Maps.newHashMap();
        for(Entry<Collection<String> , String> entry: filteredKeys.entrySet()){
            returnMap.put(Joiner.on(SPACE).join(entry.getKey()), entry.getValue());
        }
        return returnMap;
    }
    
}
