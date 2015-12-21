package com.b3sk.popularmovies.Rest;

import com.b3sk.popularmovies.Models.MovieData;
import com.b3sk.popularmovies.Models.MovieDataDetail;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Joopk on 12/9/2015.
 */
public interface MovieApiInterface {

    //Builds initial API call with sort/api key query param
    @GET("/3/discover/movie?")
    Call<MovieData> getQueryParam(@Query("sort_by") String sortMethod,
                                  @Query("api_key") String apiKey);

    @GET("/3/movie/{id}?&append_to_response=trailers,reviews")
    Call<MovieDataDetail> getIdAndKey(
            @Path("id") String id,
            @Query("api_key") String apiKey);


}
