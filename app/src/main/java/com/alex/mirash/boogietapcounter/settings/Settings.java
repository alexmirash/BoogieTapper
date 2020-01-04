package com.alex.mirash.boogietapcounter.settings;

import android.util.Log;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.settings.options.SettingID3v2Version;
import com.alex.mirash.boogietapcounter.settings.options.SettingRoundMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingSaveMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;
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
    private SettingID3v2Version id3v2Version;
    private SettingSaveMode saveMode;
    private boolean isAutoRefresh;
    private boolean isAddBpmToFileName;
    private boolean isShowOutputDetails;

    private List<SettingChangeObserver<SettingTapMode>> tapModeObservers;
    private List<SettingChangeObserver<SettingUnit>> unitChangeObservers;
    private List<SettingChangeObserver<SettingRoundMode>> roundModeChangeObservers;
    private List<SettingChangeObserver<Boolean>> showDetailsChangeObservers;

    public Settings() {
        loadFromPreferences();
    }

    private void loadFromPreferences() {
        tapMode = PreferencesManager.getTapMode();
        unit = PreferencesManager.getUnit();
        roundMode = PreferencesManager.getRoundMode();
        id3v2Version = PreferencesManager.getID3v2Version();
        saveMode = PreferencesManager.getSaveMode();
        isAutoRefresh = PreferencesManager.getAutoRefreshValue();
        isAddBpmToFileName = PreferencesManager.getAddBpmToFileNameValue();

        isShowOutputDetails = Const.IS_SHOW_OUTPUT_DETAILS_DEFAULT_VALUE;
        PreferencesManager.remove(Const.KEY_SHOW_OUTPUT_DETAILS);
    }

    @NonNull
    public SettingTapMode getTapMode() {
        return tapMode;
    }

    public void setTapMode(@NonNull SettingTapMode mode) {
        if (tapMode != mode) {
            tapMode = mode;
            PreferencesManager.setTapMode(mode);
            notifySettingChange(mode, tapModeObservers);
        }
    }

    @NonNull
    public SettingUnit getUnit() {
        return unit;
    }

    public void setUnit(@NonNull SettingUnit value) {
        if (unit != value) {
            unit = value;
            PreferencesManager.setUnit(value);
            notifySettingChange(value, unitChangeObservers);
        }
    }

    @NonNull
    public SettingRoundMode getRoundMode() {
        return roundMode;
    }

    public void setRoundMode(@NonNull SettingRoundMode value) {
        if (roundMode != value) {
            roundMode = value;
            PreferencesManager.setRoundMode(value);
            notifySettingChange(value, roundModeChangeObservers);
        }
    }

    @NonNull
    public SettingID3v2Version getId3v2Version() {
        return id3v2Version;
    }

    public void setId3v2Version(@NonNull SettingID3v2Version value) {
        id3v2Version = value;
        PreferencesManager.setID3v2Version(value);
    }

    @NonNull
    public SettingSaveMode getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(@NonNull SettingSaveMode value) {
        saveMode = value;
        PreferencesManager.setSaveMode(value);
    }

    public boolean isAutoRefresh() {
        return isAutoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        isAutoRefresh = autoRefresh;
        PreferencesManager.setAutoRefresh(autoRefresh);
    }

    public boolean isShowOutputDetails() {
        return isShowOutputDetails;
    }

    public void setShowOutputDetails(boolean showOutputDetails) {
        if (isShowOutputDetails != showOutputDetails) {
            isShowOutputDetails = showOutputDetails;
            PreferencesManager.setShowOutputDetails(showOutputDetails);
            notifySettingChange(showOutputDetails, showDetailsChangeObservers);
        }
    }

    public boolean isAddBpmToFileName() {
        return isAddBpmToFileName;
    }

    public void setAddBpmToFileName(boolean addBpmToFileName) {
        isAddBpmToFileName = addBpmToFileName;
        PreferencesManager.setAddBpmToFileNameValue(addBpmToFileName);
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

    public void addShowDetailsObserver(SettingChangeObserver<Boolean> observer) {
        if (showDetailsChangeObservers == null) {
            showDetailsChangeObservers = new ArrayList<>();
        }
        showDetailsChangeObservers.add(observer);
    }

    public void removeShowDetailsObserver(SettingChangeObserver<Boolean> observer) {
        if (showDetailsChangeObservers != null) {
            showDetailsChangeObservers.remove(observer);
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
            Log.d(TAG, "clear round mode " + roundModeChangeObservers.size());
            roundModeChangeObservers.clear();
            roundModeChangeObservers = null;
        }
        if (showDetailsChangeObservers != null) {
            Log.d(TAG, "clear show details " + showDetailsChangeObservers.size());
            showDetailsChangeObservers.clear();
            showDetailsChangeObservers = null;
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
