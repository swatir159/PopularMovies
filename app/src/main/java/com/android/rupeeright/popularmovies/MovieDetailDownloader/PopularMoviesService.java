package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;


import com.android.rupeeright.popularmovies.DataStorage.MovieDetailProvider;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesSelection;
import com.android.rupeeright.popularmovies.POJO.MovieJSON;
import com.android.rupeeright.popularmovies.POJO.MoviesOutputJSON;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PopularMoviesService extends IntentService {
    private final String LOG_TAG = PopularMoviesService.class.getSimpleName();

    public PopularMoviesService() {
        super("PopularMoviesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /* using two constants to restrict the download of the data , primary_release_year and
                    no_of_pages_to_downlaod, both defined in  PopMoviesConstant
         */
        if (PopMoviesConstants.DEBUG) Log.d("PopMovies", LOG_TAG + ":"+ " onHandleIntent");
        Context ctx = getApplicationContext();
        MovieDownloaderSupport d = new MovieDownloaderSupport(ctx);
        d.downloadMovieData();
    }
    public static class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (PopMoviesConstants.DEBUG) Log.i( "PopMovies", AlarmReceiver.class.getSimpleName() + ":" + " downloadMovieData" );
            Intent sendIntent = new Intent(context, PopularMoviesService.class);
            //sendIntent.putExtra(SunshineService.LOCATION_QUERY_EXTRA, intent.getStringExtra(SunshineService.LOCATION_QUERY_EXTRA));
            context.startService(sendIntent);

        }
    }
}