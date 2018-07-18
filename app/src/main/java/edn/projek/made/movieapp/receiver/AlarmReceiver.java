package edn.projek.made.movieapp.receiver;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edn.projek.made.movieapp.BuildConfig;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.api.Endpoint;
import edn.projek.made.movieapp.api.ServiceGenerator;
import edn.projek.made.movieapp.db.ReleaseHelper;
import edn.projek.made.movieapp.home.HomeActvity;
import edn.projek.made.movieapp.model.Movie;
import edn.projek.made.movieapp.model.ResponseMovie;
import edn.projek.made.movieapp.util.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static edn.projek.made.movieapp.util.Constant.NOTIF_ID_DAILY;
import static edn.projek.made.movieapp.util.Constant.NOTIF_ID_RELEASE;
import static edn.projek.made.movieapp.util.Constant.TYPE_DAILY;
import static edn.projek.made.movieapp.util.Constant.TYPE_RELEASE;


public class AlarmReceiver extends BroadcastReceiver {
    ReleaseHelper helper;
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("type");
        String message = intent.getStringExtra("message");
        int notifId = type.equalsIgnoreCase(TYPE_DAILY) ? NOTIF_ID_DAILY : NOTIF_ID_RELEASE;
        helper=new ReleaseHelper(context);
        helper.open();
        if (notifId == NOTIF_ID_DAILY){
            showAlarmNotification(context,"Movie Apps", message, notifId);
            checkReleaseMovie(context);

        }else{
            List<Movie>movieList=helper.query();
            if (movieList.size()>0){

                for (Movie movie :movieList) {
                    showAlarmNotification(context,movie.getTitle(),"Hari ini "+movie.getTitle()+" Release",notifId);
                }
            }
            helper.close();
        }
    }

    private void checkReleaseMovie(final Context context) {
        HashMap<String,String> query=new HashMap<>();
        query.put("api_key", BuildConfig.API_KEY);
        if (context.getResources().getString(R.string.languages).equalsIgnoreCase("English")){
            query.put("language","en-us");
        }else{
            query.put("language","id");

        }
        ServiceGenerator.createService(Endpoint.class).getMovieUpcoming(query).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                if (response.code()==200){
                    if (response.body().getResults()!=null){
                        if (helper.query().size()>0){
                            helper.deleteAll();
                        }
                        for (Movie movie : response.body().getResults()){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date convertedDate = new Date();
                            try {
                                convertedDate = dateFormat.parse(movie.getReleaseDate());
                                if (DateUtils.isToday(convertedDate.getTime())){
                                    helper.insert(movie);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        helper.close();
                        switchOnRelese(context);
                    }
                }else{

                }

            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {


            }
        });
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId){
        Intent intent1 = new Intent(context,HomeActvity.class);
        intent1.addFlags(FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pIntent1 = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent1, 0);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setColor(context.getResources().getColor(android.R.color.transparent))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pIntent1)
                .setSound(defaultSoundUri);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("1","channelread",NotificationManager.IMPORTANCE_HIGH));
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void switchOnRelese(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title","Release");
        intent.putExtra("message", "");
        intent.putExtra("type", TYPE_RELEASE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
