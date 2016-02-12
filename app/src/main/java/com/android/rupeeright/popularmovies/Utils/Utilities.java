package com.android.rupeeright.popularmovies.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.format.Time;

import com.android.rupeeright.popularmovies.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by swatir on 2/8/2016.
 */
public class Utilities {
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
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(date);
    }
}
