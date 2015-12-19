package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joopk on 12/9/2015.
 */
public class MovieInfo implements Parcelable {

    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int i) {
            return new MovieInfo[i];
        }
    };

    private String poster_path;

    private String overview;
    private String release_date;
    private String id;
    private String title;
    private String vote_average;

    private MovieInfo(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readString();
        title = in.readString();
        vote_average = in.readString();
    }

    public MovieInfo(){}

    /**
     * @return The posterPath
     */
    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path){
        this.poster_path = poster_path;
    }

    /**
     * @return The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @return The releaseDate
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    public void setId(String id){this.id = id;}

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The voteAverage
     */
    public String getVoteAverage() {
        return vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(vote_average);
    }

}

