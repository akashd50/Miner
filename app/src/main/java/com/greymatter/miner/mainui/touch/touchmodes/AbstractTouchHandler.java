package com.greymatter.miner.mainui.touch.touchmodes;

import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.R;
import com.greymatter.miner.containers.ToDrawContainer;
import com.greymatter.miner.game.objects.base.IGameObject;
import com.greymatter.miner.helpers.VectorHelper;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewMode;
import com.greymatter.miner.opengl.objects.Camera;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class AbstractTouchHandler {
    private TouchHelper touchHelper;
    private Camera mainCamera;
    private TouchEventBundle touchEventBundle;
    public AbstractTouchHandler(Camera mainCamera, TouchHelper touchHelper){
        this.touchHelper = touchHelper;
        this.mainCamera = mainCamera;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchDown(v);
            case MotionEvent.ACTION_MOVE:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchMove(v);
            case MotionEvent.ACTION_UP:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchUp(v);
        }
        return false;
    }

    public Vector3f getLocalTouchPoint3f(Vector2f touchPoint) {
        Vector3f touchPointLocal = new Vector3f(getLocalX(touchPoint.x), getLocalY(touchPoint.y), 0f);
        float angle = (float)Math.atan2(mainCamera.getUpVector().y, mainCamera.getUpVector().x);
        touchPointLocal = VectorHelper.rotateAroundZ(touchPointLocal, angle - (float)Math.toRadians(90));

        touchPointLocal.x+=mainCamera.getTranslation().x;
        touchPointLocal.y+=mainCamera.getTranslation().y;

        return touchPointLocal;
    }

    public Vector2f getLocalTouchPoint2f(Vector2f touchPoint) {
        Vector2f touchPointLocal = new Vector2f(getLocalX(touchPoint.x), getLocalY(touchPoint.y));
        float angle = (float)Math.atan2(mainCamera.getUpVector().y, mainCamera.getUpVector().x);
        touchPointLocal = VectorHelper.rotateAroundZ(touchPointLocal, (angle - (float)Math.toRadians(90)));

        touchPointLocal.x+=mainCamera.getTranslation().x;
        touchPointLocal.y+=mainCamera.getTranslation().y;

        return touchPointLocal;
    }

    public Vector2f getLocalTouchPointVector(float x, float y) {
        return new Vector2f(getLocalX(x), getLocalY(y));
    }

    public float getLocalX(float x) {
        //return mainCamera.getTranslation().x + mainCamera.getCameraWidth() * x/mainCamera.getViewportWidth() - mainCamera.getCameraWidth()/2;
        return mainCamera.getCameraWidth() * x/mainCamera.getViewportWidth() - mainCamera.getCameraWidth()/2;
    }

    public float getLocalY(float y) {
        //return mainCamera.getTranslation().y + mainCamera.getCameraHeight()/2 - mainCamera.getCameraHeight() * y/mainCamera.getViewportHeight();
        return mainCamera.getCameraHeight()/2 - mainCamera.getCameraHeight() * y/mainCamera.getViewportHeight();
    }

    public Vector2f devicePixelsToLocalUnit(Vector2f pixels) {
        Vector2f pixelsToLocal = new Vector2f(-(mainCamera.getCameraWidth()/mainCamera.getViewportWidth())*pixels.x,
                (mainCamera.getCameraHeight()/mainCamera.getViewportHeight())*pixels.y);
        float angle = (float)Math.atan2(mainCamera.getUpVector().y, mainCamera.getUpVector().x);
        pixelsToLocal = VectorHelper.rotateAroundZ(pixelsToLocal, (angle - (float)Math.toRadians(90)));
        return pixelsToLocal;
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

    public boolean doOnTouchDown(View v) {
        Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
        for(IGameObject gameObject : ToDrawContainer.getAllReversed()) {
            if(gameObject.onTouchDownEvent(touchPoint)) return true;
        }
        return false;
    }
    public boolean doOnTouchMove(View v) {
        Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
        for(IGameObject gameObject : ToDrawContainer.getAllReversed()) {
            if(gameObject.onTouchMoveEvent(touchPoint)) return true;
        }
        return false;
    }

    public boolean doOnTouchUp(View v) {
        Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
        for(IGameObject gameObject : ToDrawContainer.getAllReversed()) {
            if(gameObject.onTouchUpEvent(touchPoint)) return true;
        }
        return false;
    }

    public abstract ViewMode getViewMode();
}
