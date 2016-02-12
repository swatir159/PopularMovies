package com.android.rupeeright.popularmovies.DataStorage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

import java.util.Arrays;

/**
 * Created by swatir on 2/9/2016.
 */
public class MovieContract {

    private static final String LOG_TAG = MovieContract.class.getSimpleName();

    public static final String CONTENT_AUTHORITY = "com.android.rupeeright.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movies";

    /* Inner class that defines the table contents of Movies */
    public static final class MoviesEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static  final String  TABLE_NAME = "movies";


        /* Return result from the API
         {
         "poster_path":"\/atZ4SrQv0eB58M5Nr1Tn8Ttp3p0.jpg",
         "adult":false,
         "overview":"Sunil belongs to a middle-class family, and is intent in pursuing his career with a music group,
                     despite of his dad disapproval. Sunil is also in love with Anna, but Anna does not really love him,
                     but likes him as a friend. Sunil is persistent, but instead Anna openly declares her love for Chris.
                     In order to impress his dad, Sunil forges his examination results, but then later confesses to his family,
                     who receive this news in utter dismay. Will this change Sunil's ways?
                     Will Anna change her mind about Sunil or will she get married to Chris?",
         "release_date":"1993-01-01",
         "genre_ids":[35,18,10402],
         "id":15419,
         "original_title":"Kabhi Haan Kabhi Naa",
         "original_language":"hi",
         "title":"Kabhi Haan Kabhi Naa",
         "backdrop_path":"\/hAJ3Ea1ZbIjRQKf9ht8LvWwOrSe.jpg",
         "popularity":1,
         "vote_count":3,
         "video":false,
         "vote_average":6.83}

        */

        /* movie ID as returned by API */
        public static  final String COLUMN_MOVIE_ID = "movie_id";
        public static  final String COLUMN_TITLE = "title";
        public static  final String COLUMN_POSTER_PATH = "poster_path";
        public static  final String COLUMN_OVERVIEW = "overview";
        public static  final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static  final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_BACKDROP_PATH     = "backdrop_path";
        public static final String COLUMN_ORIGINAL_LAN      = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE    = "original_title";
        public static final String COLUMN_POPULARITY        = "popularity";
        public static final String COLUMN_VIDEO             = "video";
        public static final String COLUMN_VOTE_COUNT        = "vote_count";
        public static final String COLUMN_ADULT        = "adult";
        /* no column for genre id */

        public static Uri buildMovieUri(long id) {
            if (PopMoviesConstants.DEBUG) {
                Log.d("PopMovies", LOG_TAG + ":" + " buildMovieUri " );
            }
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMovieDBIDUri(int id) {
            if (PopMoviesConstants.DEBUG) {
                Log.d("PopMovies", LOG_TAG + ":" + " buildMovieDBIDUri" );
            }
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


        public static long getIDFromUri(Uri uri) {
            if (PopMoviesConstants.DEBUG) {
                Log.d("PopMovies", LOG_TAG + ":" + " getIDFromUri " );
            }
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static int getMovieDBIDFromUri(Uri uri) {
            if (PopMoviesConstants.DEBUG) {
                Log.d("PopMovies", LOG_TAG + ":" + "  getMovieDBIDFromUri" );
            }
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

    }
}
