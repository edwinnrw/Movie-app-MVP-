package edn.projek.made.movieapp.search.presenter;

import java.util.List;

import edn.projek.made.movieapp.model.Movie;

public class SearchContract {
    public  interface Presenter{
        void getMovie(String query);
    }
    public interface View{
        void setMovies(List<Movie> movies);
        void showDialog();
        void dismissDialog();
    }
}
