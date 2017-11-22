package com.example.jorge.popularmoviesstage1.model;

/**
 * Created by jorge on 06/10/2017.
 */

// mew
import com.google.gson.annotations.SerializedName;

/** Model for get API Movies with this field */

public class
Movies {
    private int id;
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    private String overview;
    @SerializedName("vote_average")
    private float  voteAverage;
    @SerializedName("release_date")
    private String releaseDate;


    public String getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String poster_path) {
        this.posterPath = poster_path;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdrop_path) {
        this.backdropPath = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return  toString().valueOf(voteAverage);
    }

    public void setVoteAverage(float vote_average) {
        this.voteAverage = vote_average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String release_date) {
        this.releaseDate = release_date;
    }
}
