package com.android.rupeeright.popularmovies.DataStorage;

import java.util.Arrays;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.rupeeright.popularmovies.BuildConfig;
import com.android.rupeeright.popularmovies.DataStorage.base.BaseContentProvider;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

public class MovieDetailProvider extends BaseContentProvider {
    private static final String TAG = MovieDetailProvider.class.getSimpleName();

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
    private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

    public static final String AUTHORITY = "com.android.rupeeright.popularmovies";
    public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

    private static final int URI_TYPE_MOVIES = 0;
    private static final int URI_TYPE_MOVIES_ID = 1;

    private static final int URI_TYPE_REVIEWS = 2;
    private static final int URI_TYPE_REVIEWS_ID = 3;

    private static final int URI_TYPE_TRAILERS = 4;
    private static final int URI_TYPE_TRAILERS_ID = 5;


    /****************** my added code *****************/
    private static final int URI_TYPE_MOVIEDB_ID = 6;
    private static final int URI_TYPE_MOVIES_WITH_FAV = 7;
    private static final int URI_TYPE_MOVIES_WITH_DETAILS = 8;
    /**************************************************** */

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.TABLE_NAME, URI_TYPE_MOVIES);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.TABLE_NAME + "/#", URI_TYPE_MOVIES_ID);
        /****************** my added code *****************/
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.TABLE_NAME + "/movieDBID/#", URI_TYPE_MOVIEDB_ID);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.TABLE_NAME + "/with_fav", URI_TYPE_MOVIES_WITH_FAV);
        URI_MATCHER.addURI(AUTHORITY, MoviesColumns.TABLE_NAME + "/Details/#", URI_TYPE_MOVIES_WITH_DETAILS);
        /* ********************************************************************* */
        URI_MATCHER.addURI(AUTHORITY, ReviewsColumns.TABLE_NAME, URI_TYPE_REVIEWS);
        URI_MATCHER.addURI(AUTHORITY, ReviewsColumns.TABLE_NAME + "/#", URI_TYPE_REVIEWS_ID);

        URI_MATCHER.addURI(AUTHORITY, TrailersColumns.TABLE_NAME, URI_TYPE_TRAILERS);
        URI_MATCHER.addURI(AUTHORITY, TrailersColumns.TABLE_NAME + "/#", URI_TYPE_TRAILERS_ID);


    }

    @Override
    protected SQLiteOpenHelper createSqLiteOpenHelper() {
        return MovieDBHelper.getInstance(getContext());
    }

    @Override
    protected boolean hasDebug() {
        return DEBUG;
    }

    @Override
    public String getType(Uri uri) {
        int match = URI_MATCHER.match(uri);
        switch (match) {
            case URI_TYPE_MOVIES:
                return TYPE_CURSOR_DIR + MoviesColumns.TABLE_NAME;
            case URI_TYPE_MOVIES_ID:
                return TYPE_CURSOR_ITEM + MoviesColumns.TABLE_NAME;

            case URI_TYPE_REVIEWS:
                return TYPE_CURSOR_DIR + ReviewsColumns.TABLE_NAME;
            case URI_TYPE_REVIEWS_ID:
                return TYPE_CURSOR_ITEM + ReviewsColumns.TABLE_NAME;

            case URI_TYPE_TRAILERS:
                return TYPE_CURSOR_DIR + TrailersColumns.TABLE_NAME;
            case URI_TYPE_TRAILERS_ID:
                return TYPE_CURSOR_ITEM + TrailersColumns.TABLE_NAME;

            /* ******************************** my added code here *********** */
            case URI_TYPE_MOVIEDB_ID:
                return TYPE_CURSOR_ITEM + MoviesColumns.TABLE_NAME;
            case URI_TYPE_MOVIES_WITH_FAV:
                return TYPE_CURSOR_DIR + MoviesColumns.TABLE_NAME;
            case URI_TYPE_MOVIES_WITH_DETAILS:
                return TYPE_CURSOR_DIR + MoviesColumns.TABLE_NAME;
            /* **************************************************************** */
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
        return super.insert(uri, values);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
        return super.bulkInsert(uri, values);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (DEBUG) Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (DEBUG)
            Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
                    + " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
        QueryParams res = new QueryParams();
        String id = null;
        int matchedId = URI_MATCHER.match(uri);
        switch (matchedId) {
            case URI_TYPE_MOVIES:
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_MOVIES ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }


                res.table = MoviesColumns.TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.TABLE_NAME;
                res.selection = selection;
                res.orderBy = MoviesColumns.DEFAULT_ORDER;
                break;
            case URI_TYPE_MOVIES_ID:

                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_MOVIES_ID ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }

                res.table = MoviesColumns.TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.TABLE_NAME;
                res.selection = selection;
                res.orderBy = MoviesColumns.DEFAULT_ORDER;
                break;
            case URI_TYPE_MOVIES_WITH_DETAILS:

                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_MOVIES_WITH_DETAILS ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }

                res.table = MoviesColumns.TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.TABLE_NAME;
                if (ReviewsColumns.hasColumns(projection)){
                    res.tablesWithJoins += " LEFT OUTER JOIN " + ReviewsColumns.TABLE_NAME + " ON "
                            + MoviesColumns.TABLE_NAME + "."+ MoviesColumns._ID + "="
                            + ReviewsColumns.TABLE_NAME + "."+ ReviewsColumns.MOVIE_ID;

                }
                if (TrailersColumns.hasColumns(projection)){
                    res.tablesWithJoins += " LEFT OUTER JOIN " + TrailersColumns.TABLE_NAME + " ON "
                            + MoviesColumns.TABLE_NAME + "."+ MoviesColumns._ID + "="
                            + TrailersColumns.TABLE_NAME + "."+ TrailersColumns.MOVIE_ID;
                }
                res.orderBy = MoviesColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_REVIEWS:
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_REVIEWS ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                res.table = ReviewsColumns.TABLE_NAME;
                res.idColumn = ReviewsColumns._ID;
                res.tablesWithJoins = ReviewsColumns.TABLE_NAME;
                if (MoviesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MoviesColumns.TABLE_NAME + " AS " + ReviewsColumns.PREFIX_MOVIES + " ON " + ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.MOVIE_ID + "=" + ReviewsColumns.PREFIX_MOVIES + "." + MoviesColumns._ID;
                }
                res.orderBy = ReviewsColumns.DEFAULT_ORDER;
                break;
            case URI_TYPE_REVIEWS_ID:
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_REVIEWS_ID ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                res.table = ReviewsColumns.TABLE_NAME;
                res.idColumn = ReviewsColumns._ID;
                res.tablesWithJoins = ReviewsColumns.TABLE_NAME;
                if (MoviesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MoviesColumns.TABLE_NAME + " AS " + ReviewsColumns.PREFIX_MOVIES + " ON " + ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.MOVIE_ID + "=" + ReviewsColumns.PREFIX_MOVIES + "." + MoviesColumns._ID;
                }
                res.orderBy = ReviewsColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TRAILERS:
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_TRAILERS ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                res.table = TrailersColumns.TABLE_NAME;
                res.idColumn = TrailersColumns._ID;
                res.tablesWithJoins = TrailersColumns.TABLE_NAME;
                if (MoviesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MoviesColumns.TABLE_NAME + " AS " + TrailersColumns.PREFIX_MOVIES + " ON " + TrailersColumns.TABLE_NAME + "." + TrailersColumns.MOVIE_ID + "=" + TrailersColumns.PREFIX_MOVIES + "." + MoviesColumns._ID;
                }
                res.orderBy = TrailersColumns.DEFAULT_ORDER;
                break;

            case URI_TYPE_TRAILERS_ID:
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_TRAILERS_ID ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                res.table = TrailersColumns.TABLE_NAME;
                res.idColumn = TrailersColumns._ID;
                res.tablesWithJoins = TrailersColumns.TABLE_NAME;
                if (MoviesColumns.hasColumns(projection)) {
                    res.tablesWithJoins += " LEFT OUTER JOIN " + MoviesColumns.TABLE_NAME + " AS " + TrailersColumns.PREFIX_MOVIES + " ON " + TrailersColumns.TABLE_NAME + "." + TrailersColumns.MOVIE_ID + "=" + TrailersColumns.PREFIX_MOVIES + "." + MoviesColumns._ID;
                }
                res.orderBy = TrailersColumns.DEFAULT_ORDER;
                break;

            /* **************************************************************** */
            case URI_TYPE_MOVIEDB_ID :
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_MOVIEDB_ID ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                id = uri.getLastPathSegment();

                res.table = MoviesColumns.TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.TABLE_NAME;
                if (ReviewsColumns.hasColumns(projection)){
                    res.tablesWithJoins += " LEFT OUTER JOIN " + ReviewsColumns.TABLE_NAME + " ON "
                            + MoviesColumns.TABLE_NAME + "."+ MoviesColumns._ID + "="
                            + ReviewsColumns.TABLE_NAME + "."+ ReviewsColumns.MOVIE_ID;

                }
                if (TrailersColumns.hasColumns(projection)){
                    res.tablesWithJoins += " LEFT OUTER JOIN " + TrailersColumns.TABLE_NAME + " ON "
                            + MoviesColumns.TABLE_NAME + "."+ MoviesColumns._ID + "="
                            + TrailersColumns.TABLE_NAME + "."+ TrailersColumns.MOVIE_ID;
                }
                res.orderBy = MoviesColumns.DEFAULT_ORDER;
                if (selection != null)
                    res.selection = MoviesColumns.TABLE_NAME + "." + MoviesColumns.MOVIE_ID + " = "  + id +
                                                " + and (" + selection + ")";
                else
                    res.selection = MoviesColumns.TABLE_NAME + "." + MoviesColumns.MOVIE_ID + " = "  + id;
                break;

            case URI_TYPE_MOVIES_WITH_FAV :
                if (PopMoviesConstants.DEBUG)
                {
                    Log.i("PopMovies1", "+++++++++\n+++++++++++case URI_TYPE_MOVIES_WITH_FAV ");
                    if (projection != null) Log.i( "PopMovies1", " projection = " + projection.toString() );
                    if (selection != null) Log.i( "PopMovies1", " selection= " + selection.toString() );
                }
                res.table = MoviesColumns.TABLE_NAME;
                res.idColumn = MoviesColumns._ID;
                res.tablesWithJoins = MoviesColumns.TABLE_NAME;
                res.orderBy = MoviesColumns.DEFAULT_ORDER;
                if (selection != null)
                    res.selection = MoviesColumns.TABLE_NAME + "." + MoviesColumns.FAVORITE + " = TRUE"   +
                            " + and (" + selection + ")";
                else
                    res.selection = MoviesColumns.TABLE_NAME + "." + MoviesColumns.FAVORITE + " = TRUE"  ;
                break;

            /* **************************************************************** */
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }

        switch (matchedId) {
            case URI_TYPE_MOVIES_ID:
            case URI_TYPE_REVIEWS_ID:
            case URI_TYPE_TRAILERS_ID:
            case URI_TYPE_MOVIES_WITH_DETAILS:

                id = uri.getLastPathSegment();
        }
        if (id != null) {
            if (selection != null) {
                res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
            } else {
                res.selection = res.table + "." + res.idColumn + "=" + id;
            }
        } else {
            res.selection = selection;
        }
        return res;
    }
}
