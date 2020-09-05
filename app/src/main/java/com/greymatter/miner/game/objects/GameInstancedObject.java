package com.greymatter.miner.game.objects;

import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Instance;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GameInstancedObject extends GameObject {
    public GameInstancedObject(String id, InstanceGroup drawable) {
        super(id, drawable);
        initialize();
    }

    public GameInstancedObject(InstanceGroup drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    private void initialize() {
        this.shouldCheckClicks(false);
    }

    public GameInstancedObject addInstance() {
        InstanceGroup instanced = getDrawable().asInstanceGroup();

        GenericObject genericObject = new GenericObject(
                new Instance("INSTANCE_"+instanced.getTotalInstances()).setParentGroup(instanced));

        genericObject.setOnTouchListener(new OnTouchListener() {
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

        genericObject.shouldDraw(false);
        genericObject.shouldCheckClicks(true);
        genericObject.getTransforms().setTranslationTransformationOffset(new Vector3f(1f,0f,0f));

        instanced.addInstance(genericObject.getDrawable().asInstance());
        addChild(genericObject.getId(), genericObject);
        return this;
    }
}
