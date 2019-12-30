package com.alex.mirash.boogietapcounter.mp3;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.tapper.tool.Const;

public class PlayerUtils {

    public static boolean millisToTime(@NonNull TimeHolder timeHolder, long millis) {
        boolean changed = false;
        long hours = millis / Const.MILLIS_IN_HOUR;
        long minutes = (millis % Const.MILLIS_IN_HOUR) / Const.MILLIS_IN_MINUTE;
        long seconds = ((millis % Const.MILLIS_IN_HOUR) % Const.MILLIS_IN_MINUTE) / 1000;
        if (timeHolder.hours != hours) {
            changed = true;
            timeHolder.hours = hours;
        }
        if (timeHolder.minutes != minutes) {
            changed = true;
            timeHolder.minutes = minutes;
        }
        if (timeHolder.seconds != seconds) {
            changed = true;
            timeHolder.seconds = seconds;
        }
        return changed;
    }

    public static String millisToTimeString(long millis) {
        TimeHolder timeHolder = new TimeHolder();
        millisToTime(timeHolder, millis);
        return timeHolder.toString();
    }

    public static float getProgress(long currentDuration, long totalDuration) {
        if (totalDuration == 0) return 0;
        return currentDuration / (float) totalDuration;
    }

    public static int getProgressTime(float progress, long totalDuration) {
        return (int) (totalDuration * progress);
    }

    public static class TimeHolder {
        long hours;
        long minutes;
        long seconds;

        @NonNull
        @Override
        public String toString() {
            String timerString = "";
            String secondsString;
            // Add hours if there
            if (hours > 0) {
                timerString = hours + ":";
            }
            if (seconds < 10) {
                secondsString = "0" + seconds;
            } else {
                secondsString = "" + seconds;
            }
            return timerString + minutes + ":" + secondsString;
        }
    }
}