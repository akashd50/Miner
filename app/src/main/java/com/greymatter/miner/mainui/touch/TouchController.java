package com.greymatter.miner.mainui.touch;

import android.view.MotionEvent;

import com.greymatter.miner.generalhelpers.VectorHelper;

import javax.vecmath.Vector3f;

public class TouchController {
    private Vector3f touchPoint1TouchDownPt, prevTouchPoint1, currTouchPoint1, currTouchPoint2;
    private long touchPoint1DownTime, touchPoint2DownTime, touchPoint1UpTime, touchPoint2UpTime;
    public TouchController() {
        currTouchPoint1 = new Vector3f();
        currTouchPoint2 = new Vector3f();
        touchPoint1TouchDownPt = new Vector3f();
    }

    public void onTouchDown(MotionEvent event) {
        touchPoint1DownTime = System.currentTimeMillis();
        currTouchPoint1.x = event.getX();
        currTouchPoint1.y = event.getY();

        touchPoint1TouchDownPt.x = event.getX();
        touchPoint1TouchDownPt.y = event.getY();
    }

    public void onTouchMove(MotionEvent event) {
        prevTouchPoint1 = new Vector3f(currTouchPoint1);
        currTouchPoint1.x = event.getX();
        currTouchPoint1.y = event.getY();
    }

    public void onTouchUp(MotionEvent event) {
        touchPoint1UpTime = System.currentTimeMillis();
        currTouchPoint1.x = event.getX();
        currTouchPoint1.y = event.getY();
    }

    public Vector3f getPointer1MovementDiff() {
        if(currTouchPoint1!=null && prevTouchPoint1!=null) {
            return VectorHelper.sub(currTouchPoint1, prevTouchPoint1);
        }
        return new Vector3f();
    }

    public boolean isTouchPoint1LongPress() {
        return touchPoint1UpTime - touchPoint1DownTime > 500;
    }

    public boolean isTouchPoint1Drag() {
        Vector3f diff = VectorHelper.sub(touchPoint1TouchDownPt, currTouchPoint1);
        return Math.abs(diff.x) > 20 || Math.abs(diff.y) > 20;
    }

    public Vector3f getPrevTouchPoint1() {
        return prevTouchPoint1;
    }

    public Vector3f getCurrTouchPoint1() {
        return currTouchPoint1;
    }

    public Vector3f getCurrTouchPoint2() {
        return currTouchPoint2;
    }

    public long getTouchPoint1DownTime() {
        return touchPoint1DownTime;
    }

    public long getTouchPoint2DownTime() {
        return touchPoint2DownTime;
    }
}
