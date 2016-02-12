package com.android.rupeeright.popularmovies.WebDataFetcher;

import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

/**
 * Created by swatir on 1/13/2016.
 */
public class MovieDBConnector {
    //Volatile keyword ensures that multiple threads handle the unique/instance correctly
    private volatile static MovieDBConnector uniqueInstance;
    /* the URL http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=a06a137818569958a0f7a1299a22809e */
    private final String mURL = PopMoviesConstants.MOVIEDB_URL;
    private String mAPIKey = "";

    private MovieDBConnector() {
    }


    //Check for an instance and if there isn't one enter the synchronized method
    public static MovieDBConnector getInstance() {
        if (uniqueInstance == null) {
            synchronized (MovieDBConnector.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MovieDBConnector(); //Once in the block, check again and if still null, create the instance
                }
            }
        }
        return uniqueInstance;
    }

    public String getMoviesQuery(String settingsValue) {
        return mURL + PopMoviesConstants.DISCOVER_MOVIE_PART_OF_URL + settingsValue + PopMoviesConstants.API_KEY_PART_OF_URL + mAPIKey;

    }

    public void setmAPIKey(String api_key)
    {
        this.mAPIKey = api_key;
    }
}


