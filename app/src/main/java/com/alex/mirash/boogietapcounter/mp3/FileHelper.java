package com.alex.mirash.boogietapcounter.mp3;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alex.mirash.boogietapcounter.ToastUtils;
import com.alex.mirash.boogietapcounter.mp3agic.ID3v2;
import com.alex.mirash.boogietapcounter.mp3agic.ID3v22Tag;
import com.alex.mirash.boogietapcounter.mp3agic.InvalidDataException;
import com.alex.mirash.boogietapcounter.mp3agic.Mp3File;
import com.alex.mirash.boogietapcounter.mp3agic.NotSupportedException;
import com.alex.mirash.boogietapcounter.mp3agic.UnsupportedTagException;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.tapper.tool.Const;

import java.io.File;
import java.io.IOException;

/**
 * @author Mirash
 */
public class FileHelper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void writeBpmTag(File baseMp3File, int bpm) throws InvalidDataException, IOException, UnsupportedTagException {
        if (baseMp3File == null) {
            ToastUtils.showToast("Fail: File is null");
            return;
        }
        File folder = baseMp3File.getParentFile();
        if (folder == null) {
            ToastUtils.showToast("Fail: parent folder is null");
            return;
        }
        File editFolder = new File(folder.getPath(), Const.FODLER_NAME);
        if (!editFolder.exists()) {
            if (!editFolder.mkdirs()) {
                ToastUtils.showToast("Fail: could not create <" + Const.FODLER_NAME + "> folder");
                return;
            }
        }
        Mp3File mp3file = new Mp3File(baseMp3File);
        ID3v2 id3v2 = mp3file.getId3v2Tag();
        if (id3v2 == null) {
            id3v2 = new ID3v22Tag();
            mp3file.setId3v2Tag(id3v2);
        }
        id3v2.setBPM(bpm);
        String saveFileName = Settings.get().isAddBpmToFileName() ? "(" + bpm + ") " + baseMp3File.getName() : baseMp3File.getName();
        File saveFile = new File(editFolder.getPath(), saveFileName);
        try {
            mp3file.save(saveFile.getPath());
        } catch (NotSupportedException e) {
            ToastUtils.showLongToast("Fail save: " + e.getDetailedMessage());
        }
    }

    public static int getBpm(Mp3File mp3file) {
        if (mp3file != null) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            if (id3v2Tag != null) {
                return id3v2Tag.getBPM();
            }
        }
        return -1;
    }
}
