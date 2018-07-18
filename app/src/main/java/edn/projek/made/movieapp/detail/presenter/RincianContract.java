package edn.projek.made.movieapp.detail.presenter;

import android.net.Uri;

import edn.projek.made.movieapp.model.Movie;

public class RincianContract {
    public  interface Presenter{
        void insertMovieFavorite(Movie movie);
        void deleteMovieFavorite(Movie movie);
        void checkIsChecked(Movie movie, Uri data);
    }
    public interface View{
        void setChecked();
        void setUnchecked();
    }
}
