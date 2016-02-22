package com.android.rupeeright.popularmovies.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.ShareActionProvider;
import android.text.method.ScrollingMovementMethod;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesContentValues;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesCursor;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsCursor;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersCursor;
import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UISupport.PagerIndicator;
import com.android.rupeeright.popularmovies.UISupport.TrailerPagerAdapter;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.validation.Validator;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, PagerIndicator.SetPage {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();
    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int MOVIEITEM_LOADER_ID = 2;
    private long mRecdFilmID = -1L;

    private static final String[] MOVIEITEM_COLUMNS = {
            MoviesColumns.TABLE_NAME + "." + MoviesColumns._ID,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.MOVIE_ID,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.BACKDROP_PATH,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.TITLE,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.RELEASE_DATE,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.VOTE_AVERAGE,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.OVERVIEW,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.POSTER_PATH,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.FAVORITE ,
           ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.AUTHOR,
            ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.CONTENT,
            ReviewsColumns.TABLE_NAME + "." + ReviewsColumns.URL,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.TYPE,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.NAME,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.SIZE,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.ISO_639_1,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.TRAILER_ID,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.KEY,
            TrailersColumns.TABLE_NAME + "." + TrailersColumns.SITE

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
    public static final int COL_FAVORITE =8;
    public static final int COL_AUTHOR=9;
    public static final int COL_CONTENT=10;
    public static final int COL_URL=11;
    public static final int COL_TYPE=12;
    public static final int COL_NAME=13;
    public static final int COL_SIZE=14;
    public static final int COL_ISO_639_1=15;
    public static final int COL_TRAILER_ID=16;
    public static final int COL_KEY=17;
    public static final int COL_SITE=18;

    private Context mContext;
    private int mHeightLocal;
    private Film mRecdFilm;

    private int mPosterH;
    private int mPosterW;
    View mMovieDetailView;
    private int mBackdropH;
    private int mBackdropW;


    private LinearLayout mReviewSection;
    private LinearLayout mTrailerSection;
    private PagerIndicator mPagerIndicator;
    private ViewPager mTrailerPager;
    private ShareActionProvider mShareActionProvider;
    private TrailerPagerAdapter mTrailerPagerAdapter;

    private String mScrip;
    private static final String TRAILER_SHARE_HASHTAG = PopMoviesConstants.MOVIE_TRAILER_SHARE_TAG;

    public MovieDetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        Activity activity;
        if (context instanceof Activity)
        {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), " OnAttach:context is actity type ");
            activity = (Activity) context;
        }
        else {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), " OnAttach: context is Not of actity type ");
            activity = getActivity();
        }
        setSelectedFilmFromIntent(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag),  " onCreate");

        /* handle the config chnages */
        long savedMovieId = -1L;

        if(savedInstanceState != null && savedInstanceState.containsKey(PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG)) {
            savedMovieId = savedInstanceState.getLong(PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG);
        } else {
            Bundle arguments = getArguments();
            if (arguments != null && arguments.containsKey(PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG)) {
                savedMovieId = arguments.getLong(PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG);
            }
        }
        if(savedMovieId != -1) {
            mRecdFilmID = savedMovieId;
            //showDetailsByMovieDBId(mMovieDBId);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mRecdFilmID != -1) {
            outState.putLong(PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG, mRecdFilmID);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), LOG_TAG + ":" + getResources().getString(R.string.Message11));

        mMovieDetailView= inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mTrailerSection     = (LinearLayout)mMovieDetailView.findViewById(R.id.trailer_section);
        mReviewSection      = (LinearLayout)mMovieDetailView.findViewById(R.id.review_section);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getActivity().getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    mReviewSection.setVisibility(View.VISIBLE);
                    mTrailerSection.setVisibility(View.VISIBLE);

                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
        if (mRecdFilmID != -1) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int mHeightLocal = displayMetrics.heightPixels;
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message12));
        }
        return mMovieDetailView;

    }

    private void setSelectedFilmFromIntent(Activity context)
    {
        Intent i = context.getIntent();
        String InstanceTagLocal =  PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        if (i != null ) {
            mRecdFilmID = i.getLongExtra(InstanceTagLocal, 0);
        }
        else {
            mRecdFilmID = 0;
            Toast.makeText(getActivity(), "No Selected FilmID received in Detail Fragment", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mTrailerPagerAdapter != null) {
            mTrailerPagerAdapter.releaseLoaders();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        // initiateTrailerReviewDownload(mRecdFilmID);
        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        if ( mRecdFilmID != -1) {
            if (PopMoviesConstants.DEBUG) {
                Log.i(LOG_TAG, "+++ Calling initLoader()! +++");
                if (getLoaderManager().getLoader(MOVIEITEM_LOADER_ID) == null) {
                    Log.i(LOG_TAG, "+++ Initializing the new Loader... +++" + "selected film" + mRecdFilmID );
                } else {
                    Log.i(LOG_TAG, "+++ Reconnecting with existing Loader (id '1')... +++" + "selected film" + mRecdFilmID);
                }
            }
            getLoaderManager().initLoader(MOVIEITEM_LOADER_ID, null, this);
        }
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movie_detail, menu);
        MenuItem id = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(id);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void LoadFragmentFromFilmData(View fragmentViewParam , /*int heightParam , */ final Film mRecdFilm)
    {
        if (PopMoviesConstants.DEBUG) {
           // Log.i(getResources().getString(R.string.logcat_tag), "LoadFragmentFromFilmData: film= " + mRecdFilm.toString());
        }
        //Log.d(getResources().getString(R.string.logcat_tag), "loading the poster - using Glide" );
        ImageView imageViewPoster = (ImageView) fragmentViewParam.findViewById(R.id.MoviePoster);
        mPosterH = imageViewPoster.getMaxHeight();
        mPosterW = imageViewPoster.getMaxWidth();

        String baseImgURL = PopMoviesConstants.POSTER_BASE_PATH + PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW;
        if(mRecdFilm.getPosterPath()!=null){
            if ( mRecdFilm.getFavorite()){

                Glide.with(this)
                        .load(baseImgURL + mRecdFilm.getPosterPath())
                        .error(R.drawable.big_problem)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewPoster);

            }else
            Glide.with(this)
                    .load(baseImgURL + mRecdFilm.getPosterPath())
                    .error(R.drawable.big_problem)
                    .into(imageViewPoster);

        }
        ImageView imageViewBackdrop = (ImageView) fragmentViewParam.findViewById(R.id.Backdrop);
        mBackdropH = imageViewBackdrop.getMaxHeight();
        mBackdropW = imageViewBackdrop.getMaxWidth();

        imageViewBackdrop.setScaleType(ImageView.ScaleType.FIT_XY);
        // TODO: make the call version specific - below 16 setAlpha and >= 16 , setImageAlpha
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageViewBackdrop.setImageAlpha(PopMoviesConstants.BACKDROP_OPACITY_VALUE);
        }
        else imageViewBackdrop.setAlpha(PopMoviesConstants.BACKDROP_OPACITY_VALUE);

        if (mRecdFilm.getBackdropPath() != null)
        {
            if ( mRecdFilm.getFavorite()){

                Glide.with(this)
                        .load(baseImgURL + mRecdFilm.getBackdropPath())
                        .error(R.drawable.big_problem)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageViewBackdrop);

            }else
            Glide.with(this)
                    .load(baseImgURL + mRecdFilm.getBackdropPath())
                    .error(R.drawable.big_problem)
                    .into(imageViewBackdrop);

        }
        //Log.d(getResources().getString(R.string.logcat_tag), "loading the Title");
        TextView title = (TextView) fragmentViewParam.findViewById(R.id.MovieTitle);
        //title.setHeight((int)Math.round(heightParam*PopMoviesConstants.TITLE_HEIGHT_PERCENT));
        title.setText(mRecdFilm.getTitle());

        TextView releaseDate = (TextView) fragmentViewParam.findViewById(R.id.ReleaseDate);
        releaseDate.setText(mRecdFilm.getFormattedDate());

        if (PopMoviesConstants.DEBUG) Log.d(getResources().getString(R.string.logcat_tag), "loading the Rating Value");
        TextView ratingVal = (TextView) fragmentViewParam.findViewById(R.id.RatingValue);
        ratingVal.setText( mRecdFilm.getRating() + "/10");

        //Log.d(getResources().getString(R.string.logcat_tag), "loading the Overview");

        TextView overView = (TextView) fragmentViewParam.findViewById(R.id.Overview);
        //overView.setHeight((int)Math.round(heightParam* PopMoviesConstants.OVERVIEW_HEIGHT_PERCENT));
        overView.setText(mRecdFilm.getOverview());
        overView.setMovementMethod(new ScrollingMovementMethod());
        overView.setContentDescription(getString(R.string.overview_content_description));

        CheckBox checkFav = (CheckBox) fragmentViewParam.findViewById(R.id.favorite_checkbox);
        checkFav.setTag(mRecdFilmID);
        checkFav.setChecked(mRecdFilm.getFavorite());

        checkFav.setOnClickListener
       (
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //is chkIos checked?
                    MoviesContentValues mCV = new MoviesContentValues();
                    if (((CheckBox) v).isChecked()) {
                        // Toast.makeText(getActivity(), "Got it Checked", Toast.LENGTH_SHORT).show();
                        mCV.putFavorite(Boolean.TRUE);
                    } else {
                        // Toast.makeText(getActivity(), "Alas! Not Checked", Toast.LENGTH_SHORT).show();
                        mCV.putFavorite(Boolean.FALSE);
                    }
                    //String Str = MoviesColumns.TABLE_NAME + "." + MoviesColumns.MOVIE_ID + " = " + mRecdFilmID;
                    String Str = MoviesColumns.TABLE_NAME + "." + MoviesColumns._ID + " = " + mRecdFilmID;
                    int n = getActivity().getContentResolver().update(MoviesColumns.CONTENT_URI, mCV.values(), Str, null);
                    if (PopMoviesConstants.DEBUG)
                        Log.d("PoPMovies1", " After fav update (Rows updated = " + n + " )");
                    getActivity().getContentResolver().notifyChange(Uri.withAppendedPath(MoviesColumns.CONTENT_URI, "/with_fav"), null);

                    /* storing images for future offline view */
                    String baseImgURL = PopMoviesConstants.POSTER_BASE_PATH + PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_DETAIL_VIEW;
                    FutureTarget<File> future1 = Glide.with(mContext).load(baseImgURL+ mRecdFilm.getPosterPath()).downloadOnly(mPosterW, mPosterH);
                    FutureTarget<File> future2 = Glide.with(mContext).load(baseImgURL+ mRecdFilm.getBackdropPath()).downloadOnly(mBackdropW, mBackdropH);
                }
            }
       );
    }


        /* ***************** Loader callbacks*************************************** */

