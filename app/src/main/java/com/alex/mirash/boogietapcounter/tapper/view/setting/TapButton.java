package com.alex.mirash.boogietapcounter.tapper.view.setting;

import android.content.Context;
import android.os.Build;
import androidx.appcompat.widget.AppCompatButton;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.settings.SettingChangeObserver;
import com.alex.mirash.boogietapcounter.settings.options.SettingTapMode;
import com.alex.mirash.boogietapcounter.settings.Settings;

/**
 * @author Mirash
 */

public class TapButton extends AppCompatButton implements SettingChangeObserver<SettingTapMode> {

    public TapButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setAllCaps(false);
        if (!isInEditMode()) {
            Settings.get().addTapModeObserver(this);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                setBackgroundResource(R.drawable.tap_button_selector);
            }
        }

    }

    @Override
    public void onSettingChanged(SettingTapMode setting) {
        String upText = getResources().getString(R.string.tap_button_label_main);
        String allText = upText + "\n\n" + getResources().getString(setting.getTapButtonResId());
        int spanStart = upText.length() + 1;

        SpannableString buttonText = new SpannableString(allText);
        buttonText.setSpan(new RelativeSizeSpan(0.66f), spanStart, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        buttonText.setSpan(new ForegroundColorSpan(getResources().getColor(
                R.color.tap_button_text_color_bottom)), spanStart, allText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        setText(buttonText);
    }
}
