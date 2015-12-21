package com.b3sk.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.b3sk.popularmovies.Models.MovieData;
import com.b3sk.popularmovies.Models.MovieInfo;
import com.b3sk.popularmovies.Models.RealmMovie;
import com.b3sk.popularmovies.Rest.MovieApiInterface;
import com.b3sk.popularmovies.Rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * A  fragment containing a grid view filled with movie poster thumbnails.
 */
public class MovieFragment extends Fragment {

    public final static String PAR_KEY = "com.b3sk.popularmovies.par";
    private MovieAdapter movieAdapter;
    private List<MovieInfo> movieList;


    public MovieFragment() {
    }


    //Check if there is a previously saved activity state.
    //Utilizes parcelable interface.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(PAR_KEY)) {
            movieList = new ArrayList<>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList(PAR_KEY);
        }
    }

    //Saves state of activity as parcelable.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PAR_KEY, (ArrayList) movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //Instantiates a new custom array adapter to handle the movie objects.
        movieAdapter = new MovieAdapter(getActivity(), movieList);

        //Use the custom adapter to populate a grid view.
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movie);
        gridView.setAdapter(movieAdapter);


        //Set on click listener to launch movie info activity after clicking a
        //movie poster thumbnail on the gridview.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieInfo movie = movieAdapter.getItem(i);
                if (movie != null) {
                    ((MovieCallback) getActivity())
                            .onItemSelected(movie);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }


    public interface MovieCallback {
        void onItemSelected(MovieInfo movie);
    }


    private void updateMovie() {

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sortMethod = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popularity));
        //Populate the gridview based on sorting preference
        if (!getString(R.string.pref_sort_favorites).equals(sortMethod)) {
            updateFromAPI(sortMethod);

        } else {
            updateFromFavorites();
        }
    }

    private void updateFromAPI(String sortMethod) {

        MovieApiInterface service = RestClient.getClient();
        Call<MovieData> call = service.getQueryParam(sortMethod, BuildConfig.MOVIE_DB_API_KEY);


        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(Response<MovieData> response) {
                MovieData result = response.body();
                movieList = result.getResults();
                //if two pane mode and detail fragment isn't populated add first movie
                if (getActivity().findViewById(R.id.movie_detail_container) != null &&
                        !InfoActivityFragment.checkIfFragmentPopulated()) {
                    ((MovieCallback) getActivity())
                            .onItemSelected(movieList.get(0));
                }
                movieAdapter.clear();
                for (int i = 0; i < movieList.size(); i++) {
                    movieAdapter.add(movieList.get(i));
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", "well this aint right");
            }
        });
    }


    private void updateFromFavorites() {
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        RealmQuery<RealmMovie> query = realm.where(RealmMovie.class);
        RealmResults<RealmMovie> result = query.findAll();

        List<MovieInfo> favoriteList = new ArrayList<>();
        for (RealmMovie movie : result) {
            Log.d("FavoriteLOOPER", movie.getId());
            MovieInfo movieInfo = new MovieInfo();
            movieInfo.setId(movie.getId());
            movieInfo.setPosterPath(movie.getPosterPath());
            movieInfo.setImageBytes(movie.getImageBytes());
            favoriteList.add(movieInfo);
        }
        realm.commitTransaction();
        movieList = favoriteList;
        //if two pane mode and detail fragment isn't populated add first movie
        if (getActivity().findViewById(R.id.movie_detail_container) != null &&
                !InfoActivityFragment.checkIfFragmentPopulated() &&
                movieList.size() > 0) {
            ((MovieCallback) getActivity())
                    .onItemSelected(movieList.get(0));
        }
        movieAdapter.clear();
        for (int i = 0; i < movieList.size(); i++) {
            movieAdapter.add(movieList.get(i));
        }

    }


}
