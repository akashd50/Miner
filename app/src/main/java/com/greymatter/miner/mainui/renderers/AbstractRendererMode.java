package com.greymatter.miner.mainui.renderers;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import com.greymatter.miner.mainui.touch.MainGLTouchHelper;
import com.greymatter.miner.mainui.touch.TouchController;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

public abstract class AbstractRendererMode {
    private TouchController touchController;
    private Camera mainCamera;

    public AbstractRendererMode(TouchController touchController, Camera camera) {
        this.touchController = touchController;
        this.mainCamera = camera;
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public TouchController getTouchController() {
        return touchController;
    }

    public void onDrawFrame() {
        GLES30.glClearColor(0.05f,0.05f,0.1f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        CollisionDetectionSystem.updateSystemObjectsForces();
    }
}
