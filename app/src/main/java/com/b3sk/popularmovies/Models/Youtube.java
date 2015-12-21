package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Youtube {


    private String name;
    private String source;


    public Youtube() {
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

}
