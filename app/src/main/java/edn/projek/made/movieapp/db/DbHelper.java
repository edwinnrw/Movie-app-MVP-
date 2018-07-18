package edn.projek.made.movieapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static edn.projek.made.movieapp.db.DbContract.MovieColumns.BACKDROPPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.ID;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.OVERVIEW;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.POSTERPATH;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.RELEASE;
import static edn.projek.made.movieapp.db.DbContract.MovieColumns.TITLE;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.BACKDROPPATHR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.IDR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.OVERVIEWR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.POSTERPATHR;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.RELEASER;
import static edn.projek.made.movieapp.db.DbContract.MovieReleaseColumns.TITLER;
import static edn.projek.made.movieapp.db.DbContract.TABLE_FAV;
import static edn.projek.made.movieapp.db.DbContract.TABLER;

public class DbHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "DB_MOVIE";
    private static final int DATABASE_VERSION= 1;

    public static String CREATE_TABLE = "CREATE TABLE "+TABLE_FAV+"" +
            " ("+ID + " INTEGER PRIMARY KEY, " + TITLE + " TEXT NOT NULL, " +
            OVERVIEW + " TEXT NOT NULL, "+ RELEASE + " TEXT NOT NULL, " + POSTERPATH + " TEXT NOT NULL, "+
            BACKDROPPATH + " TEXT NOT NULL );";

    public static String CREATE_TABLER = "CREATE TABLE "+TABLER+"" +
            " ("+IDR + " INTEGER PRIMARY KEY, " + TITLER + " TEXT NOT NULL, " +
            OVERVIEWR + " TEXT NOT NULL, "+ RELEASER + " TEXT NOT NULL, " + POSTERPATHR + " TEXT NOT NULL, "+
            BACKDROPPATHR + " TEXT NOT NULL );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAV);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TITLER);
        onCreate(sqLiteDatabase);
    }
}
