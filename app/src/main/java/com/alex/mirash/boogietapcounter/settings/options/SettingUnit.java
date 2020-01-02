package com.alex.mirash.boogietapcounter.settings.options;

/**
 * @author Mirash
 */

public enum SettingUnit {
    BEAT(1),
    TACT(4);

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
        return BEAT;
    }

    public float fromBeats(float bpm) {
        return bpm / beats;
    }
}
