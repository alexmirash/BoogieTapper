package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.BpmStrategyListener;
import com.alex.mirash.boogietapcounter.tapper.tool.TapControlListener;


/**
 * @author Mirash
 */

public abstract class BpmCalculateStrategy implements TapControlListener {
    protected DataHolder data = new DataHolder();
    protected BpmStrategyListener dataUpdateListener;

    private boolean isMeasuring;

    public void refresh() {
        data = new DataHolder();
        setIsMeasuring(false);
    }

    protected void setIsMeasuring(boolean measuring) {
        isMeasuring = measuring;
    }

    public boolean isMeasuring() {
        return isMeasuring;
    }

    public void setBpmUpdateListener(BpmStrategyListener listener) {
        dataUpdateListener = listener;
    }

    protected void notifyBpmUpdated() {
        if (dataUpdateListener != null) {
            dataUpdateListener.onBpmUpdate(data);
        }
    }

    protected void notifyNewMeasurementStarted() {
        if (dataUpdateListener != null) {
            dataUpdateListener.onNewMeasurementStarted();
        }
    }

    public DataHolder getData() {
        return data;
    }
}
