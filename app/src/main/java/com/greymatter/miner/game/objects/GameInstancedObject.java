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
    private int instanceSelected;
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
                //OnTouchListener.super.defaultOnTouchMove(gameObject, pointer);
                Transforms transforms = gameObject.getTransforms();
                float scaleDist = (float)VectorHelper.getDistanceWithSQRT(transforms.getTranslation(), VectorHelper.toVector3f(pointer));
                //transforms.translateBy(scaleDist - transforms.getScale().x,0f);
                transforms.scaleTo(scaleDist, transforms.getScale().y);
                return true;
            }

            @Override
            public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
                return false;
            }
        });

        genericObject.shouldDraw(false);
        genericObject.shouldCheckClicks(true);
        instanced.addInstance(genericObject.getDrawable().asInstance());
        addChild(genericObject.getId(), genericObject);
        return this;
    }

//    @Override
//    public boolean isClickedHelper(Vector2f pointer) {
////        for (int i = 0; i < instanceRBs.size(); i++) {
////            if(instanceRBs.get(i).isClicked(pointer)) return true;
////        }
//        return false;
//    }
}
