package com.alex.mirash.boogietapcounter.settings.options;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.settings.unit.BeatValue;
import com.alex.mirash.boogietapcounter.settings.unit.TactValue;
import com.alex.mirash.boogietapcounter.settings.unit.UnitValue;

/**
 * @author Mirash
 */

public enum SettingUnit {
    BEAT(1) {
        @NonNull
        @Override
        public UnitValue getUnitValue(float bpm) {
            return new BeatValue(bpm);
        }
    },
    TACT(4) {
        @NonNull
        @Override
        public UnitValue getUnitValue(float bpm) {
            return new TactValue(fromBeats(bpm));
        }
    };

    SettingUnit(int beats) {
        this.beats = beats;
    }

    public int getBeats() {
        return beats;
    }

    private int beats;

    @NonNull
    public abstract UnitValue getUnitValue(float bpm);

    public float fromBeats(float bpm) {
        return bpm / beats;
    }

    @NonNull
    public static SettingUnit getValue(int position) {
        SettingUnit[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    @NonNull
    public static SettingUnit getDefaultValue() {
        return BEAT;
    }
}
