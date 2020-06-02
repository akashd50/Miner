package com.greymatter.miner.physics.objects;

public class CircleCollider extends Collider {
    private float radius, transformedRadius;
    public CircleCollider(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTransformedRadius() {
        return transformedRadius;
    }

    @Override
    public void updateParams() {
        transformedRadius = getScale().x * radius;
    }
}
