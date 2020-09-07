package com.greymatter.miner.game.objects;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import com.greymatter.miner.opengl.objects.renderers.InstancedRenderer;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GamePipeline extends GameInstanceGroup {
    private GameInstanceGroup jointIndicators;
    public GamePipeline(String id, InstanceGroup drawable) {
        super(id, drawable);
        initialize();
    }

    public GamePipeline(InstanceGroup drawable) {
        super(drawable);
        initialize();
    }

    private void initialize() {
        InstanceGroup joint = new InstanceGroup("JOINTS");
        joint.setRenderer(new InstancedRenderer()).build();
        joint.setMaterial(MaterialContainer.get(MaterialDef.ADD_MARKER_MATERIAL));
        jointIndicators = new GameInstanceGroup(joint);
        this.addChild("JOINTS", jointIndicators);

        GameObject firstJointInstance = jointIndicators.addInstance();
        firstJointInstance.setCircularRB();
        firstJointInstance.setOnTouchListener(jointTouchListener);
        firstJointInstance.moveTo(0f,0f, ZHelper.OVER_FRONT);
        firstJointInstance.scaleTo(0.3f,0.3f);

        jointIndicators.setAnimator(new BooleanAnimator().withFPS(6).setOnAnimationFrameHandler(new OnAnimationFrameHandler() {
            @Override
            public void animate(GameObject object, ValueAnimator animator) {
                for (int i = 0; i < jointIndicators.getTotalInstances(); i++) {
                    jointIndicators.getInstance(i).getTransforms().rotateBy(0f,0f,2f);
                }
            }
        }));
    }

    @Override
    public void onFrameUpdate() {
        super.onFrameUpdate();
        jointIndicators.getAnimator().update();
    }

    @Override
    public GameInstance addInstance() {
        GameInstance newInstance = super.addInstance();
        newInstance.getTransforms().setTranslationTransformationOffset(new Vector3f(1f,0f,0f));
        newInstance.getTransforms().scaleTo(0f,0.3f);
        return newInstance;
    }

    public GameInstanceGroup getJointIndicator() {
        return jointIndicators;
    }

    OnTouchListener jointTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
            GameObject newPipeInstance = addInstance();
            newPipeInstance.moveTo(gameObject.getLocation().x,gameObject.getLocation().y, ZHelper.FRONT);
            return true;
        }

        @Override
        public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
            Transforms transforms = getChild("INSTANCE_"  + (getDrawable().asInstanceGroup().getTotalInstances()-1)).getTransforms();
            Vector3f pointer3f = VectorHelper.toVector3f(pointer);
            float scaleDist = (float)VectorHelper.getDistanceWithSQRT(transforms.getTranslation(), pointer3f);
            float angle = (float)VectorHelper.angleBetweenDegrees(transforms.getTranslation(), pointer3f);

            transforms.scaleTo(scaleDist/2, transforms.getScale().y);
            transforms.rotateTo(0f,0f,angle);
            return true;
        }

        @Override
        public boolean onTouchUp(IGameObject gameObject, Vector2f pointer) {
            GameObject newInstance = jointIndicators.addInstance();
            newInstance.setCircularRB();
            newInstance.moveTo(pointer.x, pointer.y, ZHelper.OVER_FRONT);
            newInstance.scaleTo(0.3f,0.3f);
            newInstance.setOnTouchListener(jointTouchListener);
            return false;
        }
    };
}
