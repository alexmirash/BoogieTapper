package com.alex.mirash.boogietapcounter.mp3;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.alex.mirash.boogietapcounter.BoogieApp;
import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.ToastUtils;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingPlayNextMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingSaveMode;
import com.alex.mirash.boogietapcounter.settings.unit.UnitValue;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.SONG_PROGRESS_UPDATE_INTERVAL;

/**
 * @author Mirash
 */
public class Mp3PlayerControl {
    public static final String TAG = "mp3Control";

    private final PlayerView playerView;
    private MediaPlayer mediaPlayer;
    private List<File> files;
    private SparseArray<Mp3File> mp3Files;
    private int currentPosition = -1;
    private final Handler handler = new Handler();
    private Runnable updateTimeTask;
    private Mp3PlayerCallback callback;

    public Mp3PlayerControl(PlayerView playerView) {
        this.playerView = playerView;
        playerView.setPlayPauseButtonClickListener(v -> {
            if (mediaPlayer != null) {
                toggle();
            }
        });
        playerView.setNextButtonClickListener(v -> playNextFile());
        playerView.setPreviousButtonClickListener(v -> playPreviousFile());
        playerView.setSeekBarListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopUpdateSongProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                stopUpdateSongProgress();
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = PlayerUtils.getProgressTime(seekBar.getProgress() / (float) seekBar.getMax(), totalDuration);
                mediaPlayer.seekTo(currentPosition);
                startUpdateSongProgress();
            }
        });
    }

    public void setCallback(Mp3PlayerCallback callback) {
        this.callback = callback;
    }

    public void initialize(@NonNull List<File> filesList) {
        Log.d(TAG, "initialize: " + filesList.size());
        files = filesList;
        mp3Files = new SparseArray<>(filesList.size());
        currentPosition = 0;
        playFile(currentPosition);
        playerView.setVisibility(View.VISIBLE);
    }

    private void playFile(int position) {
        Log.d(TAG, "playFile " + position);
        if (position < 0 || position >= files.size()) {
            Log.e(TAG, "playFile incorrect position " + position);
            return;
        }
        File file = files.get(position);
        Mp3File mp3File = mp3Files.get(position);
        if (mp3File == null) {
            try {
                mp3File = new Mp3File(file);
                mp3Files.put(position, mp3File);
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                Log.e(TAG, "playFile new Mp3File(file) " + e);
            }
        }
        Uri audioFileUri = Uri.fromFile(file);
        if (audioFileUri != null) {
            String path = audioFileUri.getPath();
            if (path != null) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnCompletionListener(mp -> {
                        Log.d(TAG, "onComplete");
                        stopUpdateSongProgress();
                        playerView.setPlaying(false);
                        SettingPlayNextMode playNextMode = Settings.get().getPlayNextMode();
                        switch (playNextMode) {
                            case NEXT_FILE:
                                playNextFile();
                                break;
                            case REPEAT:
                                mediaPlayer.seekTo(0);
                                applyPlayerViewProgress();
                                start();
                                break;
                            case NONE:
                                mediaPlayer.seekTo(0);
                                applyPlayerViewProgress();
                                break;
                        }
                    });
                    mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                        Log.e(TAG, "onError: " + what + ", " + extra);
                        playerView.setPlaying(false);
                        return false;
                    });
                } else {
                    mediaPlayer.reset();
                }
                try {
                    mediaPlayer.setDataSource(playerView.getContext(), audioFileUri);
                } catch (IOException e) {
                    ToastUtils.showToast("Fail: playFile " + e);
                    return;
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    Log.e(TAG, "prepare failed: " + e);
                    ToastUtils.showToast("mediaPlayer.prepare() fail: " + e);
                    return;
                }
                start();
                playerView.setSongInfo(new SongInfo(file.getName(), position, files.size(), mediaPlayer.getDuration()));
                if (mp3File != null) {
                    updateSongTagInfo(mp3File);
                }
                if (callback != null) {
                    callback.onFilePlayStart();
                }
            }
        }
    }

    private void updateSongTagInfo(Mp3File mp3File) {
        playerView.setSongBpm(FileHelper.getBpm(mp3File));
        playerView.setID3v2Version(FileHelper.getID3v2Version(mp3File));
    }

    private void startUpdateSongProgress() {
        if (updateTimeTask == null) {
            updateTimeTask = new Runnable() {
                @Override
                public void run() {
                    applyPlayerViewProgress();
                    handler.postDelayed(this, SONG_PROGRESS_UPDATE_INTERVAL);
                }
            };
            handler.postDelayed(updateTimeTask, SONG_PROGRESS_UPDATE_INTERVAL);
        }
    }

    private void applyPlayerViewProgress() {
        long currentDuration = mediaPlayer.getCurrentPosition();
        float progress = PlayerUtils.getProgress(currentDuration, mediaPlayer.getDuration());
        playerView.setProgressTime(currentDuration);
        playerView.setProgress(progress);
    }

    private void stopUpdateSongProgress() {
        if (updateTimeTask != null) {
            handler.removeCallbacks(updateTimeTask);
            updateTimeTask = null;
        }
    }

    private void playNextFile() {
        if (currentPosition == files.size() - 1) {
            currentPosition = 0;
        } else {
            currentPosition++;
        }
        playFile(currentPosition);
    }

    private void playPreviousFile() {
        if (currentPosition == 0) {
            currentPosition = files.size() - 1;
        } else {
            currentPosition--;
        }
        playFile(currentPosition);
    }

    private void pause() {
        stopUpdateSongProgress();
        playerView.setPlaying(false);
        mediaPlayer.pause();
    }

    private void start() {
        startUpdateSongProgress();
        playerView.setPlaying(true);
        mediaPlayer.start();
    }

    private void toggle() {
        if (mediaPlayer.isPlaying()) {
            pause();
        } else {
            start();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveBpm(@NonNull UnitValue unitValue) {
        Log.d(TAG, "saveBpm: " + unitValue);
        SettingSaveMode saveMode = Settings.get().getSaveMode();
        File baseFile = files.get(currentPosition);
        File resultFile = saveMode.saveBpm(baseFile, mp3Files.get(currentPosition), unitValue);
        if (resultFile != null && resultFile.exists()) {
            if (saveMode == SettingSaveMode.REWRITE) {
                files.set(currentPosition, resultFile);
                Mp3File mp3File;
                try {
                    mp3File = new Mp3File(resultFile);
                } catch (IOException | UnsupportedTagException | InvalidDataException ignored) {
                    mp3File = null;
                }
                playerView.setTitle(resultFile.getName());
                if (mp3File != null) {
                    mp3Files.setValueAt(currentPosition, mp3File);
                    updateSongTagInfo(mp3File);
                }
            }
            ToastUtils.showLongToast(String.format(BoogieApp.getInstance().getString(R.string.save_success_message), resultFile.getPath()));
        } else {
            Log.e(TAG, "saveBpm failed");
            ToastUtils.showToast("BPM save failed");
        }
    }

    public void clear() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        stopUpdateSongProgress();
        callback = null;
    }
}
