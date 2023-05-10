package com.alex.mirash.boogietapcounter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.alex.mirash.boogietapcounter.mp3.Mp3PlayerCallback;
import com.alex.mirash.boogietapcounter.mp3.Mp3PlayerControl;
import com.alex.mirash.boogietapcounter.settings.SettingChangeObserver;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingRoundMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.controller.BeatController;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.EventsListener;
import com.alex.mirash.boogietapcounter.tapper.tool.PreferencesManager;
import com.alex.mirash.boogietapcounter.tapper.tool.Utils;
import com.alex.mirash.boogietapcounter.tapper.view.SaveButton;
import com.alex.mirash.boogietapcounter.tapper.view.output.DataOutputView;
import com.alex.mirash.boogietapcounter.tapper.view.setting.SettingsView;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

public class MainActivity extends BasePermissionsActivity implements EventsListener, Mp3PlayerCallback {
    private static final float DRAWER_PARALLAX_RATIO = 0.25f;

    private BeatController beatController;
    private View contentContainerView;
    private DataOutputView outputView;
    private SaveButton bpmSaveButton;
    private View refreshView;
    private DrawerLayout drawer;

    private Animation refreshAnimation;
    private Mp3PlayerControl mp3PlayerControl;

    private SettingChangeObserver<SettingUnit> unitUpdateObserver;
    private SettingChangeObserver<SettingRoundMode> roundModeUpdateObserver;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        contentContainerView = findViewById(R.id.content_container);
        contentContainerView.findViewById(R.id.content_main_view).setOnTouchListener((v, event) -> true);
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
        TextView versionTextView = drawer.findViewById(R.id.app_version);
        versionTextView.setText(Utils.getAppVersion());
        Utils.initEasterEgg(drawer.findViewById(R.id.drawer_image), drawer.findViewById(R.id.drawer_hbk_logo));

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.addHeaderView(new SettingsView(this));

        initTapElements();
        bpmSaveButton = findViewById(R.id.bpm_save_button);
        bpmSaveButton.setOnClickListener(v -> {
            DataHolder dataHolder = beatController.getData();
            if (dataHolder != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mp3PlayerControl.saveBpm(dataHolder.getSelectedUnitValue());
                } else {
                    ToastUtils.showLongToast("=( Sorry, but this action requires API >= " + Build.VERSION_CODES.O);
                }
            }
        });
        mp3PlayerControl = new Mp3PlayerControl(findViewById(R.id.mp3_player));
        mp3PlayerControl.setCallback(this);

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

    private void setBpmSaveButtonEnabled(boolean enabled) {
        bpmSaveButton.setEnabled(enabled);
        if (enabled) {
            addRoundModeObserver();
        } else {
            removeRoundModeObserver();
        }
    }

    private void updateBpmSaveButtonText(DataHolder data) {
        if (data != null) {
            bpmSaveButton.setText(String.valueOf(data.getSelectedUnitValue().getRoundValue()));
        }
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            if (refreshView == null) {
                refreshView = findViewById(R.id.action_refresh);
            }
            if (refreshView != null) {
                refreshView.startAnimation(refreshAnimation);
            }
            beatController.refresh();
            return true;
        } else if (id == R.id.action_file_open) {
            performTaskOnPermissionsGranted(result -> showFilePicker(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFolderPick(String path, @NonNull File folder) {
        Log.d(TAG, "handleFolderPick: " + path);
        if (!folder.canWrite()) {
            ToastUtils.showToast("Selected folder is <read only>. Please select folder from internal storage");
            Log.e(TAG, "Cannot write to selected folder");
            return;
        }
        File parent = folder.getParentFile();
        if (parent != null && parent.exists()) {
            PreferencesManager.setLastFilePath(parent.getPath());
        } else {
            PreferencesManager.setLastFilePath(path);
        }
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
        bpmSaveButton.setVisibility(View.VISIBLE);
    }

    private void handleFilePick(String path, @NonNull File file) {
        Log.d(TAG, "handleFilePick: " + path);
        File folder = file.getParentFile();
        if (folder == null || !folder.canWrite()) {
            ToastUtils.showToast("Selected folder is <read only>. Please select folder from internal storage");
            Log.e(TAG, "Cannot write to selected folder");
            return;
        }
        PreferencesManager.setLastFilePath(folder.getPath());
        List<File> mp3Files = new ArrayList<>(1);
        mp3Files.add(file);
        mp3PlayerControl.initialize(mp3Files);
        bpmSaveButton.setVisibility(View.VISIBLE);
    }

    //this functionality won't work on Android 13+, work with Files like that is not available anymore
    private void showFilePicker() {
        String lastPath = PreferencesManager.getLastFilePath();
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(lastPath == null ? DialogConfigs.DEFAULT_DIR : lastPath);
        properties.extensions = new String[]{"mp3"};
        FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
        dialog.setDialogSelectionListener(files -> {
            String path = files == null || files.length == 0 ? null : files[0];
            if (path != null) {
                drawer.closeDrawer(GravityCompat.START);
                File file = new File(path);
                if (file.isDirectory()) {
                    handleFolderPick(path, file);
                } else {
                    handleFilePick(path, file);
                }
            } else {
                ToastUtils.showToast("File select failed");
            }
        });
        dialog.setTitle(getString(R.string.select_file_title));
        dialog.show();

        TextView button = dialog.findViewById(R.id.cancel);
        if (button != null) {
            button.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.colorPrimary)));
        }
        button = dialog.findViewById(R.id.select);
        if (button != null) {
            button.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.colorPrimary)));
        }
    }

    @Override
    public void onNewMeasurementStarted() {
        Log.d(TAG, "onNewMeasurementStarted");
        outputView.refresh(true);
        removeUnitObserver();
    }

    @Override
    public void onMeasurementStopped(DataHolder resultData) {
        Log.d(TAG, "onMeasurementStopped");
        if (resultData != null && resultData.getDetails().getIntervalsCount() > 0) {
            outputView.setHighlighted(true);
            addUnitObserver();
        }
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        Log.d(TAG, "onBpmUpdate");
        outputView.setData(data);
        if (data != null) {
            setBpmSaveButtonEnabled(true);
            updateBpmSaveButtonText(data);
        }
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        outputView.refresh();
        setBpmSaveButtonEnabled(false);
        removeUnitObserver();
    }

    @Override
    public void onFilePlayStart() {
        Log.d(TAG, "onFilePlayStart");
        beatController.refresh();
    }

    private void addRoundModeObserver() {
        if (roundModeUpdateObserver == null) {
            roundModeUpdateObserver = setting -> updateBpmSaveButtonText(beatController.getData());
            Settings.get().addRoundModeObserver(roundModeUpdateObserver);
        }
    }

    private void removeRoundModeObserver() {
        if (roundModeUpdateObserver != null) {
            Settings.get().removeRoundModeObserver(roundModeUpdateObserver);
            roundModeUpdateObserver = null;
        }
    }

    private void addUnitObserver() {
        if (unitUpdateObserver == null) {
            unitUpdateObserver = setting -> {
                outputView.setData(beatController.getData());
                updateBpmSaveButtonText(beatController.getData());
            };
            Settings.get().addUnitObserver(unitUpdateObserver);
        }
    }

    private void removeUnitObserver() {
        if (unitUpdateObserver != null) {
            Settings.get().removeUnitObserver(unitUpdateObserver);
            unitUpdateObserver = null;
        }
    }
}
