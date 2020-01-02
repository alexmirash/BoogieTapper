package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingID3v2Version;
import com.alex.mirash.boogietapcounter.settings.options.SettingRoundMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.options.SettingUnit;

/**
 * @author Mirash
 */

@SuppressLint("ClickableViewAccessibility")
public class SettingsView extends LinearLayout {

    public SettingsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.view_settings, this);

        Settings settings = Settings.get();
        initSettingSpinner(R.id.settings_tap_mode_item, R.id.settings_tap_mode_spinner,
                settings.getTapMode().ordinal(), new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setTapMode(SettingTapMode.getValue(position));
                    }
                });
        initSettingSpinner(R.id.settings_unit_item, R.id.settings_unit_spinner,
                settings.getUnit().ordinal(), new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setUnit(SettingUnit.getValue(position));
                    }
                });
        initSettingSpinner(R.id.settings_id3v2v_item, R.id.settings_id3v2v_spinner,
                settings.getSettingID3v2Version().ordinal(), new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setSettingID3v2Version(SettingID3v2Version.getValue(position));
                    }
                });
        initSettingSpinner(R.id.settings_round_mode_item, R.id.settings_round_mode_spinner,
                settings.getRoundMode().ordinal(), new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Settings.get().setRoundMode(SettingRoundMode.getValue(position));
                    }
                });
        initSettingCheckBox(R.id.settings_bpm_filename_item, R.id.settings_bpm_filename_checkbox,
                settings.isAddBpmToFileName(), (buttonView, isChecked) -> Settings.get().setAddBpmToFileName(isChecked));
        initSettingCheckBox(R.id.settings_show_details_item, R.id.settings_show_details_checkbox,
                settings.isShowOutputDetails(), (buttonView, isChecked) -> Settings.get().setShowOutputDetails(isChecked));
    }

    private void initSettingSpinner(int itemViewId, int spinnerId, int selectedPosition, AdapterView.OnItemSelectedListener listener) {
        View itemView = findViewById(itemViewId);
        Spinner spinner = itemView.findViewById(spinnerId);
        spinner.setSelection(selectedPosition);
        spinner.setOnItemSelectedListener(listener);
        itemView.setOnTouchListener((v, event) -> {
            spinner.onTouchEvent(event);
            return true;
        });
    }

    private void initSettingCheckBox(int itemViewId, int checkBoxId, boolean isChecked, CompoundButton.OnCheckedChangeListener listener) {
        View itemView = findViewById(itemViewId);
        CheckBox checkBox = itemView.findViewById(checkBoxId);
        checkBox.setChecked(isChecked);
        checkBox.setOnCheckedChangeListener(listener);
        itemView.setOnClickListener(v -> checkBox.setChecked(!checkBox.isChecked()));
    }

    private abstract class OnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
