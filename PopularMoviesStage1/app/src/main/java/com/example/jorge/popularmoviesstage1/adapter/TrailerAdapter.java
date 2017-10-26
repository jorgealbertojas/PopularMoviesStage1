package com.example.jorge.popularmoviesstage1.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jorge.popularmoviesstage1.R;

import com.example.jorge.popularmoviesstage1.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W500;

/**
 * Created by jorge on 26/10/2017.
 */

/**
 * {@link TrailerAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
*/

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> data;

    private Context mContext;



    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private static TrailerAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public TrailerAdapter(List<Trailer> data) {
        this.data = data;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_trailer)
        ImageView mTrailerImageView;

        @BindView(R.id.tv_name)
        TextView mNameTextView;


        public TrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerImageView = (ImageView) view.findViewById(R.id.iv_trailer);
            mNameTextView = (TextView) view.findViewById(R.id.tv_name);
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
            Trailer trailer = data.get(adapterPosition);
            mClickHandler.onClick(trailer);
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
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_trailer, viewGroup, false);
        mContext = viewGroup.getContext();
        return new TrailerAdapter.TrailerAdapterViewHolder(v);
    }


    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param TrailerAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerAdapterViewHolder TrailerAdapterViewHolder, int position) {

        Trailer trailer = ((Trailer) data.get(position));

        TrailerAdapterViewHolder.mTrailerImageView.setTag(trailer.getKey());

        TrailerAdapterViewHolder.mNameTextView.setText(trailer.getName());

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
