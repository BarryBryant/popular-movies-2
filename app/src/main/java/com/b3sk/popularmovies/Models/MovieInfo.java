package com.b3sk.popularmovies.Models;

/**
 * Created by Joopk on 12/9/2015.
 */
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Boolean adult;
    private String overview;
    private String release_date;
    private List<Object> genreIds = new ArrayList<Object>();
    private String id;
    private String original_title;
    private String originalLanguage;
    private String title;
    private Object backdropPath;
    private Double popularity;
    private Integer voteCount;
    private Boolean video;
    private String vote_average;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The posterPath
     */
    public String getPosterPath() {
        return poster_path;
    }



    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }


    /**
     *
     * @return
     * The releaseDate
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }


    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }


    /**
     *
     * @return
     * The voteAverage
     */
    public String getVoteAverage() {
        return vote_average;
    }



    private MovieInfo(Parcel in) {
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        id = in.readString();
        title = in.readString();
        vote_average = in.readString();

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

