package com.example.allseven64.cataloguemovieuiux;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.allseven64.cataloguemovieuiux.fragment.AboutFragment;
import com.example.allseven64.cataloguemovieuiux.fragment.FavoriteFragment;
import com.example.allseven64.cataloguemovieuiux.fragment.HomeFragment;
import com.example.allseven64.cataloguemovieuiux.fragment.NowPlayingFragment;
import com.example.allseven64.cataloguemovieuiux.fragment.SettingFragment;
import com.example.allseven64.cataloguemovieuiux.fragment.UpcomingFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_settings) {
            Intent mIntent = new Intent (Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String title = "";

        if (id == R.id.nav_home) {
            // Handle the home action
            title = getResources().getString(R.string.home);;
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_about) {
            title = getResources().getString(R.string.about);
            fragment = new AboutFragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.nav_now_playing) {
            title = getResources().getString(R.string.now_playing);
            fragment = new NowPlayingFragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.nav_upcoming) {
            title = getResources().getString(R.string.upcoming);
            fragment = new UpcomingFragment();
            fragment.setArguments(bundle);
        }
        else if (id == R.id.nav_favorite) {
            title = getResources().getString(R.string.favorite);
            fragment = new FavoriteFragment();
            fragment.setArguments(bundle);

        }
        else if (id == R.id.nav_setting) {
            title = getResources().getString(R.string.setting);
            fragment = new SettingFragment();
            fragment.setArguments(bundle);

        }

        //Ganti Halaman dengan memanggil fragment replace
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        getSupportActionBar().setTitle(title);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
