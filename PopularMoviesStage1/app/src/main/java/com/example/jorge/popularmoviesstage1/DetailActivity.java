package com.example.jorge.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.popularmoviesstage1.utilities.Utilite;
import com.squareup.picasso.Picasso;

import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W154;

/** Activity for show detail movies */
public class DetailActivity extends AppCompatActivity {

    String mId;
    String mTitle;
    String mPoster_path;
    String mOverview;
    String mVote_average;
    String mRelease_date;

    ImageView iv_imageMovies;
    TextView tv_id;
    TextView tv_title;
    TextView tv_Overview;
    TextView tv_Vote_average;
    TextView tv_Release_date;

    /** Create activity Detail */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /** Get Put Extra activity Main Activity*/
        Bundle extras = getIntent().getExtras();
        mPoster_path = extras.getString(Utilite.PUT_EXTRA_IMAGE_POSTER);
        mTitle = extras.getString(Utilite.PUT_EXTRA_TITLE);
        mOverview = extras.getString(Utilite.PUT_EXTRA_OVERVIEW);
        mVote_average =  extras.getString(Utilite.PUT_EXTRA_VOTE_AVERAGE);
        mRelease_date =  extras.getString(Utilite.PUT_EXTRA_RELEASE_DATE);
        mId =  extras.getString(Utilite.PUT_EXTRA_ID);

        /** Get field the XML Detail for show data Movies */
        tv_id  = (TextView) findViewById(R.id.tv_id);
        tv_title  = (TextView) findViewById(R.id.tv_Title);
        tv_Overview  = (TextView) findViewById(R.id.tv_overview);
        tv_Vote_average  = (TextView) findViewById(R.id.tv_vote_average);
        tv_Release_date  = (TextView) findViewById(R.id.tv_release_date);
        iv_imageMovies  = (ImageView) findViewById(R.id.iv_imageMovies);

        /** Put data Movies */
        tv_title.setText(mTitle);
        tv_id.setText(mId);
        tv_title.setText(mTitle);
        tv_Overview.setText(mOverview);
        tv_Vote_average.setText(mVote_average);
        tv_Release_date.setText(mRelease_date);
        Picasso.with(this).load(URL_IMAGE + URL_SIZE_W154 + mPoster_path).fit().into(iv_imageMovies);

    }
}
