package com.alex.mirash.boogietapcounter.tapper.tool;

import com.alex.mirash.boogietapcounter.settings.GlobalSettings;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;

/**
 * @author Mirash
 */

public final class ConvertUtils {
    public static float getTempInUnits(long tapInterval) {
        GlobalSettings settings = GlobalSettings.get();
        SettingUnit unit = settings.getUnit();
        SettingTapMode tapMode = settings.getTapMode();
        return (Const.MILLIS_IN_MINUTE * tapMode.getBeats()) / (float) (tapInterval * unit.getBeats());
    }
}
