package com.android.rupeeright.popularmovies.DataSync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.android.rupeeright.popularmovies.MovieDetailDownloader.MovieDownloaderSupport;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;

public class MovieDBSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String LOG_TAG = MovieDBSyncAdapter.class.getSimpleName();

    // Interval at which to sync with the movie Database, in seconds.
    // 60 seconds (1 minute) * 60 mins ( 1 hour ) * 24 hours  = 1 day
    public static final int SYNC_INTERVAL = 60 * 60* 1;
    /* 2 hours flexi time */
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
        private static Context mContext;
    private static ContentResolver mContentResolver;
    MovieDownloaderSupport downloader;

    public MovieDBSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContext = context;
        mContentResolver = context.getContentResolver();

    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */

    public MovieDBSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContext = context;
        mContentResolver = context.getContentResolver();

    }

    public static void initialize(Context context) {
        String LOGCAT_TAG = context.getString(R.string.logcat_tag);
        String mAuthority = PopMoviesConstants.CONTENT_AUTHORITY;
        if ( PopMoviesConstants.DEBUG) Log.i(LOGCAT_TAG, LOG_TAG + ":" +  "initializing sync adapter");

        Account mAccount = getSyncAccount(context);
        // Get an instance of the Android account manager
        AccountManager mAccountManager =
                (AccountManager) context.getSystemService(
                        Context.ACCOUNT_SERVICE);

        // if the account type does not exist, add a new account
        String accountType = context.getString(R.string.sync_account_type);
        Account[] defaultAccount =  mAccountManager.getAccountsByType(accountType);
        if (defaultAccount == null || defaultAccount.length ==0  ) {
            if ( PopMoviesConstants.DEBUG) Log.i(LOGCAT_TAG, LOG_TAG + ":" + "account does not exist. Add new account ");
            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.
             */
            if (! mAccountManager.addAccountExplicitly(mAccount, null, null)) {
                /*
                 * If you don't set android:syncable="true" in
                 * in your <provider> element in the manifest,
                 * then call context.setIsSyncable(account, AUTHORITY, 1)
                 * here.
                 */
                 if ( PopMoviesConstants.DEBUG) Log.i(LOGCAT_TAG, LOG_TAG + ":" + "failed to add new account");
            }else {
                //account added successfully, set periodical sync
                mContentResolver.setIsSyncable(mAccount, mAuthority, 1);
                // Inform the system that this account is eligible for auto sync when the network is up
                mContentResolver.setSyncAutomatically(mAccount, mAuthority, true);
                // Recommend a schedule for automatic synchronization. The system may modify this based
                // on other scheduled syncs and network utilization.
                if ( PopMoviesConstants.DEBUG) Log.i(LOGCAT_TAG, LOG_TAG + ":" + "add new account successfully");
                setPeriodicSync(SYNC_INTERVAL, SYNC_FLEXTIME, mAccount, mAuthority);
            }
        }
        if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "from initialize");
        syncImmediately(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        if (PopMoviesConstants.DEBUG) Log.d("PopMovies", LOG_TAG + ":"+ " onHandleIntent");

        downloader = new MovieDownloaderSupport(mContext);
        downloader.downloadMovieData();
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account mAccount = getSyncAccount(context);
        String mAuthority = PopMoviesConstants.CONTENT_AUTHORITY;
        setPeriodicSync( syncInterval, flexTime, mAccount, mAuthority);
    }

    private static void setPeriodicSync(int syncInterval, int flexTime, Account account, String authority){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }
    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {

        if ( PopMoviesConstants.DEBUG) Log.i(context.getString(R.string.logcat_tag), LOG_TAG + ":" +  "executing sync immidiately");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " starting getSyncAccount");
        // Get an instance of the Android account manager
        AccountManager mAccountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == mAccountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!mAccountManager.addAccountExplicitly(newAccount, "", null)) {
                if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " No account added. Return null");
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " Account added");
            onAccountCreated(newAccount, context);
        } else{
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " Account password obtained");
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " starting onAccountCreated");
        /*
         * Since we've created an account
         */
        MovieDBSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, PopMoviesConstants.CONTENT_AUTHORITY, true);

        /*
         * Finally, let's do a sync to get things started
         */
        //if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + "onaccountadded");
        //syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}