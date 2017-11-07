package com.example.jorge.popularmoviesstage1.interfaceMovies;

import com.example.jorge.popularmoviesstage1.model.Reviews;
import com.example.jorge.popularmoviesstage1.model.Trailer;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.REVIEWS;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.API_KEY;

/**
 * Created by jorge on 07/11/2017.
 */

public interface ReviewsInterface {
    /** Get order Popular API Retrofit */
    @GET(REVIEWS + API_KEY)
    Call<ListWrapperMovies<Reviews>> getReviews();
}
