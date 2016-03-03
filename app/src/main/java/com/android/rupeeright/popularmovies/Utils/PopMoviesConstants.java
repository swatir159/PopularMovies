package com.android.rupeeright.popularmovies.Utils;

import android.net.Uri;

/**
 * Created by swatir on 1/13/2016.
 */
public class PopMoviesConstants {

    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    public static final String POSTER_BASE_PATH = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW = "w185/";
    public static final String BACKDROP_BASE_PATH = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_TO_DOWNLOAD_IN_GRID = "original";   /* \"w92\", \"w154\", \"w185\", \"w342\", \"w500\", \"w780\", or \"original\" */

    public static final double OVERVIEW_HEIGHT_PERCENT = 0.40;
    public static final double POSTER_HEIGHT_PERCENT = 0.40;
    public static final double TITLE_HEIGHT_PERCENT = 0.20;
    public static final int BACKDROP_OPACITY_VALUE = 50;
    //public static final String APP_NAME = "popularmovies";

    public static final String MOVIEDB_URL = "http://api.themoviedb.org/3/";
    public static final String DISCOVER_MOVIE_PART_OF_URL = "discover/movie?sort_by=";
    public static final String API_KEY_PART_OF_URL = "&api_key=";

    public static final String TAG_TASK_MAIN_FRAGMENT = "task_fragment";
    public static final String TAG_TASK_DETAIL_FRAGMENT = "detail_fragment";
    public static final boolean DEBUG = false; /* true; */
    public static final String DEBUG_LEVEL = "INFO";
    public static final String DEBUG_LEVEL_1 = "ERROR";
    public static final String DEBUG_LEVEL_2 = "WARNING";
    public static final String DEBUG_LEVEL_3 = "INFO";

    public static final String MAIN_ACTIVITY_INSTANCE_TAG = "GetAllFims";
    public static final String DETAIL_ACTIVITY_INSTANCE_TAG = "GetSelectedFilm";
    public static final String DETAIL_FRAGMENT_INSTANCE_TAG = "GetSelectedFilm";
    public static final String DETAIL_ACTIVITY_EXTRA_INFO_INSTANCE_TAG = "selectedfilm";
    public static final String DETAIL_ACTIVITY_TRAILER_TAG = "GetSelectedFilmTrailer";
    public static final String DETAIL_ACTIVITY_REVIEW_TAG = "GetSelectedFilmReview";
    public static final String MOVIE_TRAILER_SHARE_TAG = "#PopMoviesApp";
    public static final int REVIEW_TYPE = 2;
    public static final int TRAILER_TYPE = 1;

    public static final int MOVIE_DATA_RETENTION_DAYS = 365;

    /* for getting the data from the movie db - in order to restrict the data download these are invented. */
    public static final int NO_OF_PAGES_TO_DOWNLOAD = 5;
    public static final int PRIMARY_RELEASE_YEAR_TO_GET_DATA = 2015;


    public static final String CONTENT_AUTHORITY = "com.android.rupeeright.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movies";
    public static final String PATH_MOVIES_WITH_FAVOURITES = "movies/favorites";
    public static final String PATH_TRAILER = "trailers";

    public static final int data_available_backgroud= 0xDDFCE1;
    public static final int data_not_available_backgroud= 0xF7E4EA;
    public static final int header_text_size = 18;
}
