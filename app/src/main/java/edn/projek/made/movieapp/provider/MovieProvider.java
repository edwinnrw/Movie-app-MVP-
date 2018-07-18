package edn.projek.made.movieapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import edn.projek.made.movieapp.db.DbContract;
import edn.projek.made.movieapp.db.FavoriteHelper;

import static edn.projek.made.movieapp.db.DbContract.AUTHORITY;
import static edn.projek.made.movieapp.db.DbContract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(AUTHORITY, DbContract.TABLE_FAV, FAVORITE);

        sUriMatcher.addURI(AUTHORITY,
                DbContract.TABLE_FAV+ "/#",
                FAVORITE_ID);
    }

    private FavoriteHelper favoriteHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = new FavoriteHelper(getContext());
        favoriteHelper.open();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FAVORITE:
                cursor = favoriteHelper.queryProvider(strings,s,strings1,s1);
                break;
            case FAVORITE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case FAVORITE:
                added = favoriteHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                updated =  favoriteHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVORITE_ID:
                deleted =  favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case FAVORITE:
                deleted =  favoriteHelper.deleteProviderById(s,strings);
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
}
