package com.greymatter.miner.game.objects.buildings;

import com.greymatter.miner.animators.BooleanAnimator;
import com.greymatter.miner.animators.OnAnimationFrameHandler;
import com.greymatter.miner.animators.ValueAnimator;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.containers.MaterialContainer;
import com.greymatter.miner.game.GameInstance;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.GameInstanceGroup;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.loaders.enums.definitions.MaterialDef;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.InstanceGroup;
import com.greymatter.miner.opengl.objects.renderers.InstancedRenderer;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GasPump extends GameBuilding {
    private GameInstanceGroup pipelineGroup, jointIndicators;

    public GasPump(Drawable drawable) {
        super(drawable.getId(), drawable);
        initialize();
    }

    public GasPump(String id, Drawable drawable) {
        super(id, drawable);
        initialize();
    }

    private void initialize() {
        InstanceGroup square = new InstanceGroup("PIPES");
        square.setRenderer(new InstancedRenderer()).build();
        square.setMaterial(MaterialContainer.get(MaterialDef.GROUND_MATERIAL));
        pipelineGroup = new GameInstanceGroup(square);
        pipelineGroup.moveTo(0f,0f, ZHelper.FRONT);
        this.addChild("PIPES", pipelineGroup);

        InstanceGroup joint = new InstanceGroup("JOINTS");
        joint.setRenderer(new InstancedRenderer()).build();
        joint.setMaterial(MaterialContainer.get(MaterialDef.ADD_MARKER_MATERIAL));
        jointIndicators = new GameInstanceGroup(joint);
        jointIndicators.moveTo(0f,0f, ZHelper.OVER_FRONT);
        this.addChild("JOINTS", jointIndicators);

        GameObject firstJointInstance = jointIndicators.addInstance();
        firstJointInstance.setCircularRB();
        firstJointInstance.setOnTouchListener(jointTouchListener);
        firstJointInstance.moveTo(0f,0f, ZHelper.OVER_FRONT);
        firstJointInstance.scaleTo(0.3f,0.3f);

        jointIndicators.setAnimator(new BooleanAnimator().withFPS(10).setOnAnimationFrameHandler(new OnAnimationFrameHandler() {
            @Override
            public void onAnimationFrame(GameObject object, ValueAnimator animator) {
                for (int i = 0; i < jointIndicators.getTotalInstances(); i++) {
                    jointIndicators.getInstance(i).getTransforms().rotateBy(0f,0f,10f);
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
    public void onTransformsChanged() {
        super.onTransformsChanged();

        if(jointIndicators!=null) {
            Vector3f directionToCenter = VectorHelper.sub(GameObjectsContainer.get(GameManager.getCurrentPlanet()).getLocation(), getLocation());
            directionToCenter.normalize();
            jointIndicators.getInstance(0).moveTo(getLocation().x + directionToCenter.x * 0.6f, getLocation().y + directionToCenter.y * 0.6f);
        }
    }

    public GameInstance addInstance() {
        GameInstance newInstance = pipelineGroup.addInstance();
        newInstance.getTransforms().setTranslationTransformationOffset(new Vector3f(1f,0f,0f));
        newInstance.getTransforms().scaleTo(0f,0.3f);
        return newInstance;
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
            Transforms transforms = pipelineGroup.getChild("INSTANCE_"  + (pipelineGroup.getTotalInstances()-1)).getTransforms();
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
