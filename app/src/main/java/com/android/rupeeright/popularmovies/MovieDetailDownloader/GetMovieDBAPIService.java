package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import com.android.rupeeright.popularmovies.POJO.ReviewOutputJSON;
import com.android.rupeeright.popularmovies.POJO.TrailerOutputJSON;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by swatir on 2/20/2016.
 */
public interface GetMovieDBAPIService {

        @GET("/3/movie/{id}/videos")
        Call<TrailerOutputJSON> getTrailerInfoFromAPI(@Path("id") int moveDBIDParama,
                                                      @QueryMap Map<String, String> callParams);

        @GET("/3/movie/{id}/reviews")
        Call<ReviewOutputJSON> getReviewInfoFromAPI(@Path("id") int moveDBIDParama,
                                                    @QueryMap Map<String, String> callParams);

        @GET("/3/discover/movie")
        Call<ReviewOutputJSON> getMovieListFromAPI(@QueryMap Map<String, String> callParams);
}
