package com.android.rupeeright.popularmovies.POJO;

/**
 * Created by swatir on 2/20/2016.
 */


        import java.util.ArrayList;
        import java.util.List;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


//@Generated("org.jsonschema2pojo")
public class MoviesOutputJSON {

    @SerializedName("page")
    @Expose
    private Long page;
    @SerializedName("results")
    @Expose
    private List<MovieJSON> results = new ArrayList<MovieJSON>();
    @SerializedName("total_results")
    @Expose
    private Long totalResults;
    @SerializedName("total_pages")
    @Expose
    private Long totalPages;

    /**
     * No args constructor for use in serialization
     *
     */
    public MoviesOutputJSON() {
    }

    /**
     *
     * @param results
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public MoviesOutputJSON(Long page, List<MovieJSON> results, Long totalResults, Long totalPages) {
        this.page = page;
        this.results = results;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The page
     */
    public Long getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(Long page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The results
     */
    public List<MovieJSON> getResults() {
        return results;
    }

    /**
     *
     * @param results
     * The results
     */
    public void setResults(List<MovieJSON> results) {
        this.results = results;
    }

    /**
     *
     * @return
     * The totalResults
     */
    public Long getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     * The total_results
     */
    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Long getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append("  ++++ MovieOutoutJSON details ++++");
        sb.append("Page = ").append(this.getPage()).append("\n");
        int i = 0;
        for (MovieJSON movie : results) {
           // sb.append(" item = [").append(movie.toString()).append("]");
            sb.append(" result[").append(String.valueOf(i++)).append("] ==> [title =").append(movie.getTitle()).append(", id=").append(movie.getId()).append("]\n");
        }
        sb.append("Total Pages =").append(this.getTotalPages()).append(", ");
        sb.append("Tota results").append( this.getTotalResults() );
        return sb.toString();
    }

}