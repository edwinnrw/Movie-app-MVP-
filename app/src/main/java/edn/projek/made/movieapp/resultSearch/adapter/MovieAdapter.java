package edn.projek.made.movieapp.resultSearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.detail.RincianActivity;
import edn.projek.made.movieapp.model.Movie;
import edn.projek.made.movieapp.resultSearch.ResultActivity;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mDataSet;
    Context context;

    public MovieAdapter(List<Movie> mDataSet, Context context) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_movie, parent, false);

        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder holderR = (ViewHolder) holder;
        final Movie iteminfo = (Movie) mDataSet.get(position);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500"+iteminfo.getPosterPath())
                .into(holder.imageView);
        holder.txt_title.setText(iteminfo.getTitle());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-mm-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat2.parse(iteminfo.getReleaseDate());
            simpleDateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
            String newFormatttedDate = simpleDateFormat2.format(parsedDate);
            holder.txt_release.setText(newFormatttedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RincianActivity.class);
                intent.putExtra("movie",(Serializable)iteminfo);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_movie)
        ImageView imageView;
        @BindView(R.id.txt_title)
        TextView txt_title;
        @BindView(R.id.txt_release)
        TextView txt_release;
        View view;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            this.view=view;



        }
    }
}
