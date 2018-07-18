package edn.projek.favorite.model;

import android.database.Cursor;

import java.io.Serializable;

import edn.projek.favorite.db.DbContract;

import static edn.projek.favorite.db.DbContract.MovieColumns.ID;
import static edn.projek.favorite.db.DbContract.getColumnInt;
import static edn.projek.favorite.db.DbContract.getColumnString;


public class Movie implements Serializable {

    private int id;

    private String title;

    private String posterPath;

    private String originalTitle;


    private String backdropPath;

    private String overview;

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

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
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
