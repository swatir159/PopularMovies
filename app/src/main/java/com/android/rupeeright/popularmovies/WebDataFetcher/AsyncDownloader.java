package com.android.rupeeright.popularmovies.WebDataFetcher;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.DataStorage.MovieContract;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UI.MainActivityFragment;
import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;


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
import java.util.Vector;


/**
 * Created by swatir on 1/13/2016.
 */
public class AsyncDownloader extends AsyncTask<String, Void, Void> {

    //public static final String TAG = AsyncDownloader.class.getSimpleName();


    private Context mContext;
    //private MainActivityFragment mMovieGridFragment;
    //private List<Film> mFilmsList;
    //private ProgressDialog mDialog;
    private String mURL;
    //private long mStartTime;

    public AsyncDownloader(Context ctxParam /*, MainActivityFragment movieFrgParam */ ) {
        mContext = ctxParam;
        //classToLoad = c;
        //this.mMovieGridFragment = movieFrgParam;
        //this.mDialog = null;
        this.mURL = null;

    }

    /**
     * onPreExecute runs on the UI thread before doInBackground.
     * This will start showing a small dialog that says Loading with a spinner
     * to let the user know download is in progress
     */
    /*
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

    */
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
    protected Void doInBackground(String... params)
    {
        //String url = params[0];
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
                    getMovieDataFromJson(jsonData);
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // return jsonData; //This is returned to onPostExecute()
        return null;
    }

    /*  *************************************
    @Override
    protected void onCancelled() {
        //if (mMovieGridFragment.getCallbacks() != null) {
        super.onCancelled();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            this.mDialog = null;
        }

        if (PopMoviesConstants.DEBUG) Log.i(mContext.getString(R.string.logcat_tag), mContext.getString(R.string.Message9));
       // Toast toast = Toast.makeText(mMovieGridFragment.getActivity(),
       //         mContext.getString(R.string.Message9), Toast.LENGTH_LONG);
       // toast.setGravity(Gravity.TOP, 25, 400);
       // toast.show();
       //
        //mFilmsList = null;
        //mMovieGridFragment.updateFilmList(mFilmsList);
        //mMovieGridFragment.updateAdapter(mFilmsList);
        //userOnBoardingTask=null;
        //}
    }
    *************************************** */
    /*
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
*   */

