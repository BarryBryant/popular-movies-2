package com.b3sk.popularmovies.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Reviews {


    private List<ReviewResult> results = new ArrayList<ReviewResult>();


    public Reviews() {
    }


    /**
     * @return The results
     */
    public List<ReviewResult> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<ReviewResult> results) {
        this.results = results;
    }


}
