package com.android.rupeeright.popularmovies.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.MovieContract;
import com.android.rupeeright.popularmovies.MovieDetailDownloader.PopularMoviesService;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.UISupport.ImageAdapter;
import com.android.rupeeright.popularmovies.UISupport.MovieListAdapter;
import com.android.rupeeright.popularmovies.UISupport.NoInternetConnectionDialog;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.android.rupeeright.popularmovies.WebDataFetcher.AsyncDownloader;
import com.android.rupeeright.popularmovies.WebDataFetcher.MovieDBConnector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
     // Set this to false to disable log
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private int mPosition = GridView.INVALID_POSITION;

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int MOVIELIST_LOADER_ID = 1;
    private static final String[] MOVIELIST_COLUMNS = {
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry._ID,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_MOVIE_ID,
            MovieContract.MoviesEntry.TABLE_NAME + "." + MovieContract.MoviesEntry.COLUMN_POSTER_PATH

    };
    // indexes of columns in MOVIELIST_COLUMNS  - change synchronously
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_POSTER_PATH = 2;

    //private ImageAdapter mAdapter;
    private MovieListAdapter mMovieListAdapter; // Replace ImageAdapter with MovieListAdapter
    private int  mImageSize;
    private int  mImageSpacing;

    //private AsyncDownloader mDownloader;
    //private ArrayList<Film> mFilmsList;


    public MainActivityFragment() {

    }


   @Override
    public void onCreate (Bundle savedInstanceState)
    {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), Res.getString(R.string.DebugMsg2) );
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        /* Now that we get the film list from Loader, we do not need to manage the instance anymore
        setRetainInstance(true);

        String InstanceTagLocal =  PopMoviesConstants.MAIN_ACTIVITY_INSTANCE_TAG;
        if(savedInstanceState == null || !savedInstanceState.containsKey(InstanceTagLocal)) {

            //String newSortOrderPref = Utilities.getSortOrderPreference(getActivity());

            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message2) + "instance: " + this.toString());
            createMovieList();
        }
        else {
            mFilmsList = savedInstanceState.getParcelableArrayList(InstanceTagLocal);
        }
        */
    }
    /* **********
    @Override
    public void onSaveInstanceState(Bundle outState) {
        String InstanceTagLocal =  PopMoviesConstants.MAIN_ACTIVITY_INSTANCE_TAG;
        outState.putParcelableArrayList(InstanceTagLocal, mFilmsList);
        super.onSaveInstanceState(outState);
    }

    *******************************   */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //mAdapter = new ImageAdapter( (Context)this.getActivity(), mFilmsList);
        //Create a cursor to create the MovieListAdapter
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "Inside onCreateView" );

        mMovieListAdapter = new MovieListAdapter( (Context)this.getActivity(), null /* getMovielistCursor() */,0 );


        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setImageParams();
        GridView gridview = (GridView) rootView.findViewById(R.id.pomoviepostergridview);

        gridview.setAdapter(mMovieListAdapter);
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "Inside onCreateView after setAdapter " );

        mMovieListAdapter.setImageSizeDetails(mImageSize, mImageSpacing);

       gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick(AdapterView<?> parent, View v,
                                   int position, long id) {
               displayToast(v.getContext(), "" + position + " id=<" + id + ">");
               if (PopMoviesConstants.DEBUG) Log.i( "PopMovies1", LOG_TAG + ":" + " onItemClick: position=<"+ position + "> id=<" + id + ">" );
               //displayMovieDetails(parent, position);
               Cursor movieItem = (Cursor) parent.getItemAtPosition(position);
               Integer selectedMovieDBId = movieItem.getInt(COL_MOVIE_ID);

               String InstanceTagLocal = PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
               Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
               intent.putExtra(InstanceTagLocal, selectedMovieDBId);
               startActivity(intent);


           }
       });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.action_refresh) {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message13) );
            //createMovieList();
            downloadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (PopMoviesConstants.DEBUG) {
            Log.i(LOG_TAG, "+++ Calling initLoader()! +++");
            if (getLoaderManager().getLoader(MOVIELIST_LOADER_ID) == null) {
                Log.i(LOG_TAG, "+++ Initializing the new Loader... +++");
            } else {
                Log.i(LOG_TAG, "+++ Reconnecting with existing Loader (id '1')... +++");
            }
        }

        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(MOVIELIST_LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);

    }

    /* ********** Loader will handle this ********************************************************************
    public void updateFilmList(List<Film> FilmListParam)
    {
        if ( mFilmsList != null && !mFilmsList.isEmpty())
        {
            mFilmsList.clear();
            mFilmsList = null;
        }

        int dataSize = FilmListParam.size();

        if (dataSize != 0) {
            mFilmsList = new ArrayList<Film>(dataSize);

            for (int i = 0; i < dataSize; i++) {
                mFilmsList.add( FilmListParam.get(i));
            }
        }
    }

    public void updateAdapter(List<Film> FilmsParam) {
        mAdapter.setFilms(FilmsParam);
        mAdapter.notifyDataSetChanged();
    }



    private void displayMovieDetails(View v, int position ){
        String InstanceTagLocal =  PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        //Intent intent = new Intent(v.getContext(), DisplayMovieDetailsActivity.class);
        Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
        Film mySelectedFilm = mAdapter.getFilms().get(position);
        Bundle selectedFilmData = new Bundle();
        selectedFilmData.putParcelable(InstanceTagLocal, mySelectedFilm);
        intent.putExtra(InstanceTagLocal, mySelectedFilm);
        startActivity(intent);
    }

********************************************************** */

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ((Context) this.getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void initiateDownloadFromWeb() {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.d(Res.getString(R.string.logcat_tag), Res.getString(R.string.Message4));

        MovieDBConnector url = MovieDBConnector.getInstance();
        String newSortOrderPref = Utilities.getSortOrderPreference(getActivity());

        url.setmAPIKey(getResources().getString(R.string.themovieDB_api_key));
        String getMoviesHttpMethod = url.getMoviesQuery(Utilities.getSortByPreference(getActivity())+ "." + Utilities.getSortOrderPreference(getActivity()));

        AsyncDownloader mDownloader = new AsyncDownloader((Context)this.getActivity());
        mDownloader.execute(getMoviesHttpMethod);
    }

  /*  private void createMovieList()
    {
        if ( !isNetworkAvailable() ){
            Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message3));
             noInternetAlertMessage();
            //displayToast( this.getContext(), "No Network Available");
        }
        else
        {
            if ( mFilmsList != null && !mFilmsList.isEmpty())
            {
                mFilmsList.clear();
                mFilmsList = null;
            }

            initiateDownloadFromWeb();
        }
    }
    */


    /* ************************************************************************ */
    private void displayToast(Context context, String text) {
        //Context context = myView.getContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence styledText = Html.fromHtml(text);
        Toast toast = Toast.makeText(context, styledText, duration);
        toast.show();
    }

    private void noInternetAlertMessage() {

        FragmentManager dlgFragment = getFragmentManager();
        if (dlgFragment != null){
            NoInternetConnectionDialog dialog = new NoInternetConnectionDialog();
            displayToast((Context) this.getActivity(), this.getActivity().getResources().getString(R.string.no_internet_message));
        }

    }

    private void setImageParams()
    {
        // Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        mImageSize= 100;
        mImageSpacing = (int) (mImageSize*0.05);
        try{
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int height = displayMetrics.heightPixels;
            final int width = displayMetrics.widthPixels;
            mImageSize = (height > width ? height : width) / 2;
            mImageSpacing = (int) (mImageSize*0.05);

        }catch(Exception e)
        {
            if (PopMoviesConstants.DEBUG) Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message1));
            e.printStackTrace();
        }

    }

    private Cursor getMovielistCursor(){

        Context c= getContext();
        String prefSortBy = Utilities.getSortByPreference(c);
        String prefSortOrder = Utilities.getSortOrderPreference(c);
        String cursor_sortOrder = "";
        if (prefSortBy == "Popularity") {
            cursor_sortOrder = MovieContract.MoviesEntry.COLUMN_POPULARITY;
        } else if (prefSortBy == "Rating"){
            cursor_sortOrder = MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE;
        }

        if (prefSortBy == "Asc") {
            cursor_sortOrder = cursor_sortOrder + " ASC";
        } else if (prefSortBy == "Desc"){
            cursor_sortOrder = cursor_sortOrder + " DESC";
        }

        return getActivity().getContentResolver().query(MovieContract.MoviesEntry.CONTENT_URI,
                null, null, null, cursor_sortOrder);

    }

    /* ***************** Loader callbacks*************************************** */


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // get the user preference from shared preference
        // based on that return the cursor
        // TODO: Implement favourite
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnCreateLoader" );

        Context c= getContext();
        String prefSortBy = Utilities.getSortByPreference(c);
        String prefSortOrder = Utilities.getSortOrderPreference(c);
        String cursor_sortOrder = "";
        if (prefSortBy == "Popularity") {
            cursor_sortOrder = MovieContract.MoviesEntry.COLUMN_POPULARITY;
        } else if (prefSortBy == "Rating"){
            cursor_sortOrder = MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE;
        }

        if (prefSortBy == "Asc") {
            cursor_sortOrder = cursor_sortOrder + " ASC";
        } else if (prefSortBy == "Desc"){
            cursor_sortOrder = cursor_sortOrder + " DESC";
        }

        return new CursorLoader(
                getActivity(),
                MovieContract.MoviesEntry.CONTENT_URI,
                MOVIELIST_COLUMNS,
                null,
                null,
                cursor_sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoadFinished" );

        mMovieListAdapter.swapCursor(data);

        //mMovieListAdapter.getCursor().getCount();
        if (mPosition != GridView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            //mMovieListAdapter.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoaderReset" );
        mMovieListAdapter.swapCursor(null);
    }
 /* ******************************************************************************  */
    private void downloadMovieData() {

        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " downloadMovieData" );

        Intent alarmIntent = new Intent(getActivity(), PopularMoviesService.AlarmReceiver.class);
        //        alarmIntent.putExtra(SunshineService.LOCATION_QUERY_EXTRA, Utility.getPreferredLocation(getActivity()));

        //Wrap in a pending intent which only fires once.
        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 0,    alarmIntent,   PendingIntent.FLAG_ONE_SHOT);
        //getBroadcast(context, 0, i, 0);

        AlarmManager am=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        //Set the AlarmManager to wake up the system.
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);


        //Intent intent = new Intent(getActivity(), PopularMoviesService.class);
        //intent.putExtra(SunshineService.LOCATION_QUERY_EXTRA,
        //        Utility.getPreferredLocation(getActivity()));
        //getActivity().startService(intent);


    }


}
