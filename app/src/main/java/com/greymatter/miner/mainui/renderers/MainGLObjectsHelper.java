package com.greymatter.miner.mainui.renderers;

import android.util.Log;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.loaders.Loader;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

class MainGLObjectsHelper {
    static Camera gameCamera, UICamera;
    static boolean initialSetup = true;
    static void onSurfaceChanged(int width, int height) {
        if(initialSetup) {
            gameCamera = new Camera(width, height);
            UICamera = new Camera(width, height);
            AppServices.setGameCamera(gameCamera);
            AppServices.setUICamera(UICamera);
            initialSetup = false;
        }else{
            gameCamera.onSurfaceChanged(width,height);
            UICamera.onSurfaceChanged(width, height);
        }
    }

    static void initialize() {
        Loader.loadAll();
        CollisionDetectionSystem.initialize();
    }

    static void onDestroy() {
        Log.v("On Destroy","Closing all background threads");
        CollisionDetectionSystem.onDestroy();
    }
}