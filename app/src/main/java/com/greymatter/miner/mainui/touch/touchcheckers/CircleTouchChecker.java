package com.greymatter.miner.mainui.touch.touchcheckers;

import com.greymatter.miner.opengl.objects.drawables.Drawable;
import javax.vecmath.Vector2f;

public class CircleTouchChecker extends TouchChecker {
    private float radius;
    public CircleTouchChecker(Drawable drawable, float radius) {

    }

    public boolean isClicked(Vector2f touchPoint) {
        return false;
    }
}
