package edn.projek.made.movieapp.resultSearch.presenter;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

import edn.projek.made.movieapp.BuildConfig;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.util.Constant;
import edn.projek.made.movieapp.api.Endpoint;
import edn.projek.made.movieapp.api.ServiceGenerator;
import edn.projek.made.movieapp.model.ResponseMovie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultPresenter implements  ResultContract.Presenter {
    Context context;
    ResultContract.View view;


    public ResultPresenter(final Context context, ResultContract.View view){
        this.context=context;
        this.view=view;
    }
    @Override
    public void getMovie(String queryParam) {
        HashMap<String,String>query=new HashMap<>();
        query.put("api_key", BuildConfig.API_KEY);
        query.put("query", queryParam);
        if (context.getResources().getString(R.string.languages).equalsIgnoreCase("English")){
            query.put("language","en-us");
        }else{
            query.put("language","id");

        }
        ServiceGenerator.createService(Endpoint.class).getMovie(query).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                view.setMovies(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                Toast.makeText(context, context.getResources().getString(R.string.error_response), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
