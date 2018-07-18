package edn.projek.favorite.favorite;


import java.util.List;

import edn.projek.favorite.model.Movie;

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
