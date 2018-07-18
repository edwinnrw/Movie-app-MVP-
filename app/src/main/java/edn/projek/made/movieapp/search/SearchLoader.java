package edn.projek.made.movieapp.search;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

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

public class SearchLoader extends AsyncTaskLoader<List<Movie>> {
    private List<Movie>mData;
    private boolean mHasResult = false;
    Context context;
    private String queryParam;

    public SearchLoader(@NonNull Context context, String query) {
        super(context);
        onContentChanged();
        this.queryParam =query;
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

        HashMap<String,String>query=new HashMap<>();
        query.put("api_key", BuildConfig.API_KEY);
        query.put("query", queryParam);
        if (context.getResources().getString(R.string.languages).equalsIgnoreCase("English")){
            query.put("language","en-us");
        }else{
            query.put("language","id");

        }
        List<Movie> movieList = new ArrayList<>();

        try {
            Endpoint endpoint= ServiceGenerator.createService(Endpoint.class);
            Call<ResponseMovie>  call = endpoint.getMovie(query);
            ResponseMovie responseMovie=call.execute().body();
            movieList=responseMovie.getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  movieList;
    }

    protected void onReleaseResources(List<Movie> data) {
        //nothing to do.
    }

}
