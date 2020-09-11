package com.greymatter.miner;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;

import com.greymatter.miner.mainui.renderers.MainGLRenderer;
import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;

public class AppServices {
    private static AssetManager assetManager;
    private static Context context;
    private static GLSurfaceView surfaceView;
    private static MainGLRenderer glRenderer;
    private static Camera gameCamera, uiCamera;
    private static TouchHelper touchHelper;
    public static void setContext(Context ctx) {
        context = ctx;
        assetManager = ctx.getAssets();
    }

    public static void setTouchHelper(TouchHelper th) {
        touchHelper = th;
    }

    public static void setGLSurfaceView(GLSurfaceView surface) {
        surfaceView = surface;
    }

    public static void setMainGLRenderer(MainGLRenderer renderer) {
        glRenderer = renderer;
    }

    public static void setGameCamera(Camera camera) {
        gameCamera = camera;
    }

    public static void setUICamera(Camera uiCamera) {
        AppServices.uiCamera = uiCamera;
    }

    public static Camera getUICamera() {
        return uiCamera;
    }

    public static Context getAppContext() {
        return context;
    }

    public static Camera getGameCamera() {
        return gameCamera;
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

    public static TouchHelper getTouchHelper() {
        return touchHelper;
    }
}
