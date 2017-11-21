package com.example.jorge.popularmoviesstage1.adapter;

import android.content.res.Resources;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.jorge.popularmoviesstage1.data.StarContract;
import com.example.jorge.popularmoviesstage1.data.StarDbHelper;
import com.example.jorge.popularmoviesstage1.model.Movies;
import com.example.jorge.popularmoviesstage1.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import butterknife.BindView;
import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.COLUMN_ID;
import static com.example.jorge.popularmoviesstage1.data.StarContract.StarEntry.TABLE_NAME;
import static com.example.jorge.popularmoviesstage1.utilities.Common.getAllStar;
import static com.example.jorge.popularmoviesstage1.utilities.Common.verifyStarExist;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_IMAGE;
import static com.example.jorge.popularmoviesstage1.utilities.Utilite.URL_SIZE_W500;



/**
 * Created by jorge on 27/09/2017.
 */
/**
 * {@link MoviesAdapter} exposes a list of weather forecasts to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movies> data;

    private Context mContext;

    public Cursor mCursor;

    private SQLiteDatabase mDb;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private static MoviesAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movies movies);
    }

    /**
     * Creates a ForecastAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {

        mClickHandler = clickHandler;
    }

    /** create lit de Adapter Travel**/
    public MoviesAdapter(List<Movies> data) {
        this.data = data;
    }

    public MoviesAdapter(List<Movies> data,Cursor cursor, Context context) {
        this.data = data;
        this.mCursor = cursor;
        StarDbHelper dbHelper = new StarDbHelper(context);
        this.mDb = dbHelper.getWritableDatabase();
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.iv_imageMovies) ImageView mMovieImageView;
        @BindView(R.id.iv_star) ImageView mStarImageView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.iv_imageMovie);
            mStarImageView = (ImageView) view.findViewById(R.id.iv_star);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {

            int adapterPosition = getAdapterPosition();
            Movies movie = data.get(adapterPosition);
            mClickHandler.onClick(movie);
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
    public MoviesAdapter.MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v;
        v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.informatiom_movie, viewGroup, false);
        mContext = viewGroup.getContext();
        return new MoviesAdapterViewHolder(v);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param MoviesAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder MoviesAdapterViewHolder, int position) {

        Movies movies = ((Movies) data.get(position));


        MoviesAdapterViewHolder.mStarImageView.setTag(movies.getId());

        mCursor = getAllStar(mDb);


        putStarInit(MoviesAdapterViewHolder.mStarImageView,MoviesAdapterViewHolder.mStarImageView.getTag().toString());

        MoviesAdapterViewHolder.mStarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mCursor = getAllStar(mDb);

                putStar((ImageView) view,view.getTag().toString());

            }
        });

        Picasso.with(mContext)
                .load(URL_IMAGE + URL_SIZE_W500 + movies.getPosterPath())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.error)
                .into(MoviesAdapterViewHolder.mMovieImageView);
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


    public void putStarInit(ImageView imageView, String id) {

        if (verifyStarExist(id,mDb)) {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_star)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.error)
                    .into(imageView);

        } else {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_star_full)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.error)
                    .into(imageView);
        }
    }

    public void putStar(ImageView imageView, String id) {

        if (verifyStarExist(id,mDb)) {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_star_full)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.error)
                    .into(imageView);
            removeStar(imageView, imageView.getTag().toString());

        } else {
            Picasso.with(mContext)
                    .load(R.mipmap.ic_star)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.error)
                    .into(imageView);
            addNewStar(imageView.getTag().toString());
        }
    }


    private long addNewStar(String id) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, id);
        return mDb.insert(StarContract.StarEntry.TABLE_NAME, null, cv);
    }


    // COMPLETED (1) Create a new function called removeGuest that takes long id as input and returns a boolean
    /**
     * Removes the record with the specified id
     *
     * @param id the DB id to be removed
     * @return True: if removed successfully, False: if failed
     */
    private boolean removeStar(ImageView imageView, String id) {
            // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
            return mDb.delete(StarContract.StarEntry.TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;

    }

    public List<Movies> getData() {
        return data;
    }





}
