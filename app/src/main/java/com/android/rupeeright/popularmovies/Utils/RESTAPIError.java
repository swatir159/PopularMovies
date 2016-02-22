package com.android.rupeeright.popularmovies.Utils;

/**
 * Created by swatir on 2/14/2016.
 */
public class RESTAPIError {
    private int statusCode;
    private String message;
    public RESTAPIError(){

    }
    public int status(){
        return statusCode;
    }
    public String message(){
        return message;
    }
}
