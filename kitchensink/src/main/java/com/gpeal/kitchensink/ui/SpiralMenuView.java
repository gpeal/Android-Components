package com.gpeal.kitchensink.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.gpeal.kitchensink.R;

import java.util.ArrayList;
import java.util.List;

public class SpiralMenuView extends FrameLayout {
    private static final String TAG = SpiralMenuView.class.getSimpleName();
    private static final int STATE_CLOSED = 0;
    private static final int STATE_OPENED = 1;
    private static final int STATE_TRANSITIONING = 2;
    private static final double RADIUS = 350;
    private static final double STARTING_ANGLE = 270;
    private static final double ENDING_ANGLE = 360;
    private static final int START_DELAY_DELTA = 75;
    private static final int DURATION = 500;

    private int mState = STATE_CLOSED;
    private ImageButton mMenuButton;
    private List<ImageButton> mMenuItems;
    private List<MenuIconAnimator> mMenuItemAnimators;
    private Animator mOpeningMenuButtonAnimator;
    private Animator mClosingMenuButtonAnimator;
    private TransitionDrawable mGradientTransition;

    private final MediaPlayer mOpeningMediaPlayer;
    private final MediaPlayer mClosingMediaPlayer;

    public SpiralMenuView(Context context) {
        this(context, null);
    }

    public SpiralMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiralMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mOpeningMediaPlayer = MediaPlayer.create(getContext(), R.raw.nav_out);
        mClosingMediaPlayer = MediaPlayer.create(getContext(), R.raw.nav_in);
    }

    @Override
    protected void onFinishInflate() {
        mMenuItems = new ArrayList<ImageButton>();
        mMenuItemAnimators = new ArrayList<MenuIconAnimator>();
        int childCount = getChildCount();
        if (childCount <= 1) {
            throw new RuntimeException("There must be at least two children. One menu button and one menu item. " +
                    "There are " + getChildCount());
        }

        double directionDelta = (ENDING_ANGLE - STARTING_ANGLE) / (double) (getChildCount() - 2);
        double direction = STARTING_ANGLE;
        int startDelay = 0;
        // the last child is the menu button
        for (int i = 0; i < getChildCount() - 1; i++) {
            ImageButton menuItem = (ImageButton) getChildAt(i);
            mMenuItems.add(menuItem);
            menuItem.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    onMenuItemClick(view);
                }
            });
            MenuIconAnimator animator =
                    new MenuIconAnimator(menuItem, RADIUS, direction, startDelay, DURATION);
            animator.setTarget(menuItem);
            mMenuItemAnimators.add(animator);
            direction += directionDelta;
            startDelay += START_DELAY_DELTA;

            // set an animation listener on the last menu item so we can change the state
            if (i == getChildCount() - 2) {
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (((MenuIconAnimator) animator).isOpen()) {
                            mState = STATE_OPENED;
                        } else {
                            mState = STATE_CLOSED;
                        }
                    }
                    @Override
                    public void onAnimationStart(Animator animator) {}
                    @Override
                    public void onAnimationCancel(Animator animator) {}
                    @Override
                    public void onAnimationRepeat(Animator animator) {}
                });
            }
        }

        mMenuButton = (ImageButton) getChildAt(getChildCount() - 1);
        mMenuButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                onMenuButtonClick();
            }
        });
        mOpeningMenuButtonAnimator = ObjectAnimator.ofFloat(mMenuButton, "rotation", 0f, -135f);
        mOpeningMenuButtonAnimator.setDuration(DURATION);
        mClosingMenuButtonAnimator = ObjectAnimator.ofFloat(mMenuButton, "rotation", -135f, 0f);
        mClosingMenuButtonAnimator.setDuration(DURATION);

        View spiralMenu = findViewById(R.id.spiral_menu);
        mGradientTransition = (TransitionDrawable) spiralMenu.getBackground();
    }


    private void onMenuButtonClick() {
        switch(mState) {
            case STATE_CLOSED:
                mState = STATE_TRANSITIONING;
                for(MenuIconAnimator a : mMenuItemAnimators) {
                    a.open();
                }
                mOpeningMenuButtonAnimator.start();
                mGradientTransition.startTransition(DURATION / 2);
                mOpeningMediaPlayer.start();
                break;
            case STATE_OPENED:
                mState = STATE_TRANSITIONING;
                for (MenuIconAnimator a : mMenuItemAnimators) {
                    a.close();
                }
                mClosingMenuButtonAnimator.start();
                mGradientTransition.reverseTransition(DURATION / 2);
                mClosingMediaPlayer.start();
                break;
            case STATE_TRANSITIONING:
                mClosingMediaPlayer.start();
                break;
        }
    }

    private void onMenuItemClick(View view) {

    }

    private void expandMenu() {
        for (ImageButton b : mMenuItems) {
            b.setVisibility(View.VISIBLE);
        }
    }

}
