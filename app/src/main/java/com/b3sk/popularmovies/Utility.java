package com.b3sk.popularmovies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.b3sk.popularmovies.Models.MovieDataDetail;
import com.b3sk.popularmovies.Models.RealmMovie;
import com.b3sk.popularmovies.Models.RealmReview;
import com.b3sk.popularmovies.Models.RealmTrailer;
import com.b3sk.popularmovies.Models.ReviewResult;
import com.b3sk.popularmovies.Models.Reviews;
import com.b3sk.popularmovies.Models.Trailers;
import com.b3sk.popularmovies.Models.Youtube;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Joopk on 12/17/2015.
 */
public class Utility {
    /**
     *
     * @param movie MovieDataDetail to convert to a RealmMovie object
     * @return RealmMovie object
     */
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
        if(trailers != null) {
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
        }

        Reviews reviews = movie.getReviews();
        if(reviews != null) {
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
        }
        realmMovie.setReviews(realmReviews);
        realmMovie.setTrailers(realmTrailers);


        return realmMovie;
    }


    /**
     *
     * @param realmMovie to convert to a movieDataDetail object
     * @return MovieDataDetail object
     */
    public static MovieDataDetail realmToMovieDataDetail(RealmMovie realmMovie) {
        MovieDataDetail movieDataDetail = new MovieDataDetail();
        Trailers trailers = new Trailers();
        Reviews reviews = new Reviews();
        List<ReviewResult> reviewResults = new ArrayList<>();
        List<Youtube> youtubes = new ArrayList<>();

        movieDataDetail.setPosterPath(realmMovie.getPosterPath());
        movieDataDetail.setId(realmMovie.getId());
        movieDataDetail.setOverview(realmMovie.getOverview());
        movieDataDetail.setTitle(realmMovie.getTitle());
        movieDataDetail.setVoteAverage(realmMovie.getVoteAverage());
        movieDataDetail.setReleaseDate(realmMovie.getReleaseDate());
        movieDataDetail.setImageBytes(realmMovie.getImageBytes());

        List<RealmReview> realmReviews = realmMovie.getReviews();
        List<RealmTrailer> realmTrailers = realmMovie.getTrailers();

        if (realmReviews.size() > 0) {
            for (RealmReview realmReview : realmReviews) {
                ReviewResult revRes = new ReviewResult();
                revRes.setAuthor(realmReview.getAuthor());
                revRes.setContent(realmReview.getContent());
                reviewResults.add(revRes);
            }

            reviews.setResults(reviewResults);
            movieDataDetail.setReviews(reviews);
        }

        if (realmTrailers.size() > 0) {
            for (RealmTrailer realmTrailer : realmTrailers) {
                Youtube youtube = new Youtube();
                youtube.setName(realmTrailer.getName());
                youtube.setSource(realmTrailer.getSource());
                youtubes.add(youtube);

            }

            trailers.setYoutube(youtubes);
            movieDataDetail.setTrailers(trailers);
        }


        return movieDataDetail;
    }

    /**
     *
     * @param bitmap to be converted to a byte array
     * @return byte array for storage
     */
    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     *
     * @param image byte array to be converted to a Bitmap
     * @return Bitmap for populating image view
     */
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
