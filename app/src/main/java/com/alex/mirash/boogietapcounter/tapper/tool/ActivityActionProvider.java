package com.alex.mirash.boogietapcounter.tapper.tool;

import android.view.Menu;
import android.view.View;

/**
 * @author Mirash
 */

public interface ActivityActionProvider {
    void onBack();

    View getRefreshButton();

    Menu getNavigationMenu();
}
