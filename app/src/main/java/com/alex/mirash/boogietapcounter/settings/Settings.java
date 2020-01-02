package com.alex.mirash.boogietapcounter.settings;

import android.util.Log;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.settings.options.SettingRoundMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.tool.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */

public class Settings {
    private SettingTapMode tapMode;
    private SettingUnit unit;
    private SettingRoundMode roundMode;
    private boolean isAutoRefresh;
    private boolean isAddBpmToFileName;

    private List<SettingChangeObserver<SettingTapMode>> tapModeObservers;
    private List<SettingChangeObserver<SettingUnit>> unitChangeObservers;
    private List<SettingChangeObserver<SettingRoundMode>> roundModeChangeObservers;

    public Settings() {
        loadFromPreferences();
    }

    private void loadFromPreferences() {
        tapMode = PreferencesManager.getTapMode();
        unit = PreferencesManager.getUnit();
        roundMode = PreferencesManager.getRoundMode();
        isAutoRefresh = PreferencesManager.getAutoRefreshValue();
        isAddBpmToFileName = PreferencesManager.getAddBpmToFileNameValue();
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

    public SettingRoundMode getRoundMode() {
        return roundMode;
    }

    public void setRoundMode(SettingRoundMode value) {
        roundMode = value;
        PreferencesManager.setRoundMode(value);
        notifySettingChange(value, roundModeChangeObservers);
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

    public void addRoundModeObserver(SettingChangeObserver<SettingRoundMode> observer) {
        if (roundModeChangeObservers == null) {
            roundModeChangeObservers = new ArrayList<>();
        }
        roundModeChangeObservers.add(observer);
    }

    public void removeRoundModeObserver(SettingChangeObserver<SettingRoundMode> observer) {
        if (roundModeChangeObservers != null) {
            roundModeChangeObservers.remove(observer);
        }
    }

    public void clearObservers() {
        if (tapModeObservers != null) {
            Log.d(TAG, "clear tap mode " + tapModeObservers.size());
            tapModeObservers.clear();
            tapModeObservers = null;
        }
        if (unitChangeObservers != null) {
            Log.d(TAG, "clear unit " + unitChangeObservers.size());
            unitChangeObservers.clear();
            unitChangeObservers = null;
        }
        if (roundModeChangeObservers != null) {
            Log.d(TAG, "clear unit " + roundModeChangeObservers.size());
            roundModeChangeObservers.clear();
            roundModeChangeObservers = null;
        }
    }

    private <T> void notifySettingChange(T setting, List<SettingChangeObserver<T>> observers) {
        if (observers != null) {
            for (SettingChangeObserver<T> observer : observers) {
                Log.d(TAG, "notify: " + observer.getClass().getSimpleName() + ", " + setting);
                observer.onSettingChanged(setting);
            }
        }
    }
}
