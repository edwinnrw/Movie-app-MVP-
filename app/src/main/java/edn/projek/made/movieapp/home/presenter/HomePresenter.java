package edn.projek.made.movieapp.home.presenter;

import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import edn.projek.made.movieapp.home.PlayingFragment;
import edn.projek.made.movieapp.model.Movie;

import edn.projek.made.movieapp.home.HomeLoader;


public class HomePresenter implements HomeContract.Presenter, LoaderManager.LoaderCallbacks<List<Movie>>  {
    Context context;
    HomeContract.View view;
    LoaderManager loaderManager;
    static String PLAYING="playing";
    static String UPCOMING="upcoming";
    public HomePresenter(Context context, HomeContract.View view, LoaderManager loaderManager) {
        this.context = context;
        this.view = view;
        this.loaderManager=loaderManager;
    }



    @Override
    public void getMovieNow() {
        view.showDialog();
        Bundle bundle = new Bundle();
        bundle.putString("jenis","home");
        loaderManager.initLoader(0, bundle, this);

    }

    @Override
    public void getMovieUpcoming() {
        view.showDialog();
        Bundle bundle = new Bundle();
        bundle.putString("jenis","upcoming");
        loaderManager.initLoader(1, bundle, this);
    }


    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        return new HomeLoader(context,bundle.getString("jenis"));
    }


    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        view.dismissDialog();
        view.setMovies(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

}
