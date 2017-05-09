package com.alex.mirash.boogietapcounter;

import android.app.Application;

import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.tool.PreferencesManager;

/**
 * @author Mirash
 */

public class BoogieApp extends Application {
    private Settings settings;
    private PreferencesManager preferences;
    private static BoogieApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        preferences = new PreferencesManager(this);
        settings = new Settings();
    }

    public static BoogieApp getInstance() {
        return instance;
    }

    public Settings getSettings() {
        return settings;
    }

    public PreferencesManager getPreferences() {
        return preferences;
    }
}
