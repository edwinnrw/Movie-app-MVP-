package edn.projek.made.movieapp.search.presenter;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.home.HomeLoader;
import edn.projek.made.movieapp.model.Movie;
import edn.projek.made.movieapp.search.SearchLoader;
import edn.projek.made.movieapp.util.Constant;
import edn.projek.made.movieapp.api.Endpoint;
import edn.projek.made.movieapp.api.ServiceGenerator;
import edn.projek.made.movieapp.model.ResponseMovie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter implements  SearchContract.Presenter, LoaderManager.LoaderCallbacks<List<Movie>> {
    Context context;
    SearchContract.View view;
    LoaderManager loaderManager;

    public SearchPresenter(final Context context, SearchContract.View view, LoaderManager loaderManager){
        this.context=context;
        this.view=view;
        this.loaderManager=loaderManager;
    }
    @Override
    public void getMovie(String queryParam) {
        view.showDialog();
        Bundle bundle = new Bundle();
        bundle.putString("query",queryParam);
        loaderManager.initLoader(0,bundle,this);


    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new SearchLoader(context,bundle.getString("query"));

    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        view.dismissDialog();
        view.setMovies(movies);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
