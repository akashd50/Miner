package com.greymatter.miner.mainui.touch;

import android.graphics.drawable.DrawableContainer;
import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.game.containers.GameObjectsContainer;
import com.greymatter.miner.game.objects.GameObject;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

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

    public static boolean onTouch(View v, MotionEvent event) {
        touchHelper.onTouch(event);
        return ViewModeManager.getActiveTouchMode().onTouch(v, event);
    }

    public static void onClick(View v) {
        ViewModeManager.getActiveTouchMode().onClick(v);
    }
    public static void onLongClick(View v) {
        ViewModeManager.getActiveTouchMode().onClick(v);
    }
}
