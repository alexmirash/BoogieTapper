package com.alex.mirash.boogietapcounter.mp3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingPlayNextMode;
import com.google.android.material.button.MaterialButton;

import static com.alex.mirash.boogietapcounter.tapper.tool.Const.TAG;

/**
 * @author Mirash
 */
public class PlayerView extends LinearLayout {
    private View prevButton;
    private View nextButton;
    private MaterialButton playPauseButton;
    private MaterialButton playNextModeButton;
    private TextView titleTextView;
    private TextView positionTextView;
    private TextView bpmTextView;
    private TextView id3v2TextView;
    private SeekBar seekBar;
    private TextView timeProgressTextView;
    private TextView durationTextView;
    private final PlayerUtils.TimeHolder timeHolder = new PlayerUtils.TimeHolder();

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(getContext(), R.layout.player_view, this);
        prevButton = findViewById(R.id.player_button_previous);
        nextButton = findViewById(R.id.player_button_next);
        playPauseButton = findViewById(R.id.player_button_play_pause);
        playNextModeButton = findViewById(R.id.player_button_play_next_mode);
        titleTextView = findViewById(R.id.player_song_name);
        titleTextView.setSelected(true);
        positionTextView = findViewById(R.id.player_song_position);
        bpmTextView = findViewById(R.id.player_song_bpm);
        id3v2TextView = findViewById(R.id.player_id3v2_version);
        seekBar = findViewById(R.id.player_progress_bar);
        timeProgressTextView = findViewById(R.id.player_song_progress_time);
        durationTextView = findViewById(R.id.player_song_duration);
        playNextModeButton.setOnClickListener(v -> Settings.get().changePlayNextMode());
        applyPlayNextMode(Settings.get().getPlayNextMode());
        Settings.get().addPlayNextModeObserver(this::applyPlayNextMode);
    }

    public void setPreviousButtonClickListener(OnClickListener onClickListener) {
        prevButton.setOnClickListener(onClickListener);
    }

    public void setNextButtonClickListener(OnClickListener onClickListener) {
        nextButton.setOnClickListener(onClickListener);
    }

    public void setPlayPauseButtonClickListener(OnClickListener onClickListener) {
        playPauseButton.setOnClickListener(onClickListener);
    }

    @SuppressLint("SetTextI18n")
    public void setSongInfo(@Nullable SongInfo songInfo) {
        if (songInfo == null) {
            setTitle("");
            positionTextView.setText("");
            durationTextView.setText("");
        } else {
            setTitle(songInfo.getTitle());
            positionTextView.setText((songInfo.getPosition() + 1) + "/" + songInfo.getTotalCount());
            durationTextView.setText(PlayerUtils.millisToTimeString(songInfo.getDuration()));
        }
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setSongBpm(int bpm) {
        bpmTextView.setText(bpm < 0 ? "" : "bpm: " + bpm);
    }

    public void setID3v2Version(String version) {
        if (version == null) {
            id3v2TextView.setText("");
        } else {
            id3v2TextView.setText("tag v" + version + "");
        }
    }

    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setPlaying(boolean isPlaying) {
        playPauseButton.setIconResource(isPlaying
                ? R.drawable.ic_pause_black_24dp
                : R.drawable.ic_play_arrow_black_24dp);
    }

    public void setProgress(float progress) {
        seekBar.setProgress(Math.round(seekBar.getMax() * progress));
    }

    public void setProgressTime(long time) {
        boolean changed = PlayerUtils.millisToTime(timeHolder, time);
        if (changed) {
            timeProgressTextView.setText(timeHolder.toString());
        }
    }

    public void applyPlayNextMode(SettingPlayNextMode playNextMode) {
        Log.d(TAG, "applyPlayNextMode: " + playNextMode);
        playNextMode.updateButton(playNextModeButton);
    }
}
