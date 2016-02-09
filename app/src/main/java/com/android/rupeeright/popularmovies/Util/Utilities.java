package com.android.rupeeright.popularmovies.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.rupeeright.popularmovies.R;

/**
 * Created by swatir on 2/8/2016.
 */
public class Utilities {
    public static String getSortOrderPreference (Context context) {
        String sortPrefKey = context.getString(R.string.pref_sort_by_key);
        String sortPrefKeyDef = context.getString(R.string.sort_pref_key_default);
        String sortOrderKey = context.getString(R.string.pref_sort_by_order_key);
        String sortPrefOrderKeyDef = context.getString(R.string.sort_pref_order_default);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortKey =  sharedPreferences.getString(sortPrefKey,sortPrefKeyDef ) + "." + sharedPreferences.getString(sortOrderKey, sortPrefOrderKeyDef);
        return sortKey;
    }
}
