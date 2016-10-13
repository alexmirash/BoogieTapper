package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import android.annotation.SuppressLint;

import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.BpmStrategyListener;
import com.alex.mirash.boogietapcounter.tapper.tool.TapControlListener;


/**
 * @author Mirash
 */

public abstract class BpmCalculateStrategy implements TapControlListener {
    protected DataHolder data = new DataHolder();
    protected BpmStrategyListener dataUpdateListener;

    public void refresh() {
        data = new DataHolder();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return "{ " + String.format("%.2f", (data.getBpm() / 4f)) + "}";
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
}
