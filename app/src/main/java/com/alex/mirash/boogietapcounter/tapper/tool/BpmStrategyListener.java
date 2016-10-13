package com.alex.mirash.boogietapcounter.tapper.tool;

import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;

/**
 * @author Mirash
 */

public interface BpmStrategyListener extends BpmUpdateListener<DataHolder> {
    void onNewMeasurementStarted();
}
