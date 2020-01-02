package com.alex.mirash.boogietapcounter.mp3;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */
public class FileHelper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    public static File writeBpmTag(File baseMp3File, @Nullable Mp3File mp3file, int bpm) throws InvalidDataException, IOException, UnsupportedTagException, NotSupportedException {
        Log.d(TAG, "writeBpmTag: " + mp3file + ", " + bpm);
        if (baseMp3File == null) {
            ToastUtils.showToast("Fail: File is null");
            return null;
        }
        File folder = baseMp3File.getParentFile();
        if (folder == null) {
            ToastUtils.showToast("Fail: parent folder is null");
            return null;
        }
        File editFolder = new File(folder.getPath(), Const.APP_MODIFIED);
        if (!editFolder.exists()) {
            if (!editFolder.mkdirs()) {
                ToastUtils.showToast("Fail: could not create <" + Const.APP_MODIFIED + "> folder");
                return null;
            }
        }
        if (mp3file == null) {
            mp3file = new Mp3File(baseMp3File);
        }
        ID3v2 id3v2Tag = mp3file.getId3v2Tag();
        if (id3v2Tag == null) {
            id3v2Tag = Settings.get().getSettingID3v2Version().createId3v2Tag();
            id3v2Tag.setComment(Const.APP_MODIFIED);
            mp3file.setId3v2Tag(id3v2Tag);
        } else if (id3v2Tag instanceof ID3v22Tag) {
            id3v2Tag = fromTag(id3v2Tag);
            mp3file.setId3v2Tag(id3v2Tag);
        }
        id3v2Tag.setBPM(bpm);
        String saveFileName = Settings.get().isAddBpmToFileName() ? "(" + bpm + ") " + baseMp3File.getName() : baseMp3File.getName();
        File saveFile = new File(editFolder.getPath(), saveFileName);
        mp3file.save(saveFile.getPath());
        return saveFile;
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

    @Nullable
    public static String getID3v2Version(Mp3File mp3file) {
        if (mp3file != null) {
            ID3v2 id3v2Tag = mp3file.getId3v2Tag();
            if (id3v2Tag != null) {
                return id3v2Tag.getVersion();
            }
        }
        return null;
    }

    @NonNull
    public static ID3v2 fromTag(@NonNull ID3v2 from) {
        ID3v2 to = Settings.get().getSettingID3v2Version().createId3v2Tag();
        try {
            String str;
            to.setComment(from.getComment());
            to.setUnsynchronisation(from.hasUnsynchronisation());
            to.setBPM(from.getBPM());
            to.setAlbumArtist(from.getAlbumArtist());
            to.setAlbum(from.getAlbum());
            to.setAlbumImage(from.getAlbumImage(), from.getAlbumImageMimeType());
            to.setArtistUrl(from.getArtistUrl());
            to.setArtist(from.getArtist());
            to.setOriginalArtist(from.getOriginalArtist());
            to.setAudiofileUrl(from.getAudiofileUrl());
            to.setAudioSourceUrl(from.getAudioSourceUrl());
            to.setChapters(from.getChapters());
            to.setChapterTOC(from.getChapterTOC());
            to.setCommercialUrl(from.getCommercialUrl());
            to.setItunesComment(from.getItunesComment());
            to.setCompilation(from.isCompilation());
            to.setComposer(from.getComposer());
            to.setCopyright(from.getCopyright());
            to.setCopyrightUrl(from.getCopyrightUrl());
            to.setDate(from.getDate());
            to.setEncoder(from.getEncoder());
            to.setFooter(from.hasFooter());
            to.setGenre(from.getGenre());
            str = from.getGenreDescription();
            if (str != null && str.length() > 0) {
                to.setGenreDescription(str);
            }
            to.setGrouping(from.getGrouping());
            to.setKey(from.getKey());
            to.setLyrics(from.getLyrics());
            to.setYear(from.getYear());
            to.setTrack(from.getTrack());
            to.setTitle(from.getTitle());
            to.setWmpRating(from.getWmpRating());
            to.setUrl(from.getUrl());
            to.setRadiostationUrl(from.getRadiostationUrl());
            to.setPublisherUrl(from.getPublisherUrl());
            to.setPublisher(from.getPublisher());
            to.setPaymentUrl(from.getPaymentUrl());
            to.setPartOfSet(from.getPartOfSet());
            to.setPadding(from.getPadding());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "tag convert exception: " + e);
            ToastUtils.showLongToast("Tag version convert exception:\n" + e + "\nSome metadata may be lost");
        }
        return to;
    }
}
