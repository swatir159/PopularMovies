package com.android.rupeeright.popularmovies.UISupport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.rupeeright.popularmovies.Utils.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Utils.Utilities;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;


/*
 * Created by swatir on 2/16/2016.
 */
public class TrailerPagerAdapter extends PagerAdapter {
    public static final String LOG_TAG = TrailerPagerAdapter.class.getSimpleName();
/*
    int pages = 1;

    int[] res = {
            android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_directions,
            android.R.drawable.ic_menu_gallery

    };

    int[] backgrounfColor = {
            0xFF101010,
            0xFF202020,
            0xFF303030,
            0xFF404040,
            0xFF505050
    };

    public void releaseLoaders() {

    }

    public TrailerPagerAdapter(Context ctx, List<String> TrailerIds ){


        if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + " Constructor ");
    }

    @Override
    public int getCount() {
        return pages;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {


        TextView tv = new TextView(container.getContext());
        tv.setTextColor(Color.WHITE);
        tv.setTextSize(30);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setText(String.valueOf(position));

        ImageView iv = new ImageView(container.getContext());
        iv.setImageResource(res[position]);
        ViewGroup.LayoutParams p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(p);

        LinearLayout l = new LinearLayout(container.getContext());
        l.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams p2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        l.setBackgroundColor(backgrounfColor[position]);
        l.setLayoutParams(p2);
        l.addView(tv);
        l.addView(iv);

        final int page = position;

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(container.getContext(),"page " + page + "clicked" , Toast.LENGTH_SHORT).show();

            }
        });

        container.addView(l);
        return l;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
*/
    private static Context getmContext() {
        return mCtx;
    }

    private static Context mCtx;
    private final List<String> mTrailerIDs;
    private final LayoutInflater mInflater;

    private final List<com.google.android.youtube.player.YouTubeThumbnailLoader> mLoaders;

    public TrailerPagerAdapter(Context ctx, List<String> TrailerIds ){
        mCtx = ctx;
        mTrailerIDs = TrailerIds;
        mInflater = LayoutInflater.from(ctx);

        mLoaders = new ArrayList<com.google.android.youtube.player.YouTubeThumbnailLoader>();
        if (PopMoviesConstants.DEBUG) Log.d("PopMovies1", LOG_TAG + ":" + " Constructor ");
    }



    @Override
    public int getCount() {
        if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", LOG_TAG + ":" + "getCount = " + mTrailerIDs.size() );
        return mTrailerIDs.size();

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View)object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //return false;
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {

        //return super.instantiateItem(container, position);

        View view = mInflater.inflate(R.layout.trailer_item,container,false);
        final String currTrailerId = mTrailerIDs.get(position);

        final YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(mCtx);
        if (result != YouTubeInitializationResult.SUCCESS)
        {
            //If there are any issues we can show an error dialog.
            //result.getErrorDialog((android.app.Activity)mCtx, 0).show();
            if (PopMoviesConstants.DEBUG) Log.d("Po)pMovies1", LOG_TAG + ":" + " Problem in youtube initialization ");
        }
        else
        {
            // Initializing YouTube player view
            if (PopMoviesConstants.DEBUG)
                Log.d("PopMovies1", LOG_TAG + ":" + " youtube initialized - loading thumbnail ");
            YouTubeThumbnailView youTubePlayerView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail);
            youTubePlayerView.setTag(currTrailerId);
            youTubePlayerView.initialize(mCtx.getString(R.string.theyoutube_api_key), new YouTubeThumbnailLoader());

        }
        ImageView playerBtn = (ImageView) view.findViewById(R.id.youtube_play_button);
        playerBtn.setTag(currTrailerId);
        if (PopMoviesConstants.DEBUG)
                        Log.d("PopMovies1", LOG_TAG + ":" + " play button set ");

        playerBtn.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Activity activity = (Activity) TrailerPagerAdapter.getmContext();
                    String youtubeId = (String) v.getTag();

