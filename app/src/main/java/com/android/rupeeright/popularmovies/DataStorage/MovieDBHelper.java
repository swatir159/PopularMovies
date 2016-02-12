package com.android.rupeeright.popularmovies.DataStorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

/**
 * Created by swatir on 2/9/2016.
 */
public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = MovieDBHelper.class.getSimpleName();
    private static final int DATABASE_VERSION=1;
    static final String DATABASE_NAME = "movie.db";

    public MovieDBHelper( Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " Constructor ");
        }

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " onCreate");
        }
        final String SQL_CREATE_MOVIESENTRY = "CREATE TABLE "+ MovieContract.MoviesEntry.TABLE_NAME + " (" +
                MovieContract.MoviesEntry._ID                      + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH     + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_MOVIE_ID        + " INTEGER UNIQUE NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_ORIGINAL_LAN      + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_ORIGINAL_TITLE    + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_OVERVIEW          + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_RELEASE_DATE      + " INTEGER NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_POSTER_PATH       + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_POPULARITY        + " REAL NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_TITLE             + " TEXT NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_VIDEO             + " BOOLEAN NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE      + " REAL NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_VOTE_COUNT        + " INTEGER NOT NULL, " +
                MovieContract.MoviesEntry.COLUMN_ADULT             + " BOOLEAN NOT NULL " +
                ");";
        db.execSQL(SQL_CREATE_MOVIESENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " onUpgrade ");
        }
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MoviesEntry.TABLE_NAME);
        onCreate(db);

    }
}
