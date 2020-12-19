package com.greymatter.miner.helpers.touchListeners;

import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.objects.GameInstanceGroup;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.game.objects.buildings.OilDrill;
import com.greymatter.miner.game.objects.resources.OilDeposit;
import com.greymatter.miner.game.objects.resources.OilDepositGroup;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.helpers.ZHelper;
import com.greymatter.miner.mainui.touch.OnTouchListener;
import com.greymatter.miner.opengl.objects.Transforms;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GameConstants.OIL_DEPOSIT_GROUP_1;

public class OilDrillJointTouchListener implements OnTouchListener {
    @Override
    public boolean onTouchDown(IGameObject gameObject, Vector2f pointer) {
        OilDrill oilDrill = (OilDrill)gameObject.getParent().getParent();
        GameObject newPipeInstance = oilDrill.addInstance();
        newPipeInstance.moveTo(gameObject.getLocalLocation().x,gameObject.getLocalLocation().y, ZHelper.FRONT);
        return true;
    }

    @Override
    public boolean onTouchMove(IGameObject gameObject, Vector2f pointer) {
        OilDrill oilDrill = (OilDrill)gameObject.getParent().getParent();
        GameInstanceGroup pipelineGroup = oilDrill.getPipelineGroup();

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
        OilDrill oilDrill = (OilDrill)gameObject.getParent().getParent();
        GameInstanceGroup jointIndicators = oilDrill.getJointIndicators();

        GameObject newInstance = jointIndicators.createAndAddInstance();
        newInstance.setCircularRB();
        newInstance.moveTo(pointer.x, pointer.y, ZHelper.OVER_FRONT);
        newInstance.scaleTo(0.3f,0.3f);
        newInstance.setOnTouchListener(this);
        // Check if it connected to a oil resource

        OilDepositGroup oilDeposits = (OilDepositGroup)ContainerManager.getAllResourcesContainer().get(OIL_DEPOSIT_GROUP_1);
        OilDeposit overlappingDeposit = oilDeposits.getOverlappingDeposit(pointer);
        if (overlappingDeposit != null) {
            oilDrill.addNewConnectedOilDeposit(overlappingDeposit);
        }
        return false;
    }
}
