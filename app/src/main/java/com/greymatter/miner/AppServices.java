package com.greymatter.miner;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;
import android.view.ScaleGestureDetector;

import com.greymatter.miner.mainui.renderers.MainGLRenderer;

public class AppServices {
    private static AssetManager assetManager;
    private static Context context;
    private static GLSurfaceView surfaceView;
    private static MainGLRenderer glRenderer;
    private static ScaleGestureDetector scaleGestureDetector;

    public static void init(Context ctx, GLSurfaceView surface, MainGLRenderer renderer) {
        context = ctx;
        assetManager = context.getAssets();
        surfaceView = surface;
        glRenderer = renderer;
    }

    public static Context getAppContext() {
        return context;
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
