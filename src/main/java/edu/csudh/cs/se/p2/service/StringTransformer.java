package edu.csudh.cs.se.p2.service;

import java.util.Collection;
import java.util.regex.Pattern;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

public class StringTransformer implements Function<String, String>{

    private Function<String, String> lowerCaseFilter = new Function<String, String>() {
        public String apply(String input) {
            if(Strings.isNullOrEmpty(input)){
                return null;
            }
            return input.toLowerCase();
        }
    };

    private Function<String, String> noiseFilter = new Function<String, String>() {
        
        Collection<String> noiseWords = ImmutableList.of("a", "an", "the", "and", "or", "of", "to", "be", "is", "in", "out", "by", "as", "at", "off");
        private static final String EMPTY = "";
        

        public String apply(String input) {            
            for(String word : noiseWords){
                input = input.replaceAll(word, EMPTY);
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
    
    
    public String apply(String s){
        return transform(s);
    }
    
    public String transform(String s){
        return compositeFilter.apply(s);
    }
    
    /*public static void main(String[] args){
    Function<String, String> sr = new Function<String, String>() {
        //Pattern noiseWords = Pattern.compile("");
        Pattern noiseWords = Pattern.compile("((.*)(\\s+((a((n(d)?)?|t|s)?)?|o(r|f(f)?)|(t(o|he))?|(i(s|n))?|(b(e|y))?)?\\s+)+(.*))+");


        public String apply(String input) {
            if(Strings.isNullOrEmpty(input)){
                return input;
            }
            if (noiseWords.matcher(input).matches()) {
                return noiseWords.matcher(input).replaceAll("$2");
            }
            return null;
        }
    };
    
    System.out.println(sr.apply("this is a test"));
}*/
}
