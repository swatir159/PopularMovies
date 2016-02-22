package com.android.rupeeright.popularmovies.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by swatir on 2/16/2016.
 */





import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//@Generated("org.jsonschema2pojo")
public class ReviewJSON  {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     *
     * @param author
     * The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     *
     * @return
     * The content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     * The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append("  ++++ ReviewJSON details ++++");
        sb.append("Id").append(getId());
        sb.append("Author").append(getAuthor());
        sb.append("Content").append(getContent());
        sb.append("URL=").append(getUrl());
        return sb.toString();
    }

}