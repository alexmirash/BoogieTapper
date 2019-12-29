package com.alex.mirash.boogietapcounter.settings.options;

import com.alex.mirash.boogietapcounter.R;

/**
 * @author Mirash
 */

public enum SettingTapMode {
    EVERY_BEAT(1) {
        @Override
        public int getTapButtonResId() {
            return R.string.tap_button_label_bottom_beat;
        }
    },
    EVERY_SECOND_BEAT(2) {
        @Override
        public int getTapButtonResId() {
            return R.string.tap_button_label_bottom_second_beat;
        }
    },
    EVERY_FOUR(4) {
        @Override
        public int getTapButtonResId() {
            return R.string.tap_button_label_bottom_four_beat;
        }
    },
    EVERY_EIGHT(8) {
        @Override
        public int getTapButtonResId() {
            return R.string.tap_button_label_bottom_eight_beat;
        }
    };

    SettingTapMode(int beats) {
        this.beats = beats;
    }

    public int getBeats() {
        return beats;
    }

    public abstract int getTapButtonResId();

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
