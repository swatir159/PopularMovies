package com.android.rupeeright.popularmovies.DataSync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MovieDBSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static MovieDBSyncAdapter sSMovieDBSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("MovieDBSyncService", "onCreate - MovieSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSMovieDBSyncAdapter == null) {
                sSMovieDBSyncAdapter = new MovieDBSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSMovieDBSyncAdapter.getSyncAdapterBinder();
    }
}