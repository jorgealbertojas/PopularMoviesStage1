package com.example.jorge.popularmoviesstage1.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jorge on 26/10/2017.
 */

/** Model for get API trailer of the movies with this field */

public class Trailer {

    private String id;
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
