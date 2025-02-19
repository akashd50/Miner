package com.greymatter.miner.mainui.renderers;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import android.opengl.GLES30;

import com.greymatter.miner.mainui.viewmode.ViewModeManager;

public class MainGLRenderer implements GLSurfaceView.Renderer  {

    public MainGLRenderer() {}

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glEnable(GLES20.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);

        MainGLHelper.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);

        MainGLHelper.onSurfaceChanged(width, height);
        MainGLHelper.gameCamera.translateTo(new Vector3f(0f,0f,30f));
        MainGLHelper.UICamera.translateTo(new Vector3f(0f,0f,30f));

        MainGLHelper.gameCamera.setZoomValue(5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        ViewModeManager.getActiveRenderer().onDrawFrame();
    }

    public void onDestroy() {
        MainGLHelper.onDestroy();
    }
}
