package com.greymatter.miner.mainui.touch.touchcheckers;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.physics.objects.rb.PolygonRB;

import javax.vecmath.Vector2f;

public class PolygonTouchChecker extends TouchChecker {
    private PolygonRB polygonCollider;
    public PolygonTouchChecker(PolygonRB collider) {
        this.polygonCollider = collider;
    }

    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, polygonCollider.getTransformedVertices());
    }
}
