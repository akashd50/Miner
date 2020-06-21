package com.greymatter.miner.opengl;

import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.R;
import com.greymatter.miner.generalhelpers.TouchController;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.objects.Camera;

import javax.vecmath.Vector3f;

import static com.greymatter.miner.opengl.MainGLObjectsHelper.*;

public class MainGLTouchHelper {
    private static Camera camera;
    private static TouchController touchController;
    public static void onViewChanged(Camera cam) {
        camera = cam;
        touchController = new TouchController();
    }

    public static void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchController.onTouchDown(event);
                doOnTouchDown();
                break;
            case MotionEvent.ACTION_MOVE:
                touchController.onTouchMove(event);
                doOnTouchMove();
                break;
            case MotionEvent.ACTION_UP:
                touchController.onTouchUp(event);
                doOnTouchUp();
                break;
        }
    }

    public static void onClick(View v) {
        switch (v.getId()) {
            case R.id.move_left:
                Vector3f left = VectorHelper.getNormal(ball.getCollider().getUpVector());
                ball.updateVelocity(VectorHelper.multiply(left, 0.01f));
                break;
            case R.id.move_right:
                Vector3f right = VectorHelper.multiply(VectorHelper.getNormal(ball.getCollider().getUpVector()), -1f);
                ball.updateVelocity(VectorHelper.multiply(right, 0.01f));
                break;
            default:
                break;
        }
    }

    private static void doOnTouchDown() {

    }

    private static void doOnTouchMove() {
        camera.translateBy(convertToLocalUnit(touchController.getPointer1MovementDiff()));
    }

    private static void doOnTouchUp() {
        if(!touchController.isTouchPoint1Drag()) {
            Vector3f touchPoint = getLocalTouchPointVector(touchController.getCurrTouchPoint1());

            ball.translateTo(touchPoint);
            ball.setVelocity(new Vector3f(0f, 0f, 0f));
            ball.rotateTo(new Vector3f());
            ball.getCollider().setAngularAcceleration(0f);
            ball.getCollider().setAngularVelocity(0f);

//            Vector3f newPos = new Vector3f(touchPoint);
//            newPos.y += 2.5f;
//
//            ball2.translateTo(newPos);
//            ball2.setVelocity(new Vector3f(0f, -1.05f, 0f));
//            ball2.rotateTo(new Vector3f());
//            ball2.getCollider().setAngularAcceleration(0f);
//            ball2.getCollider().setAngularVelocity(0f);
        }
    }

    private static Vector3f getLocalTouchPointVector(Vector3f touchPoint) {
        return new Vector3f(getLocalX(touchPoint.x), getLocalY(touchPoint.y), 0f);
    }

    private static Vector3f getLocalTouchPointVector(float x, float y) {
        return new Vector3f(getLocalX(x), getLocalY(y), 0f);
    }

    private static float getLocalX(float x) {
        return camera.getTranslation().x + camera.getCameraWidth() * x/camera.getViewportWidth() - camera.getCameraWidth()/2;
    }

    private static float getLocalY(float y) {
        return camera.getTranslation().y + camera.getCameraHeight()/2 - camera.getCameraHeight() * y/camera.getViewportHeight();
    }

    private static Vector3f convertToLocalUnit(Vector3f pixels) {
        return new Vector3f(-(camera.getCameraWidth()/camera.getViewportWidth())*pixels.x,
                            (camera.getCameraHeight()/camera.getViewportHeight())*pixels.y, 0f);
    }
}
