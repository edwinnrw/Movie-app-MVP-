package edn.projek.made.movieapp.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import edn.projek.made.movieapp.preference.AppPreference;
import edn.projek.made.movieapp.receiver.AlarmReceiver;

import static edn.projek.made.movieapp.util.Constant.NOTIF_ID_DAILY;
import static edn.projek.made.movieapp.util.Constant.TYPE_DAILY;

public class SettingPresenter implements SettingContract.Presenter {
    SettingContract.View view;
    Context context;
    AppPreference appPreference;

    public SettingPresenter(SettingContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.appPreference=new AppPreference(context);
    }

    @Override
    public void switchOnOffReminder(boolean onOff) {
        appPreference.setOnOffReminder(onOff);
        if (onOff){
            switchOnDaily();
            view.setTextOnSwitch();
        }else {
            switchOffDaily();
            view.setTextOffSwitch();
        }
    }



    @Override
    public void checkOnOffReminder() {
        if (appPreference.getOnOffReminder()){
            view.setSwitchReminder(true);
        }else{
            view.setSwitchReminder(false);
        }

    }
    private void switchOffDaily() {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title","Movie Apps");
        intent.putExtra("message", "Movie Apps Missing You.");
        intent.putExtra("type", TYPE_DAILY);
        int requestCode = NOTIF_ID_DAILY;
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
    private void switchOnDaily(){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title","Movie Apps");
        intent.putExtra("message", "Movie Apps Missing You.");
        intent.putExtra("type", TYPE_DAILY);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        int requestCode = NOTIF_ID_DAILY;
        PendingIntent pendingIntent =  PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}
