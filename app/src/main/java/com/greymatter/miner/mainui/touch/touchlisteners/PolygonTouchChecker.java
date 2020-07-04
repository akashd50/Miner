package com.greymatter.miner.mainui.touch.touchlisteners;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.PolygonCollider;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class PolygonTouchChecker extends TouchChecker {
    private PolygonCollider polygonCollider;
    private Drawable drawable;
    public PolygonTouchChecker(Drawable drawable, PolygonCollider collider) {
        this.drawable = drawable;
        this.polygonCollider = collider;
    }

    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, polygonCollider.getTransformedVertices());
    }
}
