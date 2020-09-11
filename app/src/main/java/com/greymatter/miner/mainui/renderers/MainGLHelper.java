package com.greymatter.miner.mainui.renderers;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.loaders.Loader;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.mainui.viewmode.ViewModeManager;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

public class MainGLHelper {
    static TouchHelper touchHelper;
    static Camera gameCamera, UICamera;
    static boolean initialSetup = true;
    static void onSurfaceChanged(int width, int height) {
        if(initialSetup) {
            gameCamera = new Camera(width, height);
            UICamera = new Camera(width, height);
            AppServices.setGameCamera(gameCamera);
            AppServices.setUICamera(UICamera);

            touchHelper = new TouchHelper();
            AppServices.setTouchHelper(touchHelper);

            ViewModeManager.switchToGeneralMode(touchHelper, AppServices.getGameCamera());
            ViewModeManager.setActiveUITouchHandler(touchHelper, AppServices.getUICamera());
            initialSetup = false;
        }else{
            gameCamera.onSurfaceChanged(width,height);
            UICamera.onSurfaceChanged(width, height);
        }
    }

    static void onSurfaceCreated() {
        Loader.loadAll();
        CollisionDetectionSystem.initialize();
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

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}