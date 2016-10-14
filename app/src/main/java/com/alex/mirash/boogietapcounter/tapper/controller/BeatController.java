package com.alex.mirash.boogietapcounter.tapper.controller;

import android.util.Log;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.controller.delay.IdleHandler;
import com.alex.mirash.boogietapcounter.tapper.controller.strategy.BpmCalculateStrategy;
import com.alex.mirash.boogietapcounter.tapper.controller.strategy.DelayAverageStrategy;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.tool.BpmStrategyListener;
import com.alex.mirash.boogietapcounter.tapper.tool.EventsListener;
import com.alex.mirash.boogietapcounter.tapper.tool.TapControlListener;

/**
 * @author Mirash
 */

public class BeatController implements TapControlListener, BpmStrategyListener {
    private BpmCalculateStrategy strategy;
    private IdleHandler idleHandler;

    private EventsListener listener;

    private DataHolder resultDataHolder;

    public BeatController() {
        strategy = new DelayAverageStrategy();
        strategy.setBpmUpdateListener(this);

        idleHandler = new IdleHandler() {
            @Override
            protected void onIdle() {
                Log.d("LOL", "onMeasurementStopped");
                stopMeasurementInternal();
            }
        };
    }

    private void stopMeasurementInternal() {
        setResultDataHolder(strategy.getData());
        strategy.refresh();
        idleHandler.clear();
        if (listener != null) {
            listener.onMeasurementStopped(resultDataHolder);
        }
    }

    public void stopMeasurement() {
        if (strategy.isMeasuring()) {
            stopMeasurementInternal();
        }
    }

    public void refresh() {
        strategy.refresh();
        setResultDataHolder(strategy.getData());
        idleHandler.clear();
        if (listener != null) {
            listener.onRefresh();
        }
    }

    @Override
    public void onTap() {
        strategy.onTap();
        if (Settings.get().getIsAutoRefresh()) {
            idleHandler.updateHandler();
        }
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        Log.d("LOL", "on Bpm Update " + data.getDetails().getAverageTapInterval());
        if (Settings.get().getIsAutoRefresh()) {
            idleHandler.setIdleTime((int) data.getDetails().getAverageTapInterval() + Settings.AUTO_REFRESH_TIME);
        }
        if (listener != null) {
            listener.onBpmUpdate(data);
        }
    }

    @Override
    public void onNewMeasurementStarted() {
        setResultDataHolder(strategy.getData());
        if (listener != null) {
            listener.onNewMeasurementStarted();
        }
    }

    public void setListener(EventsListener eventsListener) {
        listener = eventsListener;
    }

    private void setResultDataHolder(DataHolder data) {
        resultDataHolder = data;
    }

    public DataHolder getData() {
        return resultDataHolder;
    }
}
