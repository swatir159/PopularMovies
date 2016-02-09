package com.android.rupeeright.popularmovies.UI;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.R;
import com.bumptech.glide.Glide;

import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.Util.PopMoviesConstants;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    private Film mRecdFilm;
    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message11));

        View MovieDetailView= inflater.inflate(R.layout.fragment_movie_detail, container, false);

        setSelectedFilmFromIntent();
        //Log.d(getResources().getString(R.string.logcat_tag), mRecdFilm.toString() );
        /* decide the heights of the controls on the basis of the displayMetrics
        */

        if (mRecdFilm != null) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int HeightLocal = displayMetrics.heightPixels;
            //final int WidthLocal = displayMetrics.widthPixels;
            LoadFragmentFromFilmData(MovieDetailView, HeightLocal);
            Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message12));
        }

        return MovieDetailView;

    }


    private void setSelectedFilmFromIntent()
    {
        // Get intent data
        Intent i = getActivity().getIntent();
        String InstanceTagLocal =  PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        if (i != null ) {
            mRecdFilm = i.getParcelableExtra(InstanceTagLocal);
        }
        else {
            mRecdFilm = null;
            Toast.makeText(getActivity(), "No Selected Film", Toast.LENGTH_SHORT).show();
        }

    }

    private void LoadFragmentFromFilmData(View fragmentViewParam, int heightParam)
    {
        Log.d(getResources().getString(R.string.logcat_tag), "loading the poster - using Glide" );
        ImageView imageView = (ImageView) fragmentViewParam.findViewById(R.id.MovieDetailView);
        //imageView.setMaxHeight((int)Math.round(heightParam * PopMoviesConstants.POSTER_HEIGHT_PERCENT)) ;

        // Film film = imageAdapter.getInstance().films.get(position);
        //imageView.setImageResource(film.getPosterPath());
        String baseImgURL = PopMoviesConstants.POSTER_BASE_PATH + PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW;
        Glide.with(this)
                .load(baseImgURL + mRecdFilm.getPosterPath())
                .error(R.drawable.big_problem)
                .into(imageView);

        Log.d(getResources().getString(R.string.logcat_tag), "loading the Title");
        TextView title = (TextView) fragmentViewParam.findViewById(R.id.MovieTitle);
        title.setHeight((int)Math.round(heightParam*PopMoviesConstants.TITLE_HEIGHT_PERCENT));
        title.setText(mRecdFilm.getTitle());

        // RatingBar rating = (RatingBar) findViewById(R.id.UserRating);
        //float f = Float.parseFloat( film.getRating().trim() );
        //rating.setRating(recdFilm.getRating());
        Log.d(getResources().getString(R.string.logcat_tag), "loading the Release date");
        TextView releaseDate = (TextView) fragmentViewParam.findViewById(R.id.ReleaseDate);
        releaseDate.setText(mRecdFilm.getFormattedDate());

        Log.d(getResources().getString(R.string.logcat_tag), "loading the Rating Value");
        TextView ratingVal = (TextView) fragmentViewParam.findViewById(R.id.RatingValue);
        ratingVal.setText( Float.toString(mRecdFilm.getRating()));

        Log.d(getResources().getString(R.string.logcat_tag), "loading the Overview");
        TextView overView = (TextView) fragmentViewParam.findViewById(R.id.OverView);
        overView.setHeight((int)Math.round(heightParam* PopMoviesConstants.OVERVIEW_HEIGHT_PERCENT));
        overView.setText(mRecdFilm.getOverview());
        overView.setMovementMethod(new ScrollingMovementMethod());
    }

}