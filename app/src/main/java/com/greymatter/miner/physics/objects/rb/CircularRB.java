package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class CircularRB extends RigidBody {
    private float radius, transformedRadius;
    public CircularRB(String id, float radius) {
        super(id);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTransformedRadius() {
        if(!isDynamicallyUpdated()) {
            updateParamsOverride();
        }
        return transformedRadius;
    }

    @Override
    public void updateParamsOverride() {
        transformedRadius = getTransforms().getScale().x * radius;
    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return VectorHelper.getDistanceWithSQRT(getTransforms().getTranslation(),
                new Vector3f(touchPoint.x, touchPoint.y, 0f)) <= transformedRadius;
    }
}
