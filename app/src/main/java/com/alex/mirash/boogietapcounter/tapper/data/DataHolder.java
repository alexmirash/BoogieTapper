package com.alex.mirash.boogietapcounter.tapper.data;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

public class DataHolder {
    private float bpm;  //среднее значение бит в минуту
    private final ExtendedStats details;

    public DataHolder() {
        details = new ExtendedStats();
    }

    public float getTemp() {
        return bpm / Settings.get().getUnit().getBeats();
    }

    public float getBpm() {
        return bpm;
    }

    public float getBeatsInterval() {
        return Const.SEC_IN_MINUTE / bpm;
    }

    public void setBpm(float bpmValue) {
        bpm = bpmValue;
    }

    @NonNull
    public ExtendedStats getDetails() {
        return details;
    }

    public float getTempUnitsInterval() {
        return getBeatsInterval() * Settings.get().getUnit().getBeats();
    }
}
