package com.alex.mirash.boogietapcounter.settings;

/**
 * @author Mirash
 */

public enum SettingAutoRefreshTime {
    SEC_2(2000),
    SEC_3(3000),
    SEC_4(4000),
    SEC_5(5000),
    NEVER(-1);

    SettingAutoRefreshTime(int timeInMillis) {
        this.time = timeInMillis;
    }

    public int getTime() {
        return time;
    }

    private int time;

    public static SettingAutoRefreshTime getValue(int position) {
        SettingAutoRefreshTime[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultRefreshTime();
    }

    public static SettingAutoRefreshTime getDefaultRefreshTime() {
        return SEC_2;
    }
}
