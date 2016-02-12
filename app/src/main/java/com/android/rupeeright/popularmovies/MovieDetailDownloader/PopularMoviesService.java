package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import com.android.rupeeright.popularmovies.DataStorage.MovieContract;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.android.rupeeright.popularmovies.WebDataFetcher.AsyncDownloader;
import com.android.rupeeright.popularmovies.WebDataFetcher.MovieDBConnector;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PopularMoviesService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private final String LOG_TAG = PopularMoviesService.class.getSimpleName();



    /*
    private static final String ACTION_FOO = "com.android.rupeeright.popularmovies.MovieDetailDownloader.action.FOO";
    private static final String ACTION_BAZ = "com.android.rupeeright.popularmovies.MovieDetailDownloader.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.android.rupeeright.popularmovies.MovieDetailDownloader.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.android.rupeeright.popularmovies.MovieDetailDownloader.extra.PARAM2";
 */
    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    /*
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PopularMoviesService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
*/
    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PopularMoviesService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }
    */

    public PopularMoviesService() {
        super("PopularMoviesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       /* if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
        */

        if (PopMoviesConstants.DEBUG) Log.d("PopMovies", LOG_TAG + ":"+ " onHandleIntent");

        MovieDBConnector url = MovieDBConnector.getInstance();
        //String newSortOrderPref = Utilities.getSortOrderPreference(getActivity());

        url.setmAPIKey(getApplicationContext().getResources().getString(R.string.themovieDB_api_key));
        String getMoviesHttpMethod = url.getMoviesQuery("Popularity.Asc");

        downloadDataandInsertintoDB(getMoviesHttpMethod);

    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
   /*  private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }
*/
    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
  /*  private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
    */

    protected Void downloadDataandInsertintoDB(String urlParam)
    {
        //String url = params[0];

        if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" + " downloadDataandInsertintoDB");

        /*
        OkHttpClient cl = new OkHttpClient();
Request req = new Request.Builder()
              .url("http://www.google.com")
              .header("Connection", "close")
              .get()
              .build();
Response res = cl.newCall(req).execute();
         */
        OkHttpClient client = new OkHttpClient();

        //client.networkInterceptors().add(new StethoInterceptor());
        //client.setConnectTimeout(150, "ms");
        Request request = new Request.Builder()
                .url(urlParam)
                .header("Connection", "close")
                .get()
                .build();
        Call call = client.newCall(request);

        Response response = null;

        String jsonData = null;

        try {
                response = call.execute();
                if (response.isSuccessful()) {
                    jsonData = response.body().string();
                    if (PopMoviesConstants.DEBUG) Log.i("PopMovies", "Data Downloaded - Now will Parse & enter in DB");
                    getMovieDataFromJson(jsonData);
                    response.body().close();
                }
                if (!response.isSuccessful()) {
                    response.body().close();
                    throw new IOException("Unexpected code " + response);
                }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // return jsonData; //This is returned to onPostExecute()
        return null;
    }


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

        if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" + " getMoviesDatafromJSON");

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
                            Long ReleaseDate = 0L;
                        if (DateLocal != null && !DateLocal.equals("null")&& !DateLocal.equals("")) {
                            ReleaseDate = Utilities.getDateInDBDateformatFromMovieDbDateString(DateLocal);
                        }

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
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_RELEASE_DATE, ReleaseDate);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_TITLE, TitleLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VIDEO, VideoLocal); /* Boolean.FALSE */
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_AVERAGE, RatingLocal);
                            movieValues.put(MovieContract.MoviesEntry.COLUMN_VOTE_COUNT, VoteCountLocal);
                            cVVector.add(movieValues);

                            //Film FilmLocal = new Film(IdLocal, TitleLocal, PosterPathLocal, OverviewLocal, RatingLocal,strRatingLocal, DateLocal);
                            //Log.d("logcat_tag", "The Film [" + Integer.toString(i) + "] Details :=" + FilmLocal.toString());
                            //mFilmsList.add(FilmLocal);

                    }
                    int inserted = 0;
                    // add to database
                    if (cVVector.size() > 0) {
                        // Student: call bulkInsert to add the weatherEntries to the database here
                        ContentValues[] cvArray = new ContentValues[cVVector.size()];
                        cVVector.toArray(cvArray);
                        getApplicationContext().getContentResolver().bulkInsert(MovieContract.MoviesEntry.CONTENT_URI, cvArray);
                    }
                    if (PopMoviesConstants.DEBUG) Log.d(LOG_TAG, "PopularMovies Service Complete. " + cVVector.size() + " Inserted");
                }
                // Collections.sort(films, new FilmComparator());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
       /* String[] resultStrs = convertContentValuesToUXFormat(cVVector);
        return resultStrs; */
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
