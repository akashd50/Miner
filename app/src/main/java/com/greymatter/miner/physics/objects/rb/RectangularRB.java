package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class RectangularRB extends RigidBody {
    public RectangularRB(String id) {
        super(id);
    }

    @Override
    public void updateParamsOverride() {}

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        /*
         * This applies reverted transformations to the point to bring the point into a local space
         * between -1 and 1 and then checks if that point meets the touch criteria.
         */
        Transforms transforms = getTransforms();
        Vector3f normalizedTouchPoint = VectorHelper.toVector3f(touchPoint);
        normalizedTouchPoint = VectorHelper.getUnitLocation(normalizedTouchPoint, transforms);
        VectorHelper.revertTransformations(normalizedTouchPoint, transforms);

        return normalizedTouchPoint.x < 1f && normalizedTouchPoint.x > -1f
                && normalizedTouchPoint.y > -1f && normalizedTouchPoint.y < 1f;
    }
}
