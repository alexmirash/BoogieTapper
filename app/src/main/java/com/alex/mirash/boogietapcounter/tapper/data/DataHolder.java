package com.alex.mirash.boogietapcounter.tapper.data;

import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

public class DataHolder {
    private float bpm;  //среднее значение бит в минуту

    private ExtendedStats details;

    public DataHolder() {
        details = new ExtendedStats();
    }

    public float getTemp() {
        return bpm / 4f;
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

    public ExtendedStats getDetails() {
        return details;
    }
}
