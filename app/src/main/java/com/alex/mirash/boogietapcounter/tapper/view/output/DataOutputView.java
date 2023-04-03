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
    private final OutputCellView tactView;
    private final OutputCellView beatView;
    private final OutputCellView tactIntervalView;
    private final OutputCellView beatIntervalView;
    private final View detailsView;

    private final String emptyString;
    private final String measuringStartedEmptyString;

    public DataOutputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_data_output, this);
        tactView = findViewById(R.id.data_output_tact);
        beatView = findViewById(R.id.data_output_bpm);
        detailsView = findViewById(R.id.data_output_details);
        tactIntervalView = detailsView.findViewById(R.id.data_output_interval_tact);
        beatIntervalView = detailsView.findViewById(R.id.data_output_interval_bpm);

        emptyString = getResources().getString(R.string.empty_value);
        measuringStartedEmptyString = getResources().getString(R.string.empty_measurement_started_value);

        tactView.setLabelText(getResources().getString(R.string.output_tact_label));
        beatView.setLabelText(getResources().getString(R.string.output_bpm_label));
        beatIntervalView.setLabelText(getResources().getString(R.string.output_beat_interval_label));
        tactIntervalView.setLabelText(getResources().getString(R.string.output_tact_interval_label));

        refresh();
        applyShowDetails(Settings.get().isShowOutputDetails());
        if (!isInEditMode()) {
            Settings.get().addShowDetailsObserver(this::applyShowDetails);
        }
    }

    public void setData(@Nullable DataHolder data) {
        if (data != null) {
            float beats = data.getBpm();
            setBeats(beats);
            setTact(SettingUnit.TACT.fromBeats(beats));
            float beatInterval = data.getBeatsInterval();
            setBeatsInterval(beatInterval);
            setTactInterval(SettingUnit.TACT.getBeats() * beatInterval);
        }
    }

    public void refresh() {
        refresh(false);
    }

    public void refresh(boolean isMeasurementStarted) {
        setHighlighted(false);
        String emptyValue = isMeasurementStarted ? measuringStartedEmptyString : emptyString;
        tactView.setValueText(emptyValue);
        beatView.setValueText(emptyValue);
        beatIntervalView.setValueText(emptyValue);
        tactIntervalView.setValueText(emptyValue);
    }

    public void setTact(float tact) {
        tactView.setValue(tact);
    }

    public void setBeats(float beats) {
        beatView.setValue(beats);
    }

    public void setBeatsInterval(float interval) {
        beatIntervalView.setValue(interval);
    }

    public void setTactInterval(float interval) {
        tactIntervalView.setValue(interval);
    }

    private void applyShowDetails(Boolean showDetails) {
        detailsView.setVisibility(showDetails != null && showDetails ? VISIBLE : GONE);
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        tactView.setHighlighted(highlighted);
    }
}
