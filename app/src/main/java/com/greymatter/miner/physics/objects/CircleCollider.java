package com.greymatter.miner.physics.objects;

import android.util.Log;

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

    @Override
    public void scaleTo(Vector3f newScale) {
        if(newScale.x != newScale.y) {
            Log.v("Scaling warning", "X and Y scales should be same for circle colliders.");
        }

        super.scaleTo(newScale);
        radius = getScale().x * radius;
    }

    @Override
    public void scaleBy(Vector3f newScale) {
        if(newScale.x != newScale.y) {
            Log.v("Scaling warning", "X and Y scales should be same for circle colliders.");
        }

        super.scaleBy(newScale);
        radius = getScale().x * radius;
    }
}
