package com.android.rupeeright.popularmovies.DataStorage.movies;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code movies} table.
 */
public class MoviesCursor extends AbstractCursor implements MoviesModel {
    public MoviesCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(MoviesColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The id of the movie in MovieDB
     */
    public int getMovieId() {
        Integer res = getIntegerOrNull(MoviesColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(MoviesColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterPath() {
        String res = getStringOrNull(MoviesColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOverview() {
        String res = getStringOrNull(MoviesColumns.OVERVIEW);
        return res;
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getVoteAverage() {
        Double res = getDoubleOrNull(MoviesColumns.VOTE_AVERAGE);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Long getReleaseDate() {
        Long res = getLongOrNull(MoviesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBackdropPath() {
        String res = getStringOrNull(MoviesColumns.BACKDROP_PATH);
        return res;
    }

    /**
     * Get the {@code original_language} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalLanguage() {
        String res = getStringOrNull(MoviesColumns.ORIGINAL_LANGUAGE);
        return res;
    }

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalTitle() {
        String res = getStringOrNull(MoviesColumns.ORIGINAL_TITLE);
        return res;
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getPopularity() {
        Double res = getDoubleOrNull(MoviesColumns.POPULARITY);
        return res;
    }

    /**
     * Get the {@code video} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getVideo() {
        Boolean res = getBooleanOrNull(MoviesColumns.VIDEO);
        return res;
    }

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getVoteCount() {
        Double res = getDoubleOrNull(MoviesColumns.VOTE_COUNT);
        return res;
    }

    /**
     * Get the {@code adult} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getAdult() {
        Boolean res = getBooleanOrNull(MoviesColumns.ADULT);
        return res;
    }

    /**
     * Get the {@code favorite} value.
     */
    public boolean getFavorite() {
        Boolean res = getBooleanOrNull(MoviesColumns.FAVORITE);
        if (res == null)
            throw new NullPointerException("The value of 'favorite' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
