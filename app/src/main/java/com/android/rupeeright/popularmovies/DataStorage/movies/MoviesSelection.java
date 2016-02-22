package com.android.rupeeright.popularmovies.DataStorage.movies;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractSelection;

/**
 * Selection for the {@code movies} table.
 */
public class MoviesSelection extends AbstractSelection<MoviesSelection> {
    @Override
    protected Uri baseUri() {
        return MoviesColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MoviesCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MoviesCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MoviesCursor} object, which is positioned before the first entry, or null.
     */
    public MoviesCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MoviesCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MoviesCursor query(Context context) {
        return query(context, null);
    }


    public MoviesSelection id(long... value) {
        addEquals("movies." + MoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection idNot(long... value) {
        addNotEquals("movies." + MoviesColumns._ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection orderById(boolean desc) {
        orderBy("movies." + MoviesColumns._ID, desc);
        return this;
    }

    public MoviesSelection orderById() {
        return orderById(false);
    }

    public MoviesSelection movieId(int... value) {
        addEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection movieIdNot(int... value) {
        addNotEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public MoviesSelection movieIdGt(int value) {
        addGreaterThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public MoviesSelection movieIdGtEq(int value) {
        addGreaterThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public MoviesSelection movieIdLt(int value) {
        addLessThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public MoviesSelection movieIdLtEq(int value) {
        addLessThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public MoviesSelection orderByMovieId(boolean desc) {
        orderBy(MoviesColumns.MOVIE_ID, desc);
        return this;
    }

    public MoviesSelection orderByMovieId() {
        orderBy(MoviesColumns.MOVIE_ID, false);
        return this;
    }

    public MoviesSelection title(String... value) {
        addEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleNot(String... value) {
        addNotEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleLike(String... value) {
        addLike(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleContains(String... value) {
        addContains(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleStartsWith(String... value) {
        addStartsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection titleEndsWith(String... value) {
        addEndsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public MoviesSelection orderByTitle(boolean desc) {
        orderBy(MoviesColumns.TITLE, desc);
        return this;
    }

    public MoviesSelection orderByTitle() {
        orderBy(MoviesColumns.TITLE, false);
        return this;
    }

    public MoviesSelection posterPath(String... value) {
        addEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathNot(String... value) {
        addNotEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathLike(String... value) {
        addLike(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathContains(String... value) {
        addContains(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection posterPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public MoviesSelection orderByPosterPath(boolean desc) {
        orderBy(MoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public MoviesSelection orderByPosterPath() {
        orderBy(MoviesColumns.POSTER_PATH, false);
        return this;
    }

    public MoviesSelection overview(String... value) {
        addEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewNot(String... value) {
        addNotEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewLike(String... value) {
        addLike(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewContains(String... value) {
        addContains(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewStartsWith(String... value) {
        addStartsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection overviewEndsWith(String... value) {
        addEndsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public MoviesSelection orderByOverview(boolean desc) {
        orderBy(MoviesColumns.OVERVIEW, desc);
        return this;
    }

    public MoviesSelection orderByOverview() {
        orderBy(MoviesColumns.OVERVIEW, false);
        return this;
    }

    public MoviesSelection voteAverage(Double... value) {
        addEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageLt(double value) {
        addLessThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection voteAverageLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public MoviesSelection orderByVoteAverage(boolean desc) {
        orderBy(MoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public MoviesSelection orderByVoteAverage() {
        orderBy(MoviesColumns.VOTE_AVERAGE, false);
        return this;
    }

    public MoviesSelection releaseDate(Long... value) {
        addEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateNot(Long... value) {
        addNotEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateGt(long value) {
        addGreaterThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateGtEq(long value) {
        addGreaterThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateLt(long value) {
        addLessThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection releaseDateLtEq(long value) {
        addLessThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public MoviesSelection orderByReleaseDate(boolean desc) {
        orderBy(MoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public MoviesSelection orderByReleaseDate() {
        orderBy(MoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public MoviesSelection backdropPath(String... value) {
        addEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection backdropPathNot(String... value) {
        addNotEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection backdropPathLike(String... value) {
        addLike(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection backdropPathContains(String... value) {
        addContains(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection backdropPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection backdropPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public MoviesSelection orderByBackdropPath(boolean desc) {
        orderBy(MoviesColumns.BACKDROP_PATH, desc);
        return this;
    }

    public MoviesSelection orderByBackdropPath() {
        orderBy(MoviesColumns.BACKDROP_PATH, false);
        return this;
    }

    public MoviesSelection originalLanguage(String... value) {
        addEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection originalLanguageNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection originalLanguageLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection originalLanguageContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection originalLanguageStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection originalLanguageEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public MoviesSelection orderByOriginalLanguage(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, desc);
        return this;
    }

    public MoviesSelection orderByOriginalLanguage() {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, false);
        return this;
    }

    public MoviesSelection originalTitle(String... value) {
        addEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection originalTitleNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection originalTitleLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection originalTitleContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection originalTitleStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection originalTitleEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public MoviesSelection orderByOriginalTitle(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_TITLE, desc);
        return this;
    }

    public MoviesSelection orderByOriginalTitle() {
        orderBy(MoviesColumns.ORIGINAL_TITLE, false);
        return this;
    }

    public MoviesSelection popularity(Double... value) {
        addEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection popularityNot(Double... value) {
        addNotEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection popularityGt(double value) {
        addGreaterThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection popularityGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection popularityLt(double value) {
        addLessThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection popularityLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public MoviesSelection orderByPopularity(boolean desc) {
        orderBy(MoviesColumns.POPULARITY, desc);
        return this;
    }

    public MoviesSelection orderByPopularity() {
        orderBy(MoviesColumns.POPULARITY, false);
        return this;
    }

    public MoviesSelection video(Boolean value) {
        addEquals(MoviesColumns.VIDEO, toObjectArray(value));
        return this;
    }

    public MoviesSelection orderByVideo(boolean desc) {
        orderBy(MoviesColumns.VIDEO, desc);
        return this;
    }

    public MoviesSelection orderByVideo() {
        orderBy(MoviesColumns.VIDEO, false);
        return this;
    }

    public MoviesSelection voteCount(Double... value) {
        addEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection voteCountNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection voteCountGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection voteCountGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection voteCountLt(double value) {
        addLessThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection voteCountLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public MoviesSelection orderByVoteCount(boolean desc) {
        orderBy(MoviesColumns.VOTE_COUNT, desc);
        return this;
    }

    public MoviesSelection orderByVoteCount() {
        orderBy(MoviesColumns.VOTE_COUNT, false);
        return this;
    }

    public MoviesSelection adult(Boolean value) {
        addEquals(MoviesColumns.ADULT, toObjectArray(value));
        return this;
    }

    public MoviesSelection orderByAdult(boolean desc) {
        orderBy(MoviesColumns.ADULT, desc);
        return this;
    }

    public MoviesSelection orderByAdult() {
        orderBy(MoviesColumns.ADULT, false);
        return this;
    }

    public MoviesSelection favorite(boolean value) {
        addEquals(MoviesColumns.FAVORITE, toObjectArray(value));
        return this;
    }

    public MoviesSelection orderByFavorite(boolean desc) {
        orderBy(MoviesColumns.FAVORITE, desc);
        return this;
    }

    public MoviesSelection orderByFavorite() {
        orderBy(MoviesColumns.FAVORITE, false);
        return this;
    }
}
