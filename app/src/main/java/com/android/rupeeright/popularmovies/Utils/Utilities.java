package com.android.rupeeright.popularmovies.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.MovieDBHelper;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesSelection;
import com.android.rupeeright.popularmovies.MovieDetailDownloader.PopularMoviesService;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UISupport.NoInternetConnectionDialog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.*;



/**
 * Created by swatir on 2/8/2016.
 */
public class Utilities {

    public static boolean isNetworkAvailable(Context ctx ) {
        ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    public static void downloadMovieData(Context ctx, String LOG_TAG, FragmentManager dlgFragment) {

        Resources Res = ctx.getResources();
        if (PopMoviesConstants.DEBUG) Log.i(Res.getString(R.string.logcat_tag), LOG_TAG + ":" + " downloadMovieData");
        if ( !Utilities.isNetworkAvailable(ctx) ){
            Log.d(Res.getString(R.string.logcat_tag), Res.getString(R.string.Message3));
            if (dlgFragment != null) {
                NoInternetConnectionDialog dialog = new NoInternetConnectionDialog();
            }
            else {
                Toast toast = Toast.makeText(ctx, Res.getString(R.string.no_internet_message), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else{
            Intent alarmIntent = new Intent(ctx, PopularMoviesService.AlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(ctx, 0, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pi);

            // MovieDBSyncAdapter.syncImmediately(getActivity());
        }
    }

    public static String getSortByPreference (Context context) {
        String sortPrefKey = context.getString(R.string.pref_sort_by_key);
        String sortPrefKeyDef = context.getString(R.string.sort_pref_key_default);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortKey =  sharedPreferences.getString(sortPrefKey,sortPrefKeyDef ) ;
        return sortKey;
    }

    public static String getSortOrderPreference (Context context) {
        String sortOrderKey = context.getString(R.string.pref_sort_by_order_key);
        String sortPrefOrderKeyDef = context.getString(R.string.sort_pref_order_default);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortKey =   sharedPreferences.getString(sortOrderKey, sortPrefOrderKeyDef);
        return sortKey;
    }


    public static long normalizeDate(long startDate) {
        // normalize the start date to the beginning of the (UTC) day
        Time time = new Time();
        time.set(startDate);
        int julianDay = Time.getJulianDay(startDate, time.gmtoff);
        return time.setJulianDay(julianDay);
    }

    /**
     * Convert movieDB date string to Long Date( millisec)  - takes a date string returns long

     * @throws ParseException
     */
    public static long getDateInDBDateformatFromMovieDbDateString (String movieDbDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = dateFormat.parse(movieDbDate);
        return date.getTime();
    }

    /**
     * Convert epoch milliseconds to the date strings that movieDB uses
     * @param milliseconds
     * @return
     */
    public static String DbDateInStringFromMilliseconds(long milliseconds) {
        Date date = new Date(milliseconds);
        DateFormat format = new SimpleDateFormat("d MMM yyyy");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }

/*    public static RESTAPIError parseError(Response<?> response){
        Converter<ResponseBody, RESTAPIError> converter =
                MovieDBExtraInfoDownloadServiceGenerator.getRetrofit().responseBodyConverter(RESTAPIError.class, new Annotation[0]);
        RESTAPIError error;
        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new RESTAPIError();
        }
        return error;
    }
*/
    public static boolean DatabaseBExits(Context ctx){
        MovieDBHelper mv = MovieDBHelper.getInstance(ctx);
        SQLiteDatabase db = null;
        try{
            db= mv.getReadableDatabase();
            db.close();

        } catch (SQLiteCantOpenDatabaseException e){
            // database does not exist yet!
        }
        return (db != null);
    }

    public static int DataExists(Context ctx) {
        int count = 0;
        if (DatabaseBExits(ctx)){
            MoviesSelection where = new MoviesSelection();
            MoviesCursor cursor = where.query(ctx.getContentResolver());
            cursor.moveToFirst();
            count = cursor.getCount();
            cursor.close();

        }
        return ( count );
    }

}
