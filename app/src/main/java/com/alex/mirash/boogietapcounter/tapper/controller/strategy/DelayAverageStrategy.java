package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.data.ExtendedStats;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

public class DelayAverageStrategy extends BpmCalculateStrategy {

    private long tapTime;
    private int intervalsCount;
    private float averageTapInterval;

    @Override
    public void refresh() {
        super.refresh();
        tapTime = 0;
        intervalsCount = 0;
        averageTapInterval = 0;
    }

    @Override
    public void onTap() {
        long curTapTime = System.currentTimeMillis();
        if (tapTime == 0) {
            tapTime = curTapTime;

            setIsMeasuring(true);
            notifyNewMeasurementStarted();
        } else {
            long tapInterval = curTapTime - tapTime;
            tapTime = curTapTime;
            averageTapInterval = (averageTapInterval * intervalsCount + tapInterval) / (intervalsCount + 1);
            data.setBpm(Settings.get().getTapMode().getBeats() * Const.MILLIS_IN_MINUTE / averageTapInterval);

            ExtendedStats details = data.getDetails();
            details.setAverageTapInterval(averageTapInterval);
            intervalsCount++;

            details.setIntervalsCount(intervalsCount);

            notifyBpmUpdated();
        }
    }
}
