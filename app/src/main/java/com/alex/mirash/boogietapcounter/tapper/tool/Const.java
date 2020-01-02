package com.alex.mirash.boogietapcounter.tapper.tool;

import java.util.concurrent.TimeUnit;

/**
 * @author Mirash
 */

public final class Const {
    public static final String TAG = "LOL";
    public static final String KEY_TAP_MODE = "tap_mode";
    public static final String KEY_TEMP_UNIT = "unit";
    public static final String KEY_ROUND_MODE = "round_mode";
    public static final String KEY_AUTO_REFRESH = "auto_refresh";
    public static final String KEY_ADD_BPM_TO_FILENAME = "add_filename_bpm";
    public static final String KEY_LAST_FILE_PATH = "last_file_path";
    public static final String FOLDER_NAME = "TaРас_modified";
    public static final long SEC_IN_MINUTE = TimeUnit.MINUTES.toSeconds(1);
    public static final long MILLIS_IN_MINUTE = TimeUnit.MINUTES.toMillis(1);
    public static final long MILLIS_IN_HOUR = TimeUnit.HOURS.toMillis(1);
    public static boolean AUTO_REFRESH_DEFAULT_VALUE = true;
    public static boolean IS_ADD_BPM_TO_FILE_NAME_DEFAULT_VALUE = true;
    public static int AUTO_REFRESH_TIME = 2000;
    public static int SONG_PROGRESS_UPDATE_INTERVAL = 100;

}
