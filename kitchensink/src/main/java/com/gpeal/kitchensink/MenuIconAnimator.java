package com.gpeal.kitchensink;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.widget.ImageView;

public class MenuIconAnimator extends ValueAnimator {
    private static final String TAG = MenuIconAnimator.class.getSimpleName();

    private boolean mAppear;
    private final double mRadius;
    private final double mDirection;
    private final int mDuration;
    private final ImageView mTarget;

    public MenuIconAnimator(ImageView target, double radius, double direction, int startDelay,
                            int duration) {

        mTarget = target;
        mRadius = radius;
        mDirection = direction;
        mDuration = duration;
        setStartDelay(startDelay);
        setDuration(duration);
        setInterpolator(MenuItemInterpolator.getInstance());

        addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {
                Log.d(TAG, "onAnimationStart");
                if (mAppear) {
                    mTarget.setVisibility(ImageView.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG, "onAnimationEnd");
                if (!mAppear) {
                    mTarget.setVisibility(ImageView.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        });

        addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                doUpdate();
        }
        });
    }

    public void open() {
        mAppear = true;
        setFloatValues(0f, 1f);
        ((MenuItemInterpolator) getInterpolator()).setDirection(MenuItemInterpolator.DIRECTION_FORWARD);
        setDuration(mDuration);
        start();
    }

    public void close() {
        mAppear = false;
        setFloatValues(1f, 0f);
        ((MenuItemInterpolator) getInterpolator()).setDirection(MenuItemInterpolator.DIRECTION_BAKWARD);
        setDuration((int)((double) mDuration * 0.85));
        start();
    }

    public boolean isOpen() {
        return mAppear;
    }

    private void doUpdate() {
        float val = (Float) getAnimatedValue();
        double xOffset = val * mRadius * Math.cos(Math.toRadians(mDirection));
        double yOffset = val * mRadius * Math.sin(Math.toRadians(mDirection));
        mTarget.setTranslationX((float) xOffset);
        mTarget.setTranslationY((float) yOffset);
        mTarget.setRotation(getAnimatedFraction() * 720f);
    }
}
