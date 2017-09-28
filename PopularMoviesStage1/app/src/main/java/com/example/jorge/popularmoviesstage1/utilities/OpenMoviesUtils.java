package com.example.jorge.popularmoviesstage1.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by jorge on 28/09/2017.
 */

public final class OpenMoviesUtils {

    public static String[] getSimpleMoviesStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        /* Movie information. Each day's forecast info is an element of the "list" array */
        final String OWM_RESULTS = "results";

        final String OWM_POSTER_PATH = "poster_path";

        /* String array to hold each ulr image movie String */
        String[] parsedMovieData = null;

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        JSONArray movieArray = forecastJson.getJSONArray(OWM_RESULTS);

        parsedMovieData = new String[movieArray.length()];



        for (int i = 0; i < movieArray.length(); i++) {

            JSONObject urlImageMovie = movieArray.getJSONObject(i);

            parsedMovieData[i] = urlImageMovie.getString(OWM_POSTER_PATH);

        }

        return parsedMovieData;
    }

}
