package com.greymatter.miner.mainui.touch.touchviewmodes;

import android.view.View;

import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;

import javax.vecmath.Vector3f;

import static com.greymatter.miner.game.GC.MAIN_CHARACTER;

public class GeneralMode extends AbstractViewMode {
    public GeneralMode(TouchController controller, Camera camera) {
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
        Vector3f touchPoint = getLocalTouchPoint3f(getTouchController().getCurrTouchPoint1());
        if(DrawableContainer.get(MAIN_CHARACTER).isClicked(VectorHelper.toVector2f(touchPoint))){
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
