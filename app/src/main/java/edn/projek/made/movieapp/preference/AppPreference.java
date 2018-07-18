package edn.projek.made.movieapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {
    private String KEY_NOTIF = "reminder";
    private SharedPreferences preferences;
    public AppPreference(Context context) {
        String PREFS_NAME = "AppPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    public void setOnOffReminder(boolean is) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_NOTIF, is);
        editor.apply();
    }
    public Boolean getOnOffReminder() {
        return preferences.getBoolean(KEY_NOTIF, false);
    }
}
