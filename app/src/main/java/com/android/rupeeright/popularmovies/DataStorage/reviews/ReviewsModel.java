package com.android.rupeeright.popularmovies.DataStorage.reviews;

import com.android.rupeeright.popularmovies.DataStorage.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The Review info
 */
public interface ReviewsModel extends BaseModel {

    /**
     * The id of the movie in local database, matches with _ID
     */
    long getMovieId();

    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    int getMoviedbId();

    /**
     * The review id of the movie like 5010553819c2952d1b000451 
     * Cannot be {@code null}.
     */
    @NonNull
    String getReviewId();

    /**
     * author of the review like Travis Bell
     * Cannot be {@code null}.
     */
    @NonNull
    String getAuthor();

    /**
     * actual review content  
     * Cannot be {@code null}.
     */
    @NonNull
    String getContent();

    /**
     * url of the review like http://j.mp/QSjAK2 
     * Cannot be {@code null}.
     */
    @NonNull
    String getUrl();
}
