package com.greymatter.miner.mainui.viewmodes;

import android.view.MotionEvent;
import android.view.View;

public interface IViewMode {
    default void onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                doOnTouchDown();
                break;
            case MotionEvent.ACTION_MOVE:
                doOnTouchMove();
                break;
            case MotionEvent.ACTION_UP:
                doOnTouchUp();
                break;
        }
    }

    void onClick(View v);
    void doOnTouchDown();
    void doOnTouchMove();
    void doOnTouchUp();
}
