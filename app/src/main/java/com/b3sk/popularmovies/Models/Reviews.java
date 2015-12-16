package com.b3sk.popularmovies.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Reviews {
    private Integer page;
    private List<ReviewResult> reviewResults = new ArrayList<ReviewResult>();


    /**
     * @return The reviewResults
     */
    public List<ReviewResult> getReviewResults() {
        return reviewResults;
    }

    /**
     * @param reviewResults The reviewResults
     */
    public void setReviewResults(List<ReviewResult> reviewResults) {
        this.reviewResults = reviewResults;
    }


}
