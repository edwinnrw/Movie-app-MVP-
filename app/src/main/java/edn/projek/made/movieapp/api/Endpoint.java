package edn.projek.made.movieapp.api;

import java.util.Map;

import edn.projek.made.movieapp.model.ResponseMovie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Endpoint {
    @GET("./3/search/movie")
    Call<ResponseMovie> getMovie(@QueryMap Map<String, String> options);

    @GET("./3/movie/now_playing")
    Call<ResponseMovie> getMovieNow(@QueryMap Map<String, String> options);

    @GET("./3/movie/upcoming")
    Call<ResponseMovie> getMovieUpcoming(@QueryMap Map<String, String> options);

}
