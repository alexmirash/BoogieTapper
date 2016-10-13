package com.alex.mirash.boogietapcounter.tapper.controller;

import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.controller.strategy.BpmCalculateStrategy;
import com.alex.mirash.boogietapcounter.tapper.controller.strategy.DelayAverageStrategy;
import com.alex.mirash.boogietapcounter.tapper.tool.BpmUpdateListener;
import com.alex.mirash.boogietapcounter.tapper.tool.TapControlListener;

/**
 * @author Mirash
 */

public class TapCountController extends BpmUpdateObserverable<DataHolder>
        implements TapControlListener, BpmUpdateListener<DataHolder> {
    private BpmCalculateStrategy strategy;

    public TapCountController() {
        strategy = new DelayAverageStrategy();
        strategy.setBpmUpdateListener(this);
    }

    public void refresh() {
        strategy.refresh();
    }

    @Override
    public void onTap() {
        strategy.onTap();
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        notifyObservers(data);
    }
}
