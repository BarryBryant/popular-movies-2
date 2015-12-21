package com.b3sk.popularmovies;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
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


    /**
     * @param context the current context
     * @param movies  An ArrayList of MovieInfo objects with posters to display in a grid
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
        String thumbLink = getContext().getString(R.string.thumb_link, movieInfo.getPosterPath());
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        final String sortMethod = sharedPrefs.getString(
                getContext().getString(R.string.pref_sort_key),
                getContext().getString(R.string.pref_sort_popularity));

        //If sort pref is set to favorites, load image from offline database
        //If not, get image from link
        if (!getContext().getString(R.string.pref_sort_favorites).equals(sortMethod)) {
            Picasso.with(getContext()).load(thumbLink).into(posterView);
        } else {
            Bitmap image = Utility.getImage(movieInfo.getImageBytes());
            posterView.setImageBitmap(image);
        }
        return convertView;
    }

}
