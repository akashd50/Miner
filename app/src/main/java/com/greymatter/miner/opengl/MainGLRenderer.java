package com.greymatter.miner.opengl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.Triangle;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        MainGLRendererHelper.loadShaders();
        MainGLRendererHelper.loadMaterials();
        MainGLRendererHelper.loadObjects();

        MainGLRendererHelper.quad.scaleTo(new Vector2f(0.5f,0.1f));
        MainGLRendererHelper.quad.translateTo(new Vector3f());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);

        MainGLRendererHelper.initializeCamera(width, height);
        MainGLRendererHelper.camera.translateTo(new Vector3f(0f,0f,5f));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1.0f,0f,0f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        MainGLRendererHelper.quad.rotateBy(new Vector3f(0f,0f,0.1f));
        ShaderHelper.useProgram(MainGLRendererHelper.quadShader);
        ShaderHelper.setCameraProperties(MainGLRendererHelper.quadShader, MainGLRendererHelper.camera);
        MainGLRendererHelper.quad.onDrawFrame();
    }

}
