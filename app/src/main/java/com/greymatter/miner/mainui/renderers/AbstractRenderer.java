package com.greymatter.miner.mainui.renderers;

import android.opengl.GLES30;

import com.greymatter.miner.mainui.touch.TouchHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

public abstract class AbstractRenderer {
    private TouchHelper touchHelper;
    private Camera mainCamera;

    public AbstractRenderer(TouchHelper touchHelper, Camera camera) {
        this.touchHelper = touchHelper;
        this.mainCamera = camera;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public TouchHelper getTouchHelper() {
        return touchHelper;
    }

    public synchronized void onDrawFrame() {
        GLES30.glClearColor(0.05f,0.05f,0.1f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    }
}
