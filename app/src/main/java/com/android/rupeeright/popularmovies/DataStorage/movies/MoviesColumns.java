package com.android.rupeeright.popularmovies.DataStorage.movies;

import android.net.Uri;
import android.provider.BaseColumns;

import com.android.rupeeright.popularmovies.DataStorage.MovieDetailProvider;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;

/**
 * Details about the movies received from theMovieDB
 */
public class MoviesColumns implements BaseColumns {
    public static final String TABLE_NAME = "movies";
    public static final Uri CONTENT_URI = Uri.parse(MovieDetailProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    /**
     * The id of the movie in MovieDB
     */
    public static final String MOVIE_ID = "movie_id"; //26 original value : movies__movie_id

    public static final String TITLE = "title";

    public static final String POSTER_PATH = "poster_path";

    public static final String OVERVIEW = "overview";

    public static final String VOTE_AVERAGE = "vote_average";

    public static final String RELEASE_DATE = "release_date";

    public static final String BACKDROP_PATH = "backdrop_path";

    public static final String ORIGINAL_LANGUAGE = "original_language";

    public static final String ORIGINAL_TITLE = "original_title";

    public static final String POPULARITY = "popularity";

    public static final String VIDEO = "video";

    public static final String VOTE_COUNT = "vote_count";

    public static final String ADULT = "adult";

    public static final String FAVORITE = "favorite";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            MOVIE_ID,
            TITLE,
            POSTER_PATH,
            OVERVIEW,
            VOTE_AVERAGE,
            RELEASE_DATE,
            BACKDROP_PATH,
            ORIGINAL_LANGUAGE,
            ORIGINAL_TITLE,
            POPULARITY,
            VIDEO,
            VOTE_COUNT,
            ADULT,
            FAVORITE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(MOVIE_ID) || c.contains("." + MOVIE_ID)) return true;
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(POSTER_PATH) || c.contains("." + POSTER_PATH)) return true;
            if (c.equals(OVERVIEW) || c.contains("." + OVERVIEW)) return true;
            if (c.equals(VOTE_AVERAGE) || c.contains("." + VOTE_AVERAGE)) return true;
            if (c.equals(RELEASE_DATE) || c.contains("." + RELEASE_DATE)) return true;
            if (c.equals(BACKDROP_PATH) || c.contains("." + BACKDROP_PATH)) return true;
            if (c.equals(ORIGINAL_LANGUAGE) || c.contains("." + ORIGINAL_LANGUAGE)) return true;
            if (c.equals(ORIGINAL_TITLE) || c.contains("." + ORIGINAL_TITLE)) return true;
            if (c.equals(POPULARITY) || c.contains("." + POPULARITY)) return true;
            if (c.equals(VIDEO) || c.contains("." + VIDEO)) return true;
            if (c.equals(VOTE_COUNT) || c.contains("." + VOTE_COUNT)) return true;
            if (c.equals(ADULT) || c.contains("." + ADULT)) return true;
            if (c.equals(FAVORITE) || c.contains("." + FAVORITE)) return true;
        }
        return false;
    }

}
