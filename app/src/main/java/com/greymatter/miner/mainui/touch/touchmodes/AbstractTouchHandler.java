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

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class AbstractTouchHandler {
    private TouchHelper touchHelper;
    private Camera mainCamera;
    private TouchEventBundle touchEventBundle;
    private IGameObject currentlySelectedObject;
    public AbstractTouchHandler(Camera mainCamera, TouchHelper touchHelper){
        this.touchHelper = touchHelper;
        this.mainCamera = mainCamera;
    }

    public boolean onTouch(View v, MotionEvent event) {
        Vector2f touchPoint = getLocalTouchPoint2f(getTouchHelper().getCurrTouchPoint1());
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchDown(touchPoint);
            case MotionEvent.ACTION_MOVE:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchMove(touchPoint);
            case MotionEvent.ACTION_UP:
                if(v.getId() == R.id.mainGLSurfaceView) return doOnTouchUp(touchPoint);
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
        return mainCamera.getCameraWidth() * x/mainCamera.getViewportWidth() - mainCamera.getCameraWidth()/2;
    }

    public float getLocalY(float y) {
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

    public IGameObject getCurrentlySelectedObject() {
        return currentlySelectedObject;
    }

    public void setTouchEventBundle(TouchEventBundle touchEventBundle) {
        this.touchEventBundle = touchEventBundle;
    }

    public abstract void onClick(View v);
    public abstract void onLongClick(View v);

    public boolean doOnTouchDown(Vector2f touchPoint) {
        for(IGameObject gameObject : gameObjectsForTouchChecking()) {
            if(doOnTouchDownHelper(gameObject, touchPoint)) {
                return true;
            }
        }
        return false;
    }

    private boolean doOnTouchDownHelper(IGameObject gameObject, Vector2f touchPoint) {
        for(IGameObject child : gameObject.getForegroundChildren()) {
            if(doOnTouchDownHelper(child, touchPoint)) {
                return true;
            }
        }

        if(gameObject.isClicked(touchPoint)) {
            gameObject.setTouchDownOffset(VectorHelper.sub(touchPoint, VectorHelper.toVector2f(gameObject.getLocation())));

            if(gameObject.getOnTouchListener() != null && gameObject.getOnTouchListener().onTouchDown(gameObject, touchPoint)) {
                currentlySelectedObject = gameObject;
                return true;
            }
        }

        for(IGameObject child : gameObject.getBackgroundChildren()) {
            if(doOnTouchDownHelper(child, touchPoint)) {
                return true;
            }
        }
        return false;
    }

    public boolean doOnTouchMove(Vector2f touchPoint) {
        if(currentlySelectedObject!=null) {
            return currentlySelectedObject.getOnTouchListener()!=null
                    && currentlySelectedObject.getOnTouchListener().onTouchMove(currentlySelectedObject, touchPoint);
        }
        return false;
    }

    public boolean doOnTouchUp(Vector2f touchPoint) {
        if(currentlySelectedObject!=null) {
            boolean touchHandled, clickHandled;
            touchHandled = (currentlySelectedObject.getOnTouchListener() != null
                    && currentlySelectedObject.getOnTouchListener().onTouchUp(currentlySelectedObject, touchPoint));
            clickHandled = !touchHelper.isTouchPoint1Drag()
                    && currentlySelectedObject.getOnClickListener() != null
                    && currentlySelectedObject.getOnClickListener().onClick(currentlySelectedObject);

            currentlySelectedObject = null;
            return touchHandled || clickHandled;
        }

        return false;
    }

    public abstract ArrayList<IGameObject> gameObjectsForTouchChecking();
    public abstract ViewMode getViewMode();
}
