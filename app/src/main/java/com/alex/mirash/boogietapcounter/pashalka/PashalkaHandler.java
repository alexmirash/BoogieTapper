package com.alex.mirash.boogietapcounter.pashalka;

import android.view.View;
import android.view.ViewStub;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author Mirash
 */

public class PashalkaHandler {
    private View hideImageView;
    private View bezuglyiView;
    private ViewStub bezuglyiStub;
    private int bezuglyiCounter;

    public PashalkaHandler(View imageView, View hbkView, ViewStub bezuglyiView) {
        hideImageView = imageView;
        bezuglyiStub = bezuglyiView;
        hbkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bezuglyiCounter == 9) {
                    bezuglyiCounter = 0;
                    itsBezuglyiTime();
                } else {
                    bezuglyiCounter++;
                }
            }
        });
    }

    private void itsBezuglyiTime() {
        if (bezuglyiView == null) {
            bezuglyiView = bezuglyiStub.inflate();
        } else {
            bezuglyiView.setVisibility(View.VISIBLE);
        }
        AnimationSet runAnimation = new AnimationSet(true);
        Animation translate = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_PARENT, 1,
                0, 0, 0, 0);
        runAnimation.setDuration(3500);
        runAnimation.addAnimation(translate);
        runAnimation.setInterpolator(new DecelerateInterpolator());
        runAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                hideImageView.setVisibility(View.INVISIBLE);
                Animation hide = new AlphaAnimation(1, 0);
                hide.setDuration(250);
                hide.setFillAfter(true);
                hideImageView.startAnimation(hide);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                hideImageView.setVisibility(View.VISIBLE);
                Animation show = new AlphaAnimation(0, 1);
                show.setDuration(250);
                hideImageView.startAnimation(show);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        bezuglyiView.startAnimation(runAnimation);
        bezuglyiView.setVisibility(View.INVISIBLE);
    }
}
