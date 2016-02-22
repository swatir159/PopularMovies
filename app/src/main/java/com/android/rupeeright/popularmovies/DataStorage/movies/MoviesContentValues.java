package com.android.rupeeright.popularmovies.DataStorage.movies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movies} table.
 */
public class MoviesContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MoviesColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MoviesSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MoviesSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The id of the movie in MovieDB
     */
    public MoviesContentValues putMovieId(int value) {
        mContentValues.put(MoviesColumns.MOVIE_ID, value);
        return this;
    }


    public MoviesContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(MoviesColumns.TITLE, value);
        return this;
    }


    public MoviesContentValues putPosterPath(@Nullable String value) {
        mContentValues.put(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesContentValues putPosterPathNull() {
        mContentValues.putNull(MoviesColumns.POSTER_PATH);
        return this;
    }

    public MoviesContentValues putOverview(@Nullable String value) {
        mContentValues.put(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesContentValues putOverviewNull() {
        mContentValues.putNull(MoviesColumns.OVERVIEW);
        return this;
    }

    public MoviesContentValues putVoteAverage(@Nullable Double value) {
        mContentValues.put(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesContentValues putVoteAverageNull() {
        mContentValues.putNull(MoviesColumns.VOTE_AVERAGE);
        return this;
    }

    public MoviesContentValues putReleaseDate(@Nullable Long value) {
        mContentValues.put(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesContentValues putReleaseDateNull() {
        mContentValues.putNull(MoviesColumns.RELEASE_DATE);
        return this;
    }

    public MoviesContentValues putBackdropPath(@Nullable String value) {
        mContentValues.put(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesContentValues putBackdropPathNull() {
        mContentValues.putNull(MoviesColumns.BACKDROP_PATH);
        return this;
    }

    public MoviesContentValues putOriginalLanguage(@Nullable String value) {
        mContentValues.put(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesContentValues putOriginalLanguageNull() {
        mContentValues.putNull(MoviesColumns.ORIGINAL_LANGUAGE);
        return this;
    }

    public MoviesContentValues putOriginalTitle(@Nullable String value) {
        mContentValues.put(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesContentValues putOriginalTitleNull() {
        mContentValues.putNull(MoviesColumns.ORIGINAL_TITLE);
        return this;
    }

    public MoviesContentValues putPopularity(@Nullable Double value) {
        mContentValues.put(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesContentValues putPopularityNull() {
        mContentValues.putNull(MoviesColumns.POPULARITY);
        return this;
    }

    public MoviesContentValues putVideo(@Nullable Boolean value) {
        mContentValues.put(MoviesColumns.VIDEO, value);
        return this;
    }

    public MoviesContentValues putVideoNull() {
        mContentValues.putNull(MoviesColumns.VIDEO);
        return this;
    }

    public MoviesContentValues putVoteCount(@Nullable Double value) {
        mContentValues.put(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesContentValues putVoteCountNull() {
        mContentValues.putNull(MoviesColumns.VOTE_COUNT);
        return this;
    }

    public MoviesContentValues putAdult(@Nullable Boolean value) {
        mContentValues.put(MoviesColumns.ADULT, value);
        return this;
    }

    public MoviesContentValues putAdultNull() {
        mContentValues.putNull(MoviesColumns.ADULT);
        return this;
    }

    public MoviesContentValues putFavorite(boolean value) {
        mContentValues.put(MoviesColumns.FAVORITE, value);
        return this;
    }

}
