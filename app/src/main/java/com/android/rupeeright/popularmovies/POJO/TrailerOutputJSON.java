package com.android.rupeeright.popularmovies.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swatir on 2/15/2016.
 */

//@Generated("org.jsonschema2pojo")
public class TrailerOutputJSON {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<TrailerJSON> results = new ArrayList<TrailerJSON>();

    public TrailerOutputJSON(int idParam, List<TrailerJSON> trailersParam){
        this.id = idParam;
        this.results = trailersParam;
    }
    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The results
     */
    public List<TrailerJSON> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<TrailerJSON> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        //return "Yet to develop"; //ToStringBuilder.reflectionToString(this);
        StringBuilder sb = new StringBuilder(100);

        sb.append("++++++ Trailer API output JSON");
        sb.append(" Id :").append(id);
        for (TrailerJSON trailer : results) {
            sb.append("\n item = [ id=").append(trailer.getId()).append("  site =").append(trailer.getSite()).append(" ]");
        }
        return sb.toString();
    }

}
