package edn.projek.made.movieapp.setting;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edn.projek.made.movieapp.R;

public class SettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, SettingContract.View {
    @BindView(R.id.switch_on_off)
    Switch switch_on_off;
    @BindView(R.id.txt_language)
    TextView txtLanguage;
    SettingPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        presenter=new SettingPresenter(this,this);
        txtLanguage.setText(Locale.getDefault().getDisplayName());
        switch_on_off.setOnCheckedChangeListener(this);
        presenter.checkOnOffReminder();
    }
    @OnClick(R.id.setting_bahasa)
    public void onClick(){
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        presenter.switchOnOffReminder(b);
    }

    @Override
    public void showDialog(String a) {
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSwitchReminder(boolean b) {
        switch_on_off.setChecked(b);
    }

    @Override
    public void setTextOnSwitch() {
        switch_on_off.setText("On");
    }

    @Override
    public void setTextOffSwitch() {
        switch_on_off.setText("Off");

    }
}
