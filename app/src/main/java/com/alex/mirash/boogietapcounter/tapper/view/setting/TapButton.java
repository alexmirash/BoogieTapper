package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.Settings;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.google.android.material.button.MaterialButton;

/**
 * @author Mirash
 */

public class TapButton extends MaterialButton {

    public TapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAllCaps(false);
        applyTapMode(Settings.get().getTapMode());
        if (!isInEditMode()) {
            Settings.get().addTapModeObserver(this::applyTapMode);
        }
    }

    private void applyTapMode(SettingTapMode tapMode) {
        String upText = getResources().getString(R.string.tap_button_label_main);
        String allText = upText + "\n\n" + getResources().getString(tapMode.getTapButtonResId());
        int spanStart = upText.length() + 1;

        SpannableString buttonText = new SpannableString(allText);
        buttonText.setSpan(new RelativeSizeSpan(0.66f), spanStart, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        buttonText.setSpan(new ForegroundColorSpan(getResources().getColor(
                R.color.tap_button_text_color_bottom)), spanStart, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(buttonText);
    }
}
