package com.greymatter.miner.mainui.touch.touchviewmodes;

import android.view.MotionEvent;
import android.view.View;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class AbstractViewMode {
    private TouchController touchController;
    private Camera mainCamera;
    private TouchEventBundle touchEventBundle;
    public AbstractViewMode(Camera mainCamera, TouchController touchController){
        this.touchController = touchController;
        this.mainCamera = mainCamera;
    }

    public void onTouch(MotionEvent event) {
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

    public Vector3f getLocalTouchPoint3f(Vector3f touchPoint) {
        return new Vector3f(getLocalX(touchPoint.x), getLocalY(touchPoint.y), 0f);
    }

    public Vector2f getLocalTouchPoint2f(Vector3f touchPoint) {
        return new Vector2f(getLocalX(touchPoint.x), getLocalY(touchPoint.y));
    }

    public Vector3f getLocalTouchPointVector(float x, float y) {
        return new Vector3f(getLocalX(x), getLocalY(y), 0f);
    }

    public float getLocalX(float x) {
        return mainCamera.getTranslation().x + mainCamera.getCameraWidth() * x/mainCamera.getViewportWidth() - mainCamera.getCameraWidth()/2;
    }

    public float getLocalY(float y) {
        return mainCamera.getTranslation().y + mainCamera.getCameraHeight()/2 - mainCamera.getCameraHeight() * y/mainCamera.getViewportHeight();
    }

    public Vector3f convertToLocalUnit(Vector3f pixels) {
        return new Vector3f(-(mainCamera.getCameraWidth()/mainCamera.getViewportWidth())*pixels.x,
                (mainCamera.getCameraHeight()/mainCamera.getViewportHeight())*pixels.y, 0f);
    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }

    public TouchController getTouchController() {
        return this.touchController;
    }

    public TouchEventBundle getTouchEventBundle() {
        return touchEventBundle;
    }

    public void setTouchEventBundle(TouchEventBundle touchEventBundle) {
        this.touchEventBundle = touchEventBundle;
    }

    public abstract void onClick(View v);
    public abstract void doOnTouchDown();
    public abstract void doOnTouchMove();
    public abstract void doOnTouchUp();
}
