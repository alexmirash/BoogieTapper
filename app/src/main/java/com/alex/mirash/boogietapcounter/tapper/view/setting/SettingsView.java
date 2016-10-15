package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.SettingUnit;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public class SettingsView extends LinearLayout {
    private Spinner tapModeSpinner;
    private Spinner tempUnitSpinner;
    private CheckBox refreshTimeCheckbox;

    public SettingsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_settings, this);
        tapModeSpinner = (Spinner) findViewById(R.id.settings_tap_mode_spinner);
        tempUnitSpinner = (Spinner) findViewById(R.id.settings_unit_spinner);
        refreshTimeCheckbox = (CheckBox) findViewById(R.id.settings_refresh_time_checkbox);

        Settings settings = Settings.get();
        initSettingSpinner(tapModeSpinner, R.id.settings_tap_mode_label, settings.getTapMode().ordinal(),
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setTapMode(SettingTapMode.getValue(position));
                        Log.d("LOL", "tap mode selected: " + Settings.get().getTapMode());
                    }
                });
        initSettingSpinner(tempUnitSpinner, R.id.settings_unit_label, settings.getUnit().ordinal(),
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setUnit(SettingUnit.getValue(position));
                        Log.d("LOL", "unit selected: " + Settings.get().getUnit());
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
        refreshTimeCheckbox.setChecked(Settings.get().getIsAutoRefresh());
        refreshTimeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("LOL", "onCheckedChanged " + isChecked);
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
    }


    private abstract class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
