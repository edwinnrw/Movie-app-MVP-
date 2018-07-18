package edn.projek.made.movieapp.setting;

import java.util.List;

import edn.projek.made.movieapp.model.Movie;

public class SettingContract {
    public  interface Presenter{
        void switchOnOffReminder(boolean onOff);
        void checkOnOffReminder();
    }
    public interface View{
        void showDialog(String a);
        void setSwitchReminder(boolean b);
        void setTextOnSwitch();
        void setTextOffSwitch();
    }
}
