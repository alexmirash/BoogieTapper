package com.alex.mirash.boogietapcounter.settings;

/**
 * @author Mirash
 */

public enum SettingUnit {
    BEAT(1),
    FOUR(4),
    EIGHT(8);

    SettingUnit(int beats) {
        this.beats = beats;
    }

    public int getBeats() {
        return beats;
    }

    private int beats;

    public static SettingUnit getValue(int position) {
        SettingUnit[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingUnit getDefaultValue() {
        return FOUR;
    }
}
