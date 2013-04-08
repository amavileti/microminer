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

/***
 * Service class that 
 * 1. loads indexable content, 
 * 2. caches those indexes in memory
 * 3. Accepts user search string and scans indexes
 * 4. Returns acceptable index lists along with their urls
 * @author amavileti1
 *
 */
public class IndexSearchMapImplKw implements IndexSearcher{

    //Used to store indexable content
    private Map<Collection<String>, String> content;
    
    //Indexable content reader
    private UrlRepository repository;
    
    //Filter thats used to remove noise, special words
    private StringTransformer transformer;
    
    //KWIC+ rotator
    private KWRotator rotator;
    
    //Constant values
    private Pattern spacePattern = Pattern.compile("\\s+");
    private static final String SPACE = " ";
    private Map<String, String> kwicValues;

    
    public IndexSearchMapImplKw(UrlRepository repository, KWRotator rotator) {
        this.repository = repository;                
        this.rotator = rotator;
        transformer = new StringTransformer();
        kwicValues = Maps.newHashMap();
        reload(); //Index content at applet startup
    }

    /**
     * Index loader, and on demand loader
     * System loads urls at applet startup, and on demand
     * For each entry <description, url>, 
     * 1. Filter out special characters in description
     * 2. Filter keys are further split using space character into a list of string
     * 3. Map list of strings to urls
     * 4. Rotate transformed keys using KWIC+ system, and store in-memory
     */
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
    
    
    /**
     * Accept a string input, and return a <code>Map<String,String></code> mapping
     * of description to url
     * For each user input, 
     * 1. Sanitize user input by removing unwanted characters/words
     * 2. Split user input into (possibly) multiple strings (collection of strings)
     * 3. For each of the user input, if the system in-memory index has a reference
     *    to that keyword, pick it
     * 4. Join the split keywords using single space, and return it to caller
     */
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
