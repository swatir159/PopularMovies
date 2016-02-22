package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesColumns;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesSelection;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsContentValues;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersContentValues;
import com.android.rupeeright.popularmovies.POJO.MovieJSON;
import com.android.rupeeright.popularmovies.POJO.MoviesOutputJSON;
import com.android.rupeeright.popularmovies.POJO.ReviewJSON;
import com.android.rupeeright.popularmovies.POJO.ReviewOutputJSON;
import com.android.rupeeright.popularmovies.POJO.TrailerJSON;
import com.android.rupeeright.popularmovies.POJO.TrailerOutputJSON;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by swatir on 2/20/2016.
 */
public class MovieDownloaderSupport {

    private static final String LOG_TAG = MovieDownloaderSupport.class.getSimpleName();

    private  HttpLoggingInterceptor mLogger = null;
    private  OkHttpClient mHttpClient = null;
    private Gson mGson = null;
    private Retrofit mRetrofit = null;
    private GetMovieDBAPIService mService;

    private static Context mContext;
    public MovieDownloaderSupport(Context ctx){

        mContext = ctx;
    }

    public void downloadMovieData(){

        initializeDownloader();
        int curr_page = 1;
        String jsonResponse = getQueryJSON(mContext, curr_page, PopMoviesConstants.PRIMARY_RELEASE_YEAR_TO_GET_DATA);
        if (jsonResponse == null){
            Log.e("PopMovies1", LOG_TAG + ":" + "No data found.Quitting");
            return;
        }

        //Reset the database
        String exclude_favorites = MoviesColumns.TABLE_NAME + "." + MoviesColumns.FAVORITE + "=0"  ;
        mContext.getContentResolver().delete(MoviesColumns.CONTENT_URI, exclude_favorites, null); // delete all except favorites

        long total_page_count = getMovieDataFromJsonandInsertIntoDB(jsonResponse);
        if (PopMoviesConstants.NO_OF_PAGES_TO_DOWNLOAD >0 && PopMoviesConstants.NO_OF_PAGES_TO_DOWNLOAD <= total_page_count){
            total_page_count = PopMoviesConstants.NO_OF_PAGES_TO_DOWNLOAD;
        }
        while (++ curr_page<= total_page_count)
        {
            jsonResponse = getQueryJSON(mContext, curr_page, PopMoviesConstants.PRIMARY_RELEASE_YEAR_TO_GET_DATA);
            getMovieDataFromJsonandInsertIntoDB(jsonResponse);
        }
    }

