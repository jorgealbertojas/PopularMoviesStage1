package com.example.jorge.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.popularmoviesstage1.Interface.MoviesInterface;
import com.example.jorge.popularmoviesstage1.Model.Movies;
import com.example.jorge.popularmoviesstage1.adapter.MoviesAdapter;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import java.util.ArrayList;
import java.util.List;

import static com.example.jorge.popularmoviesstage1.utilities.information.API_KEY;
import static com.example.jorge.popularmoviesstage1.utilities.information.MIDLE_POPULAR;

import com.example.jorge.popularmoviesstage1.adapter.MoviesAdapter.MoviesAdapterOnClickHandler;
import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * {@link MoviesAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapterOnClickHandler {

    private final static int NUMBER_OF_COUMNS = 2;

    private MoviesAdapter mMoviesAdapter;
    private MoviesInterface mMoviesInterface;

    private RecyclerView mRecyclerView;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // COMPLETED (25) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBER_OF_COUMNS));;

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         */
        mMoviesAdapter = new MoviesAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMoviesAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        /* Once all of our views are setup, we can load the weather data. */
        if (isOnline()) {
            createStackoverflowAPI();
            mMoviesInterface.getMovies().enqueue(moviesCallback);

        }else{
            Context contexto = getApplicationContext();
            Toast toast = Toast.makeText(contexto, R.string.Error_Access,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Call Get Information Travel .
     */
    private Callback<ListWrapperMovies<Movies>> moviesCallback = new Callback<ListWrapperMovies<Movies>>() {
        @Override
        public void onResponse(Call<ListWrapperMovies<Movies>> call, Response<ListWrapperMovies<Movies>> response) {
            try{
                if (response.isSuccessful()) {
                    List<Movies> data = new ArrayList<>();
                    data.addAll(response.body().results);
                    mRecyclerView.setAdapter(new MoviesAdapter(data));

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }catch(NullPointerException e){
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable(){
                    public void run(){

                        Context contexto = getApplicationContext();
                        Toast toast = Toast.makeText(contexto, R.string.Error_Access_empty,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapperMovies<Movies>> call, Throwable t) {

        }

    };



    /**
     * checks if internet is ok .
     */
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    /**
     * This method will make the View for the JSON data visible and
     * hide the error message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showJsonDataView() {
        // First, make sure the error is invisible
       // mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
    }

    /**
     * This method will make the error message visible and hide the JSON
     * View.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't
     * need to check whether each view is currently visible or invisible.
     */
    private void showErrorMessage() {
        // Then, show the error
        //mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movies movies) {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_TITLE, movies.getTitle());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_IMAGE_BACKDROP, movies.getBackdrop_path());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_IMAGE_POSTER, movies.getPoster_path());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_OVERVIEW, movies.getOverview());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_VOTE_AVERAGE, movies.getVote_average());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_ID, movies.getId());
        startActivity(intentToStartDetailActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
           // makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createStackoverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mMoviesInterface = retrofit.create(MoviesInterface.class);
    }
}
