package com.greymatter.miner.mainui.touch.touchviewmodes;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.greymatter.miner.AppServices;
import com.greymatter.miner.R;
import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.game.containers.GameBuildingsContainer;
import com.greymatter.miner.game.objects.Townhall;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import java.util.ArrayList;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import static com.greymatter.miner.game.GC.*;

public class GeneralTouchMode extends AbstractTouchMode {
    public GeneralTouchMode(TouchController controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                Vector3f left = VectorHelper.getNormal(getMainCamera().getUpVector());
                DrawableContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(left, 0.01f));
                break;
            case R.id.move_right:
                Vector3f right = VectorHelper.multiply(VectorHelper.getNormal(getMainCamera().getUpVector()), -1f);
                DrawableContainer.get(MAIN_CHARACTER).getCollider().updateVelocity(VectorHelper.multiply(right, 0.01f));
                break;
            case R.id.items_menu:
                View view = AppServices.getAppContextAsActivity().getLayoutInflater().inflate(R.layout.items_dialog, null);
                ArrayList<Drawable> buildings = new ArrayList<>();
                buildings.add(DrawableContainer.get(MAIN_BASE));

                ListView listView = view.findViewById(R.id.temp_items_list);
                ArrayAdapter<Drawable> buildingArrayAdapter = new ArrayAdapter<>(AppServices.getAppContext(), android.R.layout.simple_list_item_1, buildings);
                listView.setAdapter(buildingArrayAdapter);

                listView.setOnItemClickListener((parent, view1, position, id) -> {
                    if(buildings.get(position).getId().compareTo(DrawableContainer.get(MAIN_BASE).getId())==0) {
                        GameBuildingsContainer.add(new Townhall(DrawableContainer.get(MAIN_BASE)));

                        TouchEventBundle touchEventBundle = new TouchEventBundle().setDrawable(DrawableContainer.get(MAIN_BASE));
                        ViewModeManager.switchToBuildingMode(getTouchController(), getMainCamera());
                        ViewModeManager.getActiveTouchMode().setTouchEventBundle(touchEventBundle);
                    }
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
    public void doOnTouchDown() {

    }

    @Override
    public void doOnTouchMove() {
        Vector2f touchPoint = getLocalTouchPoint2f(getTouchController().getCurrTouchPoint1());
        if(DrawableContainer.get(MAIN_CHARACTER).isClicked(touchPoint)){
            DrawableContainer.get(MAIN_CHARACTER).getCollider().translateTo(touchPoint);
        }else {
            getMainCamera().translateBy(convertToLocalUnit(getTouchController().getPointer1MovementDiff()));
        }
    }

    @Override
    public void doOnTouchUp() {
        if(!getTouchController().isTouchPoint1Drag()) {
            Vector3f touchPoint = getLocalTouchPoint3f(getTouchController().getCurrTouchPoint1());

            DrawableContainer.get(MAIN_CHARACTER).getCollider().translateTo(touchPoint);
            DrawableContainer.get(MAIN_CHARACTER).getCollider().setVelocity(new Vector3f(0f, 0f, 0f));
            DrawableContainer.get(MAIN_CHARACTER).getCollider().rotateTo(new Vector3f());
            DrawableContainer.get(MAIN_CHARACTER).getCollider().setAngularAcceleration(0f);
            DrawableContainer.get(MAIN_CHARACTER).getCollider().setAngularVelocity(0f);
        }
    }
}
