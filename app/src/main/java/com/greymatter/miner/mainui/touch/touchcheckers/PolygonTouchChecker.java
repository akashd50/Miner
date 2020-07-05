package com.greymatter.miner.mainui.touch.touchcheckers;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.physics.objects.PolygonCollider;

import javax.vecmath.Vector2f;

public class PolygonTouchChecker extends TouchChecker {
    private PolygonCollider polygonCollider;
    public PolygonTouchChecker(PolygonCollider collider) {
        this.polygonCollider = collider;
    }

    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, polygonCollider.getTransformedVertices());
    }
}
