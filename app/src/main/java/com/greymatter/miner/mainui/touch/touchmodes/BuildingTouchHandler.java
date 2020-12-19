package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.View;

import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.opengl.objects.Camera;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class BuildingTouchHandler extends AbstractTouchHandler {
    public BuildingTouchHandler(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onLongClick(View v) {}

    @Override
    public boolean doOnTouchDown(Vector2f touchPoint) {
        return super.doOnTouchDown(touchPoint) || doOnTouchDownExtra();
    }

    @Override
    public boolean doOnTouchMove(Vector2f touchPoint) {
        return super.doOnTouchMove(touchPoint) || doOnTouchMoveExtra();
    }

    @Override
    public boolean doOnTouchUp(Vector2f touchPoint) {
        return super.doOnTouchUp(touchPoint) || doOnTouchUpExtra();
    }

    /*------------------------------------------private functions---------------------------------*/
    private boolean doOnTouchDownExtra() {
        return false;
    }

    private boolean doOnTouchMoveExtra() {
        if(getTouchHelper().getCurrentPointerCount()==1) {
            getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
            Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameManager.getCurrentPlanet().getTransforms().getTranslation());
            fromCenterToCam.normalize();
            getMainCamera().setUpVector(fromCenterToCam);
        }else{
            getMainCamera().updateZoomValue(getTouchHelper().getScalingFactor() > 0 ? getMainCamera().getZoomValue() * -0.01f : getMainCamera().getZoomValue()* 0.01f);
        }
        return true;
    }

    private boolean doOnTouchUpExtra() {

        return false;
    }

    public ViewMode getViewMode() {
        return ViewMode.BUILDING_MODE;
    }

    @Override
    public ArrayList<IGameObject> gameObjectsForTouchChecking() {
        return ContainerManager.getActiveGameObjectsContainer().getAllReversed();
    }
}
