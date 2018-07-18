package edn.projek.made.movieapp.favorite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.favorite.presenter.FavoriteContract;
import edn.projek.made.movieapp.favorite.presenter.FavoritePresenter;
import edn.projek.made.movieapp.home.adapter.MovieAdapter;
import edn.projek.made.movieapp.model.Movie;

public class FavoriteActivity extends AppCompatActivity implements FavoriteContract.View {
    MovieAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.txt_empty)
    TextView txt_empty;
    FavoritePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ButterKnife.bind(this);
        presenter=new FavoritePresenter(this,this);
        presenter.getMovieFavorite();
    }

    @Override
    public void setMovies(List<Movie> movies) {
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new MovieAdapter(movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDialog() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void dismissDialog() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void hideEmpty() {
        txt_empty.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        txt_empty.setVisibility(View.VISIBLE);
    }


}
