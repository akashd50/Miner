package com.greymatter.miner.mainui.touch.touchmodes;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.ActiveLightsContainer;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.containers.GameObjectsContainer;
import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.enums.Tag;
import com.greymatter.miner.game.objects.buildings.GameBuilding;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public class GeneralTouchMode extends AbstractTouchMode {
    public GeneralTouchMode(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                Vector3f left = VectorHelper.getNormal(getMainCamera().getUpVector());
                GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody().getRBProps().updateVelocity(VectorHelper.multiply(left, 0.01f));
                break;
            case R.id.move_right:
                Vector3f right = VectorHelper.multiply(VectorHelper.getNormal(getMainCamera().getUpVector()), -1f);
                GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody().getRBProps().updateVelocity(VectorHelper.multiply(right, 0.01f));
                break;
            case R.id.items_menu:
                View view = AppServices.getAppContextAsActivity().getLayoutInflater().inflate(R.layout.items_dialog, null);
                ArrayList<GameObject> buildings = GameObjectsContainer.getAllWithTag(Tag.PLACABLE_GAME_BUILDING);

                ListView listView = view.findViewById(R.id.temp_items_list);
                ArrayAdapter<GameObject> buildingArrayAdapter = new ArrayAdapter<>(AppServices.getAppContext(), android.R.layout.simple_list_item_1, buildings);
                listView.setAdapter(buildingArrayAdapter);

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AppServices.getAppContext()).setView(view);
                AlertDialog dialog = dialogBuilder.create();

                listView.setOnItemClickListener((parent, view1, position, id) -> {
                    GameObject object = buildingArrayAdapter.getItem(position);

                    ToDrawContainer.add(object);
                    //if(object.getId().compareTo(SAMPLE_SCANNER) == 0) ToDrawContainer.add(GameObjectsContainer.get("g"));


                    if(object instanceof GameBuilding && object.asGameBuilding().hasLights()) {
                        ActiveLightsContainer.addAll(object.asGameBuilding().getAllLights());
                    }

                    if(object.hasTag(Tag.PHYSICS_OBJECT)) {
                        CollisionSystemContainer.add(object.getRigidBody());
                    }

                    TouchEventBundle touchEventBundle = new TouchEventBundle().setObject(object);
                    ViewModeManager.switchToBuildingMode(getTouchHelper(), getMainCamera());
                    ViewModeManager.getActiveTouchMode().setTouchEventBundle(touchEventBundle);

                    dialog.dismiss();
                });

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
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                for(GameObject gameObject : ToDrawContainer.getAll()) {
                    if(gameObject.onTouchDownEvent(touchPoint)) return true;
                }
                return doOnTouchDownGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchMove(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                for(GameObject gameObject : ToDrawContainer.getAll()) {
                    if(gameObject.onTouchMoveEvent(touchPoint)) return true;
                }
                return doOnTouchMoveGLSurface();
        }
        return false;
    }

    @Override
    public boolean doOnTouchUp(View v) {
        switch (v.getId()) {
            case R.id.mainGLSurfaceView:
                Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
                for(GameObject gameObject : ToDrawContainer.getAll()) {
                    if(gameObject.onTouchUpEvent(touchPoint)) return true;
                }
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
            getMainCamera().translateBy(VectorHelper.toVector3f(devicePixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
            Vector3f fromCenterToCam = VectorHelper.sub(getMainCamera().getTranslation(), GameObjectsContainer.get(ObjId.PLANET).getRigidBody().getTranslation());
            fromCenterToCam.normalize();
            getMainCamera().setUpVector(fromCenterToCam);
        }else{
            getMainCamera().updateZoomValue(getTouchHelper().getScalingFactor() > 0 ? getMainCamera().getZoomValue() * -0.1f : getMainCamera().getZoomValue()* 0.1f);
        }
        return true;
    }

    private boolean doOnTouchUpGLSurface() {
        if(!getTouchHelper().isTouchPoint1Drag()) {
            Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());

            GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getTransforms().translateTo(touchPoint);
            GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody().getRBProps().setVelocity(new Vector3f(0f, 0f, 0f));
            GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getTransforms().rotateTo(new Vector3f());
            GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody().getRBProps().setAngularAcceleration(0f);
            GameObjectsContainer.get(ObjId.MAIN_CHARACTER).getRigidBody().getRBProps().setAngularVelocity(0f);
            return true;
        }
        return false;
    }
}
