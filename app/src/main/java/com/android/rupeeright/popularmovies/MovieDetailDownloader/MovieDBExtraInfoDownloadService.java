package com.android.rupeeright.popularmovies.MovieDetailDownloader;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesCursor;
import com.android.rupeeright.popularmovies.DataStorage.movies.MoviesSelection;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsColumns;
import com.android.rupeeright.popularmovies.DataStorage.reviews.ReviewsContentValues;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersColumns;
import com.android.rupeeright.popularmovies.DataStorage.trailers.TrailersContentValues;
import com.android.rupeeright.popularmovies.POJO.ReviewJSON;
import com.android.rupeeright.popularmovies.POJO.ReviewOutputJSON;
import com.android.rupeeright.popularmovies.POJO.TrailerJSON;
import com.android.rupeeright.popularmovies.POJO.TrailerOutputJSON;
import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.RESTAPIError;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by swatir on 2/14/2016.
 */
public class MovieDBExtraInfoDownloadService {
    private static final String LOG_TAG = MovieDBExtraInfoDownloadService.class.getSimpleName();

    //private List<TrailerJSON> mTrailers;
    private static Context mContext;
    GetMovieDBAPIService mService =null;

   public MovieDBExtraInfoDownloadService(Context ctx){
        mContext = ctx;
       initialize();
    }

    public Context getmContext() {
        return this.mContext;
    }

    private long GetLocalDBIdfromMovieDBId(int movieIdParam){
        MoviesSelection where = new MoviesSelection();
        where.movieId(movieIdParam);
        MoviesCursor movie = where.query(this.getmContext().getContentResolver());
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
    /* To insert the data in DB */
    private int insertTrailerDataToDB(TrailerOutputJSON dataParam, long id1 ){

        /* get the ID field for the passed MovieDBID              */
        if (dataParam != null && dataParam.getResults()!=null && dataParam.getResults().size()>0) {
            int idFromParam = dataParam.getId();
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertTrailerDataToDB : " + dataParam.toString());
             ContentValues[] trailerValues = new ContentValues[dataParam.getResults().size()];
            int inc = 0;
            List<TrailerJSON> trailers = dataParam.getResults();
            for (TrailerJSON trailer : trailers) {
                TrailersContentValues t = new TrailersContentValues();
                t.putTrailerId(trailer.getId());
                //t.putMovieId( GetLocalDBIdfromMovieDBId(idFromParam));
                t.putMovieId( id1);
                t.putMoviedbId(idFromParam);
                t.putIso6391(trailer.getIso6391());
                t.putKey(trailer.getKey());
                t.putName(trailer.getName());
                t.putSite(trailer.getSite());
                t.putType(trailer.getType());
                t.putSize(trailer.getSize());
                trailerValues[inc++] = t.values();
            }
             return this.getmContext().getContentResolver().bulkInsert(TrailersColumns.CONTENT_URI, trailerValues);
        }
        else {
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertTrailerDataToDB :Error in parameter :  " + dataParam.toString());
            return 0;
        }

    }

    private int insertReviewDataToDB(ReviewOutputJSON dataParam, long id1 ){

        /* get the ID field for the passed MovieDBID
         *
        */
        if (dataParam != null && dataParam.getResults()!=null && dataParam.getResults().size()>0) {

            int idFromParam = dataParam.getId();
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertReviewDataToDB : " + dataParam.toString());

            //Vector<TrailersContentValues> cVVector = new Vector<TrailersContentValues>(dataParam.getResults().size());
            ContentValues[] reviewValues = new ContentValues[dataParam.getResults().size()];
            int inc = 0;
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertReviewDataToDB : " + dataParam.toString());
            List<ReviewJSON> reviews = dataParam.getResults();
            for (ReviewJSON review : reviews) {
                ReviewsContentValues t = new ReviewsContentValues();
                t.putReviewId(review.getId());
                //t.putMovieId(GetLocalDBIdfromMovieDBId(idFromParam));
                t.putMovieId(id1);
                t.putMoviedbId(idFromParam);
                t.putAuthor(review.getAuthor());
                t.putContent(review.getContent());
                t.putUrl(review.getUrl());
                reviewValues[inc++] = t.values();
            }

            return this.getmContext().getContentResolver().bulkInsert(ReviewsColumns.CONTENT_URI, reviewValues);
        }
        else {
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + "insertReviewDataToDB :Error in parameter :  " + dataParam.toString());
            return 0;
        }

    }


    /* we need to pass the movie Id as part of URL and also appendto resourse */
   private static Map<String, String> MovieExtraInforAPIParameters(){
       Map<String, String> parameters = new HashMap<>(2);
       parameters.put("api_key", mContext.getResources().getString(R.string.themovieDB_api_key));
       parameters.put("append_to_resource", "reviews,trailers");
       return parameters;
   }

    public void initialize(){
        HttpLoggingInterceptor myLogger = new HttpLoggingInterceptor();
        myLogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient;
        if (PopMoviesConstants.DEBUG){
            httpClient = new OkHttpClient.Builder().addInterceptor(myLogger).build();
        }
        else httpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopMoviesConstants.MOVIEDB_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();;

        mService =retrofit.create(GetMovieDBAPIService.class);

    }

   public void  getMovieDBTrailerInfo( int movieIDParam, long id){
       if (PopMoviesConstants.DEBUG)
           Log.d("PopMovies1", LOG_TAG + ":" + " getMovieDBExtraInfoFromAPIService");

       //MovieDBExtraInfoDownloadServiceGenerator serviceGen = new MovieDBExtraInfoDownloadServiceGenerator(PopMoviesConstants.TRAILER_TYPE);
       //mService =serviceGen.createService(GetMovieDBAPIService.class);


       /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/
        if ( mService == null) initialize();
       /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */
       Call<TrailerOutputJSON> call = mService.getTrailerInfoFromAPI(  movieIDParam  ,
                MovieExtraInforAPIParameters());
       try {
           TrailerOutputJSON data = call.execute().body();
           /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/
           //data.setId(movieIDParam);

            if (PopMoviesConstants.DEBUG) Log.i("PopMovies" , "Id from JSON :=" + data.getId() );
            /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */
           insertTrailerDataToDB(data, id);
       } catch (IOException e) {
           // handle errors
           e.printStackTrace();
       }

   } // getTrailerInfo

    public void  getMovieDBReviewInfo( int movieIDParam, long id){
        if (PopMoviesConstants.DEBUG)
            Log.d("PopMovies1", LOG_TAG + ":" + " getMovieDBReviewInfo");

        if ( mService == null) initialize();

        if (PopMoviesConstants.DEBUG)
            Log.d("PopMovies1", LOG_TAG + ":" + "after retrofit create");

        Call<ReviewOutputJSON> call = mService.getReviewInfoFromAPI(movieIDParam,
                MovieExtraInforAPIParameters());
        if (PopMoviesConstants.DEBUG)
            Log.d("PopMovies1", LOG_TAG + ":" + "after the call");

        try {
            ReviewOutputJSON data = call.execute().body();
           /* hardcoding movie id  for testing >>>>>>>>>>>>>>>>>>>>>>>>*/
            //data.setId(movieIDParam);
            /* <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< hardcoding movie id  for testing */
            insertReviewDataToDB(data, id);
        } catch (IOException e) {
            // handle errors
            e.printStackTrace();
        }
    } // getReviewInfo





} // Public class MovieDBExtraInforDownloadService
