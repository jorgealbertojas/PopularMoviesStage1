package com.example.jorge.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jorge.popularmoviesstage1.adapter.MoviesAdapter;
import com.example.jorge.popularmoviesstage1.adapter.TrailerAdapter;
import com.example.jorge.popularmoviesstage1.interfaceMovies.MoviesInterface;
import com.example.jorge.popularmoviesstage1.interfaceMovies.TrailerInterface;
import com.example.jorge.popularmoviesstage1.model.Movies;
import com.example.jorge.popularmoviesstage1.model.Trailer;
import com.example.jorge.popularmoviesstage1.utilities.ListWrapperMovies;
import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.MOVIE;
import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.VIDEO;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W154;

/** Activity for show detail movies */
public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {


    String mId;
    String mTitle;
    String mPosterPath;
    String mOverview;
    String mVoteAverage;
    String mReleaseDate;

    @BindView(R.id.iv_imageMovies) ImageView ivImageMovies;
    @BindView(R.id.tv_id) TextView  tvId;
    @BindView(R.id.tv_Title) TextView  tvTitle;
    @BindView(R.id.tv_overview) TextView  tvOverview;
    @BindView(R.id.tv_vote_average) TextView  tvVoteAverage;
    @BindView(R.id.tv_release_date) TextView  tvReleaseDate;

    TrailerAdapter mTrailerAdapter;
    private TrailerInterface mTrailerInterface;
    private RecyclerView mRecyclerView;

    /** Create activity Detail */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /** Get Put Extra activity Main Activity*/
        Bundle extras = getIntent().getExtras();
        mPosterPath = extras.getString(Utilite.PUT_EXTRA_IMAGE_POSTER);
        mTitle = extras.getString(Utilite.PUT_EXTRA_TITLE);
        mOverview = extras.getString(Utilite.PUT_EXTRA_OVERVIEW);
        mVoteAverage =  extras.getString(Utilite.PUT_EXTRA_VOTE_AVERAGE);
        mReleaseDate =  extras.getString(Utilite.PUT_EXTRA_RELEASE_DATE);
        mId =  extras.getString(Utilite.PUT_EXTRA_ID);

        /** Get field the XML Detail for show data Movies */
        ButterKnife.bind(this);

        /** Put data Movies */
        tvTitle.setText(mTitle);
        tvId.setText(mId);
        tvTitle.setText(mTitle);
        tvOverview.setText(mOverview);
        tvVoteAverage.setText(mVoteAverage);
        tvReleaseDate.setText(mReleaseDate);
        Picasso.with(this).load(URL_IMAGE + URL_SIZE_W154 + mPosterPath).fit().into(ivImageMovies);

        configurationRecyclerView(mId);

    }

    public void playVideo(String key){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));

        // Check if the youtube app exists on the device
        if (intent.resolveActivity(getPackageManager()) == null) {
            // If the youtube app doesn't exist, then use the browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
        }

        startActivity(intent);
    }


    @OnClick(R.id.iv_imageMovies)
    public void submit(View view) {

    }



    private void configurationRecyclerView(String mId){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_trailer);

        /*
         * LinearLayoutManager can support HORIZONTAL or VERTICAL orientations. The reverse layout
         * parameter is useful mostly for HORIZONTAL layouts that should reverse for right to left
         * languages.
         */
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);
              /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        /*
         * The ForecastAdapter is responsible for linking our weather data with the Views that
         * will end up displaying our weather data.
         */
        mTrailerAdapter = new TrailerAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mTrailerAdapter);


        /* Once all of our views are setup, we can load the weather data. */
        if (isOnline()) {
            createStackoverflowAPI(mId);
            mTrailerInterface.getTrailer().enqueue(trailerCallback);

        }else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_Access,Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Call Get InformationNew Movies .
     */
    private Callback<ListWrapperMovies<Trailer>> trailerCallback = new Callback<ListWrapperMovies<Trailer>>() {
        @Override
        public void onResponse(Call<ListWrapperMovies<Trailer>> call, Response<ListWrapperMovies<Trailer>> response) {
            try{
                if (response.isSuccessful()) {
                    List<Trailer> data = new ArrayList<>();
                    data.addAll(response.body().results);
                    mRecyclerView.setAdapter(new TrailerAdapter(data));


                } else {
                    Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
                }
            }catch(NullPointerException e){
                System.out.println("onActivityResult consume crashed");
                runOnUiThread(new Runnable(){
                    public void run(){
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, R.string.Error_Access_empty,Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        }

        @Override
        public void onFailure(Call<ListWrapperMovies<Trailer>> call, Throwable t) {
            Context contexto = getApplicationContext();
            Toast toast = Toast.makeText(contexto, R.string.Error_json_data,Toast.LENGTH_SHORT);
            toast.show();
        }

    };

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /** Find Data the API Json with Retrofit */
    private void createStackoverflowAPI(String ID_TRAILER) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl(Utilite.GITHUB_BASE_URL + MOVIE + ID_TRAILER + "/")
                .baseUrl(Utilite.GITHUB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        mTrailerInterface = retrofit.create(TrailerInterface.class);
    }


    @Override
    public void onClick(Trailer trailer) {
        playVideo(trailer.getKey());
    }
}
