package com.alex.mirash.boogietapcounter.tapper.tool;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.settings.options.SettingID3v2Version;
import com.alex.mirash.boogietapcounter.settings.options.SettingPlayNextMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingRoundMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingSaveMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;

/**
 * @author Mirash
 */

public class PreferencesManager {
    private final SharedPreferences preferences;

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

    public static void putString(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    @NonNull
    public static SettingTapMode getTapMode() {
        int position = getInt(Const.KEY_TAP_MODE, -1);
        if (position == -1) {
            return SettingTapMode.getDefaultValue();
        }
        return SettingTapMode.values()[position];
    }

    public static void setTapMode(@NonNull SettingTapMode tapMode) {
        putInt(Const.KEY_TAP_MODE, tapMode.ordinal());
    }

    @NonNull
    public static SettingUnit getUnit() {
        int position = getInt(Const.KEY_TEMP_UNIT, -1);
        if (position == -1) {
            return SettingUnit.getDefaultValue();
        }
        return SettingUnit.getValue(position);
    }

    public static void setUnit(@NonNull SettingUnit unit) {
        putInt(Const.KEY_TEMP_UNIT, unit.ordinal());
    }

    @NonNull
    public static SettingRoundMode getRoundMode() {
        int position = getInt(Const.KEY_ROUND_MODE, -1);
        if (position == -1) {
            return SettingRoundMode.getDefaultValue();
        }
        return SettingRoundMode.getValue(position);
    }

    public static void setRoundMode(@NonNull SettingRoundMode roundMode) {
        putInt(Const.KEY_ROUND_MODE, roundMode.ordinal());
    }

    @NonNull
    public static SettingID3v2Version getID3v2Version() {
        int position = getInt(Const.KEY_ID_3V2_VERSION, -1);
        if (position == -1) {
            return SettingID3v2Version.getDefaultValue();
        }
        return SettingID3v2Version.getValue(position);
    }

    public static void setID3v2Version(@NonNull SettingID3v2Version version) {
        putInt(Const.KEY_ID_3V2_VERSION, version.ordinal());
    }

    @NonNull
    public static SettingSaveMode getSaveMode() {
        int position = getInt(Const.KEY_SAVE_MODE, -1);
        if (position == -1) {
            return SettingSaveMode.getDefaultValue();
        }
        return SettingSaveMode.getValue(position);
    }

    public static void setSaveMode(@NonNull SettingSaveMode value) {
        putInt(Const.KEY_SAVE_MODE, value.ordinal());
    }

    @NonNull
    public static SettingPlayNextMode getPlayNextMode() {
        int position = getInt(Const.KEY_PLAY_NEXT_MODE, -1);
        if (position == -1) {
            return SettingPlayNextMode.getDefaultValue();
        }
        return SettingPlayNextMode.getValue(position);
    }

    public static void setPlayNextMode(@NonNull SettingPlayNextMode value) {
        putInt(Const.KEY_PLAY_NEXT_MODE, value.ordinal());
    }

    public static boolean getAutoRefreshValue() {
        return getBool(Const.KEY_AUTO_REFRESH, Const.AUTO_REFRESH_DEFAULT_VALUE);
    }

    public static void setAutoRefresh(boolean isAutoRefresh) {
        putBool(Const.KEY_AUTO_REFRESH, isAutoRefresh);
    }

    public static boolean getAddBpmToFileNameValue() {
        return getBool(Const.KEY_ADD_BPM_TO_FILENAME, Const.IS_ADD_BPM_TO_FILE_NAME_DEFAULT_VALUE);
    }

    public static void setAddBpmToFileNameValue(boolean isAddBpm) {
        putBool(Const.KEY_ADD_BPM_TO_FILENAME, isAddBpm);
    }

    public static boolean getShowOutputDetails() {
        return getBool(Const.KEY_SHOW_OUTPUT_DETAILS, Const.IS_SHOW_OUTPUT_DETAILS_DEFAULT_VALUE);
    }

    public static void setShowOutputDetails(boolean isShowDetails) {
        putBool(Const.KEY_SHOW_OUTPUT_DETAILS, isShowDetails);
    }

    public static void remove(String key) {
        getPreferences().edit().remove(key).apply();
    }

    public static String getLastFilePath() {
        return getString(Const.KEY_LAST_FILE_PATH, null);
    }

    public static void setLastFilePath(String path) {
        putString(Const.KEY_LAST_FILE_PATH, path);
    }
}
