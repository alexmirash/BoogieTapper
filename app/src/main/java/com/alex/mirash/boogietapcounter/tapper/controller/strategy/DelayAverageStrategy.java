package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.data.ExtendedStats;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

public class DelayAverageStrategy extends BpmCalculateStrategy {

    private long tapTime;
    private int intevalsCount;
    private float averageTapInterval;

    private long firstTapTime;

    @Override
    public void refresh() {
        super.refresh();
        tapTime = 0;
        intevalsCount = 0;
        averageTapInterval = 0;
        firstTapTime = 0;
    }

    @Override
    public void onTap() {
        long curTapTime = System.currentTimeMillis();
        if (tapTime == 0) {
            tapTime = curTapTime;
            firstTapTime = curTapTime;

            setIsMeasuring(true);
            notifyNewMeasurementStarted();
        } else {
            long tapInterval = curTapTime - tapTime;
            tapTime = curTapTime;
            averageTapInterval = (averageTapInterval * intevalsCount + tapInterval) / (intevalsCount + 1);

            data.setBpm(Settings.get().getTapMode().getBeats() * Const.MILLIS_IN_MINUTE / averageTapInterval);
            calcAdditionalStats(tapInterval);

            intevalsCount++;

            notifyBpmUpdated();
        }
    }

    private void calcAdditionalStats(long tapInterval) {
        //max, min tap intervals
        ExtendedStats details = data.getDetails();
        details.setAverageTapInterval(averageTapInterval);
        if (tapInterval < details.getMinTapInterval() || details.getMinTapInterval() == 0) {
            details.setMinTapInterval(tapInterval);
        }
        if (tapInterval > details.getMaxTapInterval() || details.getMaxTapInterval() == 0) {
            details.setMaxTapInterval(tapInterval);
        }

        //max, average dispersion
        if (averageTapInterval != 0) {
            float dispersion = Math.abs(averageTapInterval - tapInterval) / averageTapInterval;
            if (dispersion > details.getMaxDispersion() || details.getMaxDispersion() == 0) {
                details.setMaxDispersion(dispersion);
            }
            float averageDispersion = details.getAverageDispersion();
            if (averageDispersion == 0 && intevalsCount == 1) {
                details.setAverageDispersion(dispersion);
            } else {
                details.setAverageDispersion((averageDispersion * intevalsCount + dispersion) / (intevalsCount + 1));
            }
        }

        details.setIntervalsCount(intevalsCount + 1);
        details.setTotalTime(tapTime - firstTapTime);
     /*   if (averageTapInterval > 0 && intevalsCount > 0) {
            float dispersion;
            if (tapInterval > averageTapInterval) {
                dispersion = tapInterval / averageTapInterval;
            } else {
                diff = averageTapInterval / tapInterval;
            }
            Log.d("LOL", "diff = " + dispersion);
            if (dispersion > 1.25) {
                Log.d("LOL", "OMFG");
//                    refresh();
            }
        }*/
    }
}
