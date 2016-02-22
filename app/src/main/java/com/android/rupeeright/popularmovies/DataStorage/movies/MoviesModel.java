package com.android.rupeeright.popularmovies.DataStorage.movies;

import com.android.rupeeright.popularmovies.DataStorage.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Details about the movies received from theMovieDB
 */
public interface MoviesModel extends BaseModel {

    /**
     * The id of the movie in MovieDB
     */
    int getMovieId();

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterPath();

    /**
     * Get the {@code overview} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOverview();

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getVoteAverage();

    /**
     * Get the {@code release_date} value.
     * Can be {@code null}.
     */
    @Nullable
    Long getReleaseDate();

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBackdropPath();

    /**
     * Get the {@code original_language} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalLanguage();

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalTitle();

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getPopularity();

    /**
     * Get the {@code video} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getVideo();

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    Double getVoteCount();

    /**
     * Get the {@code adult} value.
     * Can be {@code null}.
     */
    @Nullable
    Boolean getAdult();

    /**
     * Get the {@code favorite} value.
     */
    boolean getFavorite();
}
