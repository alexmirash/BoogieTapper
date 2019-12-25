package com.alex.mirash.boogietapcounter.tapper.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.drawerlayout.widget.DrawerLayout;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.BuildConfig;
import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public final class Utils {
    public static float getTempInUnits(long tapInterval) {
        Settings settings = Settings.get();
        SettingUnit unit = settings.getUnit();
        SettingTapMode tapMode = settings.getTapMode();
        return (Const.MILLIS_IN_MINUTE * tapMode.getBeats()) / (float) (tapInterval * unit.getBeats());
    }

    public static float round(float value, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * multiplier) / multiplier);
    }

    public static String getAppVersion() {
        return BuildConfig.VERSION_CODE + "." + BuildConfig.VERSION_NAME;
    }

    public static void changeNavigationViewWidthIfNecessary(DrawerLayout drawerLayout, final View navigationView) {
        final int displayWidth = getDisplayDimensions().widthPixels;
        int drawerWidth = drawerLayout.getResources().getDimensionPixelSize(R.dimen.drawer_max_width);
        final int drawerMinSpace = drawerLayout.getResources().getDimensionPixelSize(R.dimen.drawer_min_space);
        Log.d("Screen", "display: " + displayWidth + "; drawer:" + drawerWidth + "; minSpace: " + drawerMinSpace);
        if (displayWidth - drawerWidth < drawerMinSpace) {
            drawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    ViewGroup.LayoutParams lp = navigationView.getLayoutParams();
                    int width = displayWidth - drawerMinSpace;
                    lp.width = width;
                    navigationView.requestLayout();
                    Log.d("Screen", "drawer width updated to: " + width);
                }
            });
        }
    }

    public static DisplayMetrics getDisplayDimensions() {
        WindowManager wm = (WindowManager) BoogieApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
