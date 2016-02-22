package com.android.rupeeright.popularmovies.DataStorage.trailers;

import com.android.rupeeright.popularmovies.DataStorage.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * The trailer info 
 */
public interface TrailersModel extends BaseModel {

    /**
     * The id of the movie in local database, matches with _ID
     */
    long getMovieId();

    /**
     * The id of the movie in MovieDB database, matches with movie_id
     */
    int getMoviedbId();

    /**
     * The trailer id of the movie like 533ec654c3a36854480003eb 
     * Cannot be {@code null}.
     */
    @NonNull
    String getTrailerId();

    /**
     * iso language like en
     * Cannot be {@code null}.
     */
    @NonNull
    String getIso6391();

    /**
     * iso language like SUXWAEX2jlg
     * Cannot be {@code null}.
     */
    @NonNull
    String getKey();

    /**
     * name of the trailer like Trailer 1
     * Cannot be {@code null}.
     */
    @NonNull
    String getName();

    /**
     * size of the trailer like 720
     */
    int getSize();

    /**
     * Site like youtube
     * Cannot be {@code null}.
     */
    @NonNull
    String getSite();

    /**
     * type like Trailer
     * Cannot be {@code null}.
     */
    @NonNull
    String getType();
}
