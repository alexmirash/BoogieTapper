package com.alex.mirash.boogietapcounter.tapper.tool;

/**
 * @author Mirash
 */

public interface EventsListener extends BpmStrategyListener {
    void onRefresh();

    void onIdle();
}