/* return all information about the selected movie id */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri initLoaderUri = Uri.withAppendedPath(MoviesColumns.CONTENT_URI, String.valueOf(mRecdFilmID));
        if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), LOG_TAG + ":" + "Selected film ("+ mRecdFilmID + ") calling Uri = " + initLoaderUri);
        return new CursorLoader(
                getActivity(),    /* Context */
                /* Uri.withAppendedPath(MoviesColumns.CONTENT_URI, "/movieDBID/" + String.valueOf(mRecdFilmID)), */
                initLoaderUri,
                /*MoviesColumns.ALL_COLUMNS, */
                MOVIEITEM_COLUMNS,
                null,              /* selection */
                null,              /* selectionArgs */
                null);             /* sortOrder */
    }

    @Override
    public void onLoadFinished( Loader<Cursor> loader, Cursor data) {
        if ( data == null ) {
            // return;    - not returning as we need to show blank fragment
        }
        if ( !data.moveToFirst()){
            // return;    - not returning as we need to show blank fragment
        }

        MoviesCursor mc = new MoviesCursor(data);
        String title = null;
        try {
            mc.moveToFirst();
            title = mc.getTitle();
        }catch(NullPointerException e){}

        int movieDBID = displayMovieData(mc);
        if ( movieDBID < 0 ) return;
            /* *************** Load Trainer info if any */

        TrailersCursor tc = new TrailersCursor(data);
        /*
        String toastStr = " Trailers : ";
        StringBuilder sb = new StringBuilder(30);
        sb.append(" Trailers : ");
        try{
                tc.moveToFirst();
                toastStr += tc.getTrailerId();
                do{
                    sb.append(", ").append(tc.getTrailerId());
                } while (tc.moveToNext());

            }catch(NullPointerException e){
            toastStr += "is NULL";
        }
        Log.i("PopMovies1", sb.toString());
        */
        String LoadedTrailerdata = LoadTrailerData(tc);
            /* sHARE */

        if(mShareActionProvider != null){
                mScrip = "Sharing through Pop Movies app: check this movie" + title + ".";
                if (LoadedTrailerdata != null)
                {
                    mScrip +=  PopMoviesConstants.YOUTUBE_URL + LoadedTrailerdata;
                    mShareActionProvider.setShareIntent(createShareTrailerIntent(mScrip));


                    if (PopMoviesConstants.DEBUG)
                        Log.d("PopMovies1", LOG_TAG + ":" + " sharing text <"+ mScrip + "> through intent");

                }
        }
        else
        {
                if (PopMoviesConstants.DEBUG ) Log.d("PopMovies1", LOG_TAG+":" + "Share Action Provider is null?");
        }


            /* Load Reviews */
        ReviewsCursor rv = new ReviewsCursor(data);

        String toastStr = " Reviews :";
        StringBuilder sb1 = new StringBuilder(30);
        sb1.append("Reviews :");
        try
        {
                rv.moveToFirst();
                toastStr += rv.getUrl();
                sb1.append( rv.getUrl());
                do{
                    sb1.append(", ").append(rv.getUrl());
                } while (rv.moveToNext());
        }catch(NullPointerException e){
            toastStr += "is NULL";
            sb1.append(" is Null");
        }
        Log.i("PopMovies1", sb1.toString());
        //Toast.makeText(this.getActivity(), toastStr, Toast.LENGTH_SHORT).show();
        LoadReviewData(rv);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoaderReset" );

    }
 /* ******************************************************************************  */


    private String LoadTrailerData(TrailersCursor trailerCursor) {
        /* if (PopMoviesConstants.DEBUG) */ Log.i("PopMovies1", LOG_TAG + ":"+ " LoadTrailerData");

        mTrailerSection.removeAllViews();
        Set<String> trailerSet = new LinkedHashSet<>();
        trailerCursor.moveToFirst();
        do {
            try {
                trailerSet.add(trailerCursor.getTrailerId());
            } catch (NullPointerException e){}
        } while (trailerCursor.moveToNext());

        //trailerCursor.close();
         if (PopMoviesConstants.DEBUG)
            Log.i("PopMovies1", LOG_TAG + ":"+ "LoadTrailerData : data transferred to LinkedHashSet from cursor : size = "
                        + trailerSet.size());


        if(trailerSet.size() > 0) {

            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " trailers available - adding a view " );
            TextView trailerTitle = new TextView(getActivity());
            trailerTitle.setText(R.string.trailers_title);

            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.setMargins(
                    pixelSizeFromDP(getActivity(), 25),
                    0,
                    0,
                    pixelSizeFromDP(getActivity(), 10));
            trailerTitle.setLayoutParams(textViewLayoutParams);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                trailerTitle.setBackgroundColor(getActivity().getColor(R.color.data_available_backgroud));
                trailerTitle.setTextAppearance(android.R.style.TextAppearance_Large);
            }else{
                trailerTitle.setBackgroundColor(PopMoviesConstants.data_available_backgroud);
                trailerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, PopMoviesConstants.header_text_size);
            }

            mTrailerSection.addView(trailerTitle);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " view added" );

            View trailerPagerContainer = getLayoutInflater(null).inflate(R.layout.trailer_view_pager, mTrailerSection, true);
            mTrailerPager  = (ViewPager) trailerPagerContainer.findViewById(R.id.view_pager);
            mTrailerPagerAdapter = new TrailerPagerAdapter(getActivity(), new ArrayList<String>(trailerSet));
            mTrailerPager.setAdapter(mTrailerPagerAdapter);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " adapter set to Trailer page" );

            mPagerIndicator = new PagerIndicator(getActivity(),null);
            mPagerIndicator.init(trailerSet.size());
            mPagerIndicator.setPagerController(this);
            mTrailerSection.addView(mPagerIndicator);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " page indicator added  to Trailer page" );

            mTrailerSection.setVisibility(View.VISIBLE);
            mTrailerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int currPos = position + 1;
                    if (mPagerIndicator.getCheckedRadioButtonId() != currPos)
                        mPagerIndicator.check(currPos);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } else {

            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " No trailers available - adding a blank view " );
            TextView trailerNAText = new TextView(getActivity());
            trailerNAText.setText(getString(R.string.no_trailers_available));


            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.setMargins(
                    pixelSizeFromDP(getActivity(),25),
                    0,
                    0,
                    pixelSizeFromDP(getActivity(),25));
            trailerNAText.setLayoutParams(textViewLayoutParams);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                trailerNAText.setBackgroundColor(getActivity().getColor(R.color.data_not_available_backgroud));
                trailerNAText.setTextAppearance(android.R.style.TextAppearance_Large);
            }else {
                trailerNAText.setTextSize(TypedValue.COMPLEX_UNIT_SP, PopMoviesConstants.header_text_size);
                trailerNAText.setBackgroundColor(PopMoviesConstants.data_not_available_backgroud);
            }
            mTrailerSection.addView(trailerNAText);
            mTrailerSection.setVisibility(View.VISIBLE);
        }

        if(!trailerSet.isEmpty()) {
            return trailerSet.iterator().next();
        }

        return null;
    }


    public int pixelSizeFromDP(Context ctx, float dp){
        DisplayMetrics displayMetrics = ctx.getResources().getDisplayMetrics();
        float myPixels = displayMetrics.density*dp;
        return (int)(myPixels + 0.5f);

    }

    @Override
    public void setPage(int page) {
        int pos = (page- 1)>=0?(page-1):0;
        if (mTrailerPager.getCurrentItem()!= pos)
            mTrailerPager.setCurrentItem(pos);
    }

    private Intent createShareTrailerIntent(String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        shareIntent.setType("Text/Plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text + TRAILER_SHARE_HASHTAG);
        return shareIntent;
    }

    private void LoadReviewData(ReviewsCursor reviewCursor) {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":"+ "LoadReviewData");
        View reviewItemView;
        mReviewSection.removeAllViews();
        //Set<String> reviewSet = new LinkedHashSet<>();
        reviewCursor.moveToFirst();
        int inc=-1;
        do {
            inc++;
            try {
                String reviewURL = reviewCursor.getUrl();
                if (reviewURL != null )
                {
                    if (inc == 0 )
                    {
                        // add header text
                        TextView reviewTitle = new TextView(getActivity());
                        reviewTitle.setText(getString(R.string.reviews_title));
                        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        textViewLayoutParams.setMargins(pixelSizeFromDP(getActivity(), 15), 0, 0, pixelSizeFromDP(getActivity(), 10));
                        reviewTitle.setLayoutParams(textViewLayoutParams);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            reviewTitle.setBackgroundColor(getActivity().getColor(R.color.data_available_backgroud));
                            reviewTitle.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
                        }else{
                            reviewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, PopMoviesConstants.header_text_size);
                            reviewTitle.setBackgroundColor(PopMoviesConstants.data_available_backgroud);
                        }

                        mReviewSection.addView(reviewTitle);
                        reviewTitle.setVisibility(View.VISIBLE);
                        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":" + "Header Note added");

                    }
                    //reviewSet.add(reviewURL);
                    /* @SuppressLint("InflateParams") */
                    reviewItemView = getActivity().getLayoutInflater().inflate(R.layout.review_item, null);
                    TextView authorTextView = (TextView) reviewItemView.findViewById(R.id.review_author);
                    TextView contentTextView = (TextView) reviewItemView.findViewById(R.id.review_text);
                    authorTextView.setText(reviewCursor.getAuthor());
                    contentTextView.setText(reviewCursor.getContent());
                    if (inc%2==0 ) reviewItemView.setBackgroundColor(PopMoviesConstants.data_available_backgroud);
                    else reviewItemView.setBackgroundColor(0);
                    reviewItemView.setVisibility(View.VISIBLE);
                    mReviewSection.addView(reviewItemView);
                    if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", "(" + inc + ") Note added");
                }
                else{
                    if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", "(" + inc + ") reviewUrl is null - No card added");
                }
            } catch(NullPointerException e){}
        } while (reviewCursor.moveToNext());
        //reviewCursor.close();

        if(mReviewSection.getChildCount() > 0) {

        } else {
            TextView reviewNAText = new TextView(getActivity());
            reviewNAText.setText(getString(R.string.no_reviews_available));
            LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                    ViewGroup.LayoutParams.WRAP_CONTENT);
            textViewLayoutParams.setMargins(
                    pixelSizeFromDP(getActivity(), 25),
                    0,
                    0,
                    pixelSizeFromDP(getActivity(), 25));
            reviewNAText.setLayoutParams(textViewLayoutParams);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                reviewNAText.setBackgroundColor(getActivity().getColor(R.color.data_not_available_backgroud));
                reviewNAText.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
            } else {
                reviewNAText.setTextSize(TypedValue.COMPLEX_UNIT_SP, PopMoviesConstants.header_text_size);
                reviewNAText.setBackgroundColor(PopMoviesConstants.data_not_available_backgroud);
            }
            mReviewSection.addView(reviewNAText);
            reviewNAText.setVisibility(View.VISIBLE);
            mReviewSection.setVisibility(View.VISIBLE);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":" + "No Data Header Note added");
        }
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":" + "Review data loading complete");

    }

    private int displayMovieData( MoviesCursor data) {
        Resources Res = getActivity().getResources();
        //int dataCnt = data.getCount();
        int movieDBID = 0;


        try {
            data.moveToFirst();
            Boolean fav = Boolean.FALSE;
            if (data.getFavorite()) {
                fav = Boolean.TRUE;
            }

            movieDBID = data.getMovieId();
            // public Film(String IDParam, String TitleParam, String PosterPathParam,
            //               String OverViewParam, float RatingParam, String strRatingParam,
            //               String DateParam, String BackdropParam, Boolean FavParam )
            mRecdFilm = new Film(String.valueOf(data.getMovieId()).trim(),
                    data.getTitle(),
                    data.getPosterPath(),
                    data.getOverview(),
                    data.getVoteAverage(),
                    String.valueOf(data.getVoteAverage()).trim(),
                    Utilities.DbDateInStringFromMilliseconds(data.getReleaseDate()),
                    data.getBackdropPath(),
                    fav
            );
        }catch(NullPointerException e){
            mRecdFilm = new Film("",
                    "ISSUE WITH DB",
                    null,
                    "",
                    0,
                    "0.00",
                    "00-00-0000",
                    null,
                    Boolean.FALSE
            );

            movieDBID =  -1;

        }
        LoadFragmentFromFilmData(mMovieDetailView, mRecdFilm);
        return  movieDBID;
    }

    public void onSettingsChanged(String SortBy, String SortOrder){
        getLoaderManager().restartLoader(MOVIEITEM_LOADER_ID, null, this);
    }
}