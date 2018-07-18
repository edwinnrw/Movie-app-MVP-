package edn.projek.made.movieapp.model;

import android.database.Cursor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import edn.projek.made.movieapp.db.DbContract;

import static android.provider.BaseColumns._ID;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.ID;
import static edn.projek.made.movieapp.db.DbContract.getColumnInt;
import static edn.projek.made.movieapp.db.DbContract.getColumnString;

public class Movie implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    public Movie() {

    }

    public Movie(Cursor cursor){
        this.id = getColumnInt(cursor,ID);
        this.title = getColumnString(cursor, DbContract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DbContract.MovieColumns.OVERVIEW);
        this.releaseDate = getColumnString(cursor, DbContract.MovieColumns.RELEASE);
        this.backdropPath = getColumnString(cursor, DbContract.MovieColumns.BACKDROPPATH);
        this.posterPath = getColumnString(cursor, DbContract.MovieColumns.POSTERPATH);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }


    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
