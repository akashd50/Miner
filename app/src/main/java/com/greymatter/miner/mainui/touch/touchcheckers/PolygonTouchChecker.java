package com.greymatter.miner.mainui.touch.touchcheckers;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.objects.PolygonRigidBody;

import javax.vecmath.Vector2f;

public class PolygonTouchChecker extends TouchChecker {
    private PolygonRigidBody polygonCollider;
    public PolygonTouchChecker(PolygonRigidBody collider) {
        this.polygonCollider = collider;
    }

    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.isPointInPolygon(touchPoint, polygonCollider.getTransformedVertices());
    }
}
