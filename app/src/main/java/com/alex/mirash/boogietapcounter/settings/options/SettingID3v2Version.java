package com.alex.mirash.boogietapcounter.settings.options;

import android.util.Log;

import androidx.annotation.NonNull;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.ID3v24Tag;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */

public enum SettingID3v2Version {
    ID3V23 {
        @NonNull
        @Override
        public ID3v2 createId3v2Tag() {
            Log.d(TAG, "create ID3v23Tag");
            return new ID3v23Tag();
        }
    },
    ID3V24 {
        @NonNull
        @Override
        public ID3v2 createId3v2Tag() {
            Log.d(TAG, "create ID3v24Tag");
            return new ID3v24Tag();
        }
    };

    public static SettingID3v2Version getValue(int position) {
        SettingID3v2Version[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingID3v2Version getDefaultValue() {
        return ID3V24;
    }

    @NonNull
    public abstract ID3v2 createId3v2Tag();
}
