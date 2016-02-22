package com.android.rupeeright.popularmovies.DataStorage.trailers;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractSelection;
import com.android.rupeeright.popularmovies.DataStorage.movies.*;

/**
 * Selection for the {@code trailers} table.
 */
public class TrailersSelection extends AbstractSelection<TrailersSelection> {
    @Override
    protected Uri baseUri() {
        return TrailersColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailersCursor} object, which is positioned before the first entry, or null.
     */
    public TrailersCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public TrailersCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code TrailersCursor} object, which is positioned before the first entry, or null.
     */
    public TrailersCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new TrailersCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public TrailersCursor query(Context context) {
        return query(context, null);
    }


    public TrailersSelection id(long... value) {
        addEquals("trailers." + TrailersColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection idNot(long... value) {
        addNotEquals("trailers." + TrailersColumns._ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection orderById(boolean desc) {
        orderBy("trailers." + TrailersColumns._ID, desc);
        return this;
    }

    public TrailersSelection orderById() {
        return orderById(false);
    }

    public TrailersSelection movieId(long... value) {
        addEquals(TrailersColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection movieIdNot(long... value) {
        addNotEquals(TrailersColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection movieIdGt(long value) {
        addGreaterThan(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdGtEq(long value) {
        addGreaterThanOrEquals(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdLt(long value) {
        addLessThan(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection movieIdLtEq(long value) {
        addLessThanOrEquals(TrailersColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection orderByMovieId(boolean desc) {
        orderBy(TrailersColumns.MOVIE_ID, desc);
        return this;
    }

    public TrailersSelection orderByMovieId() {
        orderBy(TrailersColumns.MOVIE_ID, false);
        return this;
    }

    public TrailersSelection moviesMovieId(int... value) {
        addEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection moviesMovieIdNot(int... value) {
        addNotEquals(MoviesColumns.MOVIE_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection moviesMovieIdGt(int value) {
        addGreaterThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection moviesMovieIdGtEq(int value) {
        addGreaterThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection moviesMovieIdLt(int value) {
        addLessThan(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection moviesMovieIdLtEq(int value) {
        addLessThanOrEquals(MoviesColumns.MOVIE_ID, value);
        return this;
    }

    public TrailersSelection orderByMoviesMovieId(boolean desc) {
        orderBy(MoviesColumns.MOVIE_ID, desc);
        return this;
    }

    public TrailersSelection orderByMoviesMovieId() {
        orderBy(MoviesColumns.MOVIE_ID, false);
        return this;
    }

    public TrailersSelection moviesTitle(String... value) {
        addEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection moviesTitleNot(String... value) {
        addNotEquals(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection moviesTitleLike(String... value) {
        addLike(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection moviesTitleContains(String... value) {
        addContains(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection moviesTitleStartsWith(String... value) {
        addStartsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection moviesTitleEndsWith(String... value) {
        addEndsWith(MoviesColumns.TITLE, value);
        return this;
    }

    public TrailersSelection orderByMoviesTitle(boolean desc) {
        orderBy(MoviesColumns.TITLE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesTitle() {
        orderBy(MoviesColumns.TITLE, false);
        return this;
    }

    public TrailersSelection moviesPosterPath(String... value) {
        addEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection moviesPosterPathNot(String... value) {
        addNotEquals(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection moviesPosterPathLike(String... value) {
        addLike(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection moviesPosterPathContains(String... value) {
        addContains(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection moviesPosterPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection moviesPosterPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.POSTER_PATH, value);
        return this;
    }

    public TrailersSelection orderByMoviesPosterPath(boolean desc) {
        orderBy(MoviesColumns.POSTER_PATH, desc);
        return this;
    }

    public TrailersSelection orderByMoviesPosterPath() {
        orderBy(MoviesColumns.POSTER_PATH, false);
        return this;
    }

    public TrailersSelection moviesOverview(String... value) {
        addEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection moviesOverviewNot(String... value) {
        addNotEquals(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection moviesOverviewLike(String... value) {
        addLike(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection moviesOverviewContains(String... value) {
        addContains(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection moviesOverviewStartsWith(String... value) {
        addStartsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection moviesOverviewEndsWith(String... value) {
        addEndsWith(MoviesColumns.OVERVIEW, value);
        return this;
    }

    public TrailersSelection orderByMoviesOverview(boolean desc) {
        orderBy(MoviesColumns.OVERVIEW, desc);
        return this;
    }

    public TrailersSelection orderByMoviesOverview() {
        orderBy(MoviesColumns.OVERVIEW, false);
        return this;
    }

    public TrailersSelection moviesVoteAverage(Double... value) {
        addEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection moviesVoteAverageNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection moviesVoteAverageGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection moviesVoteAverageGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection moviesVoteAverageLt(double value) {
        addLessThan(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection moviesVoteAverageLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_AVERAGE, value);
        return this;
    }

    public TrailersSelection orderByMoviesVoteAverage(boolean desc) {
        orderBy(MoviesColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesVoteAverage() {
        orderBy(MoviesColumns.VOTE_AVERAGE, false);
        return this;
    }

    public TrailersSelection moviesReleaseDate(Long... value) {
        addEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection moviesReleaseDateNot(Long... value) {
        addNotEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection moviesReleaseDateGt(long value) {
        addGreaterThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection moviesReleaseDateGtEq(long value) {
        addGreaterThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection moviesReleaseDateLt(long value) {
        addLessThan(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection moviesReleaseDateLtEq(long value) {
        addLessThanOrEquals(MoviesColumns.RELEASE_DATE, value);
        return this;
    }

    public TrailersSelection orderByMoviesReleaseDate(boolean desc) {
        orderBy(MoviesColumns.RELEASE_DATE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesReleaseDate() {
        orderBy(MoviesColumns.RELEASE_DATE, false);
        return this;
    }

    public TrailersSelection moviesBackdropPath(String... value) {
        addEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection moviesBackdropPathNot(String... value) {
        addNotEquals(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection moviesBackdropPathLike(String... value) {
        addLike(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection moviesBackdropPathContains(String... value) {
        addContains(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection moviesBackdropPathStartsWith(String... value) {
        addStartsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection moviesBackdropPathEndsWith(String... value) {
        addEndsWith(MoviesColumns.BACKDROP_PATH, value);
        return this;
    }

    public TrailersSelection orderByMoviesBackdropPath(boolean desc) {
        orderBy(MoviesColumns.BACKDROP_PATH, desc);
        return this;
    }

    public TrailersSelection orderByMoviesBackdropPath() {
        orderBy(MoviesColumns.BACKDROP_PATH, false);
        return this;
    }

    public TrailersSelection moviesOriginalLanguage(String... value) {
        addEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection moviesOriginalLanguageNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection moviesOriginalLanguageLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection moviesOriginalLanguageContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection moviesOriginalLanguageStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection moviesOriginalLanguageEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public TrailersSelection orderByMoviesOriginalLanguage(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesOriginalLanguage() {
        orderBy(MoviesColumns.ORIGINAL_LANGUAGE, false);
        return this;
    }

    public TrailersSelection moviesOriginalTitle(String... value) {
        addEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection moviesOriginalTitleNot(String... value) {
        addNotEquals(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection moviesOriginalTitleLike(String... value) {
        addLike(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection moviesOriginalTitleContains(String... value) {
        addContains(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection moviesOriginalTitleStartsWith(String... value) {
        addStartsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection moviesOriginalTitleEndsWith(String... value) {
        addEndsWith(MoviesColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public TrailersSelection orderByMoviesOriginalTitle(boolean desc) {
        orderBy(MoviesColumns.ORIGINAL_TITLE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesOriginalTitle() {
        orderBy(MoviesColumns.ORIGINAL_TITLE, false);
        return this;
    }

    public TrailersSelection moviesPopularity(Double... value) {
        addEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection moviesPopularityNot(Double... value) {
        addNotEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection moviesPopularityGt(double value) {
        addGreaterThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection moviesPopularityGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection moviesPopularityLt(double value) {
        addLessThan(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection moviesPopularityLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.POPULARITY, value);
        return this;
    }

    public TrailersSelection orderByMoviesPopularity(boolean desc) {
        orderBy(MoviesColumns.POPULARITY, desc);
        return this;
    }

    public TrailersSelection orderByMoviesPopularity() {
        orderBy(MoviesColumns.POPULARITY, false);
        return this;
    }

    public TrailersSelection moviesVideo(Boolean value) {
        addEquals(MoviesColumns.VIDEO, toObjectArray(value));
        return this;
    }

    public TrailersSelection orderByMoviesVideo(boolean desc) {
        orderBy(MoviesColumns.VIDEO, desc);
        return this;
    }

    public TrailersSelection orderByMoviesVideo() {
        orderBy(MoviesColumns.VIDEO, false);
        return this;
    }

    public TrailersSelection moviesVoteCount(Double... value) {
        addEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection moviesVoteCountNot(Double... value) {
        addNotEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection moviesVoteCountGt(double value) {
        addGreaterThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection moviesVoteCountGtEq(double value) {
        addGreaterThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection moviesVoteCountLt(double value) {
        addLessThan(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection moviesVoteCountLtEq(double value) {
        addLessThanOrEquals(MoviesColumns.VOTE_COUNT, value);
        return this;
    }

    public TrailersSelection orderByMoviesVoteCount(boolean desc) {
        orderBy(MoviesColumns.VOTE_COUNT, desc);
        return this;
    }

    public TrailersSelection orderByMoviesVoteCount() {
        orderBy(MoviesColumns.VOTE_COUNT, false);
        return this;
    }

    public TrailersSelection moviesAdult(Boolean value) {
        addEquals(MoviesColumns.ADULT, toObjectArray(value));
        return this;
    }

    public TrailersSelection orderByMoviesAdult(boolean desc) {
        orderBy(MoviesColumns.ADULT, desc);
        return this;
    }

    public TrailersSelection orderByMoviesAdult() {
        orderBy(MoviesColumns.ADULT, false);
        return this;
    }

    public TrailersSelection moviesFavorite(boolean value) {
        addEquals(MoviesColumns.FAVORITE, toObjectArray(value));
        return this;
    }

    public TrailersSelection orderByMoviesFavorite(boolean desc) {
        orderBy(MoviesColumns.FAVORITE, desc);
        return this;
    }

    public TrailersSelection orderByMoviesFavorite() {
        orderBy(MoviesColumns.FAVORITE, false);
        return this;
    }

    public TrailersSelection moviedbId(int... value) {
        addEquals(TrailersColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection moviedbIdNot(int... value) {
        addNotEquals(TrailersColumns.MOVIEDB_ID, toObjectArray(value));
        return this;
    }

    public TrailersSelection moviedbIdGt(int value) {
        addGreaterThan(TrailersColumns.MOVIEDB_ID, value);
        return this;
    }

    public TrailersSelection moviedbIdGtEq(int value) {
        addGreaterThanOrEquals(TrailersColumns.MOVIEDB_ID, value);
        return this;
    }

    public TrailersSelection moviedbIdLt(int value) {
        addLessThan(TrailersColumns.MOVIEDB_ID, value);
        return this;
    }

    public TrailersSelection moviedbIdLtEq(int value) {
        addLessThanOrEquals(TrailersColumns.MOVIEDB_ID, value);
        return this;
    }

    public TrailersSelection orderByMoviedbId(boolean desc) {
        orderBy(TrailersColumns.MOVIEDB_ID, desc);
        return this;
    }

    public TrailersSelection orderByMoviedbId() {
        orderBy(TrailersColumns.MOVIEDB_ID, false);
        return this;
    }

    public TrailersSelection trailerId(String... value) {
        addEquals(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection trailerIdNot(String... value) {
        addNotEquals(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection trailerIdLike(String... value) {
        addLike(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection trailerIdContains(String... value) {
        addContains(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection trailerIdStartsWith(String... value) {
        addStartsWith(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection trailerIdEndsWith(String... value) {
        addEndsWith(TrailersColumns.TRAILER_ID, value);
        return this;
    }

    public TrailersSelection orderByTrailerId(boolean desc) {
        orderBy(TrailersColumns.TRAILER_ID, desc);
        return this;
    }

    public TrailersSelection orderByTrailerId() {
        orderBy(TrailersColumns.TRAILER_ID, false);
        return this;
    }

    public TrailersSelection iso6391(String... value) {
        addEquals(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection iso6391Not(String... value) {
        addNotEquals(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection iso6391Like(String... value) {
        addLike(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection iso6391Contains(String... value) {
        addContains(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection iso6391StartsWith(String... value) {
        addStartsWith(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection iso6391EndsWith(String... value) {
        addEndsWith(TrailersColumns.ISO_639_1, value);
        return this;
    }

    public TrailersSelection orderByIso6391(boolean desc) {
        orderBy(TrailersColumns.ISO_639_1, desc);
        return this;
    }

    public TrailersSelection orderByIso6391() {
        orderBy(TrailersColumns.ISO_639_1, false);
        return this;
    }

    public TrailersSelection key(String... value) {
        addEquals(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection keyNot(String... value) {
        addNotEquals(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection keyLike(String... value) {
        addLike(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection keyContains(String... value) {
        addContains(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection keyStartsWith(String... value) {
        addStartsWith(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection keyEndsWith(String... value) {
        addEndsWith(TrailersColumns.KEY, value);
        return this;
    }

    public TrailersSelection orderByKey(boolean desc) {
        orderBy(TrailersColumns.KEY, desc);
        return this;
    }

    public TrailersSelection orderByKey() {
        orderBy(TrailersColumns.KEY, false);
        return this;
    }

    public TrailersSelection name(String... value) {
        addEquals(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection nameNot(String... value) {
        addNotEquals(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection nameLike(String... value) {
        addLike(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection nameContains(String... value) {
        addContains(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection nameStartsWith(String... value) {
        addStartsWith(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection nameEndsWith(String... value) {
        addEndsWith(TrailersColumns.NAME, value);
        return this;
    }

    public TrailersSelection orderByName(boolean desc) {
        orderBy(TrailersColumns.NAME, desc);
        return this;
    }

    public TrailersSelection orderByName() {
        orderBy(TrailersColumns.NAME, false);
        return this;
    }

    public TrailersSelection size(int... value) {
        addEquals(TrailersColumns.SIZE, toObjectArray(value));
        return this;
    }

    public TrailersSelection sizeNot(int... value) {
        addNotEquals(TrailersColumns.SIZE, toObjectArray(value));
        return this;
    }

    public TrailersSelection sizeGt(int value) {
        addGreaterThan(TrailersColumns.SIZE, value);
        return this;
    }

    public TrailersSelection sizeGtEq(int value) {
        addGreaterThanOrEquals(TrailersColumns.SIZE, value);
        return this;
    }

    public TrailersSelection sizeLt(int value) {
        addLessThan(TrailersColumns.SIZE, value);
        return this;
    }

    public TrailersSelection sizeLtEq(int value) {
        addLessThanOrEquals(TrailersColumns.SIZE, value);
        return this;
    }

    public TrailersSelection orderBySize(boolean desc) {
        orderBy(TrailersColumns.SIZE, desc);
        return this;
    }

    public TrailersSelection orderBySize() {
        orderBy(TrailersColumns.SIZE, false);
        return this;
    }

    public TrailersSelection site(String... value) {
        addEquals(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection siteNot(String... value) {
        addNotEquals(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection siteLike(String... value) {
        addLike(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection siteContains(String... value) {
        addContains(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection siteStartsWith(String... value) {
        addStartsWith(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection siteEndsWith(String... value) {
        addEndsWith(TrailersColumns.SITE, value);
        return this;
    }

    public TrailersSelection orderBySite(boolean desc) {
        orderBy(TrailersColumns.SITE, desc);
        return this;
    }

    public TrailersSelection orderBySite() {
        orderBy(TrailersColumns.SITE, false);
        return this;
    }

    public TrailersSelection type(String... value) {
        addEquals(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection typeNot(String... value) {
        addNotEquals(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection typeLike(String... value) {
        addLike(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection typeContains(String... value) {
        addContains(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection typeStartsWith(String... value) {
        addStartsWith(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection typeEndsWith(String... value) {
        addEndsWith(TrailersColumns.TYPE, value);
        return this;
    }

    public TrailersSelection orderByType(boolean desc) {
        orderBy(TrailersColumns.TYPE, desc);
        return this;
    }

    public TrailersSelection orderByType() {
        orderBy(TrailersColumns.TYPE, false);
        return this;
    }
}
