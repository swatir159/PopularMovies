package com.android.rupeeright.popularmovies.WebDataFetcher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UI.MainActivityFragment;
import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.Util.PopMoviesConstants;


import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by swatir on 1/13/2016.
 */
public class AsyncDownloader extends AsyncTask<String, Integer, String> {

    //public static final String TAG = AsyncDownloader.class.getSimpleName();
    private static final boolean DEBUG = true; // Set this to false to disable logs.

    private Context mContext;
    private MainActivityFragment mMovieGridFragment;
    private List<Film> mFilmsList;
    private ProgressDialog mDialog;
    private String mURL;
    //private long mStartTime;

    public AsyncDownloader(Context ctxParam, MainActivityFragment movieFrgParam ) {
        mContext = ctxParam;
        //classToLoad = c;
        this.mMovieGridFragment = movieFrgParam;
        this.mDialog = null;
        this.mURL = null;

    }

    /**
     * onPreExecute runs on the UI thread before doInBackground.
     * This will start showing a small dialog that says Loading with a spinner
     * to let the user know download is in progress
     */
    @Override
    protected void onPreExecute() {
//        if (mMovieGridFragment.getCallbacks() != null) {
        super.onPreExecute();

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage(mContext.getString(R.string.Message8));
        mDialog.setProgressStyle(mDialog.STYLE_SPINNER);
        mDialog.setCancelable(true);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
    //mStartTime = System.currentTimeMillis();
    //   }


    public String getmURL() {
        return mURL;
    }

    public void setmURL(String url) {
        this.mURL = url;
    }
    /**
     * doInBackground() runs in the background on a worker thread. This is where code that can block the GUI should go.
     *  Since we are using asynctask this is already in background threas we use okHttp method
     *  call.execute() which executes in current thread (which is the background threas of this Async class)
     *  Once we finish retrieving jsonData it is passed to method onPostExecute()
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {

        //String url = params[0];
        //long timeNow;
        setmURL(params[0]);
        if (PopMoviesConstants.DEBUG) Log.i(mContext.getString(R.string.logcat_tag), mContext.getString(R.string.Message5));

        OkHttpClient client = new OkHttpClient();
        //client.setConnectTimeout(150, "ms");
        Request request = new Request.Builder()
                .url(mURL)
                .build();
        Call call = client.newCall(request);

        Response response = null;

        String jsonData = null;

        try {
            //timeNow = System.currentTimeMillis();
            if (!isCancelled()) {
                response = call.execute();
                if (response.isSuccessful()) {
                    jsonData = response.body().string();
                    if (PopMoviesConstants.DEBUG) Log.i(mContext.getString(R.string.logcat_tag), mContext.getString(R.string.Message6));

                    response.body().close();
                }
                if (!response.isSuccessful()) {
                    response.body().close();
                    throw new IOException("Unexpected code " + response);
                }

            }

            //(timeNow - mStartTime < (5 * 60 * 1000));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData; //This is returned to onPostExecute()
    }

    @Override
    protected void onCancelled() {
        //if (mMovieGridFragment.getCallbacks() != null) {
        super.onCancelled();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            this.mDialog = null;
        }

        if (PopMoviesConstants.DEBUG) Log.i(mContext.getString(R.string.logcat_tag), mContext.getString(R.string.Message9));
        Toast toast = Toast.makeText(mMovieGridFragment.getActivity(),
                mContext.getString(R.string.Message9), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
        //mFilmsList = null;
        //mMovieGridFragment.updateFilmList(mFilmsList);
        //mMovieGridFragment.updateAdapter(mFilmsList);
        //userOnBoardingTask=null;
        //}
    }
    private void getFilmsFrom(String rawJSON) {
        JSONObject results = null;
        if (rawJSON != null && rawJSON.trim().length()>0 ) {
            try
            {
                results = new JSONObject(rawJSON);
                JSONArray data = results.getJSONArray("results");
                if (data != null )
                {
                    int dataSize = data.length();
                    if (dataSize == 0) {
                        //showNotFoundNotification();
                        //super.onBackPressed();
                    }
                    mFilmsList = new ArrayList<Film>(dataSize);
                    for (int i = 0; i < dataSize; i++) {
                        //Log.d("logcat_tag", Integer.toString(i) + "th data in Response ");
                        JSONObject jsonFilm;
                        jsonFilm = data.getJSONObject(i);
                        String DateLocal = jsonFilm.getString("release_date");
                        if (DateLocal != null && !DateLocal.equals("null"))
                        {
                            String IdLocal = jsonFilm.getString("id");
                            String TitleLocal= jsonFilm.getString("title");
                            String PosterPathLocal = jsonFilm.getString("poster_path");
                            String OverviewLocal = jsonFilm.getString("overview");
                            String strRatingLocal = jsonFilm.getString("vote_average");
                            float RatingLocal = Float.parseFloat(jsonFilm.getString("vote_average"));
                            Film FilmLocal = new Film(IdLocal, TitleLocal, PosterPathLocal, OverviewLocal, RatingLocal,strRatingLocal, DateLocal);
                            //Log.d("logcat_tag", "The Film [" + Integer.toString(i) + "] Details :=" + FilmLocal.toString());
                            mFilmsList.add(FilmLocal);
                        }
                    }
                }
                // Collections.sort(films, new FilmComparator());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * onPostExecute runs on the  main (GUI) thread and receives
     * the result of doInBackground.
     *
     * Here we pass a string representation of jsonData to the child/receiver
     * activity.
     *
     * @param jsonData
     */
    @Override
    protected void onPostExecute(String jsonData) {
        //if (mMovieGridFragment.getCallbacks()!= null ) {
        super.onPostExecute(jsonData);

        if (jsonData != null && jsonData.length() > 0) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                this.mDialog = null;
            }

            getFilmsFrom(jsonData);
            if (mFilmsList != null) {
                mMovieGridFragment.updateFilmList(mFilmsList);
                mMovieGridFragment.updateAdapter(mFilmsList);
            }
        }
        //}
    }

   /* private void hideProgress() {
        if(mDialog != null) {
            if(mDialog.isShowing()) { //check if dialog is showing.

                //get the Context object that was used to great the dialog
                Context context = ((ContextWrapper)mDialog.getContext()).getBaseContext();

                //if the Context used here was an activity AND it hasn't been finished or destroyed
                //then dismiss it
                if(context instanceof Activity) {
                    if(!((Activity)context).isFinishing()) // && !((Activity)context).isDestroyed()
                        mDialog.dismiss();
                } else //if the Context used wasnt an Activity, then dismiss it too
                    mDialog.dismiss();
            }
            mDialog = null;
        }
    }
    */
}
