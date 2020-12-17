package com.greymatter.miner.mainui.touch.touchmodes;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.CollisionSystemContainer;
import com.greymatter.miner.containers.AllGameObjectsContainer;
import com.greymatter.miner.containers.UIToDrawContainer;
import com.greymatter.miner.containers.ContainerManager;
import com.greymatter.miner.game.manager.GameManager;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.loaders.enums.Tag;
import com.greymatter.miner.mainui.LayoutHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Transforms;

import java.util.ArrayList;

import javax.vecmath.Vector2f;

public class UITouchHandler extends AbstractTouchHandler {
    public UITouchHandler(TouchHelper controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.done_building_placement:
                ViewModeManager.switchToGeneralMode(getTouchHelper(), AppServices.getGameCamera());
                break;
            case R.id.items_menu:
                View view = LayoutHelper.loadLayout(R.layout.items_dialog);
                AlertDialog selectionDialog = LayoutHelper.loadDialog(view);

                ArrayList<IGameObject> buildings = ContainerManager.getAllGameObjectsContainer().getAllWithTag(Tag.PLACABLE_GAME_BUILDING);
                ListView listView = view.findViewById(R.id.temp_items_list);
                ArrayAdapter<IGameObject> buildingArrayAdapter = new ArrayAdapter<>(AppServices.getAppContext(), android.R.layout.simple_list_item_1, buildings);
                listView.setAdapter(buildingArrayAdapter);

                listView.setOnItemClickListener((parent, view1, position, id) -> {
                    IGameObject object = buildingArrayAdapter.getItem(position);
                    ContainerManager.getActiveGameObjectsContainer().add(object);

                    Transforms planetTransforms = GameManager.getCurrentPlanet().getTransforms();
                    object.moveTo(0f,planetTransforms.getTranslation().y + planetTransforms.getScale().y + object.getTransforms().getScale().y);


                    if(object.hasTag(Tag.DYNAMIC_PHYSICS_OBJECT) || object.hasTag(Tag.STATIC_PHYSICS_OBJECT)) {
                        CollisionSystemContainer.add(object.getRigidBody());
                    }

                    TouchEventBundle touchEventBundle = new TouchEventBundle().setObject(object);
                    ViewModeManager.switchToBuildingMode(getTouchHelper(),  AppServices.getGameCamera());
                    ViewModeManager.getActiveTouchHandler().setTouchEventBundle(touchEventBundle);

                    selectionDialog.dismiss();
                });
                LayoutHelper.showDialog(selectionDialog);
                break;
            default:
                break;
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
        return false;
    }

    private boolean doOnTouchUpExtra() {

        return false;
    }

    public ViewMode getViewMode() {
        return ViewMode.UI_TOUCH_HANDLER;
    }

    @Override
    public ArrayList<IGameObject> gameObjectsForTouchChecking() {
        return UIToDrawContainer.getAllReversed();
    }
}
