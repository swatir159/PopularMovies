package com.android.rupeeright.popularmovies.DataStorage.trailers;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.rupeeright.popularmovies.DataStorage.MovieDetailProvider;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;

/**
 * The trailer info 
 */
public class TrailersColumns implements BaseColumns {
    public static final String TABLE_NAME = "trailers";
    public static final Uri CONTENT_URI = Uri.parse(MovieDetailProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The id of the movie in local database, matches with _ID
     */
    public static final String MOVIE_ID = "trailers__movie_id";

    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    public static final String MOVIEDB_ID = "movieDB_id";

    /**
     * The trailer id of the movie like 533ec654c3a36854480003eb 
     */
    public static final String TRAILER_ID = "trailer_id";

    /**
     * iso language like en
     */
    public static final String ISO_639_1 = "iso_639_1";

    /**
     * iso language like SUXWAEX2jlg
     */
    public static final String KEY = "key";

    /**
     * name of the trailer like Trailer 1
     */
    public static final String NAME = "name";

    /**
     * size of the trailer like 720
     */
    public static final String SIZE = "size";

    /**
     * Site like youtube
     */
    public static final String SITE = "site";

    /**
     * type like Trailer
     */
    public static final String TYPE = "type";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            MOVIEDB_ID,
            TRAILER_ID,
            ISO_639_1,
            KEY,
            NAME,
            SIZE,
            SITE,
            TYPE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(MOVIEDB_ID) || c.contains("." + MOVIEDB_ID)) return true;
            if (c.equals(TRAILER_ID) || c.contains("." + TRAILER_ID)) return true;
            if (c.equals(ISO_639_1) || c.contains("." + ISO_639_1)) return true;
            if (c.equals(KEY) || c.contains("." + KEY)) return true;
            if (c.equals(NAME) || c.contains("." + NAME)) return true;
            if (c.equals(SIZE) || c.contains("." + SIZE)) return true;
            if (c.equals(SITE) || c.contains("." + SITE)) return true;
            if (c.equals(TYPE) || c.contains("." + TYPE)) return true;
        }
        return false;
    }

    public static final String PREFIX_MOVIES = TABLE_NAME + "__" + MoviesColumns.TABLE_NAME;
}
