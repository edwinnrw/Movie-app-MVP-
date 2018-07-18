package edn.projek.made.movieapp.resultSearch.presenter;

import java.util.List;

import edn.projek.made.movieapp.model.Movie;

public class ResultContract {
    public interface View{
        void setMovies(List<Movie> movies);
    }
    public  interface Presenter{
        void getMovie(String query);
    }
}
