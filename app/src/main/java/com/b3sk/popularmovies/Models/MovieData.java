package com.b3sk.popularmovies.Models;

/**
 * Created by Joopk on 12/9/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MovieData {

    private Integer page;
    private List<MovieInfo> results = new ArrayList<MovieInfo>();
    private Integer totalResults;
    private Integer totalPages;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    /**
     *
     * @return
     * The results
     */
    public List<MovieInfo> getResults() {
        return results;
    }



}

