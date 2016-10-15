package com.alex.mirash.boogietapcounter.pashalka;

import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * @author Mirash
 */

public class PashalkaHandler {
    private View bezuglyiView;
    private ViewStub bezuglyiStub;
    private int bezuglyiCounter;

    public PashalkaHandler(View hbkView, ViewStub bezuglyiView) {
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
        bezuglyiView.startAnimation(runAnimation);
        bezuglyiView.setVisibility(View.INVISIBLE);
    }
}
