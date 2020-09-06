package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GamePipeline extends GameInstancedObject {
    public GamePipeline(String id, InstanceGroup drawable) {
        super(id, drawable);
    }

    public GamePipeline(InstanceGroup drawable) {
        super(drawable);
    }

    @Override
    public GameObject addInstance() {
        GameObject newInstance = super.addInstance();
        newInstance.getTransforms().setTranslationTransformationOffset(new Vector3f(1f,0f,0f));

        newInstance.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
                return true;
            }

            @Override
            public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
                Transforms transforms = gameObject.getTransforms();
                Vector3f pointer3f = VectorHelper.toVector3f(pointer);
                float scaleDist = (float)VectorHelper.getDistanceWithSQRT(transforms.getTranslation(), pointer3f);
                float angle = (float)VectorHelper.angleBetweenDegrees(transforms.getTranslation(), pointer3f);

                transforms.scaleTo(scaleDist/2, transforms.getScale().y);
                transforms.rotateTo(0f,0f,angle);
                return true;
            }

            @Override
            public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
                return false;
            }
        });
        return newInstance;
    }
}
