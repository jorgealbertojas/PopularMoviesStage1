package com.example.jorge.popularmoviesstage1.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jorge.popularmoviesstage1.R;
import com.example.jorge.popularmoviesstage1.model.Reviews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jorge on 07/11/2017.
 */

public class ReviewsAdapter  extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {

    private List<Reviews> data;

    private Context mContext;



    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private static ReviewsAdapterOnClickHandler mClickHandler;


    /**
     * The interface that receives onClick messages.
     */
    public interface ReviewsAdapterOnClickHandler {
        void onClick(Reviews reviews);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public ReviewsAdapter(ReviewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public ReviewsAdapter(List<Reviews> data) {
        this.data = data;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_id)
        TextView mIdTextView;

        @BindView(R.id.tv_author)
        TextView mAuthorTextView;

        @BindView(R.id.tv_content)
        TextView mContentTextView;

        @BindView(R.id.tv_url)
        TextView mUrlTextView;




        public ReviewsAdapterViewHolder(View view) {
            super(view);
            mIdTextView = (TextView) view.findViewById(R.id.tv_id);
            mAuthorTextView = (TextView) view.findViewById(R.id.tv_author);
            mContentTextView = (TextView) view.findViewById(R.id.tv_content);
            mUrlTextView = (TextView) view.findViewById(R.id.tv_url);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            ButterKnife.bind((Activity) v.getContext());
            int adapterPosition = getAdapterPosition();
            Reviews reviews = data.get(adapterPosition);
            mClickHandler.onClick(reviews);
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new ForecastAdapterViewHolder that holds the View for each list item
     */
    @Override
    public ReviewsAdapter.ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_review, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ReviewsAdapter.ReviewsAdapterViewHolder(v);
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param ReviewsAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsAdapterViewHolder ReviewsAdapterViewHolder, int position) {

        Reviews reviews = ((Reviews) data.get(position));

        ReviewsAdapterViewHolder.mIdTextView.setText(reviews.getId());
        ReviewsAdapterViewHolder.mAuthorTextView.setText(reviews.getAuthor());
        ReviewsAdapterViewHolder.mContentTextView.setText(reviews.getContent());
        ReviewsAdapterViewHolder.mUrlTextView.setText(reviews.getUrl());

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (null == data) return 0;
        return data.size();
    }


}