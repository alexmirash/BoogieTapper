package com.alex.mirash.boogietapcounter.settings.options;

/**
 * @author Mirash
 */

public enum SettingRoundMode {
    ROUND {
        @Override
        public int round(float bpm) {
            return Math.round(bpm);
        }
    },
    CEIL {
        @Override
        public int round(float bpm) {
            return (int) Math.ceil(bpm);
        }
    },
    FLOOR {
        @Override
        public int round(float bpm) {
            return (int) Math.floor(bpm);
        }
    };

    public abstract int round(float bpm);

    public static SettingRoundMode getValue(int position) {
        SettingRoundMode[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingRoundMode getDefaultValue() {
        return ROUND;
    }
}
