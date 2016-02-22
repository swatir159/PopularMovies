package com.android.rupeeright.popularmovies.UI;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.rupeeright.popularmovies.DataSync.MovieDBSyncAdapter;
import com.android.rupeeright.popularmovies.R;

import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callbacks {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private boolean mTwoPane;
    private MainActivityFragment mTaskFragment;
    private String mSortBy;
    private String mSortOrder;

    @Override
    public void onItemSelected(long id){

        if (mTwoPane){
            String InstanceTagLocal = PopMoviesConstants.DETAIL_FRAGMENT_INSTANCE_TAG;
            if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "creating 2nd pane");
            Bundle arguments = new Bundle();
            arguments.putLong(InstanceTagLocal, id);
            MovieDetailActivityFragment movieDetailFragment = new  MovieDetailActivityFragment();
            movieDetailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, movieDetailFragment, PopMoviesConstants.TAG_TASK_DETAIL_FRAGMENT).commit();
        }else
        {
            String InstanceTagLocal = PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
            if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "creating single pane");
            Intent movieDetailIntent = new Intent(this, MovieDetailActivity.class);
            movieDetailIntent.putExtra(InstanceTagLocal, id);
            startActivity(movieDetailIntent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag),getResources().getString(R.string.DebugMsg1) );
        super.onCreate(savedInstanceState);

        mSortBy = Utilities.getSortByPreference(this);
        mSortOrder = Utilities.getSortOrderPreference(this);

        setContentView(R.layout.activity_main);

        if ( savedInstanceState == null){
            int count = Utilities.DataExists(this);
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), LOG_TAG + ":NO Prev instance"
                    + "onCreate : found " + count + " rows");

        /* create a Sync account if not exits & sync immidiately */
            MovieDBSyncAdapter.initialize(this);

        }
        else {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), LOG_TAG + ":Not 1st instance"
                   );
        }


        /* create the main activity fragment  programatically */
        FragmentManager fm = (FragmentManager) this.getSupportFragmentManager();
        mTaskFragment = (MainActivityFragment) fm.findFragmentByTag(PopMoviesConstants.TAG_TASK_MAIN_FRAGMENT);
        // If the Fragment is non-null, then it is currently being retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new MainActivityFragment();
            fm.beginTransaction().add(R.id.movielist_container, mTaskFragment, PopMoviesConstants.TAG_TASK_MAIN_FRAGMENT).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Main);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.movie_detail_container) !=null){
            mTwoPane = true;
            if( savedInstanceState == null){
                mTaskFragment.setActivateOnItemClick(true);
            }
            if (PopMoviesConstants.DEBUG ) Log.d("PopMovies1", LOG_TAG + ":" + "setting two_pane view");
        }
        else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           startActivity( new Intent(this, SettingsActivity.class));
           return true;
        }

        if (id == R.id.action_refresh) {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message13) );
            //createMovieList();
            //Utilities.downloadMovieData(this, LOG_TAG, getSupportFragmentManager()) ;
            MovieDBSyncAdapter.syncImmediately(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String SortBy = Utilities.getSortByPreference(this);
        String SortOrder = Utilities.getSortOrderPreference(this);
        // update the location in our second pane using the fragment manager
        if (!SortBy.equals(mSortBy)  || !SortOrder.equals(mSortOrder)) {
            MainActivityFragment maf = (MainActivityFragment)getSupportFragmentManager().findFragmentById(R.id.movielist_container);
            if ( null != maf ) {
                maf.onSettingChanged(SortBy, SortOrder);
            }
            MovieDetailActivityFragment df = (MovieDetailActivityFragment)getSupportFragmentManager().
                                                    findFragmentById(R.id.movie_detail_container);
            if ( null != df ) {
                df.onSettingsChanged(SortBy, SortOrder);
            }
            mSortBy= SortBy;
            mSortOrder = SortOrder;
        }
    }
}
