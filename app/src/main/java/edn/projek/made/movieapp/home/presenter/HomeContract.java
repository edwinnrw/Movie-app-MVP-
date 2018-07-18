package edn.projek.made.movieapp.home.presenter;

import java.util.List;

import edn.projek.made.movieapp.model.Movie;

public class HomeContract {
    public  interface Presenter{
        void getMovieNow();
        void getMovieUpcoming();
    }
    public interface View{
        void setMovies(List<Movie> movies);
        void showDialog();
        void dismissDialog();
        void hideEmpty();
        void showEmpty();
    }
}
