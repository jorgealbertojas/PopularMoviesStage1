package com.example.jorge.popularmoviesstage1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jorge.popularmoviesstage1.adapter.ReviewsAdapter;
import com.example.jorge.popularmoviesstage1.adapter.TrailerAdapter;
import com.example.jorge.popularmoviesstage1.data.StarContract;
import com.example.jorge.popularmoviesstage1.data.StarDbHelper;
import com.example.jorge.popularmoviesstage1.interfaceMovies.ReviewsInterface;
import com.example.jorge.popularmoviesstage1.interfaceMovies.TrailerInterface;
import com.example.jorge.popularmoviesstage1.model.Reviews;
import com.example.jorge.popularmoviesstage1.model.Trailer;
import com.example.jorge.popularmoviesstage1.utilities.Common;
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

import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.COLUMN_ID;
import static com.example.jorge.popularmoviesstage1.utilities.InformationNew.MOVIE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W154;

/** Activity for show detail movies */
public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler, ReviewsAdapter.ReviewsAdapterOnClickHandler {


    private final String KEY_ADAPTER_STATE_TRAILER = "adapter_state_trailer";
    private final String KEY_RECYCLER_STATE_TRAILER = "recycler_state_trailer";

    private final String KEY_ADAPTER_STATE_REVIEWS = "adapter_state_reviews";
    private final String KEY_RECYCLER_STATE_REVIEWS = "recycler_state_reviews";

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

    @BindView(R.id.iv_star_detail) ImageView ivImageStarDetail;

    TrailerAdapter mTrailerAdapter;
    ReviewsAdapter mReviewAdapter;
    private static Bundle mBundleRecyclerViewState;

    private TrailerInterface mTrailerInterface;
    private ReviewsInterface mReviewsInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewReviews;

    private ArrayList<Trailer> mListTrailerAdapter;
    private Parcelable mListStateTrailer;

    private ArrayList<Reviews> mListReviewsAdapter;
    private Parcelable mListStateReviews;


    public Cursor mCursor;

    private SQLiteDatabase mDb;


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

        StarDbHelper dbHelper = new StarDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        ivImageStarDetail.setTag(mId);

        putStarInit(ivImageStarDetail,ivImageStarDetail.getTag().toString());

        ivImageStarDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                putStar((ImageView) view,view.getTag().toString());

            }
        });


        if (savedInstanceState == null) {


            initRecyclerViewReviews();
            initRecyclerViewTrailer();
            /*
            * For salve state the activity when rotate
            * will end up displaying our weather data.
            */
            mBundleRecyclerViewState = new Bundle();
            Parcelable listStateTrailer = mRecyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_TRAILER, listStateTrailer);

            Parcelable listStateReviews = mRecyclerViewReviews.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_REVIEWS, listStateReviews);

            mRecyclerView.setAdapter(mTrailerAdapter);
            mRecyclerViewReviews.setAdapter(mReviewAdapter);

            configurationRecyclerView(mId);

        }else{
            initRecyclerViewTrailer();
            mListStateTrailer = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_TRAILER);
            mListTrailerAdapter = (ArrayList<Trailer>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_TRAILER);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(mListStateTrailer);
            mTrailerAdapter = new TrailerAdapter(mListTrailerAdapter);
            mRecyclerView.setAdapter(mTrailerAdapter);


            initRecyclerViewReviews();
            mListStateReviews = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_REVIEWS);
            mListReviewsAdapter = (ArrayList<Reviews>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_REVIEWS);
            mRecyclerViewReviews.getLayoutManager().onRestoreInstanceState(mListStateReviews);
            mReviewAdapter = new ReviewsAdapter(mListReviewsAdapter);
            mRecyclerViewReviews.setAdapter(mReviewAdapter);
        }


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


    private void initRecyclerViewTrailer(){
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_trailer);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mTrailerAdapter = new TrailerAdapter(this);
    }


    private void initRecyclerViewReviews() {
        mRecyclerViewReviews = (RecyclerView) findViewById(R.id.rv_reviews);
        LinearLayoutManager layoutManagerReviews =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewReviews.setLayoutManager(layoutManagerReviews);
        mRecyclerViewReviews.setHasFixedSize(true);
        mReviewAdapter = new ReviewsAdapter(this);
    }

    private void
    configurationRecyclerView(String mId){



        mRecyclerView.setAdapter(mTrailerAdapter);


        mRecyclerViewReviews.setAdapter(mReviewAdapter);


        /* Once all of our views are setup, we can load the weather data. */
        if (Common.isOnline(this)) {
            createStackOverflowAPI(mId);
            mTrailerInterface.getTrailer().enqueue(trailerCallback);

            createStackOverflowAPIReview(mId);
            mReviewsInterface.getReviews().enqueue(reviewCallback);

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
                    mTrailerAdapter = new TrailerAdapter(data);
                    mRecyclerView.setAdapter(mTrailerAdapter);


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
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_json_data,Toast.LENGTH_SHORT);
            toast.show();
        }

    };

    private Callback<ListWrapperMovies<Reviews>> reviewCallback = new Callback<ListWrapperMovies<Reviews>>() {
        @Override
        public void onResponse(Call<ListWrapperMovies<Reviews>> call, Response<ListWrapperMovies<Reviews>> response) {
            try{
                if (response.isSuccessful()) {
                    List<Reviews> data = new ArrayList<>();
                    data.addAll(response.body().results);
                    mReviewAdapter = new ReviewsAdapter(data);
                    mRecyclerViewReviews.setAdapter(new ReviewsAdapter(data));


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
        public void onFailure(Call<ListWrapperMovies<Reviews>> call, Throwable t) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.Error_json_data,Toast.LENGTH_SHORT);
            toast.show();
        }

    };


    /** Find Data the API Json with Retrofit */
    private void createStackOverflowAPI(String ID_TRAILER) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.GITHUB_BASE_URL + MOVIE + ID_TRAILER + "/" )
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        mTrailerInterface = retrofit.create(TrailerInterface.class);
    }

    private void createStackOverflowAPIReview(String ID_TRAILER) {
        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilite.GITHUB_BASE_URL + MOVIE + ID_TRAILER + "/" )
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



        mReviewsInterface = retrofit.create(ReviewsInterface.class);
    }


    @Override
    public void onClick(Trailer trailer) {
        playVideo(trailer.getKey());
    }

    @Override
    public void onClick(Reviews reviews) {

    }



    @Override
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();

        mListStateTrailer = mRecyclerView.getLayoutManager().onSaveInstanceState();
        mListTrailerAdapter = (ArrayList<Trailer>) mTrailerAdapter.getData();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_TRAILER, mListStateTrailer);
        mBundleRecyclerViewState.putSerializable(KEY_ADAPTER_STATE_TRAILER, mListTrailerAdapter);

        mListStateReviews = mRecyclerViewReviews.getLayoutManager().onSaveInstanceState();
        mListReviewsAdapter = (ArrayList<Reviews>) mReviewAdapter.getData();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE_REVIEWS, mListStateReviews);
        mBundleRecyclerViewState.putSerializable(KEY_ADAPTER_STATE_REVIEWS, mListReviewsAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            mListStateTrailer = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_TRAILER);
            mListTrailerAdapter = (ArrayList<Trailer>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_TRAILER);

            mListStateReviews = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE_REVIEWS);
            mListReviewsAdapter = (ArrayList<Reviews>) mBundleRecyclerViewState.getSerializable(KEY_ADAPTER_STATE_REVIEWS);

        }
    }


    public void putStarInit(ImageView imageView, String id) {

        ContentResolver sunshineContentResolver = getContentResolver();
        Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI,null,id,null,null);

        try{


            if (cursor.getCount() > 0 ) {
                Picasso.with(this)
                        .load(R.mipmap.ic_star_full)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error)
                        .into(imageView);

            } else {
                Picasso.with(this)
                        .load(R.mipmap.ic_star)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error)
                        .into(imageView);
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

    public void putStar(ImageView imageView, String id) {


        ContentResolver sunshineContentResolver = getContentResolver();
        Cursor cursor = sunshineContentResolver.query(StarContract.StarEntry.CONTENT_URI,null,id,null,null);

        try{
            if (cursor.getCount() > 0 ) {
                Picasso.with(this)
                        .load(R.mipmap.ic_star)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error)
                        .into(imageView);
                removeStar(imageView.getTag().toString());

            } else {
                Picasso.with(this)
                        .load(R.mipmap.ic_star_full)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.drawable.error)
                        .into(imageView);
                addNewStar(imageView.getTag().toString());
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

    private int addNewStar(String id) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        ContentValues[] starContentValues = new ContentValues[1];
        starContentValues[0] = cv;

        ContentResolver sunshineContentResolver = getContentResolver();
        int rowInsert = sunshineContentResolver.bulkInsert(StarContract.StarEntry.CONTENT_URI_INSERT,starContentValues);
        return rowInsert;

    }


    /** Remove favorite of the list**/
    private int removeStar(String id) {
        String[] starContentValues = new String[1];
        starContentValues[0] = id;
        ContentResolver sunshineContentResolver = getContentResolver();
        int rowDelete = sunshineContentResolver.delete(StarContract.StarEntry.CONTENT_URI_DELETE,null,starContentValues);
        return rowDelete;

    }

}
