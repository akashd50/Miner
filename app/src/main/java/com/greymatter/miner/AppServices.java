package com.greymatter.miner;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.view.ScaleGestureDetector;

import com.greymatter.miner.mainui.renderers.MainGLRenderer;
import com.greymatter.miner.opengl.objects.Camera;

public class AppServices {
    private static AssetManager assetManager;
    private static Context context;
    private static GLSurfaceView surfaceView;
    private static MainGLRenderer glRenderer;
    private static Camera mainCamera;
    public static void setContext(Context ctx) {
        context = ctx;
        assetManager = ctx.getAssets();
    }

    public static void setGLSurfaceView(GLSurfaceView surface) {
        surfaceView = surface;
    }

    public static void setMainGLRenderer(MainGLRenderer renderer) {
        glRenderer = renderer;
    }

    public static void setCamera(Camera camera) {
        mainCamera = camera;
    }

    public static Context getAppContext() {
        return context;
    }

    public static Camera getMainCamera() {
        return mainCamera;
    }

    public static GLSurfaceView getSurfaceView() {
        return surfaceView;
    }

    public static MainGLRenderer getGLRenderer() {
        return glRenderer;
    }

    public static Activity getAppContextAsActivity() {
        return (Activity)context;
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }
}
