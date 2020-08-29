package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.View;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class BuildingTouchHandler extends AbstractTouchHandler {
    public BuildingTouchHandler(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_building_placement:
                ViewModeManager.switchToGeneralMode(getTouchHelper(), getMainCamera());
                break;
        }
    }

    @Override
    public void onLongClick(View v) {}

    @Override
    public boolean doOnTouchDown(View v) {
        return super.doOnTouchDown(v) || doOnTouchDownExtra();
    }

    @Override
    public boolean doOnTouchMove(View v) {
        return super.doOnTouchMove(v) || doOnTouchMoveExtra();
    }

    @Override
    public boolean doOnTouchUp(View v) {
        return super.doOnTouchUp(v) || doOnTouchUpExtra();
    }

    /*------------------------------------------private functions---------------------------------*/
    private boolean doOnTouchDownExtra() {

        return false;
    }

    private boolean doOnTouchMoveExtra() {
        if(getTouchHelper().getCurrentPointerCount()==1) {
            getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
            Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(GameManager.getCurrentPlanet()).getTransforms().getTranslation());
            fromCenterToCam.normalize();
            getMainCamera().setUpVector(fromCenterToCam);
        }else{
            getMainCamera().updateZoomValue(getTouchHelper().getScalingFactor() > 0 ? getMainCamera().getZoomValue() * -0.1f : getMainCamera().getZoomValue()* 0.1f);
        }
        return true;
    }

    private boolean doOnTouchUpExtra() {

        return false;
    }

    public ViewMode getViewMode() {
        return ViewMode.BUILDING_MODE;
    }
}
