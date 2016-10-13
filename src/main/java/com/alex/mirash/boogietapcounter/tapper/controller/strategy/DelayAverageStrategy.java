package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import com.alex.mirash.boogietapcounter.settings.GlobalSettings;
import com.alex.mirash.boogietapcounter.tapper.data.ExtendedStats;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

public class DelayAverageStrategy extends BpmCalculateStrategy {

    private long tapTime;
    private int tapCount;
    private float averageTapInterval;

    private long firstTapTime;


    @Override
    public void refresh() {
        super.refresh();
        tapTime = 0;
        tapCount = 0;
        averageTapInterval = 0;
        firstTapTime = 0;

        notifyDataUpdated();
    }

    @Override
    public void onTap() {
        long curTapTime = System.currentTimeMillis();
        if (tapTime == 0) {
            tapTime = curTapTime;
            firstTapTime = curTapTime;
        } else {
            long tapInterval = curTapTime - tapTime;
            tapTime = curTapTime;
            averageTapInterval = (averageTapInterval * tapCount + tapInterval) / (tapCount + 1);

            data.setBpm(GlobalSettings.get().getTapMode().getBeats() * Const.MILLIS_IN_MINUTE / averageTapInterval);
            calcAdditionalStats(tapInterval);

            tapCount++;

            notifyDataUpdated();
        }
    }

    private void calcAdditionalStats(long tapInterval) {
        //max, min tap intervals
        ExtendedStats details = data.getDetails();
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
            if (averageDispersion == 0 && tapCount == 1) {
                details.setAverageDispersion(dispersion);
            } else {
                details.setAverageDispersion((averageDispersion * tapCount + dispersion) / (tapCount + 1));
            }
        }

        details.setTapsCount(tapCount);
        details.setTotalTime(tapTime - firstTapTime);
     /*   if (averageTapInterval > 0 && tapCount > 0) {
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
