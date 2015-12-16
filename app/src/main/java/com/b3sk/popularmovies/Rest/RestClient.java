package com.b3sk.popularmovies.Rest;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Joopk on 12/9/2015.
 */
public class RestClient {

    private static MovieApiInterface movieApiInterface ;
    private static String baseUrl = "http://api.themoviedb.org" ;

    public static MovieApiInterface getClient() {


            Retrofit client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            movieApiInterface = client.create(MovieApiInterface.class);
        return movieApiInterface ;

    }

    }


