package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joopk on 12/15/2015.
 */
public class MovieDataDetail implements Parcelable {


    public MovieDataDetail() {
    }

    private String id;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
    private String vote_average;
    private Trailers trailers;
    private Reviews reviews;


    /**
     * @return The id
     */
    public String getId() {
        return id;
    }


    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * @return The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return The poster_path
     */
    public String getPosterPath() {
        return poster_path;
    }

    /**
     * @param poster_path The poster_path
     */
    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * @return The release_date
     */
    public String getReleaseDate() {
        return release_date;
    }

    /**
     * @param release_date The release_date
     */
    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * @return The vote_average
     */
    public String getVoteAverage() {
        return vote_average;
    }

    /**
     * @param vote_average The vote_average
     */
    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }

    /**
     * @return The trailers
     */
    public Trailers getTrailers() {
        return trailers;
    }

    /**
     * @param trailers The trailers
     */
    public void setTrailers(Trailers trailers) {
        this.trailers = trailers;
    }

    /**
     * @return The reviews
     */
    public Reviews getReviews() {
        return reviews;
    }

    /**
     * @param reviews The reviews
     */
    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public static final Parcelable.Creator<MovieDataDetail> CREATOR = new Parcelable.Creator<MovieDataDetail>() {
        @Override
        public MovieDataDetail createFromParcel(Parcel parcel) {
            return new MovieDataDetail(parcel);
        }

        @Override
        public MovieDataDetail[] newArray(int i) {
            return new MovieDataDetail[i];
        }
    };

    private MovieDataDetail(Parcel in) {
        id = in.readString();
        overview = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        title = in.readString();
        vote_average = in.readString();
        trailers = in.readParcelable(Trailers.class.getClassLoader());
        reviews = in.readParcelable(Reviews.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(overview);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(title);
        parcel.writeString(vote_average);
        parcel.writeParcelable(trailers, i);
        parcel.writeParcelable(reviews, i);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}


