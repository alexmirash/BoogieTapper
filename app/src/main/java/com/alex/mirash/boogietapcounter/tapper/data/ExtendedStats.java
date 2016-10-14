package com.alex.mirash.boogietapcounter.tapper.data;

import com.alex.mirash.boogietapcounter.tapper.tool.Utils;

/**
 * @author Mirash
 */

public class ExtendedStats {
    //in millis
    private long minTapInterval;
    private long maxTapInterval;

    private float averageDispersion;   //среднее отклонение
    private float maxDispersion;   //максимальное отклонение от среднего значения темпа

    private int intervalsCount;  //количество интервалов между тапами
    private long totalTime; //время от первого до последнего тапа
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

    public float getAverageDispersion() {
        return averageDispersion;
    }

    public void setAverageDispersion(float averageDispersion) {
        this.averageDispersion = averageDispersion;
    }

    public float getMaxDispersion() {
        return maxDispersion;
    }

    public void setMaxDispersion(float maxDispersion) {
        this.maxDispersion = maxDispersion;
    }

    public int getIntervalsCount() {
        return intervalsCount;
    }

    public void setIntervalsCount(int tapsCount) {
        this.intervalsCount = tapsCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public float getMinTemp() {
        return Utils.getTempInUnits(maxTapInterval);
    }

    public float getMaxTemp() {
        return Utils.getTempInUnits(minTapInterval);
    }

    public void setAverageTapInterval(float averageTapInterval) {
        this.averageTapInterval = averageTapInterval;
    }

    public float getAverageTapInterval() {
        return averageTapInterval;
    }
}
