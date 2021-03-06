package com.example.jorge.popularmoviesstage1;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jorge.popularmoviesstage1.data.StarContract;
import com.example.jorge.popularmoviesstage1.data.StarDbHelper;
import com.example.jorge.popularmoviesstage1.interfaceMovies.MoviesInterface;
import com.example.jorge.popularmoviesstage1.model.Movies;
import com.example.jorge.popularmoviesstage1.adapter.MoviesAdapter;
import com.example.jorge.popularmoviesstage1.utilities.Common;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;

import java.util.ArrayList;
import java.util.List;

import com.example.jorge.popularmoviesstage1.adapter.MoviesAdapter.MoviesAdapterOnClickHandler;
import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.ButterKnife;
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


    private final String KEY_RECYCLER_STATE = "recycler_state";
    private final String KEY_ADAPTER_STATE = "adapter_state";

    MoviesAdapter mMoviesAdapter;
    private MoviesInterface mMoviesInterface;

    private RecyclerView mRecyclerView;

    private ProgressBar mLoadingIndicator;

    private static Bundle mBundleRecyclerViewState;

    private Context mContext;

    private ArrayList<Movies> mListMoviesAdapter;

    private Parcelable mListState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mContext = this;

        // Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState == null) {

            initRecyclerView();
            /*
            * For salve state the activity when rotate
            * will end up displaying our weather data.
            */
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = mRecyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);

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
            if (Common.isOnline(this)) {
                createStackOverflowAPI();
                mMoviesInterface.getMoviesPOPULAR().enqueue(moviesCallback);

            } else {
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, R.string.Error_Access, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            initRecyclerView();
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mListMoviesAdapter = (ArrayList<Movies>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListState);

            ContentResolver sunshineContentResolver = mContext.getContentResolver();
            Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI_GET_ALL, null, null, null, null);

            mMoviesAdapter = new MoviesAdapter(mListMoviesAdapter, cursor, mContext);

            mRecyclerView.setAdapter(mMoviesAdapter);
        }
    }

    /**
     * Call Get InformationNew Movies .
     */
    private Callback<ListWrapperMovies<Movies>> moviesCallback = new Callback<ListWrapperMovies<Movies>>() {
        @Override
        public void onResponse(Call<ListWrapperMovies<Movies>> call, Response<ListWrapperMovies<Movies>> response) {
            try {
                if (response.isSuccessful()) {
                    List<Movies> data = new ArrayList<>();
                    data.addAll(response.body().results);

                    ContentResolver sunshineContentResolver = mContext.getContentResolver();
                    Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI_GET_ALL, null, null, null, null);

                    mMoviesAdapter = new MoviesAdapter(data, cursor, mContext);
                    mRecyclerView.setAdapter(mMoviesAdapter);
                    mLoadingIndicator.setVisibility(View.INVISIBLE);

                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {

                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapperMovies<Movies>> call, Throwable t) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_json_data, Toast.LENGTH_SHORT);
            toast.show();
        }

    };


    @Override
    public void onClick(Movies movies) {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_TITLE, movies.getTitle());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_IMAGE_BACKDROP, movies.getBackdropPath());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_IMAGE_POSTER, movies.getPosterPath());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_OVERVIEW, movies.getOverview());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_VOTE_AVERAGE, movies.getVoteAverage());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_RELEASE_DATE, movies.getReleaseDate());
        intentToStartDetailActivity.putExtra(Utilite.PUT_EXTRA_ID, movies.getId());
        startActivity(intentToStartDetailActivity);
    }


    /**
     * Find Data the API Json with Retrofit
     */
    private void createStackOverflowAPI() {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        mMoviesInterface = retrofit.create(MoviesInterface.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.opcao, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_movie_popular) {
            /** Get data JSON order Popular */
            mLoadingIndicator.setVisibility(View.VISIBLE);
            createStackOverflowAPI();
            mMoviesInterface.getMoviesPOPULAR().enqueue(moviesCallback);
            return true;
        }

        if (id == R.id.action_movie_top) {
            /** Get data JSON order Top Rated */
            mLoadingIndicator.setVisibility(View.VISIBLE);
            createStackOverflowAPI();
            mMoviesInterface.getMoviesTOP_RATED().enqueue(moviesCallback);
            return true;
        }


        if (id == R.id.action_movie_favorite) {
            /** Get data JSON order Top Rated */
            try {
                mListMoviesAdapter = (ArrayList<Movies>) mMoviesAdapter.getData();
                ArrayList<Movies> listMoviesAdapterTemp = new ArrayList<Movies>();
                int i = 0;
                while (i < mListMoviesAdapter.size()) {

                    String id_num = mListMoviesAdapter.get(i).getId();
                    ContentResolver sunshineContentResolver = mContext.getContentResolver();
                    Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI, null, id_num, null, null);
                    if (cursor.getCount() > 0) {
                        listMoviesAdapterTemp.add(mListMoviesAdapter.get(i));
                    }
                    i++;
                }

                ContentResolver sunshineContentResolver = mContext.getContentResolver();
                Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI_GET_ALL, null, null, null, null);
                mMoviesAdapter = new MoviesAdapter(listMoviesAdapterTemp, cursor, mContext);
                mRecyclerView.setAdapter(mMoviesAdapter);
            } catch (NullPointerException e) {
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable() {
                    public void run() {
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_numbers);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        mRecyclerView.setHasFixedSize(true);
        mMoviesAdapter = new MoviesAdapter(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        mListState = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mListMoviesAdapter = (ArrayList<Movies>) mMoviesAdapter.getData();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
        mBundleRecyclerViewState.putSerializable(KEY_ADAPTER_STATE, mListMoviesAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            mListMoviesAdapter = (ArrayList<Movies>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE);

        }
    }

}
