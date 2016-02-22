package com.android.rupeeright.popularmovies.DataStorage.trailers;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.*;

/**
 * Cursor wrapper for the {@code trailers} table.
 */
public class TrailersCursor extends AbstractCursor implements TrailersModel {
    public TrailersCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(TrailersColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The id of the movie in local database, matches with _ID
     */
    public long getMovieId() {
        Long res = getLongOrNull(TrailersColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The id of the movie in MovieDB
     */
    public int getMoviesMovieId() {
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
    public String getMoviesTitle() {
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
    public String getMoviesPosterPath() {
        String res = getStringOrNull(MoviesColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviesOverview() {
        String res = getStringOrNull(MoviesColumns.OVERVIEW);
        return res;
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getMoviesVoteAverage() {
        Double res = getDoubleOrNull(MoviesColumns.VOTE_AVERAGE);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    public Long getMoviesReleaseDate() {
        Long res = getLongOrNull(MoviesColumns.RELEASE_DATE);
        return res;
    }

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviesBackdropPath() {
        String res = getStringOrNull(MoviesColumns.BACKDROP_PATH);
        return res;
    }

    /**
     * Get the {@code original_language} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviesOriginalLanguage() {
        String res = getStringOrNull(MoviesColumns.ORIGINAL_LANGUAGE);
        return res;
    }

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getMoviesOriginalTitle() {
        String res = getStringOrNull(MoviesColumns.ORIGINAL_TITLE);
        return res;
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getMoviesPopularity() {
        Double res = getDoubleOrNull(MoviesColumns.POPULARITY);
        return res;
    }

    /**
     * Get the {@code video} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getMoviesVideo() {
        Boolean res = getBooleanOrNull(MoviesColumns.VIDEO);
        return res;
    }

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    public Double getMoviesVoteCount() {
        Double res = getDoubleOrNull(MoviesColumns.VOTE_COUNT);
        return res;
    }

    /**
     * Get the {@code adult} value.
     * Can be {@code null}.
     */
    @Nullable
    public Boolean getMoviesAdult() {
        Boolean res = getBooleanOrNull(MoviesColumns.ADULT);
        return res;
    }

    /**
     * Get the {@code favorite} value.
     */
    public boolean getMoviesFavorite() {
        Boolean res = getBooleanOrNull(MoviesColumns.FAVORITE);
        if (res == null)
            throw new NullPointerException("The value of 'favorite' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    public int getMoviedbId() {
        Integer res = getIntegerOrNull(TrailersColumns.MOVIEDB_ID);
        if (res == null)
            throw new NullPointerException("The value of 'moviedb_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The trailer id of the movie like 533ec654c3a36854480003eb 
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTrailerId() {
        String res = getStringOrNull(TrailersColumns.TRAILER_ID);
        if (res == null)
            throw new NullPointerException("The value of 'trailer_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * iso language like en
     * Cannot be {@code null}.
     */
    @NonNull
    public String getIso6391() {
        String res = getStringOrNull(TrailersColumns.ISO_639_1);
        if (res == null)
            throw new NullPointerException("The value of 'iso_639_1' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * iso language like SUXWAEX2jlg
     * Cannot be {@code null}.
     */
    @NonNull
    public String getKey() {
        String res = getStringOrNull(TrailersColumns.KEY);
        if (res == null)
            throw new NullPointerException("The value of 'key' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * name of the trailer like Trailer 1
     * Cannot be {@code null}.
     */
    @NonNull
    public String getName() {
        String res = getStringOrNull(TrailersColumns.NAME);
        if (res == null)
            throw new NullPointerException("The value of 'name' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * size of the trailer like 720
     */
    public int getSize() {
        Integer res = getIntegerOrNull(TrailersColumns.SIZE);
        if (res == null)
            throw new NullPointerException("The value of 'size' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Site like youtube
     * Cannot be {@code null}.
     */
    @NonNull
    public String getSite() {
        String res = getStringOrNull(TrailersColumns.SITE);
        if (res == null)
            throw new NullPointerException("The value of 'site' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * type like Trailer
     * Cannot be {@code null}.
     */
    @NonNull
    public String getType() {
        String res = getStringOrNull(TrailersColumns.TYPE);
        if (res == null)
            throw new NullPointerException("The value of 'type' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