  /*  private String[] getMovieDataFromJson(String rawJSON) */
    private void getMovieDataFromJson(String rawJSON)
            throws JSONException {

        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.

        /* Return result from the API
         {
         "poster_path":"\/atZ4SrQv0eB58M5Nr1Tn8Ttp3p0.jpg",
         "adult":false,
         "overview":"Sunil belongs to a middle-class family, and is intent in pursuing his career with a music group,
                     despite of his dad disapproval. Sunil is also in love with Anna, but Anna does not really love him,
                     but likes him as a friend. Sunil is persistent, but instead Anna openly declares her love for Chris.
                     In order to impress his dad, Sunil forges his examination results, but then later confesses to his family,
                     who receive this news in utter dismay. Will this change Sunil's ways?
                     Will Anna change her mind about Sunil or will she get married to Chris?",
         "release_date":"1993-01-01",
         "genre_ids":[35,18,10402],
         "id":15419,
         "original_title":"Kabhi Haan Kabhi Naa",
         "original_language":"hi",
         "title":"Kabhi Haan Kabhi Naa",
         "backdrop_path":"\/hAJ3Ea1ZbIjRQKf9ht8LvWwOrSe.jpg",
         "popularity":1,
         "vote_count":3,
         "video":false,
         "vote_average":6.83}

        */

        final String POSTER_PATH= "poster_path";
        final String ADULT=        "adult";
        final String OVERVIEW=        "overview";
        final String RELEASE_DATE = "release_date";
        final String GENRE_IDS =         "genre_ids";
        final String ID = "id";
        final String ORIGINAL_TITLE = "original_title";
        final String ORIGINAL_LANGUAGE = "original_language";
        final String TITLE =       "title";
        final String BACKDROP_PATH = "backdrop_path";
        final String POPULARITY = "popularity";
        final String VOTE_COUNT = "vote_count";
        final String VIDEO = "video";
        final String VOTE_AVERAGE = "vote_average";

                // Weather information.  Each day's forecast info is an element of the "list" array.
        final String RESULTS = "results";

        JSONObject results = null;
        if (rawJSON != null && rawJSON.trim().length()>0 ) {
            try {
                results = new JSONObject(rawJSON);
                JSONArray data = results.getJSONArray(RESULTS);
                if (data != null) {
                    int dataSize = data.length();
                    if (dataSize == 0) {
                        //showNotFoundNotification();
                        //super.onBackPressed();
                    }
                    //mFilmsList = new ArrayList<Film>(dataSize);

                    Vector<ContentValues> cVVector = new Vector<ContentValues>(dataSize);

                    Time dayTime = new Time();
                    dayTime.setToNow();

                    // we start at the day returned by local time. Otherwise this is a mess.
                    int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

                    // now we work exclusively in UTC
                    dayTime = new Time();


                    for (int i = 0; i < dataSize; i++) {
                        //Log.d("logcat_tag", Integer.toString(i) + "th data in Response ");
                        JSONObject jsonFilm;
                        jsonFilm = data.getJSONObject(i);
                        String DateLocal = jsonFilm.getString(RELEASE_DATE);
                        if (DateLocal != null && !DateLocal.equals("null")) {
                            String IdLocal = jsonFilm.getString(ID);
                            Boolean AdultLocal = jsonFilm.getBoolean(ADULT);
                            Boolean VideoLocal = jsonFilm.getBoolean(VIDEO);
                            String TitleLocal = jsonFilm.getString(TITLE);
                            String PosterPathLocal = jsonFilm.getString(POSTER_PATH);
                            String OverviewLocal = jsonFilm.getString(OVERVIEW);
                            String PopularityLocal = jsonFilm.getString(POPULARITY);
                            String strRatingLocal = jsonFilm.getString(VOTE_AVERAGE);
                            String OriginalTitleLocal = jsonFilm.getString(ORIGINAL_TITLE);
                            String OriginalLanguageLocal = jsonFilm.getString(ORIGINAL_LANGUAGE);
                            float RatingLocal = Float.parseFloat(jsonFilm.getString(VOTE_AVERAGE));
                            String BackdropPathLocal = jsonFilm.getString(BACKDROP_PATH);
                            String VoteCountLocal = jsonFilm.getString(VOTE_COUNT);
                            /*
                            final String GENRE_IDS =         "genre_ids";
                            final String VOTE_COUNT = "vote_count";
                            */


                            ContentValues movieValues = new ContentValues();

                            movieValues.put(MovieContract.MoviesEntry.COLUMN_ADULT, AdultLocal); /* Boolean.TRUE */
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_BACKDROP_PATH, BackdropPathLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, IdLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_ORIGINAL_LAN, OriginalLanguageLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_ORIGINAL_TITLE, TitleLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_OVERVIEW, OverviewLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_POPULARITY, PopularityLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_POSTER_PATH, PosterPathLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, DateLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_TITLE, TitleLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VIDEO, VideoLocal); /* Boolean.FALSE */
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE, RatingLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_COUNT, VoteCountLocal);
                            cVVector.add(movieValues);

                            //Film FilmLocal = new Film(IdLocal, TitleLocal, PosterPathLocal, OverviewLocal, RatingLocal,strRatingLocal, DateLocal);
                            //Log.d("logcat_tag", "The Film [" + Integer.toString(i) + "] Details :=" + FilmLocal.toString());
                            //mFilmsList.add(FilmLocal);
                        }
                    }
                    // add to database
                    if (cVVector.size() > 0) {
                        // Student: call bulkInsert to add the weatherEntries to the database here
                        ContentValues[] cvArray = new ContentValues[cVVector.size()];
                        cVVector.toArray(cvArray);
                        mContext.getContentResolver().bulkInsert(MovieContract.MoviesEntry.CONTENT_URI, cvArray);
                    }
                }
                // Collections.sort(films, new FilmComparator());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
       /* String[] resultStrs = convertContentValuesToUXFormat(cVVector);
        return resultStrs; */
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
    /*
    @Override
    protected void onPostExecute(String jsonData) {
        //if (mMovieGridFragment.getCallbacks()!= null ) {
        super.onPostExecute(jsonData);

        if (jsonData != null && jsonData.length() > 0) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                this.mDialog = null;
            }

            //getFilmsFrom(jsonData);

            try {
                    getMovieDataFromJson(jsonData);
            } catch (JSONException e) {
                    e.printStackTrace();
            }



            if (mFilmsList != null) {
                mMovieGridFragment.updateFilmList(mFilmsList);
                mMovieGridFragment.updateAdapter(mFilmsList);
            }
        }
        //}
    }
    */

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
