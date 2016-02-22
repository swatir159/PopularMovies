package com.android.rupeeright.popularmovies.DataStorage.reviews;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.rupeeright.popularmovies.DataStorage.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code reviews} table.
 */
public class ReviewsContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return ReviewsColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable ReviewsSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable ReviewsSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * The id of the movie in local database, matches with _ID
     */
    public ReviewsContentValues putMovieId(long value) {
        mContentValues.put(ReviewsColumns.MOVIE_ID, value);
        return this;
    }


    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    public ReviewsContentValues putMoviedbId(int value) {
        mContentValues.put(ReviewsColumns.MOVIEDB_ID, value);
        return this;
    }


    /**
     * The review id of the movie like 5010553819c2952d1b000451 
     */
    public ReviewsContentValues putReviewId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("reviewId must not be null");
        mContentValues.put(ReviewsColumns.REVIEW_ID, value);
        return this;
    }


    /**
     * author of the review like Travis Bell
     */
    public ReviewsContentValues putAuthor(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("author must not be null");
        mContentValues.put(ReviewsColumns.AUTHOR, value);
        return this;
    }


    /**
     * actual review content  
     */
    public ReviewsContentValues putContent(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("content must not be null");
        mContentValues.put(ReviewsColumns.CONTENT, value);
        return this;
    }


    /**
     * url of the review like http://j.mp/QSjAK2 
     */
    public ReviewsContentValues putUrl(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("url must not be null");
        mContentValues.put(ReviewsColumns.URL, value);
        return this;
    }

}
