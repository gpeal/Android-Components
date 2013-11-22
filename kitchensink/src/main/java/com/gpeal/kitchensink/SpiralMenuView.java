package com.gpeal.kitchensink;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class SpiralMenuView extends FrameLayout {
    private static final String TAG = SpiralMenuView.class.getSimpleName();

    private ImageButton mMenuButton;
    private List<ImageButton> mMenuIcons;

    public SpiralMenuView(Context context) {
        this(context, null);
    }

    public SpiralMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiralMenuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        mMenuIcons = new ArrayList<ImageButton>();
        int childCount = getChildCount();
        if (childCount <= 1) {
            throw new RuntimeException("There must be at least two children. One menu button and one menu item. " +
                    "There are " + getChildCount());
        }
        mMenuButton = (ImageButton) getChildAt(getChildCount() - 1);
        for (int i = 0; i < getChildCount() - 1; i++) {
            ImageButton button = (ImageButton) getChildAt(i);
            mMenuIcons.add(button);
            button.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {
                    onMenuItemClick(view);
                }
            });
        }

        mMenuButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                onMenuButtonClick();
            }
        });
    }


    private void onMenuButtonClick() {
        Log.d(TAG, "Clicked menu button");
    }

    private void onMenuItemClick(View view) {
        Log.d(TAG, "Clicked menu item");
    }
}
