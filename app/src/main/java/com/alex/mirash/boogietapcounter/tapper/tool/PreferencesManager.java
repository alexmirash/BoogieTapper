package com.alex.mirash.boogietapcounter.tapper.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public class PreferencesManager {
    private SharedPreferences preferences;

    private static SharedPreferences getPreferences() {
        return BoogieApp.getInstance().getPreferences().preferences;
    }

    public PreferencesManager(Context context) {
        preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static void putInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public static void putBool(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).apply();
    }

    public static boolean getBool(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static SettingTapMode getTapMode() {
        int position = getInt(Settings.KEY_TAP_MODE, -1);
        if (position == -1) {
            return SettingTapMode.getDefaultValue();
        }
        return SettingTapMode.values()[position];
    }

    public static void setTapMode(SettingTapMode tapMode) {
        putInt(Settings.KEY_TAP_MODE, tapMode.ordinal());
    }

    public static SettingUnit getUnit() {
        int position = getInt(Settings.KEY_TEMP_UNIT, -1);
        if (position == -1) {
            return SettingUnit.getDefaultValue();
        }
        return SettingUnit.values()[position];
    }

    public static void setUnit(SettingUnit unit) {
        putInt(Settings.KEY_TEMP_UNIT, unit.ordinal());
    }

    public static boolean getAutoRefreshValue() {
        return getBool(Settings.KEY_AUTO_REFRESH, Settings.AUTO_REFRESH_DEFAULT_VALUE);
    }

    public static void setAutoRefresh(boolean isAutoRefresh) {
        putBool(Settings.KEY_AUTO_REFRESH, isAutoRefresh);
    }
}
