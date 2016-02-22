package com.android.rupeeright.popularmovies.DataStorage.reviews;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractSelection;
import com.android.rupeeright.popularmovies.DataStorage.movies.*;

/**
 * Selection for the {@code reviews} table.
 */
public class ReviewsSelection extends AbstractSelection<ReviewsSelection> {
    @Override
    protected Uri baseUri() {
        return ReviewsColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewsCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewsCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public ReviewsCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code ReviewsCursor} object, which is positioned before the first entry, or null.
     */
    public ReviewsCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new ReviewsCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public ReviewsCursor query(Context context) {
        return query(context, null);
    }


    public ReviewsSelection id(long... value) {
        addEquals("reviews." + ReviewsColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection idNot(long... value) {
        addNotEquals("reviews." + ReviewsColumns._ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection orderById(boolean desc) {
        orderBy("reviews." + ReviewsColumns._ID, desc);
        return this;
    }

    public ReviewsSelection orderById() {
        return orderById(false);
    }

    public ReviewsSelection movieId(long... value) {
        addEquals(ReviewsColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection movieIdNot(long... value) {
        addNotEquals(ReviewsColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection movieIdGt(long value) {
        addGreaterThan(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdLt(long value) {
        addLessThan(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection movieIdLtEq(long value) {
        addLessThanOrEquals(ReviewsColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection orderByMovieId(boolean desc) {
        orderBy(ReviewsColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewsSelection orderByMovieId() {
        orderBy(ReviewsColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewsSelection moviesMovieId(int... value) {
        addEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection moviesMovieIdNot(int... value) {
        addNotEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection moviesMovieIdGt(int value) {
        addGreaterThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection moviesMovieIdGtEq(int value) {
        addGreaterThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection moviesMovieIdLt(int value) {
        addLessThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection moviesMovieIdLtEq(int value) {
        addLessThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public ReviewsSelection orderByMoviesMovieId(boolean desc) {
        orderBy(MoviesColumns.MOVIE_ID, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesMovieId() {
        orderBy(MoviesColumns.MOVIE_ID, false);
        return this;
    }

    public ReviewsSelection moviesTitle(String... value) {
        addEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection moviesTitleNot(String... value) {
        addNotEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection moviesTitleLike(String... value) {
        addLike(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection moviesTitleContains(String... value) {
        addContains(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection moviesTitleStartsWith(String... value) {
        addStartsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection moviesTitleEndsWith(String... value) {
        addEndsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public ReviewsSelection orderByMoviesTitle(boolean desc) {
        orderBy(MoviesColumns.TITLE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesTitle() {
        orderBy(MoviesColumns.TITLE, false);
        return this;
    }

    public ReviewsSelection moviesPosterPath(String... value) {
        addEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection moviesPosterPathNot(String... value) {
        addNotEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection moviesPosterPathLike(String... value) {
        addLike(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection moviesPosterPathContains(String... value) {
        addContains(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection moviesPosterPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection moviesPosterPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public ReviewsSelection orderByMoviesPosterPath(boolean desc) {
        orderBy(MoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesPosterPath() {
        orderBy(MoviesColumns.POSTER_PATH, false);
        return this;
    }

    public ReviewsSelection moviesOverview(String... value) {
        addEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection moviesOverviewNot(String... value) {
        addNotEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection moviesOverviewLike(String... value) {
        addLike(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection moviesOverviewContains(String... value) {
        addContains(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection moviesOverviewStartsWith(String... value) {
        addStartsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection moviesOverviewEndsWith(String... value) {
        addEndsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public ReviewsSelection orderByMoviesOverview(boolean desc) {
        orderBy(MoviesColumns.OVERVIEW, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesOverview() {
        orderBy(MoviesColumns.OVERVIEW, false);
        return this;
    }

    public ReviewsSelection moviesVoteAverage(Double... value) {
        addEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection moviesVoteAverageNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection moviesVoteAverageGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection moviesVoteAverageGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection moviesVoteAverageLt(double value) {
        addLessThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection moviesVoteAverageLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public ReviewsSelection orderByMoviesVoteAverage(boolean desc) {
        orderBy(MoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesVoteAverage() {
        orderBy(MoviesColumns.VOTE_AVERAGE, false);
        return this;
    }

    public ReviewsSelection moviesReleaseDate(Long... value) {
        addEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection moviesReleaseDateNot(Long... value) {
        addNotEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection moviesReleaseDateGt(long value) {
        addGreaterThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection moviesReleaseDateGtEq(long value) {
        addGreaterThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection moviesReleaseDateLt(long value) {
        addLessThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection moviesReleaseDateLtEq(long value) {
        addLessThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public ReviewsSelection orderByMoviesReleaseDate(boolean desc) {
        orderBy(MoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesReleaseDate() {
        orderBy(MoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public ReviewsSelection moviesBackdropPath(String... value) {
        addEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection moviesBackdropPathNot(String... value) {
        addNotEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection moviesBackdropPathLike(String... value) {
        addLike(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection moviesBackdropPathContains(String... value) {
        addContains(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection moviesBackdropPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection moviesBackdropPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public ReviewsSelection orderByMoviesBackdropPath(boolean desc) {
        orderBy(MoviesColumns.BACKDROP_PATH, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesBackdropPath() {
        orderBy(MoviesColumns.BACKDROP_PATH, false);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguage(String... value) {
        addEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguageNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguageLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguageContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguageStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalLanguageEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public ReviewsSelection orderByMoviesOriginalLanguage(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesOriginalLanguage() {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, false);
        return this;
    }

    public ReviewsSelection moviesOriginalTitle(String... value) {
        addEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalTitleNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalTitleLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalTitleContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalTitleStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection moviesOriginalTitleEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public ReviewsSelection orderByMoviesOriginalTitle(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_TITLE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesOriginalTitle() {
        orderBy(MoviesColumns.ORIGINAL_TITLE, false);
        return this;
    }

    public ReviewsSelection moviesPopularity(Double... value) {
        addEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection moviesPopularityNot(Double... value) {
        addNotEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection moviesPopularityGt(double value) {
        addGreaterThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection moviesPopularityGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection moviesPopularityLt(double value) {
        addLessThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection moviesPopularityLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public ReviewsSelection orderByMoviesPopularity(boolean desc) {
        orderBy(MoviesColumns.POPULARITY, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesPopularity() {
        orderBy(MoviesColumns.POPULARITY, false);
        return this;
    }

    public ReviewsSelection moviesVideo(Boolean value) {
        addEquals(MoviesColumns.VIDEO, toObjectArray(value));
        return this;
    }

    public ReviewsSelection orderByMoviesVideo(boolean desc) {
        orderBy(MoviesColumns.VIDEO, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesVideo() {
        orderBy(MoviesColumns.VIDEO, false);
        return this;
    }

    public ReviewsSelection moviesVoteCount(Double... value) {
        addEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection moviesVoteCountNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection moviesVoteCountGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection moviesVoteCountGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection moviesVoteCountLt(double value) {
        addLessThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection moviesVoteCountLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public ReviewsSelection orderByMoviesVoteCount(boolean desc) {
        orderBy(MoviesColumns.VOTE_COUNT, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesVoteCount() {
        orderBy(MoviesColumns.VOTE_COUNT, false);
        return this;
    }

    public ReviewsSelection moviesAdult(Boolean value) {
        addEquals(MoviesColumns.ADULT, toObjectArray(value));
        return this;
    }

    public ReviewsSelection orderByMoviesAdult(boolean desc) {
        orderBy(MoviesColumns.ADULT, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesAdult() {
        orderBy(MoviesColumns.ADULT, false);
        return this;
    }

    public ReviewsSelection moviesFavorite(boolean value) {
        addEquals(MoviesColumns.FAVORITE, toObjectArray(value));
        return this;
    }

    public ReviewsSelection orderByMoviesFavorite(boolean desc) {
        orderBy(MoviesColumns.FAVORITE, desc);
        return this;
    }

    public ReviewsSelection orderByMoviesFavorite() {
        orderBy(MoviesColumns.FAVORITE, false);
        return this;
    }

    public ReviewsSelection moviedbId(int... value) {
        addEquals(ReviewsColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection moviedbIdNot(int... value) {
        addNotEquals(ReviewsColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public ReviewsSelection moviedbIdGt(int value) {
        addGreaterThan(ReviewsColumns.MOVIEDB_ID, value);
        return this;
    }

    public ReviewsSelection moviedbIdGtEq(int value) {
        addGreaterThanOrEquals(ReviewsColumns.MOVIEDB_ID, value);
        return this;
    }

    public ReviewsSelection moviedbIdLt(int value) {
        addLessThan(ReviewsColumns.MOVIEDB_ID, value);
        return this;
    }

    public ReviewsSelection moviedbIdLtEq(int value) {
        addLessThanOrEquals(ReviewsColumns.MOVIEDB_ID, value);
        return this;
    }

    public ReviewsSelection orderByMoviedbId(boolean desc) {
        orderBy(ReviewsColumns.MOVIEDB_ID, desc);
        return this;
    }

    public ReviewsSelection orderByMoviedbId() {
        orderBy(ReviewsColumns.MOVIEDB_ID, false);
        return this;
    }

    public ReviewsSelection reviewId(String... value) {
        addEquals(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection reviewIdNot(String... value) {
        addNotEquals(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection reviewIdLike(String... value) {
        addLike(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection reviewIdContains(String... value) {
        addContains(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection reviewIdStartsWith(String... value) {
        addStartsWith(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection reviewIdEndsWith(String... value) {
        addEndsWith(ReviewsColumns.REVIEW_ID, value);
        return this;
    }

    public ReviewsSelection orderByReviewId(boolean desc) {
        orderBy(ReviewsColumns.REVIEW_ID, desc);
        return this;
    }

    public ReviewsSelection orderByReviewId() {
        orderBy(ReviewsColumns.REVIEW_ID, false);
        return this;
    }

    public ReviewsSelection author(String... value) {
        addEquals(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorNot(String... value) {
        addNotEquals(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorLike(String... value) {
        addLike(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorContains(String... value) {
        addContains(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorStartsWith(String... value) {
        addStartsWith(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection authorEndsWith(String... value) {
        addEndsWith(ReviewsColumns.AUTHOR, value);
        return this;
    }

    public ReviewsSelection orderByAuthor(boolean desc) {
        orderBy(ReviewsColumns.AUTHOR, desc);
        return this;
    }

    public ReviewsSelection orderByAuthor() {
        orderBy(ReviewsColumns.AUTHOR, false);
        return this;
    }

    public ReviewsSelection content(String... value) {
        addEquals(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection contentNot(String... value) {
        addNotEquals(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection contentLike(String... value) {
        addLike(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection contentContains(String... value) {
        addContains(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection contentStartsWith(String... value) {
        addStartsWith(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection contentEndsWith(String... value) {
        addEndsWith(ReviewsColumns.CONTENT, value);
        return this;
    }

    public ReviewsSelection orderByContent(boolean desc) {
        orderBy(ReviewsColumns.CONTENT, desc);
        return this;
    }

    public ReviewsSelection orderByContent() {
        orderBy(ReviewsColumns.CONTENT, false);
        return this;
    }

    public ReviewsSelection url(String... value) {
        addEquals(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection urlNot(String... value) {
        addNotEquals(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection urlLike(String... value) {
        addLike(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection urlContains(String... value) {
        addContains(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection urlStartsWith(String... value) {
        addStartsWith(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection urlEndsWith(String... value) {
        addEndsWith(ReviewsColumns.URL, value);
        return this;
    }

    public ReviewsSelection orderByUrl(boolean desc) {
        orderBy(ReviewsColumns.URL, desc);
        return this;
    }

    public ReviewsSelection orderByUrl() {
        orderBy(ReviewsColumns.URL, false);
        return this;
    }
}
