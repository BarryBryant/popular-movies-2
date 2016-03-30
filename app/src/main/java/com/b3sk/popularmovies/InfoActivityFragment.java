package com.b3sk.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoActivityFragment extends Fragment {


    private static MovieDataDetail masterMovie;
    private String movieId;


    public InfoActivityFragment() {
        setHasOptionsMenu(true);
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
            if (movie != null) {
                movieId = movie.getId();
            }
        } else if (arguments != null) {
            MovieInfo movie = arguments.getParcelable(MovieFragment.PAR_KEY);
            if (movie != null) {
                movieId = movie.getId();
            }
        }

        Button button = (Button) rootView.findViewById(R.id.favorite_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavoriteClick();
            }
        });


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.infofragment, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider shareActionProvider;

        shareActionProvider = (ShareActionProvider)
                MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.

        if (masterMovie != null) {
            shareActionProvider.setShareIntent(createMovieIntent());
        }
    }

    private Intent createMovieIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        if(masterMovie.getTrailers() != null){
        if (masterMovie.getTrailers().getYoutube().size() > 0) {
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    getContext().getString(R.string.share_info,
                            masterMovie.getTrailers().getYoutube().get(0).getSource()));
        }} else shareIntent.putExtra(Intent.EXTRA_TEXT, "There are no trailers for this movie!");
        return shareIntent;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (movieId != null) {
            updateMovieInfo(movieId);
        }
    }


    public static boolean checkIfFragmentPopulated() {
        if (masterMovie != null) {
            return true;
        } else return false;
    }

    /**
     * Retrofit call to API to retrieve MovieDataDetail object
     * or db query if favorites preference is selected
     */

    private void updateMovieInfo(String id) {

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sortMethod = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popularity));

        //perform asynchronous api call if sort method is popularity or rating
        if (!getString(R.string.pref_sort_favorites).equals(sortMethod)) {

            MovieApiInterface service = RestClient.getClient();
            Call<MovieDataDetail> call = service.getIdAndKey(
                    id, BuildConfig.MOVIE_DB_API_KEY);

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


            //Update from database
        } else {
            Realm realm = Realm.getInstance(getContext());
            realm.beginTransaction();
            RealmQuery<RealmMovie> query = realm.where(RealmMovie.class).equalTo("id", id);
            RealmResults<RealmMovie> result = query.findAll();
            realm.commitTransaction();
            if (result.size() > 0) {
                masterMovie = Utility.realmToMovieDataDetail(result.get(0));
                populateDetailView(masterMovie);
            }
        }
    }

    /**
     * Populates the detail fragment views with the movie's info
     *
     * @param movie MovieDataDetail object used to populate the views
     */
    //TODO: Use xliff in place of concatenated strings
    private void populateDetailView(MovieDataDetail movie) {

        ((TextView) getActivity().findViewById(R.id.detail_title))
                .setText(movie.getTitle());

        ((TextView) getActivity().findViewById(R.id.detail_date))
                .setText(getContext().getString(R.string.release_date, movie.getReleaseDate()));

        ((TextView) getActivity().findViewById(R.id.detail_description))
                .setText(movie.getOverview());

        ((TextView) getActivity().findViewById(R.id.detail_rating))
                .setText(getContext().getString(R.string.rating, movie.getVoteAverage()));

        //Build link and use picasso to handle setting the thumbnail image.
        //Or build image from byte array stored in database

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String sortMethod = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popularity));
        String thumbLink = getContext().getString(R.string.thumb_link, movie.getPosterPath());
        ImageView posterView = (ImageView) getActivity().findViewById(R.id.detail_poster);
        //If pref is not favorites use picasso to download
        if (!getString(R.string.pref_sort_favorites).equals(sortMethod)) {
            Picasso.with(getContext()).load(thumbLink).into(posterView);

            //Offline Support:
            //Use stored byte array to build bitmap for imageview
        } else {
            Bitmap image = Utility.getImage(movie.getImageBytes());
            posterView.setImageBitmap(image);
        }
        populateReviewAndTrailerContainer(movie);

    }

    /**
     * Checks the movie data for trailers and reviews, then adds them to the UI.
     *
     * @param movie
     */
    public void populateReviewAndTrailerContainer(MovieDataDetail movie) {
        Trailers trailers = movie.getTrailers();
        if (trailers != null) {
            List<Youtube> youtubes = trailers.getYoutube();


            if (youtubes != null) {
                for (Youtube videos : youtubes) {
                    LayoutInflater inflator = LayoutInflater.from(getContext());
                    LinearLayout container = (LinearLayout) getActivity().findViewById(
                            R.id.trailer_container);
                    View item = inflator.inflate(R.layout.trailer, null);
                    Button button = (Button) item.findViewById(R.id.launch_youtube);
                    final String trailerLink =
                            "http://www.youtube.com/watch?v=" + videos.getSource();
                    Log.d("VIDEO MASTER", videos.getSource());
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
            }
        }

        Reviews reviews = movie.getReviews();
        if (reviews != null) {
            List<ReviewResult> results = reviews.getResults();

            if (results != null) {
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
            }
        }
    }

    public void onFavoriteClick() {

        //Build byte array from image for database storage
        ImageView posterView = (ImageView) getActivity().findViewById(R.id.detail_poster);
        BitmapDrawable drawable = (BitmapDrawable) posterView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        byte[] imageBytes = Utility.bitmapToBytes(bitmap);

        //convert movieDataDetail to a Realm object for storage in realm database
        RealmMovie realmTest = Utility.movieDataDetailToRealm(masterMovie);
        realmTest.setImageBytes(imageBytes);
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        RealmMovie realmMovie = realm.copyToRealmOrUpdate(realmTest);
        realm.commitTransaction();


    }


}

