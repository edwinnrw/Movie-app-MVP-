package edn.projek.made.movieapp.detail.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import edn.projek.made.movieapp.db.FavoriteHelper;
import edn.projek.made.movieapp.model.Movie;
import edn.projek.made.movieapp.provider.MovieProvider;

import static edn.projek.made.movieapp.db.DbContract.CONTENT_URI;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.BACKDROPPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.ID;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.OVERVIEW;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.POSTERPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.RELEASE;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.TITLE;

public class RincianPresenter implements RincianContract.Presenter{
    Context context;
    RincianContract.View view;
    FavoriteHelper favoriteHelper;
    public RincianPresenter(Context context, RincianContract.View view) {
        this.context = context;
        this.view = view;
        this.favoriteHelper=new FavoriteHelper(context);
        this.favoriteHelper.open();
    }

    @Override
    public void insertMovieFavorite(Movie movie) {
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(ID,movie.getId());
        mContentValues.put(TITLE, movie.getTitle());
        mContentValues.put(OVERVIEW, movie.getOverview());
        mContentValues.put(RELEASE, movie.getReleaseDate());
        mContentValues.put(BACKDROPPATH,movie.getBackdropPath());
        mContentValues.put(POSTERPATH,movie.getPosterPath());
        context.getContentResolver().insert(CONTENT_URI, mContentValues);
    }

    @Override
    public void deleteMovieFavorite(Movie movie) {
        ContentValues mContentValues = new ContentValues();
        mContentValues.put(ID, movie.getId());
        mContentValues.put(TITLE, movie.getTitle());
        mContentValues.put(OVERVIEW, movie.getOverview());
        mContentValues.put(RELEASE, movie.getReleaseDate());
        mContentValues.put(BACKDROPPATH,movie.getBackdropPath());
        mContentValues.put(POSTERPATH,movie.getPosterPath());
        context.getContentResolver().delete(CONTENT_URI,"id ?", new String[]{String.valueOf(movie.getId())});
    }

    @Override
    public void checkIsChecked(Movie movie, Uri data) {
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, TITLE +
                " LIKE ?", new String[]{movie.getTitle()}, null);
        if (cursor != null){
            if(cursor.moveToFirst()){
                view.setChecked();
            }else{
                view.setUnchecked();
            }
        }else {
            view.setUnchecked();
        }
    }

}
