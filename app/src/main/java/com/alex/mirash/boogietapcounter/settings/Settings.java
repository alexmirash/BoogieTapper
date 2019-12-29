package com.alex.mirash.boogietapcounter.settings;

import android.util.Log;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.tool.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mirash
 */

public class Settings {
    private SettingTapMode tapMode;
    private SettingUnit unit;
    private boolean isAutoRefresh;
    private boolean isAddBpmToFileName;

    //observerable part TODO
    private List<SettingChangeObserver<SettingTapMode>> tapModeObservers;
    private List<SettingChangeObserver<SettingUnit>> unitChangeObservers;

    public Settings() {
        loadFromPreferences();
    }

    private void loadFromPreferences() {
        tapMode = PreferencesManager.getTapMode();
        unit = PreferencesManager.getUnit();
        isAutoRefresh = PreferencesManager.getAutoRefreshValue();
        isAutoRefresh = PreferencesManager.getAddBpmToFileNameValue();
    }

    public SettingTapMode getTapMode() {
        return tapMode;
    }

    public void setTapMode(SettingTapMode mode) {
        tapMode = mode;
        PreferencesManager.setTapMode(mode);
        notifySettingChange(mode, tapModeObservers);
    }

    public SettingUnit getUnit() {
        return unit;
    }

    public void setUnit(SettingUnit value) {
        unit = value;
        PreferencesManager.setUnit(value);
        notifySettingChange(value, unitChangeObservers);
    }

    public boolean getIsAutoRefresh() {
        return isAutoRefresh;
    }

    public void setIsAutoRefresh(boolean autoRefresh) {
        isAutoRefresh = autoRefresh;
        PreferencesManager.setAutoRefresh(autoRefresh);
    }

    public boolean isAddBpmToFileName() {
        return isAddBpmToFileName;
    }

    public void setAddBpmToFileName(boolean addBpmToFileName) {
        isAddBpmToFileName = addBpmToFileName;
    }

    public static Settings get() {
        return BoogieApp.getInstance().getSettings();
    }

    public void addUnitObserver(SettingChangeObserver<SettingUnit> observer) {
        if (unitChangeObservers == null) {
            unitChangeObservers = new ArrayList<>();
        }
        unitChangeObservers.add(observer);
    }

    public void removeUnitObserver(SettingChangeObserver<SettingUnit> observer) {
        if (unitChangeObservers != null) {
            unitChangeObservers.remove(observer);
        }
    }

    public void addTapModeObserver(SettingChangeObserver<SettingTapMode> observer) {
        if (tapModeObservers == null) {
            tapModeObservers = new ArrayList<>();
        }
        tapModeObservers.add(observer);
    }

    public void clearObservers() {
        if (tapModeObservers != null) {
            Log.d("WTF", "clear tap mode " + tapModeObservers.size());
            tapModeObservers.clear();
            tapModeObservers = null;
        }
        if (unitChangeObservers != null) {
            Log.d("WTF", "clear unit " + unitChangeObservers.size());
            unitChangeObservers.clear();
            unitChangeObservers = null;
        }
    }

    private <T> void notifySettingChange(T setting, List<SettingChangeObserver<T>> observers) {
        if (observers != null) {
            for (SettingChangeObserver<T> observer : observers) {
                Log.d("WTF", "notify: " + observer.getClass().getSimpleName() + ", " + setting);
                observer.onSettingChanged(setting);
            }
        }
    }
}
