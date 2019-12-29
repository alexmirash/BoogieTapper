package com.alex.mirash.boogietapcounter;

import android.Manifest;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alex.mirash.boogietapcounter.mp3.Mp3PlayerControl;
import com.alex.mirash.boogietapcounter.settings.SettingChangeObserver;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.controller.BeatController;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.EventsListener;
import com.alex.mirash.boogietapcounter.tapper.tool.PreferencesManager;
import com.alex.mirash.boogietapcounter.tapper.tool.Utils;
import com.alex.mirash.boogietapcounter.tapper.view.output.DataOutputView;
import com.alex.mirash.boogietapcounter.tapper.view.setting.SettingsView;
import com.google.android.material.navigation.NavigationView;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

public class MainActivity extends BasePermissionsActivity implements NavigationView.OnNavigationItemSelectedListener, EventsListener {

    private static final float DRAWER_PARALLAX_RATIO = 0.25f;

    private BeatController beatController;
    private View contentContainerView;
    private DataOutputView outputView;

    private DrawerLayout drawer;

    private Animation refreshAnimation;
    private NavigationView navigationView;

    private Mp3PlayerControl mp3PlayerControl;

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
        requestPermissionsIfNecessary(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
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
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.addHeaderView(new SettingsView(this));

        initTapElements();
        mp3PlayerControl = new Mp3PlayerControl(findViewById(R.id.mp3_player));

        refreshAnimation = AnimationUtils.loadAnimation(this, R.anim.refresh_menu_item_anim);

        Utils.changeNavigationViewWidthIfNecessary(drawer, navigationView);
    }

    @Override
    protected void onDestroy() {
        Settings.get().clearObservers();
        mp3PlayerControl.clear();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BoogieApp.getInstance().setIsForeground(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BoogieApp.getInstance().setIsForeground(false);
    }

    private void initTapElements() {
        beatController = new BeatController();
        beatController.setListener(this);
        View tapButton = findViewById(R.id.tap_button);
        tapButton.setOnClickListener(v -> beatController.onTap());
        outputView = findViewById(R.id.data_output_view);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            View refreshView = findViewById(R.id.action_refresh);
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
        if (id == R.id.nav_menu_pick_file) {
//            Mp3Helper.requestDocumentTree(this);
            new ChooserDialog(MainActivity.this)
                    .withFilter(true, false)
                    .withStartFile(PreferencesManager.getLastFilePath())
                    .withChosenListener((path, folder) -> {
                        drawer.closeDrawer(GravityCompat.START);
                        PreferencesManager.setLastFilePath(path);
                        if (folder != null) {
                            Log.d(TAG, path + ", " + folder.exists() + ", canWrite =? " + folder.canWrite());
                            File[] files = folder.listFiles();
                            if (files == null || files.length == 0) {
                                ToastUtils.showToast("Fail: seems folder is empty");
                                return;
                            }
                            List<File> mp3Files = new ArrayList<>(files.length);
                            for (File file : files) {
                                if (file.getName().endsWith(".mp3")) {
                                    mp3Files.add(file);
                                }
                            }
                            if (mp3Files.isEmpty()) {
                                ToastUtils.showToast("Fail: no mp3 files found");
                                return;
                            }
                            mp3PlayerControl.initialize(mp3Files);
                        }
                    })
                    .build()
                    .show();
        }
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

    @Override
    public void onBpmUpdate(DataHolder data) {
        outputView.setData(data);
    }

    @Override
    public void onRefresh() {
        outputView.refresh();
    }
}
