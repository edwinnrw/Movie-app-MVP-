package edn.projek.made.movieapp.detail;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.detail.presenter.RincianContract;
import edn.projek.made.movieapp.detail.presenter.RincianPresenter;
import edn.projek.made.movieapp.model.Movie;

import static edn.projek.made.movieapp.db.DbContract.CONTENT_URI;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.BACKDROPPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.OVERVIEW;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.POSTERPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.RELEASE;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.TITLE;

public class RincianActivity extends AppCompatActivity implements RincianContract.View, CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.img_movie)
    ImageView imageView;
    @BindView(R.id.txt_title)
    TextView txt_title;
    @BindView(R.id.txt_overview)
    TextView txt_overview;
    @BindView(R.id.txt_release)
    TextView txt_release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageFav)
    CheckBox imageFav;
    Movie movie;
    RincianPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        presenter=new RincianPresenter(this,this);
        movie=(Movie)getIntent().getSerializableExtra("movie");
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w780"+movie.getBackdropPath())
                .into(imageView);
        txt_title.setText(movie.getTitle());
        if (movie.getOverview().equalsIgnoreCase("")){
            txt_overview.setText(movie.getOverview());

        }else{
            txt_overview.setText(movie.getOverview());
        }
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat2.parse(movie.getReleaseDate());
            simpleDateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
            String newFormatttedDate = simpleDateFormat2.format(parsedDate);
            txt_release.setText(newFormatttedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        presenter.checkIsChecked(movie,getIntent().getData());
        imageFav.setOnCheckedChangeListener(this);
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
    public void setChecked() {
        imageFav.setChecked(true);
    }

    @Override
    public void setUnchecked() {
        imageFav.setChecked(false);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b){
            presenter.insertMovieFavorite(movie);
        }else {
            presenter.deleteMovieFavorite(movie);
        }
    }
}
