package com.alex.mirash.boogietapcounter.tapper.view;


import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageButton;

/**
 * @author Mirash
 */
public class AlphaImageButton extends AppCompatImageButton {
    public AlphaImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled ? 1 : 0.25f);
    }
}
