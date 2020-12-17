package com.greymatter.miner.mainui.touch.touchmodes;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.LayoutHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Transforms;

import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import static com.greymatter.miner.game.GameConstants.MAIN_CHARACTER_1;

public class GeneralTouchHandler extends AbstractTouchHandler {
    public GeneralTouchHandler(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public synchronized void onClick(View v) {}

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
            Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(GameManager.getCurrentPlanet()).getTransforms().getTranslation());
            fromCenterToCam.normalize();
            getMainCamera().setUpVector(fromCenterToCam);
        }else{
            getMainCamera().updateZoomValue(getTouchHelper().getScalingFactor() > 0 ? getMainCamera().getZoomValue() * -0.1f : getMainCamera().getZoomValue()* 0.1f);
        }
        return true;
    }

    private boolean doOnTouchUpExtra() {
        if(!getTouchHelper().isTouchPoint1Drag()) {
            Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());

            GameObjectsContainer.get(MAIN_CHARACTER_1).getTransforms().translateTo(touchPoint);
            GameObjectsContainer.get(MAIN_CHARACTER_1).getRigidBody().getRBProps().setVelocity(new Vector3f(0f, 0f, 0f));
            GameObjectsContainer.get(MAIN_CHARACTER_1).getTransforms().rotateTo(new Vector3f());
            GameObjectsContainer.get(MAIN_CHARACTER_1).getRigidBody().getRBProps().setAngularAcceleration(0f);
            GameObjectsContainer.get(MAIN_CHARACTER_1).getRigidBody().getRBProps().setAngularVelocity(0f);
            return true;
        }
        return false;
    }

    @Override
    public ViewMode getViewMode() {
        return ViewMode.GENERAL_MODE;
    }

    @Override
    public ArrayList<IGameObject> gameObjectsForTouchChecking() {
        return ToDrawContainer.getAllReversed();
    }
}
