package edn.projek.made.movieapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edn.projek.made.movieapp.model.Movie;

import static android.provider.BaseColumns._ID;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.BACKDROPPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.ID;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.OVERVIEW;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.POSTERPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.RELEASE;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.TITLE;
import static edn.projek.made.movieapp.db.DbContract.TABLE_FAV;

public class FavoriteHelper {
    private static String DATABASE_TABLE = TABLE_FAV;
    private Context context;
    private DbHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context context){
        this.context = context;
    }

    public FavoriteHelper open() throws SQLException {
        dataBaseHelper = new DbHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {

                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndexOrThrow(BACKDROPPATH)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTERPATH)));
                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Movie movie){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(ID, movie.getId());
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(OVERVIEW, movie.getOverview());
        initialValues.put(RELEASE, movie.getReleaseDate());
        initialValues.put(BACKDROPPATH,movie.getBackdropPath());
        initialValues.put(POSTERPATH, movie.getPosterPath());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Movie movie){
        ContentValues args = new ContentValues();
        args.put(ID,movie.getId());
        args.put(TITLE, movie.getTitle());
        args.put(OVERVIEW, movie.getOverview());
        args.put(RELEASE, movie.getReleaseDate());
        args.put(BACKDROPPATH, movie.getBackdropPath());
        args.put(POSTERPATH, movie.getPosterPath());
        return database.update(DATABASE_TABLE, args, ID + "= '" + movie.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_FAV, ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }
    public Cursor queryProvider(String[] strings, String s, String[] strings1, String s1){
        return database.query(DATABASE_TABLE
                ,null
                ,s
                ,strings1
                ,null
                ,null
                ,null);
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,ID +" = ?",new String[]{id} );
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }

    public int deleteProviderById(String s, String[] strings) {
        return database.delete(DATABASE_TABLE,ID + " = ?", strings);

    }
}
