package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.View;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

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
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                if(getTouchEventBundle().getObject().onTouchMoveEvent(touchPoint)) return true;
                return doOnTouchDownGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchMove(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                if(getTouchEventBundle().getObject().onTouchMoveEvent(touchPoint)) return true;
                return doOnTouchMoveGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchUp(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                if(getTouchEventBundle().getObject().onTouchMoveEvent(touchPoint)) return true;
                return doOnTouchUpGLSurface();
        }
        return false;
    }

    /*------------------------------------------private functions---------------------------------*/
    private boolean doOnTouchDownGLSurface() {

        return false;
    }

    private boolean doOnTouchMoveGLSurface() {
        getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
        Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(ObjId.PLANET).getTransforms().getTranslation());
        fromCenterToCam.normalize();
        getMainCamera().setUpVector(fromCenterToCam);
        return true;
    }

    private boolean doOnTouchUpGLSurface() {

        return false;
    }

    public ViewMode getViewMode() {
        return ViewMode.BUILDING_MODE;
    }
}
