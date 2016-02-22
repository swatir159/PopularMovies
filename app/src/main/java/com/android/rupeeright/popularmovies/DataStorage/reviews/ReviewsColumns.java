package com.android.rupeeright.popularmovies.DataStorage.reviews;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.rupeeright.popularmovies.DataStorage.MovieDetailProvider;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;

/**
 * The Review info
 */
public class ReviewsColumns implements BaseColumns {
    public static final String TABLE_NAME = "reviews";
    public static final Uri CONTENT_URI = Uri.parse(MovieDetailProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The id of the movie in local database, matches with _ID
     */
    public static final String MOVIE_ID = "reviews__movie_id";

    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    public static final String MOVIEDB_ID = "movieDB_id";

    /**
     * The review id of the movie like 5010553819c2952d1b000451 
     */
    public static final String REVIEW_ID = "review_id";

    /**
     * author of the review like Travis Bell
     */
    public static final String AUTHOR = "author";

    /**
     * actual review content  
     */
    public static final String CONTENT = "content";

    /**
     * url of the review like http://j.mp/QSjAK2 
     */
    public static final String URL = "url";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            MOVIEDB_ID,
            REVIEW_ID,
            AUTHOR,
            CONTENT,
            URL
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(MOVIEDB_ID) || c.contains("." + MOVIEDB_ID)) return true;
            if (c.equals(REVIEW_ID) || c.contains("." + REVIEW_ID)) return true;
            if (c.equals(AUTHOR) || c.contains("." + AUTHOR)) return true;
            if (c.equals(CONTENT) || c.contains("." + CONTENT)) return true;
            if (c.equals(URL) || c.contains("." + URL)) return true;
        }
        return false;
    }

    public static final String PREFIX_MOVIES = TABLE_NAME + "__" + MoviesColumns.TABLE_NAME;
}
