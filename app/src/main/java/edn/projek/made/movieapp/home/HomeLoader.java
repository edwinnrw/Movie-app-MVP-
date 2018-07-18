package edn.projek.made.movieapp.home;

import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edn.projek.made.movieapp.BuildConfig;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.api.Endpoint;
import edn.projek.made.movieapp.api.ServiceGenerator;
import edn.projek.made.movieapp.model.Movie;
import edn.projek.made.movieapp.model.ResponseMovie;
import edn.projek.made.movieapp.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeLoader extends AsyncTaskLoader<List<Movie>> {
    private List<Movie>mData;
    private boolean mHasResult = false;
    Context context;
    private String jenis;

    public HomeLoader(Context context,String jenis) {
        super(context);
        onContentChanged();
        this.jenis=jenis;
        this.context=context;
    }
    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }
    @Override
    public void deliverResult(final List<Movie> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }
    @Override
    public List<Movie> loadInBackground() {
        HashMap<String,String> query=new HashMap<>();
        query.put("api_key", BuildConfig.API_KEY);
        if (context.getResources().getString(R.string.languages).equalsIgnoreCase("English")){
            query.put("language","en-us");
        }else{
            query.put("language","id");

        }
        List<Movie> movieList = new ArrayList<>();

        try {
            Endpoint endpoint= ServiceGenerator.createService(Endpoint.class);
            Log.d("jenisLoader",jenis);
            if (jenis.equalsIgnoreCase("home")){
                Call<ResponseMovie>  call = endpoint.getMovieNow(query);
                ResponseMovie responseMovie=call.execute().body();
                movieList=responseMovie.getResults();
            }else {
                Call<ResponseMovie>  call = endpoint.getMovieUpcoming(query);
                ResponseMovie responseMovie=call.execute().body();
                movieList=responseMovie.getResults();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  movieList;
    }

    protected void onReleaseResources(List<Movie> data) {
        //nothing to do.
    }

}
