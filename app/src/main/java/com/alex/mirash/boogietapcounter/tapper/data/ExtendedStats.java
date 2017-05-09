package com.alex.mirash.boogietapcounter.tapper.data;

/**
 * @author Mirash
 */

public class ExtendedStats {
    //in millis
    private long minTapInterval;
    private long maxTapInterval;

    private int intervalsCount;  //количество интервалов между тапами
    private float averageTapInterval;

    public long getMinTapInterval() {
        return minTapInterval;
    }

    public void setMinTapInterval(long minTapInterval) {
        this.minTapInterval = minTapInterval;
    }

    public long getMaxTapInterval() {
        return maxTapInterval;
    }

    public void setMaxTapInterval(long maxTapInterval) {
        this.maxTapInterval = maxTapInterval;
    }

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
