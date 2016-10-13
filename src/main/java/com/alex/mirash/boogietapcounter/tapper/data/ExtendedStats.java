package com.alex.mirash.boogietapcounter.tapper.data;

import com.alex.mirash.boogietapcounter.tapper.tool.ConvertUtils;

/**
 * @author Mirash
 */

public class ExtendedStats {
    //in millis
    private long minTapInterval;
    private long maxTapInterval;

    private float averageDispersion;   //среднее отклонение
    private float maxDispersion;   //максимальное отклонение от среднего значения темпа

    private int tapsCount;  //количество тапов
    private long totalTime; //время от первого до последнего тапа

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

    public int getTapsCount() {
        return tapsCount;
    }

    public void setTapsCount(int tapsCount) {
        this.tapsCount = tapsCount;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public float getMinTemp() {
        return ConvertUtils.getTempInUnits(maxTapInterval);
    }

    public float getMaxTemp() {
        return ConvertUtils.getTempInUnits(minTapInterval);
    }
}
