package com.alex.mirash.boogietapcounter.settings;

/**
 * @author Mirash
 */

public enum SettingTapMode {
    EVERY_BEAT(1),
    EVERY_SECOND_BEAT(2),
    EVERY_FOUR(4),
    EVERY_EIGHT(8);

    SettingTapMode(int beats) {
        this.beats = beats;
    }

    public int getBeats() {
        return beats;
    }

    private int beats;

    public static SettingTapMode getValue(int position) {
        SettingTapMode[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingTapMode getDefaultValue() {
        return EVERY_BEAT;
    }
}
