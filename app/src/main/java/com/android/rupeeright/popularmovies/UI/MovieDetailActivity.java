package com.android.rupeeright.popularmovies.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

public class MovieDetailActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String InstanceTagLocal = PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        if ( savedInstanceState == null) {
            if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + " OnCreate : attaching fragment");
            Bundle arguments = new Bundle();
            long id = getIntent().getLongExtra( InstanceTagLocal, -1) ;
            arguments.putLong(InstanceTagLocal,id );
            MovieDetailActivityFragment movieDetailFragment = new  MovieDetailActivityFragment();
            movieDetailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.movie_detail_container, movieDetailFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }

}
