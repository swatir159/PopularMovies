package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DetailDownloaderService extends IntentService {
    private static final String LOG_TAG = DetailDownloaderService.class.getSimpleName();

    public DetailDownloaderService(){
        super("DetailDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "onHandleIntent");

        Context ctx = getApplicationContext();
        if ( !Utilities.isNetworkAvailable(ctx) ){
            Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message3));
            //noInternetAlertMessage();
            return;

        }
        else {
            if (PopMoviesConstants.DEBUG) Log.d(ctx.getString(R.string.logcat_tag), LOG_TAG + ":"+ " onHandleIntent");
            if (intent != null) {
                //final String action = intent.getAction();
                int movie_id;
                long id;
                final String param = intent.getStringExtra(PopMoviesConstants.DETAIL_ACTIVITY_TRAILER_TAG);
                if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":"  + "param : " + param);

                String[] tokens = param.trim().split("_");
                for (String s : tokens)
                    Log.i("PopMovies1", "(" + s + ")");

                movie_id = Integer.parseInt(tokens[0]);
                id = Long.parseLong(tokens[1]);

                MovieDBExtraInfoDownloadService mDownloader = new MovieDBExtraInfoDownloadService(ctx);
                if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":"  + "downloading for id= " + id + "   movieDBId = " + movie_id);
                mDownloader.getMovieDBTrailerInfo(movie_id, id);
                mDownloader.getMovieDBReviewInfo(movie_id, id);
            }
        }
    }
    public static class TrailerAlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (PopMoviesConstants.DEBUG) Log.i( "PopMovies", TrailerAlarmReceiver.class.getSimpleName() + ":" + " downloadTrailerData" );
            Intent sendIntent = new Intent(context, DetailDownloaderService.class);
            //sendIntent.putExtra(SunshineService.LOCATION_QUERY_EXTRA, intent.getStringExtra(SunshineService.LOCATION_QUERY_EXTRA));
            context.startService(sendIntent);

        }
    }
}
