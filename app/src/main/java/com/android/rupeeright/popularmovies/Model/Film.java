package com.android.rupeeright.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by swatir on 1/13/2016.
 */
public class Film /* implements Parcelable */ {
    /*
    original title
movie poster image thumbnail
A plot synopsis (called overview in the api)
user rating (called vote_average in the api)
release date

     */
    private String mID;
    private String mTitle;
    private String mPosterPath;
    private String mOverview;
    private float mRating;
    private String mStrRating;
    private String mDate;
    private String mFormattedDate;
    private String mBackdropPath;

    public Film() {
    }

    public Film(String IDParam, String TitleParam, String PosterPathParam, String OverViewParam, float RatingParam, String strRatingParam,  String DateParam, String BackdropParam )
    {
        mID = IDParam;
        mTitle = TitleParam;
        mPosterPath = PosterPathParam ;
        mOverview = OverViewParam;
        mRating = RatingParam;
        mStrRating = strRatingParam;
        setFormattedDate( DateParam );
        mBackdropPath = BackdropParam;
    }

/*
    public static final Parcelable.Creator<Film> CREATOR = new Creator<Film>() {
        public Film createFromParcel(Parcel source) {
            Film myFilm = new Film();
            myFilm.mID = source.readString();
            myFilm.mTitle=  source.readString();
            myFilm.mPosterPath = source.readString();
            myFilm.mOverview = source.readString() ;
            myFilm.mRating = source.readFloat();
            myFilm.mStrRating = source.readString();
            myFilm.mDate = source.readString();
            return myFilm;
        }
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mID);
        parcel.writeString(mTitle);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeFloat(mRating);
        parcel.writeString(mStrRating);
        parcel.writeString(mDate);
    }

*/
    /**
     * This method receives a string representation as "yyyy-MM-dd" for date
     * then it converts it to type Date and sets date with setDate(date) method
     * then it sets formatted date (which is a string).
     *
     * param  string representation of "yyyy-MM-dd" for date
     */

    public String getFormattedDate() {
        if (mFormattedDate == null || mFormattedDate.equals("null")){
            return mDate;
        }
        return mFormattedDate;
    }

    public void setFormattedDate(String DateParam) {
        mFormattedDate = null;
        //SimpleDateFormat DateLocal = new SimpleDateFormat("yyyy-MM-dd");
        //Log.d("logcat_tag", "value of DateParam in SetFormattedDate = " + DateParam);
        SimpleDateFormat ReadFormatLocal = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat WriteFormatLocal = new SimpleDateFormat("dd mmm yyyy");
        try {
            if (DateParam.trim().length() > 0 || !DateParam.trim().isEmpty())
            {
                setDate(DateParam);
                Date UnformatedDateLocal = ReadFormatLocal.parse(DateParam);
                //We use setDate() method so we don't have to repeat code in filmography adapter for every film
                if (UnformatedDateLocal != null) {
                    mFormattedDate = WriteFormatLocal.format(UnformatedDateLocal);
                }
            }
            else
            {
                setDate("");
                mFormattedDate = "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String DateParam) {
        this.mDate = DateParam;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String TitleParam) {
        this.mTitle = TitleParam;
    }

    public String getPosterPath() {
        return mPosterPath;
    }
    public void setPosterPath(String PosterPathParam) {
        this.mPosterPath = PosterPathParam;
    }

    public String getOverview() {
        return mOverview;
    }
    public void setOverview(String overviewParam) {
        this.mOverview = overviewParam;
    }

    public float getRating() {
        return mRating;
    }
    public void setRating(float ratingParam) {
        this.mRating = ratingParam;
    }

    public String getStrRating() {
        return mStrRating;
    }
    public void setStrRating(String ratingStrParam) {
        this.mStrRating = ratingStrParam;
    }

    public String getId() {
        return mID;
    }
    public void setId(String idParam) {
        this.mID = idParam;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }
    public void setBackdropPath(String idParam) {
        this.mBackdropPath = idParam;
    }

    public String toString(){
        return "id:" + this.getId() + " Title:" + this.getTitle() + " PosterPath:" + this.getPosterPath() + " Overview:" + this.getOverview() + " Rating:" + this.getRating() + " Rating:" + this.getStrRating()+ " Release Date:" + this.getFormattedDate();
    }
}
