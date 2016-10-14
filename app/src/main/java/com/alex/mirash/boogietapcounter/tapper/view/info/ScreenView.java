package com.alex.mirash.boogietapcounter.tapper.view.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.alex.mirash.boogietapcounter.R;
import com.alex.mirash.boogietapcounter.tapper.tool.ActivityActionProvider;

/**
 * @author Mirash
 */

public abstract class ScreenView extends FrameLayout {
    protected ActivityActionProvider actionProvider;

    public ScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setActionProvider(ActivityActionProvider actionProvider) {
        this.actionProvider = actionProvider;
    }

    public void show() {
        if (!isVisible()) {
            setVisibility(VISIBLE);
            startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.page_in));
            onShow();
        }
    }

    public void hide() {
        if (isVisible()) {
            setVisibility(INVISIBLE);
            startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.page_out));
            onHide();
        }
    }

    protected void onShow() {

    }

    protected void onHide() {

    }

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    protected abstract void init();
}
