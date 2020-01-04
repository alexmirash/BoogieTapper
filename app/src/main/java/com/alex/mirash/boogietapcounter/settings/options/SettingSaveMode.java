package com.alex.mirash.boogietapcounter.settings.options;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.ToastUtils;
import com.alex.mirash.boogietapcounter.mp3.FileHelper;
import com.alex.mirash.boogietapcounter.mp3.ISaveStrategy;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */

public enum SettingSaveMode implements ISaveStrategy {
    COPY {
        @Override
        public File saveBpm(@NonNull File baseMp3File, @Nullable Mp3File mp3file, int bpm) {
            File folder = baseMp3File.getParentFile();
            if (folder == null) {
                ToastUtils.showToast("Fail: parent folder is null");
                return null;
            }
            folder = new File(folder.getPath(), Const.APP_MODIFIED);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    ToastUtils.showToast("Fail: could not create <" + Const.APP_MODIFIED + "> folder");
                    return null;
                }
            }
            try {
                mp3file = new Mp3File(baseMp3File);
            } catch (IOException | InvalidDataException | UnsupportedTagException e) {
                Log.e(TAG, "saveBpm Failed: " + e);
                ToastUtils.showToast("BPM save failed");
                return null;
            }
            FileHelper.addBpmToMp3File(mp3file, bpm);
            String saveFileName = Settings.get().isAddBpmToFileName() ? "(" + bpm + ") " + baseMp3File.getName() : baseMp3File.getName();
            File saveFile = new File(folder.getPath(), saveFileName);
            try {
                mp3file.save(saveFile.getPath());
            } catch (IOException | NotSupportedException e) {
                Log.e(TAG, "saveBpm Failed: " + e);
                ToastUtils.showToast("BPM save failed");
                return null;
            }
            return saveFile;
        }
    },
    REWRITE {
        @Override
        public File saveBpm(@NonNull File baseMp3File, @Nullable Mp3File mp3file, int bpm) {
            File folder = baseMp3File.getParentFile();
            if (folder == null) {
                ToastUtils.showToast("Fail: parent folder is null");
                return null;
            }
            if (mp3file == null) {
                try {
                    mp3file = new Mp3File(baseMp3File);
                } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                    Log.e(TAG, "saveBpm Failed: " + e);
                    ToastUtils.showToast("BPM save failed");
                    return null;
                }
            }
            FileHelper.addBpmToMp3File(mp3file, bpm);
            String saveFileName = "temp_" + baseMp3File.getName();
            File saveFile = new File(folder.getPath(), saveFileName);
            try {
                mp3file.save(saveFile.getPath());
            } catch (IOException | NotSupportedException e) {
                Log.e(TAG, "saveBpm Failed: " + e);
                ToastUtils.showToast("BPM save failed");
                return null;
            }
            String path = baseMp3File.getPath();
            boolean isMoved = saveFile.renameTo(baseMp3File);
            saveFile = new File(path);
            Log.d(TAG, "isMoved = " + isMoved + ": " + saveFile.getPath() + ", " + saveFile.exists());
            return saveFile;
        }
    };

    public static SettingSaveMode getValue(int position) {
        SettingSaveMode[] values = values();
        if (position >= 0 && position < values.length) {
            return values[position];
        } else return getDefaultValue();
    }

    public static SettingSaveMode getDefaultValue() {
        return COPY;
    }
}
