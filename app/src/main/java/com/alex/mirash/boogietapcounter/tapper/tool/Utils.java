package com.alex.mirash.boogietapcounter.tapper.tool;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.BuildConfig;
import com.alex.mirash.boogietapcounter.R;

import java.util.Random;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */

public final class Utils {

    public static float round(float value, int decimalPlaces) {
        double multiplier = Math.pow(10, decimalPlaces);
        return (float) (Math.round(value * multiplier) / multiplier);
    }

    public static String getAppVersion() {
        return "v" + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
    }

    public static void changeNavigationViewWidthIfNecessary(DrawerLayout drawerLayout, final View navigationView) {
        final int displayWidth = getDisplayDimensions().widthPixels;
        int drawerWidth = drawerLayout.getResources().getDimensionPixelSize(R.dimen.drawer_max_width);
        final int drawerMinSpace = drawerLayout.getResources().getDimensionPixelSize(R.dimen.drawer_min_space);
        Log.d(TAG, "display: " + displayWidth + "; drawer:" + drawerWidth + "; minSpace: " + drawerMinSpace);
        if (displayWidth - drawerWidth < drawerMinSpace) {
            drawerLayout.post(() -> {
                ViewGroup.LayoutParams lp = navigationView.getLayoutParams();
                int width = displayWidth - drawerMinSpace;
                lp.width = width;
                navigationView.requestLayout();
                Log.d(TAG, "drawer width updated to: " + width);
            });
        }
    }

    public static DisplayMetrics getDisplayDimensions() {
        WindowManager wm = (WindowManager) BoogieApp.getInstance().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static void initEasterEgg(ImageView imageView, View button) {
        button.setOnClickListener(v -> {
            int[] images = new int[]{R.drawable.boogie_couple, R.drawable.bezugly,
                    R.drawable.taras_1, R.drawable.taras_2, R.drawable.omg, R.drawable.mirash};
            imageView.setImageResource(images[new Random().nextInt(images.length)]);
        });
    }
}
