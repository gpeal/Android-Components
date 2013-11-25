package com.gpeal.kitchensink;

import android.view.animation.Interpolator;

public class MenuItemInterpolator implements Interpolator {
    private static final MenuItemInterpolator INSTANCE = new MenuItemInterpolator();
    public static final int DIRECTION_FORWARD = 0;
    public static final int DIRECTION_BAKWARD = 1;

    public final static MenuItemInterpolator getInstance() {
        return INSTANCE;
    }

    private static final float[] VALUES = {0f,0.0508f,0.1001f,0.1480f,0.1945f,0.2396f,0.2833f,0.3257f,0.3667f,0.4065f,0.4449f,0.4820f,0.5179f,0.5526f,0.5860f,0.6182f,0.6493f,0.6791f,0.7079f,0.7355f,0.7620f,0.7874f,0.8118f,0.8351f,0.8574f,0.8787f,0.8989f,0.9183f,0.9366f,0.9540f,0.9706f,0.9862f,1.0009f,1.0148f,1.0279f,1.0401f,1.0515f,1.0622f,1.0721f,1.0813f,1.0897f,1.0974f,1.1045f,1.1109f,1.1166f,1.1217f,1.1262f,1.1301f,1.1335f,1.1363f,1.1386f,1.1403f,1.1416f,1.1424f,1.1427f,1.1427f,1.1422f,1.1413f,1.1400f,1.1383f,1.1364f,1.1341f,1.1315f,1.1286f,1.1255f,1.1221f,1.1185f,1.1147f,1.1107f,1.1066f,1.1023f,1.0978f,1.0933f,1.0887f,1.0840f,1.0792f,1.0745f,1.0697f,1.0649f,1.0601f,1.0554f,1.0508f,1.0462f,1.0418f,1.0374f,1.0332f,1.0292f,1.0253f,1.0217f,1.0182f,1.0150f,1.0121f,1.0094f,1.0070f,1.0050f,1.0032f,1.0018f,1.0008f,1.0002f,1.0000f};
    private static final float STEP_SIZE = 1.0f / (VALUES.length - 1);

    private int mDirection = DIRECTION_FORWARD;

    public MenuItemInterpolator() {
        super();
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    @Override
    public float getInterpolation(float input) {
        if (mDirection == DIRECTION_BAKWARD) {
            input = 1f - input;
        }
        int position = Math.min((int) (input * (VALUES.length - 1)), VALUES.length - 2);
        float quantized = position * STEP_SIZE;
        float difference = input - quantized;
        float weight = difference / STEP_SIZE;

        float currentValue = VALUES[position];
        float nextValue = VALUES[position + 1];

        if (mDirection == DIRECTION_BAKWARD) {
            currentValue = (currentValue - 1) * -1;
            nextValue = (nextValue - 1) * -1;
        }

        return currentValue + weight * (nextValue- currentValue);
    }

}
