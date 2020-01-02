package com.alex.mirash.boogietapcounter.tapper.view;


import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alex.mirash.boogietapcounter.R;

/**
 * @author Mirash
 */
public class SaveButton extends LinearLayout {
    private TextView textView;

    public SaveButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SaveButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        TypedValue outValue = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, outValue, true);
        } else {
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        }
        setBackgroundResource(outValue.resourceId);
        inflate(getContext(), R.layout.save_button_view, this);
        textView = findViewById(R.id.save_button_text);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textView.setVisibility(enabled ? VISIBLE : GONE);
        setAlpha(enabled ? 1 : 0.25f);
    }
}
