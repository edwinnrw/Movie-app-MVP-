package edn.projek.made.movieapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edn.projek.made.movieapp.model.Movie;

import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.BACKDROPPATHR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.IDR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.OVERVIEWR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.POSTERPATHR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.RELEASER;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.TITLER;
import static edn.projek.made.movieapp.db.DbContract.TABLER;


public class ReleaseHelper {
    private static String DATABASE_TABLE = TABLER;
    private Context context;
    private DbHelper dataBaseHelper;

    private SQLiteDatabase database;

    public ReleaseHelper(Context context){
        this.context = context;
    }

    public ReleaseHelper open() throws SQLException {
        dataBaseHelper = new DbHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DATABASE_TABLE,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {

                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(IDR)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLER)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEWR)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASER)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROPPATHR)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATHR)));
                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(IDR, movie.getId());
        initialValues.put(TITLER, movie.getTitle());
        initialValues.put(OVERVIEWR, movie.getOverview());
        initialValues.put(RELEASER, movie.getReleaseDate());
        initialValues.put(BACKDROPPATHR,movie.getBackdropPath());
        initialValues.put(POSTERPATHR, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Movie movie){
        ContentValues args = new ContentValues();
        args.put(IDR,movie.getId());
        args.put(TITLER, movie.getTitle());
        args.put(OVERVIEWR, movie.getOverview());
        args.put(RELEASER, movie.getReleaseDate());
        args.put(BACKDROPPATHR, movie.getBackdropPath());
        args.put(POSTERPATHR, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, IDR + "= '" + movie.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLER, IDR + " = '"+id+"'", null);
    }
    public int deleteAll(){
        return database.delete(TABLER, null, null);
    }

}
