package com.example.jorge.popularmoviesstage1.Model;

/**
 * Created by jorge on 06/10/2017.
 */

/** Model for get API Movies with this field */

public class Movies {
    private int id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String overview;
    private float  vote_average;
    private String release_date;

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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVote_average() {
        return  toString().valueOf(vote_average);
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
