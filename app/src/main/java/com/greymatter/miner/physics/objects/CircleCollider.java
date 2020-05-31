package com.greymatter.miner.physics.objects;

import com.greymatter.miner.physics.Collider;

import javax.vecmath.Vector3f;

public class CircleCollider extends Collider {
    private float radius;
    public CircleCollider(float radius) {
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
