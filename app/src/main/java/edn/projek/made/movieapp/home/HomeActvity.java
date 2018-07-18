package edn.projek.made.movieapp.home;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import edn.projek.made.movieapp.R;
import edn.projek.made.movieapp.favorite.FavoriteActivity;
import edn.projek.made.movieapp.search.SearchActivity;
import edn.projek.made.movieapp.setting.SettingActivity;

public class HomeActvity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.navigation)
    BottomNavigationView navigationView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_actvity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_home));
        navigationView.setSelected(true);
        navigationView.setSelectedItemId(R.id.navigation_playing);
        loadFragment(PlayingFragment.newInstance());
        navigationView.setOnNavigationItemSelectedListener(this);

    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_playing:
                loadFragment(PlayingFragment.newInstance());
                return  true;
            case R.id.navigation_upcoming:
                loadFragment(UpcomingFragment.newInstance());
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.searchItemMenu:
                startActivity(new Intent(this, SearchActivity.class));
                return true;
            case R.id.startItemMenu:
                startActivity(new Intent(this, FavoriteActivity.class));
                return  true;

            case R.id.action_change_settings:
                startActivity(new Intent(this, SettingActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
