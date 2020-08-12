package com.greymatter.miner.mainui.renderers;

import android.util.Log;

import com.greymatter.miner.AppServices;
import com.greymatter.miner.loaders.Loader;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

class MainGLObjectsHelper {
    static Camera camera;
    static boolean initialSetup = true;
    static void onSurfaceChanged(int width, int height) {
        if(initialSetup) {
            camera = new Camera(width, height);
            AppServices.setCamera(camera);
            initialSetup = false;
        }else{
            camera.onSurfaceChanged(width,height);
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