package com.alex.mirash.boogietapcounter;

import android.widget.Toast;

/**
 * @author Mirash
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String text) {
        showShortToast(text);
    }

    public static void showLongToast(String text) {
        showToast(text, Toast.LENGTH_LONG, true);
    }

    public static void showShortToast(String text) {
        showToast(text, Toast.LENGTH_SHORT, true);
    }

    public static Toast showToast(String message, int duration, boolean hidePrevious) {
        if (BoogieApp.isForeground()) {
            if (hidePrevious) {
                if (toast != null) {
                    toast.cancel();
                }
            }
            toast = Toast.makeText(BoogieApp.getInstance(), message, duration);
            toast.show();
            return toast;
        }
        return null;
    }
}
