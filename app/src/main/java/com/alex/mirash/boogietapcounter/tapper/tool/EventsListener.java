package com.alex.mirash.boogietapcounter.tapper.tool;

import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;

/**
 * @author Mirash
 */

public interface EventsListener extends BpmStrategyListener {
    void onRefresh();

    void onIdle(DataHolder resultData);
}
