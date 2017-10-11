package com.example.jorge.popularmoviesstage1.interfaceMovies;

import com.example.jorge.popularmoviesstage1.model.Movies;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.jorge.popularmoviesstage1.utilities.Utilite.API_KEY;
import static com.example.jorge.popularmoviesstage1.utilities.Information.MIDLLE_POPULAR;
import static com.example.jorge.popularmoviesstage1.utilities.Information.MIDLLE_TOP_RATED;

/**
 * Created by jorge on 06/10/2017.
 */

/** * i
 * Interface for get JSON with Retrofit  */

public interface MoviesInterface {

    /** Get order Popular API Retrofit */
    @GET(MIDLLE_POPULAR + API_KEY)
    Call<ListWrapperMovies<Movies>> getMoviesPOPULAR();

    /** Get order Top Rated API Retrofit*/
    @GET(MIDLLE_TOP_RATED + API_KEY)
    Call<ListWrapperMovies<Movies>> getMoviesTOP_RATED();


}
