package com.android.rupeeright.popularmovies.DataStorage.trailers;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code trailers} table.
 */
public class TrailersContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return TrailersColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable TrailersSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable TrailersSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The id of the movie in local database, matches with _ID
     */
    public TrailersContentValues putMovieId(long value) {
        mContentValues.put(TrailersColumns.MOVIE_ID, value);
        return this;
    }


    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    public TrailersContentValues putMoviedbId(int value) {
        mContentValues.put(TrailersColumns.MOVIEDB_ID, value);
        return this;
    }


    /**
     * The trailer id of the movie like 533ec654c3a36854480003eb 
     */
    public TrailersContentValues putTrailerId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("trailerId must not be null");
        mContentValues.put(TrailersColumns.TRAILER_ID, value);
        return this;
    }


    /**
     * iso language like en
     */
    public TrailersContentValues putIso6391(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("iso6391 must not be null");
        mContentValues.put(TrailersColumns.ISO_639_1, value);
        return this;
    }


    /**
     * iso language like SUXWAEX2jlg
     */
    public TrailersContentValues putKey(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("key must not be null");
        mContentValues.put(TrailersColumns.KEY, value);
        return this;
    }


    /**
     * name of the trailer like Trailer 1
     */
    public TrailersContentValues putName(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("name must not be null");
        mContentValues.put(TrailersColumns.NAME, value);
        return this;
    }


    /**
     * size of the trailer like 720
     */
    public TrailersContentValues putSize(int value) {
        mContentValues.put(TrailersColumns.SIZE, value);
        return this;
    }


    /**
     * Site like youtube
     */
    public TrailersContentValues putSite(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("site must not be null");
        mContentValues.put(TrailersColumns.SITE, value);
        return this;
    }


    /**
     * type like Trailer
     */
    public TrailersContentValues putType(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("type must not be null");
        mContentValues.put(TrailersColumns.TYPE, value);
        return this;
    }

}
