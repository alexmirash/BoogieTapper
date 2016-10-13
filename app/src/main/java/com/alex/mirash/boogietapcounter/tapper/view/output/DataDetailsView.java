package com.alex.mirash.boogietapcounter.tapper.view.output;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.data.DataHolder;
import com.alex.mirash.boogietapcounter.tapper.data.ExtendedStats;

/**
 * @author Mirash
 */

@SuppressLint("DefaultLocale")
public class DataDetailsView extends LinearLayout {
    private TextView maxDispersionValueView;
    private TextView averageDispersionValueView;
    private TextView tapIntervalValueView;
    private TextView tempIntervalValuesView;
    private TextView totalTimeValueView;


    public DataDetailsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.view_data_details_output, this);
        totalTimeValueView = (TextView) findViewById(R.id.details_total_time);
        tapIntervalValueView = (TextView) findViewById(R.id.details_tap_interval);
        tempIntervalValuesView = (TextView) findViewById(R.id.details_temp_interval);
        maxDispersionValueView = (TextView) findViewById(R.id.details_max_dispersion);
        averageDispersionValueView = (TextView) findViewById(R.id.details_average_dispersion);
    }

    public void setData(DataHolder data) {
        ExtendedStats details = data.getDetails();
        setTotalTime(details.getTotalTime() / 1000f);
        setTemp(details.getMinTemp(), details.getMaxTemp());
        setTapInterval(details.getMinTapInterval(), details.getMaxTapInterval());
        setAverageDispersion(details.getAverageDispersion());
        setMaxDispersion(details.getMaxDispersion());
    }

    public void setTotalTime(float totalTime) {
        totalTimeValueView.setText(String.format("%.2f", totalTime));
    }

    public void setTemp(float tempMin, float tempMax) {
        tempIntervalValuesView.setText(Math.round(tempMin) + " - " + Math.round(tempMax));
    }

    public void setTapInterval(float tapMin, float tapMax) {
        tapIntervalValueView.setText(String.format("%.2f", tapMin) + " - " + String.format("%.2f", tapMax));
    }

    public void setAverageDispersion(float value) {
        averageDispersionValueView.setText(String.format("%.2f", value * 100) + "%");
    }

    public void setMaxDispersion(float value) {
        maxDispersionValueView.setText(String.format("%.2f", value * 100) + "%");
    }

}
