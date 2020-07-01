package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.View;

import com.greymatter.miner.R;
import com.greymatter.miner.containers.DrawableContainer;
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

    @Override
    public void doOnTouchDown() {

    }

    @Override
    public void doOnTouchMove() {
        Drawable mainBase = getTouchHelper() != null ? getTouchEventBundle().getDrawable() : null;
        if(mainBase != null && mainBase.isClicked(getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1()))) {
            translateSelectedObject(mainBase);
        }else{
            getMainCamera().translateBy(VectorHelper.toVector3f(convertPixelsToLocalUnit(getTouchHelper().getPointer1MovementDiff())));
        }
    }

    private void translateSelectedObject(Drawable selected) {
        selected.getCollider().translateTo(getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1()));
        Drawable planet = DrawableContainer.get(PLANET);
        Vector3f planetToMB = VectorHelper.sub(selected.getCollider().getTranslation(), planet.getCollider().getTranslation());
        float angleToPlanet = (float)Math.atan2(planetToMB.y, planetToMB.x);
        selected.getCollider().rotateTo(new Vector3f(0f,0f,(float)Math.toDegrees(angleToPlanet) - 90));
    }

    @Override
    public void doOnTouchUp() {

    }
}
