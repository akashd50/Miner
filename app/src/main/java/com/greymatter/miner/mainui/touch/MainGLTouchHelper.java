package com.greymatter.miner.mainui.touch;

import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;

public class MainGLTouchHelper {
    private static Camera camera;
    private static TouchHelper touchHelper;
    private static boolean initialSetup = true;
    public static void onSurfaceChanged(Camera cam) {
        if(initialSetup) {
            camera = cam;
            touchHelper = new TouchHelper();
            ViewModeManager.switchToGeneralMode(touchHelper, camera);
            initialSetup = false;
        }
    }

    public static void onTouch(MotionEvent event) {
        ViewModeManager.getActiveTouchMode().onTouch(event);
    }

    public static void onClick(View v) {
        ViewModeManager.getActiveTouchMode().onClick(v);
    }
}
