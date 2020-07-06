package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.View;
import com.greymatter.miner.R;
import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

import javax.vecmath.Vector3f;

import static com.greymatter.miner.game.GC.*;

public class BuildingTouchMode extends AbstractTouchMode {
    public BuildingTouchMode(TouchHelper controller, Camera camera) {
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

    public void onLongClick(View v) {}

    @Override
    public boolean doOnTouchDown(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                return doOnTouchDownGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchMove(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                return doOnTouchMoveGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchUp(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                return doOnTouchUpGLSurface();
        }
        return false;
    }

    /*------------------------------------------private functions---------------------------------*/
    private boolean doOnTouchDownGLSurface() {

        return false;
    }

    private boolean doOnTouchMoveGLSurface() {
        GameObject bundleObject = getTouchHelper() != null && getTouchEventBundle() != null
                                ? getTouchEventBundle().getObject() : null;
        if(bundleObject != null && bundleObject.getDrawable().isClicked(getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1()))) {
            translateSelectedObject(bundleObject);
        }else{
            getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
            Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(PLANET).getCollider().getTranslation());
            fromCenterToCam.normalize();
            getMainCamera().setUpVector(fromCenterToCam);
        }
        return true;
    }

    private boolean doOnTouchUpGLSurface() {

        return false;
    }
    /*-----------------------------------private helper functions---------------------------------*/
    private void translateSelectedObject(GameObject selected) {
        selected.moveTo(getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1()));
        selected.getCollider().rotateTo(0f,0f,VectorHelper.angleBetween(GameObjectsContainer.get(PLANET).getDrawable(), selected.getDrawable()));
    }
}
