package com.b3sk.popularmovies.Rest;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.b3sk.popularmovies.MainActivity;
import com.b3sk.popularmovies.Models.MovieData;
import com.b3sk.popularmovies.R;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Joopk on 12/9/2015.
 */
public interface MovieApiInterface {

    //Builds initial API call with sort/api key query param
    @GET("/3/discover/movie?")
    Call<MovieData> getQueryParam(@Query("sort_by") String sortMethod,
            @Query("api_key") String apiKey);




}
