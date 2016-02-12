package com.android.rupeeright.popularmovies.DataStorage;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;

import java.util.Arrays;

public class MovieDetailProvider extends ContentProvider {

    private static final String LOG_TAG = MovieDetailProvider.class.getSimpleName();
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private MovieDBHelper mDBOpenHelper;

    private static final int MOVIE = 1;
    private static final int MOVIE_WITH_ID = 2;

    private static UriMatcher buildUriMatcher() {

        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " UriMatcher");
        }
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE, MOVIE );
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIE+ "/#",  MOVIE_WITH_ID );

        return matcher;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " onCreate");
        }
        mDBOpenHelper = new MovieDBHelper(getContext());

        return true;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " getType");
        }
        final int match=  sUriMatcher.match(uri);
        switch (match)
        {
            case MOVIE :
                return MovieContract.MoviesEntry.CONTENT_TYPE;

            case MOVIE_WITH_ID:
                return MovieContract.MoviesEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri);
        }

    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        if (PopMoviesConstants.DEBUG) {
            Log.d(getContext().getString(R.string.logcat_tag),  LOG_TAG + ":" + " query");
        }
        if (PopMoviesConstants.DEBUG) {
            Log.d(getContext().getString(R.string.logcat_tag),  LOG_TAG + ":" + "query uri=<" + uri + "> selection=<" + selection + "> selectionArgs=<" + Arrays.toString(selectionArgs) + "> sortOrder= <" + sortOrder + ">END");
        }
        Cursor myCursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
            {
                if (PopMoviesConstants.DEBUG) {
                    Log.d(getContext().getString(R.string.logcat_tag),LOG_TAG + ":" + " (query)case : Movie" );
                }

                if (PopMoviesConstants.DEBUG) {
                    Log.d(getContext().getString(R.string.logcat_tag),  LOG_TAG + ":" + "query: Table=< " + MovieContract.MoviesEntry.TABLE_NAME + "> selection=<" + selection +"> projection=<" + Arrays.toString(projection) + "> selectionArgs=<" + Arrays.toString(selectionArgs) + "> sortOrder=<" + sortOrder +">END");
                }
                myCursor = mDBOpenHelper.getReadableDatabase().query(
                        MovieContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case MOVIE_WITH_ID:
            {
                if (PopMoviesConstants.DEBUG) {
                    Log.d(getContext().getString(R.string.logcat_tag),LOG_TAG + ":" + " case : MOVIE_WITH_ID" );
                }

                int id = MovieContract.MoviesEntry.getMovieDBIDFromUri(uri);
                if (id >0 ) {
                    if (selection != null) {
                        selection = MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_MOVIE_ID + "=" + id + " and (" + selection + ")";
                    } else {
                        selection = MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_MOVIE_ID + "=" + id;
                    }
                }

                if (PopMoviesConstants.DEBUG) {
                    Log.d(getContext().getString(R.string.logcat_tag),  LOG_TAG + ":" + "query: Table=< " + MovieContract.MoviesEntry.TABLE_NAME + "> selection=<" + selection +"> projection=<" + Arrays.toString(projection) + "> selectionArgs=<" + Arrays.toString(selectionArgs) + "> sortOrder=<" + sortOrder +">END");
                }
                myCursor = mDBOpenHelper.getReadableDatabase().query(
                        MovieContract.MoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            default:
                if (PopMoviesConstants.DEBUG) {
                    Log.d(getContext().getString(R.string.logcat_tag),LOG_TAG + ":" + " case : default" );
                }
                throw new UnsupportedOperationException("Not yet implemented");
        }


        myCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return myCursor;
    }

    public MovieDetailProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " delete");
        }
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsdeleted;

        if ( null== selection) selection = "1";

        switch (match){
            case MOVIE :
                rowsdeleted = db.delete(MovieContract.MoviesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }

        if (rowsdeleted !=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsdeleted;
    }


    private void getNormalizeDate(ContentValues values) {
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " getNormalizeDate");
        }
        // normalize the date value
        if (values.containsKey(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE)) {
            long dateValue = values.getAsLong(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE);
            values.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, Utilities.normalizeDate(dateValue));
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " insert");
        }

        // TODO: Implement this to handle requests to insert a new row.
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIE: {
                getNormalizeDate(values);

                long _id = db.insert(MovieContract.MoviesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MovieContract.MoviesEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " update");
        }
        // TODO: Implement this to handle requests to update one or more rows.
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsupdated;

        switch (match){
            case MOVIE :
                getNormalizeDate(values);
                rowsupdated = db.update(MovieContract.MoviesEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }

        if (rowsupdated !=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsupdated;
    }
    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (PopMoviesConstants.DEBUG) {
            Log.d("PopMovies", LOG_TAG + ":" + " bulkupdate");
        }
        final SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        getNormalizeDate(value);
                        long _id = db.insert(MovieContract.MoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    // You do not need to call this method. This is a method specifically to assist the testing
    // framework in running smoothly. You can read more at:
    // http://developer.android.com/reference/android/content/ContentProvider.html#shutdown()
    @Override
    @TargetApi(11)
    public void shutdown() {
        mDBOpenHelper.close();
        super.shutdown();
    }

}
