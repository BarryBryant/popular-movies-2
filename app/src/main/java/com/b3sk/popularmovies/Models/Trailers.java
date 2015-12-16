package com.b3sk.popularmovies.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Trailers {

    private List<Youtube> youtube = new ArrayList<Youtube>();

    /**
     * @return The youtube
     */
    public List<Youtube> getYoutube() {
        return youtube;
    }

    /**
     * @param youtube The youtube
     */
    public void setYoutube(List<Youtube> youtube) {
        this.youtube = youtube;
    }


}
