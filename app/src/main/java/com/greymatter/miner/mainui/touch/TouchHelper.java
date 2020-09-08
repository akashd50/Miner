package com.greymatter.miner.mainui.touch;

import android.util.Log;
import android.view.MotionEvent;

import com.greymatter.miner.helpers.VectorHelper;
import javax.vecmath.Vector2f;

public class TouchHelper {
    private Vector2f touchPoint1TouchDownPt, touchPoint2TouchDownPt, prevTouchPoint2,
                    prevTouchPoint1, currTouchPoint1, currTouchPoint2;
    private long touchPoint1DownTime, touchPoint2DownTime, touchPoint1UpTime, touchPoint2UpTime;
    private int pointer1Id, pointer2Id, currentPointerCount;
    private float previousPointersDist, currentPointersDist;
    public TouchHelper() {
        currTouchPoint1 = new Vector2f();
        currTouchPoint2 = new Vector2f();
        prevTouchPoint1 = new Vector2f();
        prevTouchPoint2 = new Vector2f();
        touchPoint1TouchDownPt = new Vector2f();
        touchPoint2TouchDownPt = new Vector2f();
        pointer1Id = -1;
        pointer2Id = -1;
    }

    public void onTouch(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onTouchUp(event);
                break;
        }
    }

    public void onTouchDown(MotionEvent event) {
        currentPointerCount = event.getPointerCount();
        if(pointer1Id == -1) {
            touchPoint1DownTime = System.currentTimeMillis();
            touchPoint1TouchDownPt.set(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
            currTouchPoint1.set(touchPoint1TouchDownPt);
            pointer1Id = event.getPointerId(event.getActionIndex());
            Log.v("ON TOUCH DOWN: : ", "Pointer 1");
        }else{
            touchPoint2DownTime = System.currentTimeMillis();
            touchPoint2TouchDownPt.set(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
            currTouchPoint2.set(touchPoint2TouchDownPt);
            pointer2Id = event.getPointerId(event.getActionIndex());
            Log.v("ON TOUCH DOWN: : ", "Pointer 2");
        }
    }

    public void onTouchMove(MotionEvent event) {
        if(event.getPointerId(event.getActionIndex()) == pointer1Id) {
            prevTouchPoint1.set(currTouchPoint1);
            currTouchPoint1.set(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
        }

        if(event.getPointerCount()==2 && event.getPointerId(1)!= -1) {
            prevTouchPoint2.set(currTouchPoint2);
            currTouchPoint2.set(event.getX(1), event.getY(1));
        }
    }

    public void onTouchUp(MotionEvent event) {
        currentPointerCount = event.getPointerCount();

        previousPointersDist = 0;
        currentPointersDist = 0;

        if(event.getPointerId(event.getActionIndex()) == pointer2Id) {
            resetPointer2Data();
        }

        if(event.getPointerId(event.getActionIndex()) == pointer1Id) {
            resetPointer1Data();
        }
    }

    private void resetPointer1Data() {
        touchPoint1UpTime = System.currentTimeMillis();
        pointer1Id = -1;
    }

    private void resetPointer2Data() {
        touchPoint2UpTime = System.currentTimeMillis();
        pointer2Id = -1;
    }

    public Vector2f getPointer1MovementDiff() {
        if(currTouchPoint1!=null && prevTouchPoint1!=null) {
            return VectorHelper.sub(currTouchPoint1, prevTouchPoint1);
        }
        return new Vector2f();
    }

    public Vector2f getPointer2MovementDiff() {
        if(currTouchPoint2!=null && prevTouchPoint2!=null) {
            return VectorHelper.sub(currTouchPoint2, prevTouchPoint2);
        }
        return new Vector2f();
    }

    public float getScalingFactor() {
        if(currentPointerCount == 2) {
            previousPointersDist = (float)VectorHelper.getDistanceWithSQRT(prevTouchPoint1, prevTouchPoint2);
            currentPointersDist = (float)VectorHelper.getDistanceWithSQRT(currTouchPoint1, currTouchPoint2);
        }
       return currentPointersDist - previousPointersDist;
    }

    public boolean isTouchPoint1LongPress() {
        return touchPoint1UpTime - touchPoint1DownTime > 500;
    }

    public boolean isTouchPoint1Drag() {
        Vector2f diff = VectorHelper.sub(touchPoint1TouchDownPt, currTouchPoint1);
        return Math.abs(diff.x) > 20 || Math.abs(diff.y) > 20;
    }

    public Vector2f getPrevTouchPoint1() {
        return prevTouchPoint1;
    }

    public Vector2f getCurrTouchPoint1() {
        return currTouchPoint1;
    }

    public Vector2f getCurrTouchPoint2() {
        return currTouchPoint2;
    }

    public boolean isTouchPoint1Down() {
        return pointer1Id != -1;
    }

    public boolean isTouchPoint2Down() {
        return pointer2Id != -1;
    }

    public long getTouchPoint1DownTime() {
        return touchPoint1DownTime;
    }

    public int getCurrentPointerCount() {
        return currentPointerCount;
    }

    public long getTouchPoint2DownTime() {
        return touchPoint2DownTime;
    }
}
