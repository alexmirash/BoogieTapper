package com.alex.mirash.boogietapcounter.tapper.data;

/**
 * @author Mirash
 */

public class ExtendedStats {
    private int intervalsCount;  //количество интервалов между тапами
    private float averageTapInterval;

    public int getIntervalsCount() {
        return intervalsCount;
    }

    public void setIntervalsCount(int tapsCount) {
        this.intervalsCount = tapsCount;
    }

    public void setAverageTapInterval(float averageTapInterval) {
        this.averageTapInterval = averageTapInterval;
    }

    public float getAverageTapInterval() {
        return averageTapInterval;
    }
}
