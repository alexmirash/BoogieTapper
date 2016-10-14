package com.alex.mirash.boogietapcounter.tapper.view.info;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alex.mirash.boogietapcounter.R;

/**
 * @author Mirash
 */

public class InfoScreenView extends ScreenView implements View.OnClickListener {

    public InfoScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {
        inflate(getContext(), R.layout.screen_info, this);
        TextView textView = (TextView) findViewById(R.id.text_info);
        textView.setMovementMethod(new ScrollingMovementMethod());
        findViewById(R.id.info_screen_fab_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (actionProvider != null) {
            actionProvider.onBack();
        }
    }

    @Override
    protected void onShow() {
        if (actionProvider != null) {
            View refreshButton = actionProvider.getRefreshButton();
            if (refreshButton != null) {
                refreshButton.setVisibility(INVISIBLE);
                refreshButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_item_hide));
            }
            actionProvider.getNavigationMenu().findItem(R.id.nav_menu_info).setChecked(true);
        }
    }

    @Override
    protected void onHide() {
        if (actionProvider != null) {
            View refreshButton = actionProvider.getRefreshButton();
            if (refreshButton != null) {
                refreshButton.setVisibility(VISIBLE);
                refreshButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_item_show));
            }
            actionProvider.getNavigationMenu().findItem(R.id.nav_menu_info).setChecked(false);
        }
    }
}
