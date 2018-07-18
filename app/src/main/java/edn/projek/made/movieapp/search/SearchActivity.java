package edn.projek.made.movieapp.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.search.presenter.SearchContract;
import edn.projek.made.movieapp.search.presenter.SearchPresenter;
import edn.projek.made.movieapp.resultSearch.ResultActivity;
import edn.projek.made.movieapp.model.Movie;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, SearchContract.View {
    @BindView(R.id.edt_search)
    EditText editText;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    SearchPresenter searchPresenter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_search));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchPresenter =new SearchPresenter(this,this,getLoaderManager());
        btn_search.setOnClickListener(this);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onClick(View view) {

        searchPresenter.getMovie(editText.getText().toString());

    }

    @Override
    public void setMovies(List<Movie> movies) {

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("query",editText.getText().toString());
        intent.putExtra("result",(Serializable)movies);
        startActivity(intent);
    }

    @Override
    public void showDialog() {
        progressDialog.show();

    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();

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
}
