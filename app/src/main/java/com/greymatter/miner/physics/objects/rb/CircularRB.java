package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.enums.ObjId;

public class CircularRB extends RigidBody {
    private float radius, transformedRadius;
    public CircularRB(ObjId id, float radius) {
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
}
