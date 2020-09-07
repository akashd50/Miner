package com.greymatter.miner.physics.objects.rb;

import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Transforms;
import javax.vecmath.Vector2f;

public class RectangularRB extends RigidBody {
    public RectangularRB(String id) {
        super(id);
    }

    @Override
    public void updateParamsOverride() {}

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        Transforms transforms = getTransforms();

        Vector2f revertTransformations = VectorHelper.copy(touchPoint);

        revertTransformations = isClickedHelper(revertTransformations, transforms.getParent(), transforms);

        revertTransformations.x -= transforms.getTranslation().x;
        revertTransformations.y -= transforms.getTranslation().y;
        revertTransformations = VectorHelper.rotateAroundZ(revertTransformations, (float) Math.toRadians(-transforms.getRotation().z));
        revertTransformations.x *= 1/transforms.getScale().x;
        revertTransformations.y *= 1/transforms.getScale().y;

        return revertTransformations.x < 1f && revertTransformations.x > -1f
                && revertTransformations.y > -1f && revertTransformations.y < 1f;
    }

    private Vector2f isClickedHelper(Vector2f revertTransformations, Transforms transforms, Transforms prevTransforms) {
        if(transforms.getParent()!=null){
            isClickedHelper(revertTransformations, transforms.getParent(), transforms);
        }

        if(prevTransforms.isCopyTranslationFromParent()) {
            revertTransformations.x -= transforms.getTranslation().x;
            revertTransformations.y -= transforms.getTranslation().y;
        }

        if(prevTransforms.isCopyRotationFromParent()) {
            revertTransformations = VectorHelper.rotateAroundZ(revertTransformations, (float) Math.toRadians(-transforms.getRotation().z));
        }

        if(prevTransforms.isCopyScaleFromParent()) {
            revertTransformations.x *= 1 / transforms.getScale().x;
            revertTransformations.y *= 1 / transforms.getScale().y;
        }
        return revertTransformations;
    }

}
