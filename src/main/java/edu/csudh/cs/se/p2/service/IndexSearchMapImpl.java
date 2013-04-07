package edu.csudh.cs.se.p2.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import edu.csudh.cs.se.p2.repository.UrlRepository;

public class IndexSearchMapImpl implements IndexSearcher {

    private Map<String, String> content;
    private UrlRepository repository;

    public IndexSearchMapImpl(UrlRepository repository) {
        this.repository = repository;
        content = repository.loadUrls();
    }

    public Map<String, String> search(final String description) {
        Map<String, String> filteredKeys = Maps.filterKeys(content, new Predicate<String>() {
            public boolean apply(String key){
                Iterable<String> descriptions = Splitter.on(Pattern.compile("\\s+")).split(description);
                List<String> searchString = ImmutableList.copyOf(descriptions);
                Collection<String> transformedCollection = Collections2.transform(searchString, compositeFilter);
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

    private Function<String, String> lowerCaseFilter = new Function<String, String>() {
        public String apply(String input) {
            return input.toLowerCase();
        }
    };

    private Function<String, String> noiseFilter = new Function<String, String>() {
        Collection<String> noiseWords = ImmutableList.of("a", "an", "the", "and", "or", "of", "to", "be", "is", "in", "out", "by", "as", "at", "off");

        public String apply(String input) {
            if (Strings.isNullOrEmpty(input) || noiseWords.contains(input)) {
                return null;
            }
            return input;
        }
    };

    private Function<String, String> specialCharactersFilter = new Function<String, String>() {
        Pattern regex = Pattern.compile("[$&+,:;=?@#|]");

        public String apply(String input) {
            if (Strings.isNullOrEmpty(input) || regex.matcher(input).matches()) {
                return null;
            }
            return input;
        }
    };

    private Function<String, String> compositeFilter = Functions.compose(lowerCaseFilter, Functions.compose(noiseFilter, specialCharactersFilter));
}
