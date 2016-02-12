package com.android.rupeeright.popularmovies.UI;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.MovieContract;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.bumptech.glide.Glide;

import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int MOVIEITEM_LOADER_ID = 2;
    private static final String[] MOVIEITEM_COLUMNS = {
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry._ID,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_MOVIE_ID,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_TITLE,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_RELEASE_DATE,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_OVERVIEW,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_POSTER_PATH


    };
    // indexes of columns in MOVIELIST_COLUMNS  - change synchronously
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_BACKDROP_PATH = 2;

    public static final int COL_TITLE = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_VOTE_AVERAGE = 5;

    public static final int COL_OVERVIEW = 6;
    public static final int COL_POSTER_PATH = 7;

    private Integer mRecdFilmID;
    private int mHeightLocal;

    private Film mRecdFilm;
    View mMovieDetailView;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message11));

        mMovieDetailView= inflater.inflate(R.layout.fragment_movie_detail, container, false);

        setSelectedFilmFromIntent();
        //Log.d(getResources().getString(R.string.logcat_tag), mRecdFilm.toString() );
        /* decide the heights of the controls on the basis of the displayMetrics
        */

        if (mRecdFilmID != 0) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int mHeightLocal = displayMetrics.heightPixels;
            //final int WidthLocal = displayMetrics.widthPixels;

            Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message12));
        }

        return mMovieDetailView;

    }


    private void setSelectedFilmFromIntent()
    {
        // Get intent data
        Intent i = getActivity().getIntent();
        String InstanceTagLocal =  PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        if (i != null ) {
            mRecdFilmID = i.getIntExtra(InstanceTagLocal, 0);
        }
        else {
            mRecdFilmID = 0;
            Toast.makeText(getActivity(), "No Selected FilmID received in Detail Fragment", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (PopMoviesConstants.DEBUG) {
            Log.i(LOG_TAG, "+++ Calling initLoader()! +++");
            if (getLoaderManager().getLoader(MOVIEITEM_LOADER_ID) == null) {
                Log.i(LOG_TAG, "+++ Initializing the new Loader... +++");
            } else {
                Log.i(LOG_TAG, "+++ Reconnecting with existing Loader (id '1')... +++");
            }
        }

        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(MOVIEITEM_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    private void LoadFragmentFromFilmData(View fragmentViewParam , /*int heightParam , */ Film mRecdFilm)
    {
        if (PopMoviesConstants.DEBUG) {
            Log.d(getResources().getString(R.string.logcat_tag), "LoadFragmentFromFilmData: film= " + mRecdFilm.toString());
        }
        Log.d(getResources().getString(R.string.logcat_tag), "loading the poster - using Glide" );
        ImageView imageViewPoster = (ImageView) fragmentViewParam.findViewById(R.id.MoviePoster);
        //imageView.setMaxHeight((int)Math.round(heightParam * PopMoviesConstants.POSTER_HEIGHT_PERCENT)) ;

        // Film film = imageAdapter.getInstance().films.get(position);
        //imageView.setImageResource(film.getPosterPath());
        String baseImgURL = PopMoviesConstants.POSTER_BASE_PATH + PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW;
        Glide.with(this)
                .load(baseImgURL + mRecdFilm.getPosterPath())
                .error(R.drawable.big_problem)
                .into(imageViewPoster);

        ImageView imageViewBackdrop = (ImageView) fragmentViewParam.findViewById(R.id.Backdrop);
        imageViewBackdrop.setScaleType(ImageView.ScaleType.FIT_XY);
        imageViewBackdrop.setImageAlpha(100);
        //imageView.setMaxHeight((int)Math.round(heightParam * PopMoviesConstants.POSTER_HEIGHT_PERCENT)) ;

        // Film film = imageAdapter.getInstance().films.get(position);
        //imageView.setImageResource(film.getPosterPath());
        //String baseImgURL = PopMoviesConstants.POSTER_BASE_PATH + PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW;
       Glide.with(this)
                .load(baseImgURL + mRecdFilm.getBackdropPath())
                .error(R.drawable.big_problem)
                .into(imageViewBackdrop);


        //Log.d(getResources().getString(R.string.logcat_tag), "loading the Title");
        TextView title = (TextView) fragmentViewParam.findViewById(R.id.MovieTitle);
        //title.setHeight((int)Math.round(heightParam*PopMoviesConstants.TITLE_HEIGHT_PERCENT));
        title.setText(mRecdFilm.getTitle());

        // RatingBar rating = (RatingBar) findViewById(R.id.UserRating);
        //float f = Float.parseFloat( film.getRating().trim() );
        //rating.setRating(recdFilm.getRating());
        //Log.d(getResources().getString(R.string.logcat_tag), "loading the Release date");
        TextView releaseDate = (TextView) fragmentViewParam.findViewById(R.id.ReleaseDate);
        releaseDate.setText(mRecdFilm.getFormattedDate());

        Log.d(getResources().getString(R.string.logcat_tag), "loading the Rating Value");
        TextView ratingVal = (TextView) fragmentViewParam.findViewById(R.id.RatingValue);
        ratingVal.setText( Float.toString(mRecdFilm.getRating()) + "/10");

        //Log.d(getResources().getString(R.string.logcat_tag), "loading the Overview");
        TextView overView = (TextView) fragmentViewParam.findViewById(R.id.Overview);
        //overView.setHeight((int)Math.round(heightParam* PopMoviesConstants.OVERVIEW_HEIGHT_PERCENT));
        overView.setText(mRecdFilm.getOverview());
        overView.setMovementMethod(new ScrollingMovementMethod());
    }


        /* ***************** Loader callbacks*************************************** */


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(
                getActivity(),    /* Context */
                MovieContract.MoviesEntry.buildMovieDBIDUri(mRecdFilmID), /* uri */
                MOVIEITEM_COLUMNS, /* projection */
                null,              /* selection */
                null,              /* selectionArgs */
                null);             /* sortOrder */
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoadFinished" );

        //Film(String IDParam, String TitleParam, String PosterPathParam, String OverViewParam, float RatingParam,
        // String strRatingParam,  String DateParam )
        if ( data!= null &&  data.getCount() >=0 ) {
            if (PopMoviesConstants.DEBUG) {
                //Log.i(Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " OnLoadFinished : rowcount =" + data.getCount());
                //String cursorStr = DatabaseUtils.dumpCursorToString(data);
                //Log.d(Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " OnLoadFinished : Cursor =" + cursorStr);
                //StringBuilder sb= new StringBuilder("Cursor: ");
                //DatabaseUtils.dumpCursor(data, sb);
                //String  Overview = data.getString (COL_OVERVIEW);
                //Log.d(Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " OnLoadFinished :  =" + sb.toString());
            }
            data.moveToFirst();
            mRecdFilm = new Film(   String.valueOf( data.getInt(COL_MOVIE_ID)).trim() ,
                    data.getString(COL_TITLE),
                    data.getString(COL_POSTER_PATH),
                    data.getString(COL_OVERVIEW),
                    (float)data.getFloat(COL_VOTE_AVERAGE),
                    String.valueOf(data.getDouble(COL_VOTE_AVERAGE)).trim(),
                    Utilities.DbDateInStringFromMilliseconds(data.getLong(COL_RELEASE_DATE)),
                    data.getString(COL_BACKDROP_PATH)
            );

            LoadFragmentFromFilmData(mMovieDetailView,  mRecdFilm);
        }

        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " No data found." );
        return;

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoaderReset" );

    }
 /* ******************************************************************************  */

}