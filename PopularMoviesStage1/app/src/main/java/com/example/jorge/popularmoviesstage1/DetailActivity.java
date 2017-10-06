package com.example.jorge.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private String mId;
    private String mTitle;
    private String mPoster_path;
    private String mBackdrop_path;
    private String mOverview;
    private String  mVote_average;
    private String mRelease_date;

    ImageView iv_imageMovies;
    TextView tv_id;
    TextView tv_title;
    TextView tv_Overview;
    TextView tv_Vote_average;
    TextView tv_Release_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        mBackdrop_path = extras.getString(Utilite.PUT_EXTRA_IMAGE_BACKDROP);
        mTitle = extras.getString(Utilite.PUT_EXTRA_TITLE);
        mOverview = extras.getString(Utilite.PUT_EXTRA_OVERVIEW);
        mVote_average =  extras.getString(Utilite.PUT_EXTRA_VOTE_AVERAGE);
        mRelease_date =  extras.getString(Utilite.PUT_EXTRA_RELEASE_DATE);
        mId =  extras.getString(Utilite.PUT_EXTRA_ID);

        tv_id  = (TextView) findViewById(R.id.tv_id);
        tv_title  = (TextView) findViewById(R.id.tv_Title);
        tv_Overview  = (TextView) findViewById(R.id.tv_overview);
        tv_Vote_average  = (TextView) findViewById(R.id.tv_vote_average);
        tv_Release_date  = (TextView) findViewById(R.id.tv_release_date);

        iv_imageMovies  = (ImageView) findViewById(R.id.iv_imageMovies);

        tv_title.setText(mTitle);
        tv_id.setText(mId);
        tv_title.setText(mTitle);
        tv_Overview.setText(mOverview);
        tv_Vote_average.setText(mVote_average);
        tv_Release_date.setText(mRelease_date);

        Picasso.with(this).load(mBackdrop_path).fit().into(iv_imageMovies);

    }
}
