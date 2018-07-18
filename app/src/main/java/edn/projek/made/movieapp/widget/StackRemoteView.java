package edn.projek.made.movieapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.db.FavoriteHelper;
import edn.projek.made.movieapp.home.HomeActvity;
import edn.projek.made.movieapp.model.Movie;

import static edn.projek.made.movieapp.db.DbContract.CONTENT_URI;

public class StackRemoteView implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Movie> mWidgetItems = new ArrayList<>();
    private Context mContext;
    private int mAppWidgetId;

    public StackRemoteView(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();

        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null,
                null, null, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    Movie movie = new Movie(cursor);
                    mWidgetItems.add(movie);
                } while (cursor.moveToNext());

            }
        }

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Bitmap bmp = null;
        try {

            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load("http://image.tmdb.org/t/p/w500" + mWidgetItems.get(position).getBackdropPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();

        }catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error","error");
        }

        rv.setImageViewBitmap(R.id.imageView,bmp);
        rv.setTextViewText(R.id.txt_title,mWidgetItems.get(position).getTitle());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = simpleDateFormat2.parse(mWidgetItems.get(position).getReleaseDate());
            simpleDateFormat2 = new SimpleDateFormat("dd MMMM yyyy");
            String newFormatttedDate = simpleDateFormat2.format(parsedDate);
            rv.setTextViewText(R.id.txt_date,newFormatttedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
