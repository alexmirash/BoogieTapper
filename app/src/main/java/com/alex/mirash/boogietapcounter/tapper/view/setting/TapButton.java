package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.SettingChangeObserver;
import com.alex.mirash.boogietapcounter.settings.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public class TapButton extends Button implements SettingChangeObserver<SettingTapMode> {

    public TapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAllCaps(false);
        Settings.get().addTapModeObserver(this);
    }

    @Override
    public void onSettingChanged(SettingTapMode setting) {
        Log.d("WTF", "tap mode changed " + setting);

        String upText = getResources().getString(R.string.tap_button_label_main);
        int n = upText.length();
        String allText = upText + "\n\n" + getResources().getString(setting.getTapButtonResId());

        SpannableString buttonText = new SpannableString(allText);
        buttonText.setSpan(new RelativeSizeSpan(0.66f), n + 1, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE); // set size
        buttonText.setSpan(new ForegroundColorSpan(getResources().getColor(
                R.color.tap_button_text_color_bottom)), n + 1, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);// set color
        setText(buttonText);
    }
}
