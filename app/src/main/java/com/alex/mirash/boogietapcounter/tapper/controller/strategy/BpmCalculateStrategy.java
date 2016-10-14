package com.alex.mirash.boogietapcounter.tapper.controller.strategy;

import android.annotation.SuppressLint;
import android.util.Log;

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

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return "{ " + String.format("%.2f", (data.getBpm() / 4f)) + "}";
    }

    protected void setIsMeasuring(boolean measuring) {
        Log.d("LOL", "isMeasuring = " + measuring);
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
