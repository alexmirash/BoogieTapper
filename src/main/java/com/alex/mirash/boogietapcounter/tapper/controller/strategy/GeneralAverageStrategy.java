package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import com.alex.mirash.boogietapcounter.tapper.tool.Const;

/**
 * @author Mirash
 */

@Deprecated
public class GeneralAverageStrategy extends BpmCalculateStrategy {
    private long startTime;
    private int tapsCount;

    private boolean skip;

    @Override
    public void refresh() {
        super.refresh();
        startTime = 0;
        tapsCount = 0;
//        skip = false;
    }

    @Override
    public void onTap() {
        if (startTime == 0) {
     /*       if (!skip) {
                skip = true;
                return;
            }*/
            startTime = System.currentTimeMillis();
            tapsCount = 0;
        } else {
            tapsCount++;
            data.setBpm(tapsCount * (Const.MILLIS_IN_MINUTE / (float) (System.currentTimeMillis() - startTime)));
        }
    }
}
