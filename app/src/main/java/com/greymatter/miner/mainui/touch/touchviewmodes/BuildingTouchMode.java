package com.greymatter.miner.mainui.touch.touchviewmodes;

import android.view.View;

import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class BuildingTouchMode extends AbstractTouchMode {
    public BuildingTouchMode(TouchController controller, Camera camera) {
        super(camera, controller);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void doOnTouchDown() {

    }

    @Override
    public void doOnTouchMove() {
        Drawable mainBase = getTouchController() != null ? getTouchEventBundle().getDrawable() : null;
        if(mainBase!=null && mainBase.isClicked(getLocalTouchPoint2f(getTouchController().getCurrTouchPoint1()))) {
            mainBase.getCollider().translateTo(getLocalTouchPoint2f(getTouchController().getCurrTouchPoint1()));
        }else{
            getMainCamera().translateBy(convertToLocalUnit(getTouchController().getPointer1MovementDiff()));
        }
    }

    @Override
    public void doOnTouchUp() {

    }
}
