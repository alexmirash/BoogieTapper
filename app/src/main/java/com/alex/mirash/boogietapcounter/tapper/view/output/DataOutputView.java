package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;

/**
 * @author Mirash
 */

@SuppressLint("DefaultLocale")
public class DataOutputView extends LinearLayout {
    private OutputCellView tempView;
    private OutputCellView bpmView;
    private OutputCellView tempIntervalView;
    private OutputCellView bpmIntervalView;

    private String emptyString;

    public DataOutputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_data_output, this);
        tempView = (OutputCellView) findViewById(R.id.data_output_temp);
        bpmView = (OutputCellView) findViewById(R.id.data_output_bpm);
        tempIntervalView = (OutputCellView) findViewById(R.id.data_output_interval_temp);
        bpmIntervalView = (OutputCellView) findViewById(R.id.data_output_interval_bpm);

        emptyString = getResources().getString(R.string.empty_value);

        tempView.setLabelText(getResources().getString(R.string.output_temp_label));
        bpmView.setLabelText(getResources().getString(R.string.output_bpm_label));
        bpmIntervalView.setLabelText(getResources().getString(R.string.output_beat_interval_label));
        tempIntervalView.setLabelText(getResources().getString(R.string.output_temp_interval_label));

        refresh();
    }

    public void setData(DataHolder data) {
        setBeats(data.getBpm());
        setTemp(data.getTemp());
        setBeatsInterval(data.getBeatsInterval());
        setTempInterval(data.getTempUnitsInterval());
    }

    public void refresh() {
        tempView.setValueText(emptyString);
        bpmView.setValueText(emptyString);
        bpmIntervalView.setValueText(emptyString);
        tempIntervalView.setValueText(emptyString);

        tempView.clearEffects();
    }

    public void setTemp(float temp) {
        tempView.setValue(temp);
    }

    public void setBeats(float beats) {
        bpmView.setValue(beats);
    }

    public void setBeatsInterval(float interval) {
        bpmIntervalView.setValue(interval);
    }

    public void setTempInterval(float interval) {
        tempIntervalView.setValue(interval);
    }

    //TODO
    public void highlight() {
        tempView.highlight();
    }

}
