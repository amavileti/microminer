package edu.csudh.cs.se.p2.applet;

import edu.csudh.cs.se.p1.applet.KWRotator;
import edu.csudh.cs.se.p2.repository.UrlRepository;
import edu.csudh.cs.se.p2.repository.UrlRepositoryFileImpl;
import edu.csudh.cs.se.p2.service.IndexSearchMapImplKw;
import edu.csudh.cs.se.p2.service.IndexSearcher;

public final class MicrominerContainer {

    private static UrlRepository repository;
    
    private static KWRotator rotator;    
    
    private static IndexSearcher searcher;
    
    private MicrominerContainer(){
        repository = new UrlRepositoryFileImpl("");
        rotator = new KWRotator();
        searcher = new IndexSearchMapImplKw(repository, rotator);
    }
    
    public static UrlRepository getRepository(){
        return repository;
    }
    
    
    public static KWRotator getRotator(){
        return rotator;
    }
    
    
    public static IndexSearcher getSearcher(){
        return searcher;
    }
}
