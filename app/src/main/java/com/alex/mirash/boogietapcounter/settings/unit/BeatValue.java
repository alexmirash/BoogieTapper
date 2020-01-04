package com.alex.mirash.boogietapcounter.settings.unit;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;

/**
 * @author Mirash
 */
public class BeatValue extends UnitValue {

    public BeatValue(float value) {
        super(value);
    }

    @NonNull
    @Override
    protected SettingUnit getValueUnit() {
        return SettingUnit.BEAT;
    }
}
