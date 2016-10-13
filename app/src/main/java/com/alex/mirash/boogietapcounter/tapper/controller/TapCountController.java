package com.alex.mirash.boogietapcounter.tapper.controller;

import android.util.Log;
import android.widget.Toast;

import com.alex.mirash.boogietapcounter.BoogieApp;
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

public class TapCountController implements TapControlListener, BpmStrategyListener {
    private BpmCalculateStrategy strategy;
    private IdleHandler idleHandler;

    private EventsListener listener;

    public TapCountController() {
        strategy = new DelayAverageStrategy();
        strategy.setBpmUpdateListener(this);

        idleHandler = new IdleHandler() {
            @Override
            protected void onIdle() {
                Log.d("LOL", "onIdle");
                Toast.makeText(BoogieApp.getInstance(), "IDLEEEE", Toast.LENGTH_SHORT).show();
                strategy.refresh();
                idleHandler.clear();
                if (listener != null) {
                    listener.onIdle();
                }
            }
        };
    }

    public void refresh() {
        strategy.refresh();
        idleHandler.clear();
        if (listener != null) {
            listener.onRefresh();
        }
    }

    @Override
    public void onTap() {
        strategy.onTap();
        idleHandler.updateHandler();
    }

    @Override
    public void onBpmUpdate(DataHolder data) {
        Log.d("LOL", "on Bpm Update " + data.getDetails().getAverageTapInterval());
        idleHandler.setIdleTime((int) data.getDetails().getAverageTapInterval() + Settings.get().getAutoRefreshTime().getTime());
        if (listener != null) {
            listener.onBpmUpdate(data);
        }
    }

    @Override
    public void onNewMeasurementStarted() {
        if (listener != null) {
            listener.onNewMeasurementStarted();
        }
    }

    public void setListener(EventsListener eventsListener) {
        listener = eventsListener;
    }
}
