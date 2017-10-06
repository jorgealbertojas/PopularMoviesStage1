package com.example.jorge.popularmoviesstage1.Interface;

import com.example.jorge.popularmoviesstage1.Model.Movies;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.example.jorge.popularmoviesstage1.utilities.information.API_KEY;
import static com.example.jorge.popularmoviesstage1.utilities.information.MIDLE_POPULAR;

/**
 * Created by jorge on 06/10/2017.
 */

public interface MoviesInterface {
    @GET(MIDLE_POPULAR + API_KEY)
    Call<ListWrapperMovies<Movies>> getMovies();


}
