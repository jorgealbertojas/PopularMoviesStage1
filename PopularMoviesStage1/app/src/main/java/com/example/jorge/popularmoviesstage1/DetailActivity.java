package com.example.jorge.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W154;

/** Activity for show detail movies */
public class DetailActivity extends AppCompatActivity {


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

    }
}
