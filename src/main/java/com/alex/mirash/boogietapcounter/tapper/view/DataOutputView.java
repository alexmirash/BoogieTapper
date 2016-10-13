package com.alex.mirash.boogietapcounter.tapper.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;

/**
 * @author Mirash
 */

@SuppressLint("DefaultLocale")
public class DataOutputView extends LinearLayout {
    private TextView bpmTextView;
    private TextView boogieTempTextView;
    private TextView beatsIntervalTextView;

    public DataOutputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_data_output, this);
        bpmTextView = (TextView) findViewById(R.id.bpm_value);
        boogieTempTextView = (TextView) findViewById(R.id.boogie_temp_value);
        beatsIntervalTextView = (TextView) findViewById(R.id.beats_interval_value);
    }

    public void setData(DataHolder data) {
        setBeats(data.getBpm());
        setTemp(data.getTemp());
        setBeatsInterval(data.getBeatsInterval());
    }

    public void setTemp(float temp) {
        boogieTempTextView.setText(String.format("%.2f", temp));
    }

    public void setBeats(float beats) {
        bpmTextView.setText(String.format("%.2f", beats));
    }

    public void setBeatsInterval(float interval) {
        beatsIntervalTextView.setText(String.format("%.5f", interval));
    }

}
