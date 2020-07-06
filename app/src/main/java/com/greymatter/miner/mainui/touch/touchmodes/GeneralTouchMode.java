package com.greymatter.miner.mainui.touch.touchmodes;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.ActiveLightsContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.ActiveObjectsContainer;
import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameBuilding;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GC.*;

public class GeneralTouchMode extends AbstractTouchMode {
    public GeneralTouchMode(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                Vector3f left = VectorHelper.getNormal(getMainCamera().getUpVector());
                GameObjectsContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(left, 0.01f));
                break;
            case R.id.move_right:
                Vector3f right = VectorHelper.multiply(VectorHelper.getNormal(getMainCamera().getUpVector()), -1f);
                GameObjectsContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(right, 0.01f));
                break;
            case R.id.items_menu:
                View view = AppServices.getAppContextAsActivity().getLayoutInflater().inflate(R.layout.items_dialog, null);
                ArrayList<GameObject> buildings = GameObjectsContainer.getAllWithTag(PLACABLE_GAME_BUILDING);

                ListView listView = view.findViewById(R.id.temp_items_list);
                ArrayAdapter<GameObject> buildingArrayAdapter = new ArrayAdapter<>(AppServices.getAppContext(), android.R.layout.simple_list_item_1, buildings);
                listView.setAdapter(buildingArrayAdapter);

                listView.setOnItemClickListener((parent, view1, position, id) -> {
                    GameObject object = buildingArrayAdapter.getItem(position);

                    ActiveObjectsContainer.add(object);
                    if(object instanceof GameBuilding && object.asGameBuilding().hasLights()) {
                        ActiveLightsContainer.addAll(object.asGameBuilding().getAllLights());
                    }

                    if(object.hasTag(PHYSICS_OBJECT)) {
                        CollisionSystemContainer.add(object.getCollider());
                    }

                    TouchEventBundle touchEventBundle = new TouchEventBundle().setObject(object);
                    ViewModeManager.switchToBuildingMode(getTouchHelper(), getMainCamera());
                    ViewModeManager.getActiveTouchMode().setTouchEventBundle(touchEventBundle);
                });

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppServices.getAppContext()).setView(view);
                AlertDialog dialog = dialogBuilder.create();

                dialog.show();

                break;
            default:
                break;
        }
    }

    @Override
    public void onLongClick(View v) {

    }

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
        if(getTouchHelper().getCurrentPointerCount()==1) {
            Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
            if (getTouchHelper().isTouchPoint1Down() && GameObjectsContainer.get(MAIN_CHARACTER).getDrawable().isClicked(touchPoint)) {
                GameObjectsContainer.get(MAIN_CHARACTER).getCollider().translateTo(touchPoint);
            } else {
                getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
                Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(PLANET).getCollider().getTranslation());
                fromCenterToCam.normalize();
                getMainCamera().setUpVector(fromCenterToCam);
            }
        }else{
            getMainCamera().updateZoomValue(getTouchHelper().getScalingFactor() > 0 ? getMainCamera().getZoomValue() * -0.1f : getMainCamera().getZoomValue()* 0.1f);
        }
        return true;
    }

    private boolean doOnTouchUpGLSurface() {
        if(!getTouchHelper().isTouchPoint1Drag()) {
            Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());

            GameObjectsContainer.get(MAIN_CHARACTER).getCollider().translateTo(touchPoint);
            GameObjectsContainer.get(MAIN_CHARACTER).getCollider().setVelocity(new Vector3f(0f, 0f, 0f));
            GameObjectsContainer.get(MAIN_CHARACTER).getCollider().rotateTo(new Vector3f());
            GameObjectsContainer.get(MAIN_CHARACTER).getCollider().setAngularAcceleration(0f);
            GameObjectsContainer.get(MAIN_CHARACTER).getCollider().setAngularVelocity(0f);
            return true;
        }
        return false;
    }
}
