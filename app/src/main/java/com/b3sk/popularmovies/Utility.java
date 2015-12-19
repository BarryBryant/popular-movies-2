package com.b3sk.popularmovies;

import android.util.Log;

import com.b3sk.popularmovies.Models.MovieDataDetail;
import com.b3sk.popularmovies.Models.RealmMovie;
import com.b3sk.popularmovies.Models.RealmReview;
import com.b3sk.popularmovies.Models.RealmTrailer;
import com.b3sk.popularmovies.Models.ReviewResult;
import com.b3sk.popularmovies.Models.Reviews;
import com.b3sk.popularmovies.Models.Trailers;
import com.b3sk.popularmovies.Models.Youtube;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Joopk on 12/17/2015.
 */
public class Utility {

    public static RealmMovie movieDataDetailToRealm(MovieDataDetail movie) {
        RealmMovie realmMovie = new RealmMovie();
        RealmList<RealmTrailer> realmTrailers = new RealmList<RealmTrailer>();
        RealmList<RealmReview> realmReviews = new RealmList<RealmReview>();

        String id = movie.getId();
        realmMovie.setId(id);

        String overview = movie.getOverview();
        realmMovie.setOverview(overview);

        String poster = movie.getPosterPath();
        realmMovie.setPosterPath(poster);

        String release = movie.getReleaseDate();
        realmMovie.setReleaseDate(release);

        String title = movie.getTitle();
        realmMovie.setTitle(title);

        String vote = movie.getVoteAverage();
        realmMovie.setVoteAverage(vote);

        Trailers trailers = movie.getTrailers();
        List<Youtube> youtube = trailers.getYoutube();
        if (youtube != null) {
            for (Youtube videos : youtube) {
                String name = videos.getName();
                String link = videos.getSource();
                RealmTrailer trailer = new RealmTrailer();
                trailer.setName(name);
                trailer.setSource(link);
                realmTrailers.add(trailer);
            }
        }

        Reviews reviews = movie.getReviews();
        List<ReviewResult> result = reviews.getResults();
        if (result != null) {
            for (ReviewResult results : result) {

                String author = results.getAuthor();
                String content = results.getContent();
                RealmReview review = new RealmReview();
                review.setAuthor(author);
                review.setContent(content);
                realmReviews.add(review);
            }
        }
        realmMovie.setReviews(realmReviews);
        realmMovie.setTrailers(realmTrailers);


        return realmMovie;
    }

    public MovieDataDetail realmToMovieDataDetail(RealmMovie realmMovie){
        MovieDataDetail movieDataDetail = new MovieDataDetail();

    return movieDataDetail;
    }
}
