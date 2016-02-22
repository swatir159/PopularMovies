package com.android.rupeeright.popularmovies.POJO;

/**
 * Created by swatir on 2/20/2016.
 */


import java.util.ArrayList;
        import java.util.List;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


//@Generated("org.jsonschema2pojo")
public class MovieJSON {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("genre_ids")
    @Expose
    private List<Object> genreIds = new ArrayList<Object>();
    @SerializedName("id")
    @Expose
    //private Long id;
    private int id;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("backdrop_path")
    @Expose
    private /* Object */ String backdropPath;
    @SerializedName("popularity")
    @Expose
    private /* Long */ float popularity;
    @SerializedName("vote_count")
    @Expose
    private Long voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieJSON() {
    }

    /**
     *
     * @param id
     * @param genreIds
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */
    public MovieJSON(String posterPath, Boolean adult, String overview, String releaseDate, List<Object> genreIds, /*Long id */ int id, String originalTitle, String originalLanguage, String title, /*Object */ String backdropPath, Long popularity, Long voteCount, Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    /**
     *
     * @return
     * The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     *
     * @param posterPath
     * The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     *
     * @return
     * The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     *
     * @param adult
     * The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    /**
     *
     * @return
     * The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     *
     * @param overview
     * The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     *
     * @return
     * The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     *
     * @param releaseDate
     * The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     *
     * @return
     * The genreIds
     */
    public List<Object> getGenreIds() {
        return genreIds;
    }

    /**
     *
     * @param genreIds
     * The genre_ids
     */
    public void setGenreIds(List<Object> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     *
     * @return
     * The id
     */
    public /* Long  */ int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId( /* Long */ int  id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     *
     * @param originalTitle
     * The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     *
     * @return
     * The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     *
     * @param originalLanguage
     * The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The backdropPath
     */
    public  /* Object */ String getBackdropPath() {
        return backdropPath;
    }

    /**
     *
     * @param backdropPath
     * The backdrop_path
     */
    public void setBackdropPath(/* Object */ String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     *
     * @return
     * The popularity
     */
    public /* Long */ float getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(/* Long */ float popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     * The voteCount
     */
    public Long getVoteCount() {
        return voteCount;
    }

    /**
     *
     * @param voteCount
     * The vote_count
     */
    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }

    /**
     *
     * @return
     * The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    /**
     *
     * @return
     * The voteAverage
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     *
     * @param voteAverage
     * The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append("  ++++ MovieJSON details ++++");
        sb.append("ID= ").append(this.getId());
        sb.append("Adult =").append(this.getAdult()  );
        sb.append("Backdrop Path =").append(this.getBackdropPath());
        sb.append("Genre Ids").append( this.getGenreIds().toString() );
        sb.append("Original Language").append(this.getOriginalLanguage()  );
        sb.append("Original Title").append(this.getOriginalTitle()  );
        sb.append("Overview").append(this.getOverview()  );
        sb.append("Popularity").append(this.getPopularity()  );
        sb.append("Poster Path").append( this.getPosterPath() );
        sb.append("Releasedate =").append(this.getReleaseDate()  );
        sb.append("Title =").append(this.getTitle()  );
        sb.append("Video =").append(this.getVideo()  );
        sb.append("VeteAverage =").append(this.getVoteAverage()  );
        sb.append("VoteCount =").append( this.getVoteCount() );
        return sb.toString();
    }

}

