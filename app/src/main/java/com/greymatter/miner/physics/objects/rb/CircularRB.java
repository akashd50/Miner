package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;

import javax.vecmath.Vector2f;

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
        /*
         * This applies reverted transformations to the point to bring the point into a local space
         * between -1 and 1 and then checks if that point meets the touch criteria.
         */

        Transforms transforms = getTransforms();
        Vector2f normalizedTouchPoint = VectorHelper.copy(touchPoint);
        revertAllTransformations(normalizedTouchPoint, transforms.getParent(), transforms);
        VectorHelper.revertTransformations(normalizedTouchPoint, transforms);

        float distanceFromCenter = (float)VectorHelper.getDistanceWithSQRT(new Vector2f(0f, 0f),
                                        new Vector2f(normalizedTouchPoint.x, normalizedTouchPoint.y));
        return distanceFromCenter <= 1f;
    }
}