                    if ( !Utilities.isNetworkAvailable(activity)){
                        Log.i("PopMovies1", "No Network available. So not attempting to start video intents.");
                        return;
                    }
                    Log.i("PopMovies1", LOG_TAG + ":" + " Starting youtube intent with Trailer Id =  " + youtubeId);

                    Intent YTintent = null;
                    if (result == YouTubeInitializationResult.SUCCESS) {

                        Log.d("PopMovies1", LOG_TAG + ":" + "Initialization Sucess - Trying to get Intent with YTStandalone player");
                        YTintent = YouTubeStandalonePlayer.createVideoIntent(
                            (Activity) activity, activity.getString(R.string.theyoutube_api_key), youtubeId, 0, true, false);
                        if (YTintent != null) {
                            List<ResolveInfo> r = activity.getPackageManager().queryIntentActivities(YTintent, PackageManager.MATCH_DEFAULT_ONLY);
                            if (r != null && r.size() > 0) {
                                Log.d("PopMovies1", LOG_TAG + ":" + "Initialization Sucess - Intent resolved - before starting youTube intent");
                                activity.startActivity(YTintent);

                            }
                            else {
                                // Youtube not available. Open the youtube video url instead
                                String url = PopMoviesConstants.YOUTUBE_URL + youtubeId;
                                Intent intentURL = new Intent(Intent.ACTION_VIEW);
                                intentURL.setData(Uri.parse(url));
                                Log.d("PopMovies1", LOG_TAG + ":" + "Initialization Sucess - but videoIntent not Resolvable -before starting URL with viewer . intentURL = " + intentURL);
                                activity.startActivity(intentURL);
                            }
                        }
                        else{

                             Log.d("PopMovies1", LOG_TAG + ":" + "YT initialized but cannot CreateVideoIntent on the standalone player intent");
                             YTintent = YouTubeIntents.createPlayVideoIntentWithOptions(activity, currTrailerId, true, false);
                             activity.startActivity(YTintent);
                        }
                    }
                    else {
                        if (YouTubeIntents.canResolvePlayVideoIntent(mCtx)) {
                            // Start an intent to the YouTube app

                            Log.d("PopMovies1", LOG_TAG + ":" + "Sending YouTubeIntent");
                            mCtx.startActivity(YouTubeIntents.createPlayVideoIntent(mCtx, youtubeId));

                        }
                        else{

                            String url = PopMoviesConstants.YOUTUBE_URL + youtubeId;
                            Log.d("PopMovies1", LOG_TAG + ":" + "Cannot initialize YT & also cannot resolve YTIntent..starting URL with viewer: url = " + url);
                            Intent intentURL = new Intent(Intent.ACTION_VIEW);
                            intentURL.setData(Uri.parse(url));
                            activity.startActivity(intentURL);
                        }
                    }
                }
            }
        );
        container.addView(view);
        return view;
    }


    public void releaseLoaders() {
        for (com.google.android.youtube.player.YouTubeThumbnailLoader loader : mLoaders) {
            loader.release();
        }
    }


    private class YouTubeThumbnailLoader implements
            YouTubeThumbnailView.OnInitializedListener,
            com.google.android.youtube.player.YouTubeThumbnailLoader.OnThumbnailLoadedListener{

        // Callbacks from YouTubeThumbnailView.OnInitializedListener
        @Override
        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                            com.google.android.youtube.player.YouTubeThumbnailLoader youTubeThumbnailLoader) {
            String video_id = (String)youTubeThumbnailView.getTag();
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " suceess in thumbnail load- set video Tag :" +video_id );
            youTubeThumbnailLoader.setOnThumbnailLoadedListener(this);
            mLoaders.add(youTubeThumbnailLoader);
           youTubeThumbnailLoader.setVideo(video_id);
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                                            YouTubeInitializationResult error) {
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " error in thumbnail load: "  + error.toString());

            //Toast.makeText(this, getString(R.string.error_init_failure), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " video thumbnail loaded - SUCCESS "  );
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason errorReason) {
            if (PopMoviesConstants.DEBUG) Log.i("PopMovies1", " error in thumbnail load: " + errorReason.toString());
        }
    }



}