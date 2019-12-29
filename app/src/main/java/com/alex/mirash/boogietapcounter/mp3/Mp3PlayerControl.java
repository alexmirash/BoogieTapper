package com.alex.mirash.boogietapcounter.mp3;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;

import com.alex.mirash.boogietapcounter.ToastUtils;
import com.alex.mirash.boogietapcounter.mp3agic.InvalidDataException;
import com.alex.mirash.boogietapcounter.mp3agic.Mp3File;
import com.alex.mirash.boogietapcounter.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Mirash
 */
public class Mp3PlayerControl {
    public static final String TAG = "mp3Control";

    private PlayerView playerView;
    private MediaPlayer mediaPlayer;
    private List<File> files;
    private SparseArray<Mp3File> mp3Files;
    private int currentPosition = -1;
    private final Handler handler = new Handler();

    public void updateSongProgressBar() {
        handler.postDelayed(updateTimeTask, 100);
    }

    private final Runnable updateTimeTask = new Runnable() {
        @Override
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();
            // Displaying Total Duration time
//            songTotalDurationLabel.setText(""+utils.milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
//            songCurrentDurationLabel.setText(""+utils.milliSecondsToTimer(currentDuration));

            // Updating progress bar
            float progress = PlayerUtils.getProgressPercentage(currentDuration, totalDuration);
            //Log.d("Progress", ""+progress);
            playerView.setProgress(progress);
            handler.postDelayed(this, 100);
        }
    };

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
                handler.removeCallbacks(updateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateTimeTask);
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = PlayerUtils.progressToTimer(seekBar.getProgress() / (float) seekBar.getMax(), totalDuration);
                mediaPlayer.seekTo(currentPosition);
                updateSongProgressBar();
            }
        });
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
                } else {
                    mediaPlayer.reset();
                }
                try {
                    mediaPlayer.setDataSource(playerView.getContext(), audioFileUri);
                } catch (IOException e) {
                    ToastUtils.showToast("Fail: playFile " + e);
                    return;
                }
                mediaPlayer.setOnPreparedListener(mp -> start());
                mediaPlayer.setOnCompletionListener(mp -> {
                    handler.removeCallbacks(updateTimeTask);
                    playNextFile();
                });
                mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                    playerView.setPlaying(false);
                    return false;
                });
                mediaPlayer.prepareAsync();
                playerView.setSongInfo(new SongInfo(file.getName(), position, files.size(), FileHelper.getBpm(mp3File)));
            }
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

    public void seekTo(int millis) {
        mediaPlayer.seekTo(millis);
    }

    private void pause() {
        handler.removeCallbacks(updateTimeTask);
        playerView.setPlaying(false);
        mediaPlayer.pause();
    }

    private void start() {
        updateSongProgressBar();
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

    public void clear() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateTimeTask);
    }
}
