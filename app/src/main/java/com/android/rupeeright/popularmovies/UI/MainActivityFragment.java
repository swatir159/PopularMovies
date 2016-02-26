package com.android.rupeeright.popularmovies.UI;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.SyncStatusObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;

import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataSync.MovieDBSyncAdapter;
import com.android.rupeeright.popularmovies.MovieDetailDownloader.DetailDownloaderService;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UISupport.MovieListAdapter;
import com.android.rupeeright.popularmovies.UISupport.NoInternetConnectionDialog;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;


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
            MoviesColumns.TABLE_NAME + "." + MoviesColumns._ID,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.MOVIE_ID,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.POSTER_PATH,
            MoviesColumns.TABLE_NAME + "." + MoviesColumns.TITLE

    };
    // indexes of columns in MOVIELIST_COLUMNS  - change synchronously
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_POSTER_PATH = 2;
    public static final int COL_TITLE = 3;

    private static final String SELECTED_KEY = "selected_position";
    private MovieListAdapter mMovieListAdapter; // Replace ImageAdapter with MovieListAdapter
    private GridView mGridview;
    private TextView  mAlternateView;
    private int  mImageSize;
    private int  mImageSpacing;
    private Menu mOptionsMenu;


    private Object mSyncObserverHandle;

    public MainActivityFragment() {
    }

   /* implementing callbacks to handle single pane or 2 pane display on item click >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private Callbacks mCallbacks =sDummyCallbacks;
    public interface Callbacks {
        public void onItemSelected(long id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(long id){
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ( !(context instanceof Callbacks )){
            throw new IllegalStateException("Activity must implement fragment's callbacks");
        }
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mOptionsMenu = menu;
    }

    public void setActivateOnItemClick(boolean activateOnItemClick){
        /* set the selected item selected in 2 pane view  TODO*/
        if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "setActivateOnItemClick : set Gridview item selected ");
    }
    /* <<<<<<<<<<<<<< implementing callbacks to handle single pane or 2 pane display on item click */

   @Override
    public void onCreate (Bundle savedInstanceState)
    {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), Res.getString(R.string.DebugMsg2) );
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        /* handling orientation changes */
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)){
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //String InstanceTagLocal =  PopMoviesConstants.MAIN_ACTIVITY_INSTANCE_TAG;
        //outState.putParcelableArrayList(InstanceTagLocal, mFilmsList);
        if (mPosition != GridView.INVALID_POSITION){
            outState.putInt( SELECTED_KEY, mPosition);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSyncObserverHandle != null) {
            ContentResolver.removeStatusChangeListener(mSyncObserverHandle);
            mSyncObserverHandle = null;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "Inside onCreateView" );

        mMovieListAdapter = new MovieListAdapter( (Context)this.getActivity(), null /* getMovielistCursor() */,0 );

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setImageParams();
        mGridview = (GridView) rootView.findViewById(R.id.pomoviepostergridview);
        //mHeaderView = (TextView) rootView.findViewById(R.id.MovieListHeader);
        mAlternateView = (TextView) rootView.findViewById(R.id.empty_grid_view);
        mGridview.setEmptyView(  mAlternateView );
        mGridview.setAdapter(mMovieListAdapter);
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "Inside onCreateView after setAdapter ");

        mMovieListAdapter.setImageSizeDetails(mImageSize, mImageSpacing);

        if ( mPosition!= GridView.INVALID_POSITION)
        {
            mGridview.setSelection(mPosition);
        }
        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                mPosition = position;

                Cursor movieItem = (Cursor) parent.getItemAtPosition(position);
                int selectedMovieDBId = movieItem.getInt(COL_MOVIE_ID);
                long selectedMovieId = movieItem.getInt(COL_ID);

                //displayToast(v.getContext(), "" + position + " id=<" + id + "> _ID=(" + selectedMovieId
                //        + ") MovieDBID = ( " + selectedMovieDBId + ")");
                if (PopMoviesConstants.DEBUG)
                    Log.i("PopMovies1", LOG_TAG + ":" + " onItemClick: position=<" + position + "> id=<" + id + ">");
                //displayMovieDetails(parent, position);

               /* Launch the detail activity ******************************
               String InstanceTagLocal = PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
               Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
               intent.putExtra(InstanceTagLocal, selectedMovieId);
               startActivity(intent);
                **************************************************** */
                mCallbacks.onItemSelected(selectedMovieId);
               // if (Utilities.isNetworkAvailable( getActivity()))
               //     initiateTrailernReviewDownload(selectedMovieDBId, selectedMovieId ); // starting the trailer & Review download

            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY))
        {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;
    }

    /* starting intents for review / trailer download  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    private void initiateTrailernReviewDownload(int movieIDParam, long idParam){
        /* since we expect that only a few movies will be clicked,, we only want to download the review & trailer data from here */
        if (PopMoviesConstants.DEBUG) Log.i(getActivity().getResources().getString(R.string.logcat_tag), LOG_TAG + ":" + "++++++++++++++++ inside initiateTraileDownload");
        Context ctx = getActivity();
        String MovieExtraTagLocal = PopMoviesConstants.DETAIL_ACTIVITY_TRAILER_TAG;
        Intent getDetailIntent = new Intent(getActivity(),DetailDownloaderService.class );

        /*
        Intent alarmIntent = new Intent(ctx, PopularMoviesService.AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);
        */


        getDetailIntent.putExtra(MovieExtraTagLocal,
                String.valueOf(movieIDParam).trim() + "_" + String.valueOf(idParam).trim());
        ctx.startService(getDetailIntent);
    }


    /* <<<<<<<<<<<<<< starting intents for review / trailer download */
    /*
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
*/
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

    @Override
    public void onResume() {
        super.onResume();
        mSyncStatusObserver.onStatusChanged(0);

        // Watch for sync state changes
        final int mask = ContentResolver.SYNC_OBSERVER_TYPE_PENDING |
                ContentResolver.SYNC_OBSERVER_TYPE_ACTIVE;
        mSyncObserverHandle = ContentResolver.addStatusChangeListener(mask, mSyncStatusObserver);
    }

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
            mImageSpacing = (int) (mImageSize*0.01);

        }catch(Exception e)
        {
            if (PopMoviesConstants.DEBUG) Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message1));
            e.printStackTrace();
        }

    }
    /*  Loader callbacks implemenation >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // get the user preference from shared preference
        // based on that return the cursor
        // TODO: Implement favourite
        Resources Res = getActivity().getResources();
        //if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnCreateLoader" );

        Context c= getContext();
        String prefSortBy = Utilities.getSortByPreference(c);
        String prefSortOrder = Utilities.getSortOrderPreference(c);
        String cursor_sortOrder = "";
        String cursor_selection = "";
        if(prefSortBy.equals("Favorite")){
            cursor_sortOrder = null;
            cursor_selection = MoviesColumns.TABLE_NAME + "." + MoviesColumns.FAVORITE + " =1";
        }else{
            cursor_selection = null;
            if (prefSortBy.equals("Popularity")) {
                cursor_sortOrder = MoviesColumns.TABLE_NAME + "." +MoviesColumns.POPULARITY;
            } else if (prefSortBy.equals("Ratings") ){
                cursor_sortOrder = MoviesColumns.TABLE_NAME + "." +MoviesColumns.VOTE_AVERAGE;
            } /* else if (prefSortBy.equals("Favorite")) */

            if (prefSortOrder.equals("Asc")) {
                cursor_sortOrder = cursor_sortOrder + " ASC";
            } else if (prefSortOrder.equals("Desc")){
                cursor_sortOrder = cursor_sortOrder + " DESC";
            }

        }

        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":"
                        + "OnCreateLoader: sortOrder=" + cursor_sortOrder  + " selection ="+cursor_selection ) ;
        return new CursorLoader(
                getActivity(),
                MoviesColumns.CONTENT_URI,
                MOVIELIST_COLUMNS,
                cursor_selection,
                null,
                cursor_sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
       mMovieListAdapter.swapCursor(data);

        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoadFinished + (" + data.getCount() + " ) Records found" );
        if (mPosition != GridView.INVALID_POSITION)
        {
            //TODO set gridview position to mPosition - need to check if this works
            mGridview.setSelection(mPosition);
        }
        updateEmptyView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Resources Res = getActivity().getResources();
       // if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), LOG_TAG + ":" + "OnLoaderReset" );
        mMovieListAdapter.swapCursor(null);
    }
 /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Loader callbacks implemenation  */


    /**
     * Set the state of the Refresh button. If a sync is active, turn on the ProgressBar widget.
     * Otherwise, turn it off.
     *
     * @param refreshing True if an active sync is occuring, false otherwise
     */
     public void setRefreshActionButtonState(boolean refreshing) {

        if (mOptionsMenu == null) {
            return;
        }

        final MenuItem refreshItem = mOptionsMenu.findItem(R.id.action_refresh);
        if (refreshItem != null) {
            if (refreshing) {
                refreshItem.setActionView(R.layout.actionbar_indeterminate_progress);
            } else {
                refreshItem.setActionView(null);
            }
        }
    }

    /**
     * Crfate a new anonymous SyncStatusObserver. It's attached to the app's ContentResolver in
     * onResume(), and removed in onPause(). If status changes, it sets the state of the Refresh
     * button. If a sync is active or pending, the Refresh button is replaced by an indeterminate
     * ProgressBar; otherwise, the button itself is displayed.
     */
    private SyncStatusObserver mSyncStatusObserver = new SyncStatusObserver() {
        /** Callback invoked with the sync adapter status changes. */
        @Override
        public void onStatusChanged(int which) {
            getActivity().runOnUiThread(new Runnable() {
                /**
                 * The SyncAdapter runs on a background thread. To update the UI, onStatusChanged()
                 * runs on the UI thread.
                 */
                @Override
                public void run() {
                    // Create a handle to the account that was created by
                    // SyncService.CreateSyncAccount(). This will be used to query the system to
                    // see how the sync status has changed.
                    Account account = MovieDBSyncAdapter.getSyncAccount(getActivity());

                    if (account == null) {
                        // GetAccount() returned an invalid value. This shouldn't happen, but
                        // we'll set the status to "not refreshing".
                        setRefreshActionButtonState(false);
                        return;
                    }

                    // Test the ContentResolver to see if the sync adapter is active or pending.
                    // Set the state of the refresh button accordingly.
                    boolean syncActive = ContentResolver.isSyncActive(
                            account, PopMoviesConstants.CONTENT_AUTHORITY);
                    boolean syncPending = ContentResolver.isSyncPending(
                            account, PopMoviesConstants.CONTENT_AUTHORITY);
                    setRefreshActionButtonState(syncActive || syncPending);
                }
            });
        }
    };

    // since we read the settings when we create the loader, all we need to do is restart things
    void onSettingChanged(String SortBy, String SortOrder ) {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":" + " OnSettingsChanged");
        getLoaderManager().restartLoader(MOVIELIST_LOADER_ID, null, this);
    }

    private void updateEmptyView(){
        if (mMovieListAdapter.getCount()==0)
        {
            if ( mAlternateView != null)
            if (!Utilities.isNetworkAvailable(this.getActivity())){
                mAlternateView.setText(getResources().getString(R.string.no_internet_message));
            }else
                mAlternateView.setText(getResources().getString(R.string.no_movie_available));

        }
    }

}
