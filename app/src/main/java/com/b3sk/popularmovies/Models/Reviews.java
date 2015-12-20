package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Reviews implements Parcelable {


    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel parcel) {
            return new Reviews(parcel);
        }

        @Override
        public Reviews[] newArray(int i) {
            return new Reviews[i];
        }
    };
    private List<ReviewResult> results = new ArrayList<ReviewResult>();


    public Reviews() {
    }

    private Reviews(Parcel in) {
        in.readList(results, this.getClass().getClassLoader());
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
