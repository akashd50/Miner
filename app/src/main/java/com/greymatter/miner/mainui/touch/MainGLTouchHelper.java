package com.greymatter.miner.mainui.touch;

import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;

public class MainGLTouchHelper {
    private static TouchHelper touchHelper;
    private static boolean initialSetup = true;
    public static void onSurfaceChanged() {
        if(initialSetup) {
            touchHelper = new TouchHelper();
            AppServices.setTouchHelper(touchHelper);

            ViewModeManager.switchToGeneralMode(touchHelper, AppServices.getGameCamera());
            ViewModeManager.setActiveUITouchHandler(touchHelper, AppServices.getUICamera());
            initialSetup = false;
        }
    }

    public static boolean onTouch(View v, MotionEvent event) {
        touchHelper.onTouch(event);
        return ViewModeManager.getActiveUITouchHandler().onTouch(v, event) || ViewModeManager.getActiveTouchHandler().onTouch(v, event);
    }

    public static void onClick(View v) {
        ViewModeManager.getActiveUITouchHandler().onClick(v);
        ViewModeManager.getActiveTouchHandler().onClick(v);
    }

    public static void onLongClick(View v) {
        ViewModeManager.getActiveUITouchHandler().onLongClick(v);
        ViewModeManager.getActiveTouchHandler().onLongClick(v);
    }
}
