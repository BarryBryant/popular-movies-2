package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joopk on 12/15/2015.
 */
public class Youtube implements Parcelable {


    public static final Parcelable.Creator<Youtube> CREATOR = new Parcelable.Creator<Youtube>() {
        @Override
        public Youtube createFromParcel(Parcel parcel) {
            return new Youtube(parcel);
        }

        @Override
        public Youtube[] newArray(int i) {
            return new Youtube[i];
        }
    };
    private String name;
    private String source;


    public Youtube() {
    }

    private Youtube(Parcel in) {
        name = in.readString();
        source = in.readString();
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(source);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
