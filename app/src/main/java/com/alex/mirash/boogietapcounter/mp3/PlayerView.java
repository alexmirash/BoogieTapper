package com.alex.mirash.boogietapcounter.mp3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.R;
import com.google.android.material.button.MaterialButton;

/**
 * @author Mirash
 */
public class PlayerView extends LinearLayout {
    private View prevButton;
    private View nextButton;
    private MaterialButton playPauseButton;
    private TextView titleTextView;
    private TextView positionTextView;
    private TextView bpmTextView;
    private SeekBar seekBar;

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.player_view, this);
        prevButton = findViewById(R.id.player_button_previous);
        nextButton = findViewById(R.id.player_button_next);
        playPauseButton = findViewById(R.id.player_button_play_pause);
        titleTextView = findViewById(R.id.player_song_name);
        titleTextView.setSelected(true);
        positionTextView = findViewById(R.id.player_song_position);
        bpmTextView = findViewById(R.id.player_song_bpm);
        seekBar = findViewById(R.id.player_progress_bar);
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
            titleTextView.setText("");
            positionTextView.setText("");
            bpmTextView.setText("");
        } else {
            titleTextView.setText(songInfo.getTitle());
            positionTextView.setText((songInfo.getPosition() + 1) + "/" + songInfo.getTotalCount());
            int bpm = songInfo.getBpm();
            bpmTextView.setText(bpm < 0 ? "" : String.valueOf(bpm));
        }
    }

    public void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setPlaying(boolean isPlaying) {
        playPauseButton.setIconResource(isPlaying
                ? R.drawable.ic_pause_circle_outline_black_24dp
                : R.drawable.ic_play_circle_outline_black_24dp);
    }

    public void setProgress(float progress) {
        seekBar.setProgress((int) (seekBar.getMax() * progress));
    }
}
