package com.android.rupeeright.popularmovies.DataStorage.reviews;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.*;

/**
 * Cursor wrapper for the {@code reviews} table.
 */
public class ReviewsCursor extends AbstractCursor implements ReviewsModel {
    public ReviewsCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(ReviewsColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The id of the movie in local database, matches with _ID
     */
    public long getMovieId() {
        Long res = getLongOrNull(ReviewsColumns.MOVIE_ID);
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
        Integer res = getIntegerOrNull(ReviewsColumns.MOVIEDB_ID);
        if (res == null)
            throw new NullPointerException("The value of 'moviedb_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * The review id of the movie like 5010553819c2952d1b000451 
     * Cannot be {@code null}.
     */
    @NonNull
    public String getReviewId() {
        String res = getStringOrNull(ReviewsColumns.REVIEW_ID);
        if (res == null)
            throw new NullPointerException("The value of 'review_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * author of the review like Travis Bell
     * Cannot be {@code null}.
     */
    @NonNull
    public String getAuthor() {
        String res = getStringOrNull(ReviewsColumns.AUTHOR);
        if (res == null)
            throw new NullPointerException("The value of 'author' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * actual review content  
     * Cannot be {@code null}.
     */
    @NonNull
    public String getContent() {
        String res = getStringOrNull(ReviewsColumns.CONTENT);
        if (res == null)
            throw new NullPointerException("The value of 'content' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * url of the review like http://j.mp/QSjAK2 
     * Cannot be {@code null}.
     */
    @NonNull
    public String getUrl() {
        String res = getStringOrNull(ReviewsColumns.URL);
        if (res == null)
            throw new NullPointerException("The value of 'url' in the database was null, which is not allowed according to the model definition");
        return res;
    }
}
