package com.android.rupeeright.popularmovies.DataStorage;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.android.rupeeright.popularmovies.BuildConfig;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;

public class MovieDBHelper extends SQLiteOpenHelper {
    private static final String TAG = MovieDBHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "movie.db";
    private static final int DATABASE_VERSION = 3;
    private static MovieDBHelper sInstance;
    private final Context mContext;
    private final MovieDBHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_MOVIES = "CREATE TABLE IF NOT EXISTS "
            + MoviesColumns.TABLE_NAME + " ( "
            + MoviesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MoviesColumns.MOVIE_ID + " INTEGER NOT NULL, "
            + MoviesColumns.TITLE + " TEXT NOT NULL, "
            + MoviesColumns.POSTER_PATH + " TEXT, "
            + MoviesColumns.OVERVIEW + " TEXT, "
            + MoviesColumns.VOTE_AVERAGE + " REAL, "
            + MoviesColumns.RELEASE_DATE + " INTEGER, "
            + MoviesColumns.BACKDROP_PATH + " TEXT, "
            + MoviesColumns.ORIGINAL_LANGUAGE + " TEXT, "
            + MoviesColumns.ORIGINAL_TITLE + " TEXT, "
            + MoviesColumns.POPULARITY + " REAL, "
            + MoviesColumns.VIDEO + " INTEGER, "
            + MoviesColumns.VOTE_COUNT + " REAL, "
            + MoviesColumns.ADULT + " INTEGER, "
            + MoviesColumns.FAVORITE + " INTEGER NOT NULL "
            + ", CONSTRAINT unique_movie_id UNIQUE (movie_id) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_MOVIES_MOVIE_ID = "CREATE INDEX IDX_MOVIES_MOVIE_ID "
            + " ON " + MoviesColumns.TABLE_NAME + " ( " + MoviesColumns.MOVIE_ID + " );";

    public static final String SQL_CREATE_INDEX_MOVIES_TITLE = "CREATE INDEX IDX_MOVIES_TITLE "
            + " ON " + MoviesColumns.TABLE_NAME + " ( " + MoviesColumns.TITLE + " );";

    public static final String SQL_CREATE_TABLE_REVIEWS = "CREATE TABLE IF NOT EXISTS "
            + ReviewsColumns.TABLE_NAME + " ( "
            + ReviewsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ReviewsColumns.MOVIE_ID + " INTEGER NOT NULL, "
            + ReviewsColumns.MOVIEDB_ID + " INTEGER NOT NULL, "
            + ReviewsColumns.REVIEW_ID + " TEXT NOT NULL, "
            + ReviewsColumns.AUTHOR + " TEXT NOT NULL, "
            + ReviewsColumns.CONTENT + " TEXT NOT NULL, "
            + ReviewsColumns.URL + " TEXT NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + ReviewsColumns.MOVIE_ID + ") REFERENCES movies (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_reivew_url UNIQUE (url) ON CONFLICT IGNORE"
            + " );";

    public static final String SQL_CREATE_INDEX_REVIEWS_REVIEW_ID = "CREATE INDEX IDX_REVIEWS_REVIEW_ID "
            + " ON " + ReviewsColumns.TABLE_NAME + " ( " + ReviewsColumns.REVIEW_ID + " );";

    public static final String SQL_CREATE_TABLE_TRAILERS = "CREATE TABLE IF NOT EXISTS "
            + TrailersColumns.TABLE_NAME + " ( "
            + TrailersColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrailersColumns.MOVIE_ID + " INTEGER NOT NULL, "
            + TrailersColumns.MOVIEDB_ID + " INTEGER NOT NULL, "
            + TrailersColumns.TRAILER_ID + " TEXT NOT NULL, "
            + TrailersColumns.ISO_639_1 + " TEXT NOT NULL, "
            + TrailersColumns.KEY + " TEXT NOT NULL, "
            + TrailersColumns.NAME + " TEXT NOT NULL, "
            + TrailersColumns.SIZE + " INTEGER NOT NULL, "
            + TrailersColumns.SITE + " TEXT NOT NULL, "
            + TrailersColumns.TYPE + " TEXT NOT NULL "
            + ", CONSTRAINT fk_movie_id FOREIGN KEY (" + TrailersColumns.MOVIE_ID + ") REFERENCES movies (_id) ON DELETE CASCADE"
            + ", CONSTRAINT unique_trailer_id UNIQUE (trailer_id, iso_639_1, site, type) ON CONFLICT REPLACE"
            + " );";

    public static final String SQL_CREATE_INDEX_TRAILERS_TRAILER_ID = "CREATE INDEX IDX_TRAILERS_TRAILER_ID "
            + " ON " + TrailersColumns.TABLE_NAME + " ( " + TrailersColumns.TRAILER_ID + " );";

    // @formatter:on

    public static MovieDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static MovieDBHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static MovieDBHelper newInstancePreHoneycomb(Context context) {
        return new MovieDBHelper(context);
    }

    private MovieDBHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new MovieDBHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static MovieDBHelper newInstancePostHoneycomb(Context context) {
        return new MovieDBHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private MovieDBHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new MovieDBHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_MOVIES);
        db.execSQL(SQL_CREATE_INDEX_MOVIES_MOVIE_ID);
        db.execSQL(SQL_CREATE_INDEX_MOVIES_TITLE);
        db.execSQL(SQL_CREATE_TABLE_REVIEWS);
        db.execSQL(SQL_CREATE_INDEX_REVIEWS_REVIEW_ID);
        db.execSQL(SQL_CREATE_TABLE_TRAILERS);
        db.execSQL(SQL_CREATE_INDEX_TRAILERS_TRAILER_ID);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
