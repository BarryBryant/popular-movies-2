package com.b3sk.popularmovies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.b3sk.popularmovies.Models.MovieInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Barry on 11/11/2015.
 */
public class MovieAdapter extends ArrayAdapter<MovieInfo> {


    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    /**
     * @param context the current context
     * @param movies  A list of movie posters to display in a grid
     */

    public MovieAdapter(Activity context, List<MovieInfo> movies) {
        super(context, 0, movies);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieInfo movieInfo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item_movie, parent, false);
        }


        ImageView posterView = (ImageView) convertView.findViewById(R.id.grid_item_image);
        String thumbLink = "http://image.tmdb.org/t/p/w185/" + movieInfo.getPosterPath();
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sortMethod = sharedPrefs.getString(
                getContext().getString(R.string.pref_sort_key),
                getContext().getString(R.string.pref_sort_popularity));

        //If sort pref is set to favorites, load image from offline database
        if (!getContext().getString(R.string.pref_sort_favorites).equals(sortMethod)) {
            Picasso.with(getContext()).load(thumbLink).into(posterView);
        } else {
            Log.d("ADAPTER", "SUCCESSFUL LOAD FROM BMP");
            Bitmap image = Utility.getImage(movieInfo.getImageBytes());
            posterView.setImageBitmap(image);
        }
        return convertView;
    }

}
