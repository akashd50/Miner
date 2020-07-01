package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.MotionEvent;
import android.view.View;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class AbstractTouchMode {
    private TouchHelper touchHelper;
    private Camera mainCamera;
    private TouchEventBundle touchEventBundle;
    public AbstractTouchMode(Camera mainCamera, TouchHelper touchHelper){
        this.touchHelper = touchHelper;
        this.mainCamera = mainCamera;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchHelper.onTouchDown(event);
                return doOnTouchDown(v);
            case MotionEvent.ACTION_MOVE:
                touchHelper.onTouchMove(event);
                return doOnTouchMove(v);
            case MotionEvent.ACTION_UP:
                touchHelper.onTouchUp(event);
                return doOnTouchUp(v);
            case MotionEvent.ACTION_POINTER_DOWN:
                touchHelper.onTouchDown(event);
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                touchHelper.onTouchUp(event);
                return true;
        }
        return false;
    }

    public Vector3f getLocalTouchPoint3f(Vector2f touchPoint) {
        return new Vector3f(getLocalX(touchPoint.x), getLocalY(touchPoint.y), 0f);
    }

    public Vector2f getLocalTouchPoint2f(Vector2f touchPoint) {
        return new Vector2f(getLocalX(touchPoint.x), getLocalY(touchPoint.y));
    }

    public Vector2f getLocalTouchPointVector(float x, float y) {
        return new Vector2f(getLocalX(x), getLocalY(y));
    }

    public float getLocalX(float x) {
        return mainCamera.getTranslation().x + mainCamera.getCameraWidth() * x/mainCamera.getViewportWidth() - mainCamera.getCameraWidth()/2;
    }

    public float getLocalY(float y) {
        return mainCamera.getTranslation().y + mainCamera.getCameraHeight()/2 - mainCamera.getCameraHeight() * y/mainCamera.getViewportHeight();
    }

    public Vector2f convertPixelsToLocalUnit(Vector2f pixels) {
        return new Vector2f(-(mainCamera.getCameraWidth()/mainCamera.getViewportWidth())*pixels.x,
                (mainCamera.getCameraHeight()/mainCamera.getViewportHeight())*pixels.y);
    }

    public Camera getMainCamera() {
        return this.mainCamera;
    }

    public TouchHelper getTouchHelper() {
        return this.touchHelper;
    }

    public TouchEventBundle getTouchEventBundle() {
        return touchEventBundle;
    }

    public void setTouchEventBundle(TouchEventBundle touchEventBundle) {
        this.touchEventBundle = touchEventBundle;
    }

    public abstract void onClick(View v);
    public abstract void onLongClick(View v);
    public abstract boolean doOnTouchDown(View v);
    public abstract boolean doOnTouchMove(View v);
    public abstract boolean doOnTouchUp(View v);
}
