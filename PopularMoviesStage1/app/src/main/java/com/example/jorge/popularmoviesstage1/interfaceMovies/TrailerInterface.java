package com.example.jorge.popularmoviesstage1.interfaceMovies;

import com.example.jorge.popularmoviesstage1.model.Trailer;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import retrofit2.Call;
import retrofit2.http.GET;


import static com.example.jorge.popularmoviesstage1.utilities.Utilite.API_KEY;
import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.VIDEO;
import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.MOVIE;


/**
 * Created by jorge on 26/10/2017.
 */

public interface TrailerInterface {

    /** Get order Popular API Retrofit */
    @GET(MOVIE + "346364" +  VIDEO + API_KEY)
    Call<ListWrapperMovies<Trailer>> getTrailer();
}
