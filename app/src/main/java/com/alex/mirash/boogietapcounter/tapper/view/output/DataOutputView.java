package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;

/**
 * @author Mirash
 */

public class DataOutputView extends LinearLayout implements Highlightable {
    private OutputCellView tempView;
    private OutputCellView beatView;
    private OutputCellView tempIntervalView;
    private OutputCellView beatIntervalView;
    private View detailsView;

    private String emptyString;
    private String measuringStartedEmptyString;

    public DataOutputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_data_output, this);
        tempView = findViewById(R.id.data_output_temp);
        beatView = findViewById(R.id.data_output_bpm);
        detailsView = findViewById(R.id.data_output_details);
        tempIntervalView = detailsView.findViewById(R.id.data_output_interval_temp);
        beatIntervalView = detailsView.findViewById(R.id.data_output_interval_bpm);

        emptyString = getResources().getString(R.string.empty_value);
        measuringStartedEmptyString = getResources().getString(R.string.empty_measurement_started_value);

        tempView.setLabelText(getResources().getString(R.string.output_temp_label));
        beatView.setLabelText(getResources().getString(R.string.output_bpm_label));
        beatIntervalView.setLabelText(getResources().getString(R.string.output_beat_interval_label));
        tempIntervalView.setLabelText(getResources().getString(R.string.output_temp_interval_label));

        refresh();
        applyUnit(Settings.get().getUnit());
        applyShowDetails(Settings.get().isShowOutputDetails());
        if (!isInEditMode()) {
            Settings.get().addUnitObserver(this::applyUnit);
            Settings.get().addShowDetailsObserver(this::applyShowDetails);
        }
    }

    public void setData(@Nullable DataHolder data) {
        if (data != null) {
            setBeats(data.getBpm());
            setTemp(data.getTemp());
            setBeatsInterval(data.getBeatsInterval());
            setTempInterval(data.getTempUnitsInterval());
        }
    }

    public void refresh() {
        refresh(false);
    }

    public void refresh(boolean isMeasurementStarted) {
        setHighlighted(false);
        String emptyValue = isMeasurementStarted ? measuringStartedEmptyString : emptyString;
        tempView.setValueText(emptyValue);
        beatView.setValueText(emptyValue);
        beatIntervalView.setValueText(emptyValue);
        tempIntervalView.setValueText(emptyValue);
    }

    public void setTemp(float temp) {
        tempView.setValue(temp);
    }

    public void setBeats(float beats) {
        beatView.setValue(beats);
    }

    public void setBeatsInterval(float interval) {
        beatIntervalView.setValue(interval);
    }

    public void setTempInterval(float interval) {
        tempIntervalView.setValue(interval);
    }

    private void applyUnit(SettingUnit unit) {
        if (unit == SettingUnit.BEAT) {
            beatView.setVisibility(GONE);
            beatIntervalView.setVisibility(GONE);
        } else {
            beatView.setVisibility(VISIBLE);
            beatIntervalView.setVisibility(VISIBLE);
        }
    }

    private void applyShowDetails(Boolean showDetails) {
        detailsView.setVisibility(showDetails != null && showDetails ? VISIBLE : GONE);
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        tempView.setHighlighted(highlighted);
    }
}
