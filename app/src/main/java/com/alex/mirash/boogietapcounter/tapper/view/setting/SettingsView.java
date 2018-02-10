package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.Config;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public class SettingsView extends LinearLayout {
    private Spinner tapModeSpinner;
    private Spinner tempUnitSpinner;

    public SettingsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_settings, this);
        tapModeSpinner = findViewById(R.id.settings_tap_mode_spinner);
        tempUnitSpinner = findViewById(R.id.settings_unit_spinner);

        Settings settings = Settings.get();
        initSettingSpinner(tapModeSpinner, R.id.settings_tap_mode_label, settings.getTapMode().ordinal(),
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setTapMode(SettingTapMode.getValue(position));
                    }
                });
        initSettingSpinner(tempUnitSpinner, R.id.settings_unit_label, settings.getUnit().ordinal(),
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setUnit(SettingUnit.getValue(position));
                    }
                });
        initRefreshCheckbox(R.id.settings_refresh_time_label);
    }

    private void initSettingSpinner(final Spinner spinner, int labelViewId, int selectedPosition, AdapterView.OnItemSelectedListener listener) {
        spinner.setSelection(selectedPosition);
        spinner.setOnItemSelectedListener(listener);
        View labelView = findViewById(labelViewId);
        labelView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinner.onTouchEvent(event);
                return true;
            }
        });
    }

    private void initRefreshCheckbox(int labelId) {
        if (Config.IS_AUTO_REFRESH_OPTION_ENABLED) {
            final CheckBox refreshTimeCheckbox = findViewById(R.id.settings_refresh_time_checkbox);
            refreshTimeCheckbox.setChecked(Settings.get().getIsAutoRefresh());
            refreshTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Settings.get().setIsAutoRefresh(isChecked);
                }
            });
            View labelView = findViewById(labelId);
            labelView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshTimeCheckbox.setChecked(!refreshTimeCheckbox.isChecked());
                }
            });
        } else {
            findViewById(R.id.settings_refresh_time_item).setVisibility(GONE);
            findViewById(R.id.settings_refresh_time_delimiter).setVisibility(GONE);
        }
    }


    private abstract class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