    private void initializeDownloader(){
        mLogger = new HttpLoggingInterceptor();
        mLogger.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (PopMoviesConstants.DEBUG){

            mHttpClient = new OkHttpClient.Builder().addInterceptor(mLogger).build();

        }
        else
            mHttpClient = new OkHttpClient.Builder().build();
        mGson = new GsonBuilder().create();

         mRetrofit = new Retrofit.Builder()
                .baseUrl(PopMoviesConstants.MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();;

        mService =mRetrofit.create(GetMovieDBAPIService.class);

    }

    /* Builds the URL from PopMoviesConstants & gets the response */
    public String getQueryJSON(Context ctx, int page, int ReleaseYear){

        if (PopMoviesConstants.DEBUG) Log.d("PopMovies", LOG_TAG + ":" + " getQueryJSON:  downloading page : " + page + " for year =" + ReleaseYear);

        Resources res = ctx.getResources();
        String callUrl;
        if ( ReleaseYear >0 )
              callUrl = (Uri.parse(PopMoviesConstants.MOVIEDB_URL + "discover/movie").buildUpon()

                .appendQueryParameter("sort_by", res.getString(R.string.pref_sort_by_default) + "." +
                        res.getString(R.string.pref_sort_by_order_default))
                .appendQueryParameter("api_key", res.getString(R.string.themovieDB_api_key))
                .appendQueryParameter("page", String.valueOf(page))
                .appendQueryParameter("primary_release_year", String.valueOf(ReleaseYear) )).toString();
        else
            callUrl = (Uri.parse(PopMoviesConstants.MOVIEDB_URL + "discover/movie").buildUpon()

                    .appendQueryParameter("sort_by", res.getString(R.string.pref_sort_by_default) + "." +
                            res.getString(R.string.pref_sort_by_order_default))
                    .appendQueryParameter("api_key", res.getString(R.string.themovieDB_api_key))
                    .appendQueryParameter("page", String.valueOf(page)))
                    .toString();
        Request request = new Request.Builder()
                .url(callUrl)
                /* .header("Connection", "close")*/
                .get()
                .build();
        Call call = mHttpClient.newCall(request);
        Response response = null;
        String jsonData = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                jsonData = response.body().string();
                response.body().close();
            }
            if (!response.isSuccessful()) {
                response.body().close();
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /* takes the pagewise JSON list; creates Java object from it and then using content provider, inserts into DB */


    public long getMovieDataFromJsonandInsertIntoDB(String rawJSON) {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" + " getMoviesDatafromJSON  " );

        int inserted = 0;
        int updated = 0;

        MoviesOutputJSON moviesOutput = mGson.fromJson(rawJSON, MoviesOutputJSON.class);
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" + " Movies  =" + moviesOutput.toString());
        List<MovieJSON> movies = moviesOutput.getResults();
        ContentResolver cResolver = mContext.getContentResolver();
        int listSize = movies.size();

        if ( listSize>0)
        {

            ContentValues[] movieToBeAdded = new ContentValues[listSize];
            ContentValues[] movieToBeUpdated = new ContentValues[listSize];
            TrailerOutputJSON[] trailers = new TrailerOutputJSON[listSize];
            ReviewOutputJSON[]  reviews  = new ReviewOutputJSON[listSize];

            int TrailerIndex = 0;
            int ReviewIndex = 0;
            int indexToBeAdded = 0;
            int indexToBeUpdated = 0;

            for( MovieJSON movie : movies){
                int movieDBid = movie.getId();
                ContentValues cv =(createContentValueFromMovie(movie));
                MoviesSelection where = new MoviesSelection();
                where.movieId(movieDBid).and().favorite(Boolean.TRUE);
                MoviesCursor cs = where.query(cResolver);
                cs.moveToFirst();
                if (cs.getCount() == 1) {
                    // record exists - so update it - before update set the fav value with the value from DB
                    cv.put(MoviesColumns.FAVORITE, Boolean.TRUE);
                    String selector = MoviesColumns.TABLE_NAME + "," + MoviesColumns.MOVIE_ID + " =" + movieDBid ;
                    updated +=cResolver.update(MoviesColumns.CONTENT_URI, cv, selector, null);

                } else if (cs.getCount() >1 ){
                    // database integrity issue? rejecting the value
                    if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", ">1 records found in DB for movie ID " + movieDBid );
                } else
                {
                    // perfectly alright - a new record
                    movieToBeAdded[indexToBeAdded ++]=cv;
                }
                cs.close();

                TrailerOutputJSON t = downloadTrailer(movieDBid);
                if ( t!= null && t.getResults().size()>0){
                    trailers[TrailerIndex ++ ] = t;
                }

                ReviewOutputJSON r = downloadReview(movieDBid);
                if ( r!= null && r.getResults().size()>0){
                    reviews[ReviewIndex ++ ] = r;
                }
            }
            inserted = cResolver.bulkInsert(MoviesColumns.CONTENT_URI, movieToBeAdded);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" + inserted +" inserted & " +updated+ " updated /" + movies.size() + " records ");
            /* insert trailers & Reviews after the movie insert - no need to explicitly delete those as they will cascade delete*/
            int inserted1 = insertTrailersToDB(trailers, TrailerIndex - 1);
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" +"  Trailer  records inserted = "  + inserted1);
            int inserted2 = insertReviewsToDB(reviews, ReviewIndex - 1 );
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" +"  Review  records inserted = "  + inserted2);

        }
        else {
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies", LOG_TAG + ":" +  " no records inserted - blank array");
        }

        return moviesOutput.getTotalPages();
    }

    public int insertTrailersToDB(TrailerOutputJSON[]  data, int sizeL ){

        int inserted = 0;
        int count = 0;
        for(int i =0; i<= sizeL; i++)
        {
            TrailerOutputJSON to = data[i];
            if (to == null){ /* error */}
            else{
                List<TrailerJSON> trailers = to.getResults();
                if( trailers !=null && trailers.size() >0 ) {
                    count += trailers.size();

                }else {
                    /* error */
                }
            }
        }
        if (count > 0 )
        {
            ContentValues[] tCV = new ContentValues[count];
            int inc = 0;

            for(int i =0; i<= sizeL; i++)
            {
                TrailerOutputJSON to = data[i];
                if (to == null){ /* error */}
                else{
                    int idFromParam = to.getId();
                    long localDBId = GetLocalDBIdfromMovieDBId(idFromParam);
                    List<TrailerJSON> trailers = to.getResults();
                    if( trailers !=null && trailers.size() >0 ) {
                        for(TrailerJSON trailer: trailers)
                        {
                            TrailersContentValues t = new TrailersContentValues();
                            t.putTrailerId( trailer.getId() );
                            t.putMovieId(localDBId );
                            t.putMoviedbId(idFromParam);
                            t.putIso6391(trailer.getIso6391());
                            t.putKey(trailer.getKey());
                            t.putName(trailer.getName());
                            t.putSite(trailer.getSite());
                            t.putType(trailer.getType());
                            t.putSize(trailer.getSize());
                            tCV[inc++] = t.values();
                        }

                    }else {
                    /* error */
                    }
                }
            }
            inserted = mContext.getContentResolver().bulkInsert(TrailersColumns.CONTENT_URI, tCV );
        }
        else {
            /* no trailers found */
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertTrailerDataToDB :No Trailer inserted :  " );
        }
        return inserted;
    }

    private long GetLocalDBIdfromMovieDBId(int movieIdParam){
        MoviesSelection where = new MoviesSelection();
        where.movieId(movieIdParam);
        MoviesCursor movie = where.query(mContext.getContentResolver());
        if (!movie.moveToFirst()) {
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertTrailerDataToDB : No Record found in DB for MovieDBID =  " + movieIdParam);
            movie.close();
            return 0;
        }
        long id = movie.getId();
        movie.close();
        return id;
    }

    public int insertReviewsToDB(ReviewOutputJSON[]  data, int sizeL ){


        int inserted = 0;
        int count = 0;
        for(int i =0; i<= sizeL; i++)
        {
            ReviewOutputJSON to = data[i];
            if (to == null){ /* error */}
            else{
                List<ReviewJSON> trailers = to.getResults();
                if( trailers !=null && trailers.size() >0 ) {
                    count += trailers.size();

                }else {
                    /* error */
                }
            }
        }
        if (count > 0 )
        {
            ContentValues[] tCV = new ContentValues[count];
            int inc = 0;

            for(int i =0; i<= sizeL; i++)
            {
                ReviewOutputJSON to = data[i];
                if (to == null){ /* error */}
                else{
                    int idFromParam = to.getId();
                    long localDBId = GetLocalDBIdfromMovieDBId(idFromParam);
                    List<ReviewJSON> reviews = to.getResults();
                    if( reviews !=null && reviews.size() >0 ) {
                        for(ReviewJSON review: reviews)
                        {
                            ReviewsContentValues t = new ReviewsContentValues();
                            t.putReviewId(review.getId());
                            t.putMovieId(localDBId);
                            t.putMoviedbId(idFromParam);
                            t.putAuthor(review.getAuthor());
                            t.putContent(review.getContent());
                            t.putUrl(review.getUrl());
                            tCV[inc++] = t.values();
                        }

                    }else {
                    /* error */
                    }
                }
            }
            inserted = mContext.getContentResolver().bulkInsert(ReviewsColumns.CONTENT_URI, tCV );
        }
        else {
            /* no trailers found */
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertReviewDataToDB :No Review inserted :  " );
        }
        return inserted;
    }

    public TrailerOutputJSON downloadTrailer(int movieDBid){

        TrailerOutputJSON data = null;
        /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/

       /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */
        retrofit2.Call<TrailerOutputJSON> call = mService.getTrailerInfoFromAPI(  movieDBid /* 118340 */,
                MovieExtraInforAPIParameters());
        try {
            data = call.execute().body();
           /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/
            //data.setId(movieDBid);

            if (PopMoviesConstants.DEBUG) Log.i("PopMovies" , "Trailer Id from JSON :=" + data.getId() + "Records received : " + data.getResults().size() );
            /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */

        } catch (IOException e) {
            // handle errors
            e.printStackTrace();
        }
        return data;
    }

    public ReviewOutputJSON downloadReview(int movieDBid){

        ReviewOutputJSON data = null;
        /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/

       /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */
        retrofit2.Call<ReviewOutputJSON> call = mService.getReviewInfoFromAPI(  movieDBid /* 118340 */,
                MovieExtraInforAPIParameters());
        try {
            data = call.execute().body();
           /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/
            //data.setId(movieDBid);

            if (PopMoviesConstants.DEBUG) Log.i("PopMovies" , "Review Id from JSON :=" + data.getId() + "Records received : " + data.getResults().size() );
            /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */

        } catch (IOException e) {
            // handle errors
            e.printStackTrace();
        }
        return data;
    }
    /* we need to pass the movie Id as part of URL and also appendto resourse */
    private static Map<String, String> MovieExtraInforAPIParameters(){
        Map<String, String> parameters = new HashMap<>(2);
        parameters.put("api_key", mContext.getResources().getString(R.string.themovieDB_api_key));
        parameters.put("append_to_resource", "reviews,trailers");
        return parameters;
    }

    private ContentValues createContentValueFromMovie(MovieJSON movie){
        ContentValues movieData = new ContentValues();
        movieData.put(MoviesColumns.MOVIE_ID, movie.getId());
        movieData.put(MoviesColumns.TITLE, movie.getTitle());
        movieData.put( MoviesColumns.POSTER_PATH  , movie.getPosterPath());
        movieData.put( MoviesColumns.OVERVIEW , movie.getOverview());
        movieData.put( MoviesColumns.VOTE_AVERAGE   ,movie.getVoteAverage());

        Long releaseDateLocal = 0L;
        try {
            releaseDateLocal = Utilities.getDateInDBDateformatFromMovieDbDateString(movie.getReleaseDate());
        }catch(ParseException e){

        }
        movieData.put( MoviesColumns.RELEASE_DATE  , releaseDateLocal);
        movieData.put( MoviesColumns.BACKDROP_PATH  , movie.getBackdropPath());
        movieData.put(MoviesColumns.ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        movieData.put(  MoviesColumns.ORIGINAL_TITLE , movie.getOriginalTitle());
        movieData.put(MoviesColumns.POPULARITY, movie.getPopularity());
        movieData.put( MoviesColumns.VIDEO , movie.getVideo());
        movieData.put(MoviesColumns.VOTE_COUNT   , movie.getVoteCount());
        movieData.put(MoviesColumns.ADULT   , movie.getAdult());
        movieData.put(MoviesColumns.FAVORITE   , 0);

        return movieData;
    }


}
