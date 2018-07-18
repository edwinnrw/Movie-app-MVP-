package edn.projek.made.movieapp.favorite.presenter;


import java.util.List;

import edn.projek.made.movieapp.model.Movie;

public class FavoriteContract {
    public  interface Presenter{
        void getMovieFavorite();
    }
    public interface View{
        void showDialog();
        void dismissDialog();
        void hideEmpty();
        void showEmpty();
        void setMovies(List<Movie> movieList);

    }
}
