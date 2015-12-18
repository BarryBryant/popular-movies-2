package com.b3sk.popularmovies.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Joopk on 12/17/2015.
 */
public class RealmMovie extends RealmObject {

    @PrimaryKey
    private Integer id;

    private String overview;
    private String posterPath;
    private String releaseDate;
    private String title;
    private String voteAverage;
    private RealmList<RealmTrailer> trailers;
    private RealmList<RealmReview> reviews;


    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }


    /**
     * @param id The id
     */
    public void setId(Integer id) {
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
        return posterPath;
    }

    /**
     * @param poster_path The poster_path
     */
    public void setPosterPath(String poster_path) {
        this.posterPath = poster_path;
    }

    /**
     * @return The release_date
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param release_date The release_date
     */
    public void setReleaseDate(String release_date) {
        this.releaseDate = release_date;
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
        return voteAverage;
    }

    /**
     * @param vote_average The vote_average
     */
    public void setVoteAverage(String vote_average) {
        this.voteAverage = vote_average;
    }

    public RealmList<RealmTrailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(RealmList<RealmTrailer> trailers) {
        this.trailers = trailers;
    }

    public RealmList<RealmReview> getReviews() {
        return reviews;
    }

    public void setReviews(RealmList<RealmReview> reviews) {
        this.reviews = reviews;
    }
}
