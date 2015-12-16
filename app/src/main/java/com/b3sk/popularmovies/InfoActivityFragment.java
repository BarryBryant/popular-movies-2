package com.b3sk.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.b3sk.popularmovies.Models.MovieInfo;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class InfoActivityFragment extends Fragment {




        public InfoActivityFragment() {
            setHasOptionsMenu(true);
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_info, container, false);

            //Extract the bundle from the intent and get the MovieInfo
            //parcelable out of the bundle.
            Intent intent = getActivity().getIntent();
            Bundle arguments = getArguments(); //will be null if activity was launched via intent
            Bundle args = intent.getExtras();// will be null with master/detail layout
            //Examine results of getIntent and getArguments to determine how fragment was launched
            //Get info from intent/arguments to populate the detail view
            if (args != null) {


                MovieInfo movie = args.getParcelable(MovieFragment.PAR_KEY);

                //Populate the layout with the movie's info.

                ((TextView) rootView.findViewById(R.id.detail_title))
                        .setText(movie.getTitle());

                ((TextView) rootView.findViewById(R.id.detail_date))
                        .setText("(" + movie.getReleaseDate() + ")");

                ((TextView) rootView.findViewById(R.id.detail_description))
                        .setText(movie.getOverview());

                ((TextView) rootView.findViewById(R.id.detail_rating))
                        .setText("Rating: " + movie.getVoteAverage() + "/10");

                //Build link and use picasso to handle setting the thumbnail image.
                String thumbLink = "http://image.tmdb.org/t/p/w185/" + movie.getPosterPath();
                ImageView posterView = (ImageView) rootView.findViewById(R.id.detail_poster);
                Picasso.with(getContext()).load(thumbLink).into(posterView);

            }else if (arguments != null){
                MovieInfo movie = arguments.getParcelable(MovieFragment.PAR_KEY);

                //Populate the layout with the movie's info.

                ((TextView) rootView.findViewById(R.id.detail_title))
                        .setText(movie.getTitle());

                ((TextView) rootView.findViewById(R.id.detail_date))
                        .setText("(" + movie.getReleaseDate() + ")");

                ((TextView) rootView.findViewById(R.id.detail_description))
                        .setText(movie.getOverview());

                ((TextView) rootView.findViewById(R.id.detail_rating))
                        .setText("Rating: " + movie.getVoteAverage() + "/10");

                //Build link and use picasso to handle setting the thumbnail image.
                String thumbLink = "http://image.tmdb.org/t/p/w500/" + movie.getPosterPath();
                ImageView posterView = (ImageView) rootView.findViewById(R.id.detail_poster);
                Picasso.with(getContext()).load(thumbLink).into(posterView);

            }

            return rootView;
        }


    }

