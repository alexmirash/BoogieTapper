package com.alex.mirash.boogietapcounter.settings;

import com.alex.mirash.boogietapcounter.BoogieApp;

/**
 * @author Mirash
 */

public class GlobalSettings {
    private SettingTapMode tapMode;
    private SettingUnit unit;
    private SettingAutoRefreshTime autoRefreshTime;

    public GlobalSettings() {
        setDefaults();
    }

    private void setDefaults() {
        tapMode = SettingTapMode.getDefaultMode();
        unit = SettingUnit.getDefaultUnit();
        autoRefreshTime = SettingAutoRefreshTime.getDefaultRefreshTime();
    }

    public SettingTapMode getTapMode() {
        return tapMode;
    }

    public void setTapMode(SettingTapMode mode) {
        tapMode = mode;
    }

    public SettingUnit getUnit() {
        return unit;
    }

    public void setUnit(SettingUnit value) {
        unit = value;
    }

    public SettingAutoRefreshTime getAutoRefreshTime() {
        return autoRefreshTime;
    }

    public void setAutoRefreshTime(SettingAutoRefreshTime time) {
        autoRefreshTime = time;
    }

    public static GlobalSettings get() {
        return BoogieApp.getInstance().getSettings();
    }
}
