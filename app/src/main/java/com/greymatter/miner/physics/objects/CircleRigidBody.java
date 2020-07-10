package com.greymatter.miner.physics.objects;

public class CircleRigidBody extends RigidBody {
    private float radius, transformedRadius;
    public CircleRigidBody(float radius) {
        super();
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTransformedRadius() {
        if(!isUpdatedPerMovement()) {
            updateParamsOverride();
        }
        return transformedRadius;
    }

    @Override
    public void updateParamsOverride() {
        transformedRadius = getScale().x * radius;
    }
}
