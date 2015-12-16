package com.b3sk.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b3sk.popularmovies.Models.MovieDataDetail;
import com.b3sk.popularmovies.Models.MovieInfo;
import com.b3sk.popularmovies.Rest.MovieApiInterface;
import com.b3sk.popularmovies.Rest.RestClient;
import com.squareup.picasso.Picasso;

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

    public InfoActivityFragment() {
        setHasOptionsMenu(true);
    }

    //TODO: implement parcelable across all of the JSON POJO models for use as savedInstanceState

    /**
    * Retrofit call to API to retrieve MovieDataDetail object
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

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieInfo();
    }


}

