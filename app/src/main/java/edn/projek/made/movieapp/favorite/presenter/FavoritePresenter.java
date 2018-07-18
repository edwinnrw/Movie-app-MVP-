package edn.projek.made.movieapp.favorite.presenter;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import edn.projek.made.movieapp.model.Movie;

import static edn.projek.made.movieapp.db.DbContract.CONTENT_URI;

public class FavoritePresenter implements FavoriteContract.Presenter {
    Context context;
    FavoriteContract.View view;

    public FavoritePresenter(Context context, FavoriteContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getMovieFavorite() {
        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null,
                null, null, null);
        List<Movie> movieList=new ArrayList<>();
        view.showDialog();
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    Movie movie= new Movie(cursor);
                    movieList.add(movie);
                }while (cursor.moveToNext());
                if (movieList.size()>0){
                    view.dismissDialog();
                    view.setMovies(movieList);

                }else{
                    view.dismissDialog();
                    view.showEmpty();
                }
            }else {
                view.dismissDialog();
                view.showEmpty();
            }
        }
    }
}
