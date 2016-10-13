package com.alex.mirash.boogietapcounter;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.alex.mirash.boogietapcounter.tapper.controller.TapCountController;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.EventsListener;
import com.alex.mirash.boogietapcounter.tapper.view.output.DataOutputView;
import com.alex.mirash.boogietapcounter.tapper.view.setting.SettingsView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        EventsListener {

    private static final float DRAWER_PARALLAX_RATIO = 0.25f;

    private TapCountController tapCountController;
    private View contentView;
    private Button tapButton;
    private DataOutputView outputView;

    private DrawerLayout drawer;

    private Animation refreshAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        contentView = findViewById(R.id.content_main);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                contentView.setTranslationX(contentView.getWidth() * DRAWER_PARALLAX_RATIO * slideOffset);
            }
        });
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.addHeaderView(new SettingsView(this));

        initTapElements();

        refreshAnimation = AnimationUtils.loadAnimation(this, R.anim.refresh_menu_item_anim);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initTapElements() {
        tapButton = (Button) findViewById(R.id.tap_button);
        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tapCountController.onTap();
            }
        });
        outputView = (DataOutputView) findViewById(R.id.data_output_view);
        tapCountController = new TapCountController();
        tapCountController.setListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_refresh);
        item.getIcon().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            View refreshView = findViewById(R.id.action_refresh);
            if (refreshView != null) {
                refreshView.startAnimation(refreshAnimation);
            }
            tapCountController.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_termines:
                break;
            case R.id.nav_about:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onNewMeasurementStarted() {
        outputView.refresh();
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        outputView.setData(data);
    }

    @Override
    public void onRefresh() {
        outputView.refresh();
    }

    @Override
    public void onIdle() {
        outputView.highlight();
    }
}
