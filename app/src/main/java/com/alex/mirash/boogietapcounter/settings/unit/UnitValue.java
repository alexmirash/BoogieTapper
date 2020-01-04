package com.alex.mirash.boogietapcounter.settings.unit;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;

/**
 * @author Mirash
 */
public abstract class UnitValue {
    protected float value;
    protected final SettingUnit valueUnit;

    public UnitValue(float value) {
        this.value = value;
        valueUnit = getValueUnit();
    }

    @NonNull
    protected abstract SettingUnit getValueUnit();

    public float getValue() {
        return value;
    }

    public int getRoundValue() {
        return Settings.get().getRoundMode().round(value);
    }

    public int getRoundValue(@NonNull SettingUnit unit) {
        return Settings.get().getRoundMode().round(getValue(unit));
    }

    public float getValue(@NonNull SettingUnit unit) {
        if (valueUnit == unit) {
            return value;
        }
        return (valueUnit.getBeats() * value) / unit.getBeats();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
