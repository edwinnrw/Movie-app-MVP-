package edn.projek.favorite.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.favorite.R;
import edn.projek.favorite.model.Movie;

public class DetailActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
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
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat2.parse(movie.getReleaseDate());
            simpleDateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
            String newFormatttedDate = simpleDateFormat2.format(parsedDate);
            txt_release.setText(newFormatttedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }
}
