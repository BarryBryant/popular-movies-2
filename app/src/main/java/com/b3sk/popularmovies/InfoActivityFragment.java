package com.b3sk.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.b3sk.popularmovies.Models.MovieDataDetail;
import com.b3sk.popularmovies.Models.MovieInfo;
import com.b3sk.popularmovies.Models.RealmMovie;
import com.b3sk.popularmovies.Models.ReviewResult;
import com.b3sk.popularmovies.Models.Reviews;
import com.b3sk.popularmovies.Models.Trailers;
import com.b3sk.popularmovies.Models.Youtube;
import com.b3sk.popularmovies.Rest.MovieApiInterface;
import com.b3sk.popularmovies.Rest.RestClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoActivityFragment extends Fragment {


    private MovieDataDetail masterMovie;
    private String movieId;
    private String API_CALL_APPEND = "trailers,reviews";
    private boolean TESTER = true;


    public InfoActivityFragment() {
        setHasOptionsMenu(true);
    }

    //TODO: implement parcelable across all of the JSON POJO models for use as savedInstanceState

    /**
    * Retrofit call to API to retrieve MovieDataDetail object
     * or db query if favorites preference is selected
    * */

    private void updateMovieInfo() {
        MovieApiInterface service = RestClient.getClient();
        Call<MovieDataDetail> call = service.getIdAndKey(
                movieId, BuildConfig.MOVIE_DB_API_KEY, API_CALL_APPEND);

        call.enqueue(new Callback<MovieDataDetail>() {
            @Override
            public void onResponse(Response<MovieDataDetail> response) {
                MovieDataDetail result = response.body();
                populateDetailView(result);
                masterMovie = result;
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", "************FAILURE OF 2nd CALL");
            }
        });
    }

    /**
     * Populates the detail fragment views with the movie's info
     * @param movie
     */
    //TODO: Use xliff in place of concatenated strings
    private void populateDetailView(MovieDataDetail movie){

        ((TextView) getActivity().findViewById(R.id.detail_title))
                .setText(movie.getTitle());

        ((TextView) getActivity().findViewById(R.id.detail_date))
                .setText("(" + movie.getReleaseDate() + ")");

        ((TextView) getActivity().findViewById(R.id.detail_description))
                .setText(movie.getOverview());

        ((TextView) getActivity().findViewById(R.id.detail_rating))
                .setText("Rating: " + movie.getVoteAverage() + "/10");

        //Build link and use picasso to handle setting the thumbnail image.
        String thumbLink = "http://image.tmdb.org/t/p/w185/" + movie.getPosterPath();
        ImageView posterView = (ImageView) getActivity().findViewById(R.id.detail_poster);
        Picasso.with(getContext()).load(thumbLink).into(posterView);
        populateReviewAndTrailerContainer(movie);
    }

    /**
     * Checks the movie data for trailers and reviews, then adds them to the UI.
     * @param movie
     */
    public void populateReviewAndTrailerContainer(MovieDataDetail movie){
        Trailers trailers = movie.getTrailers();
        List<Youtube> youtubes = trailers.getYoutube();
        Reviews reviews = movie.getReviews();
        List<ReviewResult> results = reviews.getResults();

        if(results != null) {
            for (Youtube videos : youtubes) {
                LayoutInflater inflator = LayoutInflater.from(getContext());
                LinearLayout container = (LinearLayout) getActivity().findViewById(
                        R.id.trailer_container);
                View item = inflator.inflate(R.layout.trailer, null);
                Button button = (Button) item.findViewById(R.id.launch_youtube);
                final String trailerLink =
                        "http://www.youtube.com/watch?v="+videos.getSource();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(trailerLink)));
                    }
                });
                TextView videoDesc = (TextView) item.findViewById(R.id.video_title);
                String title = videos.getName();
                videoDesc.setText(title);
                container.addView(item);
            }
        }else Log.d("InfoActivityFragment", "Looks like there aint no trailers!");

        if(results != null) {
            for (ReviewResult reviewResult : results) {
                LayoutInflater inflator = LayoutInflater.from(getContext());
                LinearLayout container = (LinearLayout) getActivity().findViewById(
                        R.id.review_container);
                View item = inflator.inflate(R.layout.review, null);
                TextView reviewView = (TextView) item.findViewById(R.id.review_body);
                TextView authorView = (TextView) item.findViewById(R.id.review_author);
                String author = reviewResult.getAuthor();
                String reviewContent = reviewResult.getContent();
                reviewView.setText(reviewContent);
                authorView.setText(author);
                container.addView(item);
            }
        }else Log.d("InfoActivityFragment", "Looks like there aint no reviews!");
    }

    public void onFavoriteClick() {

        RealmMovie realmTest = Utility.movieDataDetailToRealm(masterMovie);
        Log.d("InfoFrag", realmTest.getTitle());
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();

        RealmMovie realmMovie = realm.copyToRealmOrUpdate(realmTest);
        realm.commitTransaction();


    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        //Extracts data sent to the detail activity via intent or master detail layout
        Intent intent = getActivity().getIntent();
        Bundle intentArgs = intent.getExtras();
        Bundle arguments = getArguments();

        if (intentArgs != null) {
            MovieInfo movie = intentArgs.getParcelable(MovieFragment.PAR_KEY);
            movieId = movie.getId();
        } else if (arguments != null) {
            MovieInfo movie = arguments.getParcelable(MovieFragment.PAR_KEY);
            movieId = movie.getId();
        }

        Button button = (Button)rootView.findViewById(R.id.favorite_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteClick();
            }
        });



        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieInfo();
    }


}

