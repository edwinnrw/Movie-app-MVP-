package edn.projek.favorite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static String TABLE_FAV = "FAVORITE";

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String OVERVIEW = "description";
        public static String RELEASE = "date";
        public static String BACKDROPPATH = "backdropath";
        public static String POSTERPATH = "posterpath";

    }

    public static final String AUTHORITY = "edn.projek.made.movieapp";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAV)
            .build();
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
