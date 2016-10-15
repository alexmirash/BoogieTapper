package com.alex.mirash.boogietapcounter.tapper.tool;

import com.alex.mirash.boogietapcounter.BuildConfig;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public final class Utils {
    public static float getTempInUnits(long tapInterval) {
        Settings settings = Settings.get();
        SettingUnit unit = settings.getUnit();
        SettingTapMode tapMode = settings.getTapMode();
        return (Const.MILLIS_IN_MINUTE * tapMode.getBeats()) / (float) (tapInterval * unit.getBeats());
    }

    public static float round(float value, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * multiplier) / multiplier);
    }

    public static String getAppVersion() {
        return BuildConfig.VERSION_CODE + "." + BuildConfig.VERSION_NAME;
    }
}
