package com.alex.mirash.boogietapcounter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alex.mirash.boogietapcounter.pashalka.PashalkaHandler;
import com.alex.mirash.boogietapcounter.settings.SettingChangeObserver;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.controller.BeatController;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.ActivityActionProvider;
import com.alex.mirash.boogietapcounter.tapper.tool.EventsListener;
import com.alex.mirash.boogietapcounter.tapper.tool.Utils;
import com.alex.mirash.boogietapcounter.tapper.view.info.InfoScreenView;
import com.alex.mirash.boogietapcounter.tapper.view.output.DataOutputView;
import com.alex.mirash.boogietapcounter.tapper.view.setting.SettingsView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        EventsListener, ActivityActionProvider {

    private static final float DRAWER_PARALLAX_RATIO = 0.25f;

    private BeatController beatController;
    private View contentContainerView;
    private DataOutputView outputView;

    private DrawerLayout drawer;

    private InfoScreenView screenInfo;

    private Animation refreshAnimation;
    private NavigationView navigationView;

    private final SettingChangeObserver<SettingUnit> unitUpdateForOldDataObserver = new SettingChangeObserver<SettingUnit>() {
        @Override
        public void onSettingChanged(SettingUnit setting) {
            outputView.highlight();
            outputView.setData(beatController.getData());
        }
    };

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
        contentContainerView = findViewById(R.id.content_container);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                contentContainerView.setTranslationX(contentContainerView.getWidth() * DRAWER_PARALLAX_RATIO * slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                beatController.stopMeasurement();
            }
        });
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.addHeaderView(new SettingsView(this));

        initTapElements();

        refreshAnimation = AnimationUtils.loadAnimation(this, R.anim.refresh_menu_item_anim);

        screenInfo = (InfoScreenView) findViewById(R.id.screen_info);
        screenInfo.setActionProvider(this);

        new PashalkaHandler(findViewById(R.id.nav_image_panel), navigationView.findViewById(R.id.hbk_logo_image),
                (ViewStub) navigationView.findViewById(R.id.bezuglyi_stub));
    }

    @Override
    protected void onDestroy() {
        Settings.get().clearObservers();
        super.onDestroy();
    }

    private void initTapElements() {
        beatController = new BeatController();
        beatController.setListener(this);
        View tapButton = findViewById(R.id.tap_button);
        tapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beatController.onTap();
            }
        });
        outputView = (DataOutputView) findViewById(R.id.data_output_view);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (screenInfo.isVisible()) {
            screenInfo.hide();
            return;
        }
        super.onBackPressed();
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
            View refreshView = getRefreshButton();
            if (refreshView != null) {
                refreshView.startAnimation(refreshAnimation);
            }
            Settings.get().removeUnitObserver(unitUpdateForOldDataObserver);
            beatController.refresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_menu_info:
                screenInfo.show();
                break;
            case R.id.nav_menu_about:
                showAboutDialog();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // EventsListener
    @Override
    public void onNewMeasurementStarted() {
        outputView.refresh(true);
        Settings.get().removeUnitObserver(unitUpdateForOldDataObserver);
    }

    @Override
    public void onMeasurementStopped(DataHolder resultData) {
        if (resultData != null && resultData.getDetails().getIntervalsCount() > 0) {
            outputView.highlight();
            Settings.get().addUnitObserver(unitUpdateForOldDataObserver);
        }
    }

    private void showAboutDialog() {
        View content = View.inflate(this, R.layout.dialog_about_content, null);
        TextView versionValueView = (TextView) content.findViewById(R.id.about_version_value);
        versionValueView.setText(Utils.getAppVersion());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.about_dialog_title))
                .setView(content)
                .setPositiveButton(android.R.string.yes, null)
                .show();
        if (dialog != null && dialog.getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        }
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        outputView.setData(data);
    }

    @Override
    public void onRefresh() {
        outputView.refresh();
    }

    // ActivityActionsProvider
    @Override
    public void onBack() {
        onBackPressed();
    }

    @Override
    public View getRefreshButton() {
        return findViewById(R.id.action_refresh);
    }

    @Override
    public Menu getNavigationMenu() {
        return navigationView.getMenu();
    }
}
