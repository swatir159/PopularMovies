package com.android.rupeeright.popularmovies.UI;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.rupeeright.popularmovies.R;
import com.android.rupeeright.popularmovies.Model.Film;
import com.android.rupeeright.popularmovies.UISupport.ImageAdapter;
import com.android.rupeeright.popularmovies.UISupport.NoInternetConnectionDialog;
import com.android.rupeeright.popularmovies.Util.PopMoviesConstants;
import com.android.rupeeright.popularmovies.Util.Utilities;
import com.android.rupeeright.popularmovies.WebDataFetcher.AsyncDownloader;
import com.android.rupeeright.popularmovies.WebDataFetcher.MovieDBConnector;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
     // Set this to false to disable logs.
     private String mOptionChosen;
    private String mLastOption;

    private ImageAdapter mAdapter;
    private ArrayList<Film> mFilmsList;


    private AsyncDownloader mDownloader;
    private int  mImageSize;
    private int  mImageSpacing;


    public MainActivityFragment() {

    }

   @Override
    public void onCreate (Bundle savedInstanceState)
    {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.i( Res.getString(R.string.logcat_tag), Res.getString(R.string.DebugMsg2) );
        super.onCreate(savedInstanceState);

        //setHasOptionsMenu(true);
        setRetainInstance(true);

        String InstanceTagLocal =  PopMoviesConstants.MAIN_ACTIVITY_INSTANCE_TAG;
        if(savedInstanceState == null || !savedInstanceState.containsKey(InstanceTagLocal)) {

            //String newSortOrderPref = Utilities.getSortOrderPreference(getActivity());

            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message2) + "instance: " + this.toString());
            createMovieList();
        }
        else {
            mFilmsList = savedInstanceState.getParcelableArrayList(InstanceTagLocal);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String InstanceTagLocal =  PopMoviesConstants.MAIN_ACTIVITY_INSTANCE_TAG;
        outState.putParcelableArrayList(InstanceTagLocal, mFilmsList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setImageParams();
        GridView gridview = (GridView) rootView.findViewById(R.id.pomoviepostergridview);
        mAdapter = new ImageAdapter( (Context)this.getActivity(), mFilmsList);
        gridview.setAdapter(mAdapter);
        mAdapter.setImageSizeDetails(mImageSize, mImageSpacing);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //displayToast(v.getContext(), "" + position);
                displayMovieDetails(parent, position);

            }
        });
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            if (PopMoviesConstants.DEBUG) Log.i(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message13) );
            createMovieList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* ****************************************************************************** */
    public void updateFilmList(List<Film> FilmListParam)
    {
        if ( mFilmsList != null && !mFilmsList.isEmpty())
        {
            mFilmsList.clear();
            mFilmsList = null;
        }

        int dataSize = FilmListParam.size();

        if (dataSize != 0) {
            mFilmsList = new ArrayList<Film>(dataSize);

            for (int i = 0; i < dataSize; i++) {
                mFilmsList.add( FilmListParam.get(i));
            }
        }
    }

    public void updateAdapter(List<Film> FilmsParam) {
        mAdapter.setFilms(FilmsParam);
        mAdapter.notifyDataSetChanged();
    }

    private void displayMovieDetails(View v, int position ){
        String InstanceTagLocal =  PopMoviesConstants.DETAIL_ACTIVITY_INSTANCE_TAG;
        //Intent intent = new Intent(v.getContext(), DisplayMovieDetailsActivity.class);
        Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
        Film mySelectedFilm = mAdapter.getFilms().get(position);
        Bundle selectedFilmData = new Bundle();
        selectedFilmData.putParcelable(InstanceTagLocal, mySelectedFilm);
        intent.putExtra(InstanceTagLocal, mySelectedFilm);
        startActivity(intent);
    }

    /* ******************************************************************** */

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) ((Context) this.getActivity()).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;

        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void initiateDownloadFromWeb() {
        Resources Res = getActivity().getResources();
        if (PopMoviesConstants.DEBUG) Log.d(Res.getString(R.string.logcat_tag), Res.getString(R.string.Message4));

        MovieDBConnector url = MovieDBConnector.getInstance();
        String newSortOrderPref = Utilities.getSortOrderPreference(getActivity());

        url.setmAPIKey(getResources().getString(R.string.themovieDB_api_key));
        String getMoviesHttpMethod = url.getMoviesQuery(newSortOrderPref);

        mDownloader = new AsyncDownloader((Context)this.getActivity(), this );
        mDownloader.execute(getMoviesHttpMethod);
    }

    private void createMovieList()
    {
        if ( !isNetworkAvailable() ){
            Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message3));
             noInternetAlertMessage();
            //displayToast( this.getContext(), "No Network Available");
        }
        else
        {
            if ( mFilmsList != null && !mFilmsList.isEmpty())
            {
                mFilmsList.clear();
                mFilmsList = null;
            }

            initiateDownloadFromWeb();
        }
    }



    /* ************************************************************************ */
    private void displayToast(Context context, String text) {
        //Context context = myView.getContext();
        int duration = Toast.LENGTH_SHORT;
        CharSequence styledText = Html.fromHtml(text);
        Toast toast = Toast.makeText(context, styledText, duration);
        toast.show();
    }

    private void noInternetAlertMessage() {

        FragmentManager dlgFragment = getFragmentManager();
        if (dlgFragment != null){
            NoInternetConnectionDialog dialog = new NoInternetConnectionDialog();
            displayToast((Context) this.getActivity(), this.getActivity().getResources().getString(R.string.no_internet_message));
        }

    }

    private void setImageParams()
    {
        // Fetch screen height and width, to use as our max size when loading images as this
        // activity runs full screen
        mImageSize= 100;
        mImageSpacing = (int) (mImageSize*0.05);
        try{
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            final int height = displayMetrics.heightPixels;
            final int width = displayMetrics.widthPixels;
            mImageSize = (height > width ? height : width) / 2;
            mImageSpacing = (int) (mImageSize*0.05);

        }catch(Exception e)
        {
            if (PopMoviesConstants.DEBUG) Log.d(getResources().getString(R.string.logcat_tag), getResources().getString(R.string.Message1));
            e.printStackTrace();
        }

    }


}
