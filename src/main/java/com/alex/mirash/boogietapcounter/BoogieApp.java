package com.alex.mirash.boogietapcounter;

import android.app.Application;
import android.util.Log;

import com.alex.mirash.boogietapcounter.settings.GlobalSettings;

/**
 * @author Mirash
 */

public class BoogieApp extends Application {
    private GlobalSettings settings;
    private static BoogieApp instance;

    @Override
    public void onCreate() {
        Log.d("LOL", "BoogieApp onCreate");
        super.onCreate();
        instance = this;
        settings = new GlobalSettings();
    }

    public static BoogieApp getInstance() {
        return instance;
    }

    public GlobalSettings getSettings() {
        return settings;
    }
}
