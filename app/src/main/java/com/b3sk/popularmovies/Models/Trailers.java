package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Trailers implements Parcelable {


    public Trailers() {}

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

    public static final Parcelable.Creator<Trailers> CREATOR = new Parcelable.Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel parcel) {
            return new Trailers(parcel);
        }

        @Override
        public Trailers[] newArray(int i) {
            return new Trailers[i];
        }
    };


    private Trailers(Parcel in) {
        in.readList(youtube, this.getClass().getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(youtube);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
