package com.alex.mirash.boogietapcounter.tapper.controller.delay;

import android.os.Handler;
import android.util.Log;

/**
 * @author Mirash
 */

public abstract class IdleHandler {
    private final Handler handler;
    private Runnable idleRunnable;

    private int idleTime;

    public IdleHandler() {
        handler = new Handler();
    }

    public void updateHandler() {
        if (idleTime == 0) return;
        Log.d("LOL", "updateHandler " + idleTime);
        if (idleRunnable != null) {
            handler.removeCallbacks(idleRunnable);
        }
        idleRunnable = new IdleRunnable();
        handler.postDelayed(idleRunnable, idleTime);
    }

    public void clear() {
        idleTime = 0;
        if (idleRunnable != null) {
            handler.removeCallbacks(idleRunnable);
            idleRunnable = null;
        }
    }

    protected abstract void onIdle();

    public void setIdleTime(int time) {
        idleTime = time;
    }

    private class IdleRunnable implements Runnable {
        @Override
        public void run() {
            onIdle();
            idleRunnable = null;
        }
    }
}
