package com.android.rupeeright.popularmovies.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by swatir on 2/16/2016.
 */

        import java.util.ArrayList;
        import java.util.List;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


//@Generated("org.jsonschema2pojo")
public class ReviewOutputJSON {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<ReviewJSON> results = new ArrayList<ReviewJSON>();
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;


    public ReviewOutputJSON(int idParam, List<ReviewJSON> reviewsParam){
        this.id = idParam;
        this.results = reviewsParam;
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
     * The page
     */
    public Integer getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<ReviewJSON> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<ReviewJSON> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(100);

        sb.append("++++++ Review API output JSON");
        sb.append(" Id :").append(id);
        for (ReviewJSON review : results) {
            sb.append(" item = [").append(review.toString()).append("]");
        }
        return sb.toString();
    }

}
