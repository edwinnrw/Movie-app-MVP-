package edn.projek.made.movieapp.resultSearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.resultSearch.adapter.MovieAdapter;
import edn.projek.made.movieapp.resultSearch.presenter.ResultPresenter;
import edn.projek.made.movieapp.model.Movie;

public class ResultActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search)
    TextView searchView;
    ResultPresenter resultPresenter;
    MovieAdapter adapter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getResources().getString(R.string.title_result));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchView.setText(getIntent().getStringExtra("query"));
        List<Movie>listMovies= (List<Movie>) getIntent().getSerializableExtra("result");
        createList(listMovies);

    }
    public void createList(List<Movie>movies){
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter=new MovieAdapter(movies,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}
