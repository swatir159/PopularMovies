package com.android.rupeeright.popularmovies.UISupport;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.UI.MainActivityFragment;
import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.bumptech.glide.Glide;

/**
 * Created by swatir on 2/10/2016.
 */
public class MovieListAdapter extends CursorAdapter {

    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private static String mBaseURLToFetchImage = PopMoviesConstants.BACKDROP_BASE_PATH;
    private static String mImageSizeToDownload = PopMoviesConstants.IMAGE_SIZE_TO_DOWNLOAD_IN_GRID;
    private int mImageSizeSpacing;
    private int mImageSize;

    public MovieListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor movieCursor, ViewGroup parent)
    {
        if (PopMoviesConstants.DEBUG) Log.i("PoMovies1", LOG_TAG + ":"+  "Inside newView");
        View convertView;
        ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.gridview_item, parent, false);

        viewHolder = new ViewHolder();
        viewHolder.movieThumbnail = (ImageView) convertView.findViewById(R.id.ivIcon);

        viewHolder.movieThumbnail.setLayoutParams(new RelativeLayout.LayoutParams(mImageSize, mImageSize));
        viewHolder.movieThumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewHolder.movieThumbnail.setPadding(mImageSizeSpacing, mImageSizeSpacing, mImageSizeSpacing, mImageSizeSpacing);
        convertView.setTag(viewHolder);

        return convertView;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
       // if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":"+  "Inside bindView");
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String posterPathinDB = cursor.getString(MainActivityFragment.COL_POSTER_PATH);
        String posterPath = mBaseURLToFetchImage + mImageSizeToDownload + posterPathinDB;
        //if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ": posterpath="+  posterPath);
        Glide.with(context)
                .load(posterPath)
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.big_problem)
                .into(viewHolder.movieThumbnail);
        //Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        //return convertView;
    }


    /*
            *
            * The view holder design pattern prevents using findViewById()     * repeatedly in the getView() method of the adapter.
            *
            * @see http://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
            */
    private static class ViewHolder {
        ImageView movieThumbnail;

        /*public ViewHolder(View view) {
            movieThumbnail = (ImageView) view.findViewById(R.id.ivIcon);
            //favoriteCheckbox = (CheckBox) view.findViewById(R.id.favorite_checkbox);

        } */

    }

    public int getmImageSize() {
        return mImageSize;
    }

    public void setImageSize(int ImageSize) {
        this.mImageSize = ImageSize;
    }

    public void setImageSizeSpacing(int ImageSize) {
        this.mImageSizeSpacing = ImageSize;}

    public void setImageSizeDetails(int ImageSize, int imageSizeSpacing) {
        this.mImageSize = ImageSize;
        this.mImageSizeSpacing = imageSizeSpacing;
    }


}
