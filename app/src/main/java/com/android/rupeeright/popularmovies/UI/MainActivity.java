package com.android.rupeeright.popularmovies.UI;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.rupeeright.popularmovies.R;

import com.android.rupeeright.popularmovies.Util.PopMoviesConstants;

public class MainActivity extends AppCompatActivity {


    private MainActivityFragment mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag),getResources().getString(R.string.DebugMsg1) );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* create the fragment  programatically */
        android.app.FragmentManager fm = (FragmentManager) this.getFragmentManager();
        mTaskFragment = (MainActivityFragment) fm.findFragmentByTag(PopMoviesConstants.TAG_TASK_MAIN_FRAGMENT);

        // If the Fragment is non-null, then it is currently being
        // retained across a configuration change.
        if (mTaskFragment == null) {
            mTaskFragment = new MainActivityFragment();
            fm.beginTransaction().add(R.id.fragment_container, mTaskFragment, PopMoviesConstants.TAG_TASK_MAIN_FRAGMENT).commit();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_Main);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
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

        return super.onOptionsItemSelected(item);
    }
}
