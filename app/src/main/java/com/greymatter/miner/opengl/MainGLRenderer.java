package com.greymatter.miner.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Drawable;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glEnable(GLES20.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        MainGLRendererHelper.loadShaders();
        MainGLRendererHelper.loadMaterials();
        MainGLRendererHelper.loadObjects();

        MainGLRendererHelper.groundQuad.translateBy(new Vector3f(0f,-1.5f,0f));
        MainGLRendererHelper.characterQuad.scaleTo(new Vector3f(0.07f,0.1f,0f));
        MainGLRendererHelper.testObject.scaleTo(new Vector3f(50f,50f,1f));
        MainGLRendererHelper.testObject.translateBy(new Vector3f(0f,-50.5f, 0f));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);

        MainGLRendererHelper.onSurfaceChanged(width, height);
        MainGLRendererHelper.camera.translateTo(new Vector3f(0f,0f,5f));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0.05f,0.05f,0.1f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        Drawable toDraw = MainGLRendererHelper.backdropQuad;
        Drawable ground = MainGLRendererHelper.groundQuad;
        Drawable character = MainGLRendererHelper.characterQuad;
        Drawable monkey = MainGLRendererHelper.testObject;

        //draw quads
        ShaderHelper.useProgram(toDraw.getShader());
        ShaderHelper.setCameraProperties(toDraw.getShader(), MainGLRendererHelper.camera);

        //toDraw.onDrawFrame();
        //ground.onDrawFrame();
        character.rotateBy(new Vector3f(1f,1f,0f));
        character.onDrawFrame();

        //draw 3d objects
        //monkey.rotateBy(new Vector3f(1f,0f,0f));
        ShaderHelper.useProgram(monkey.getShader());
        ShaderHelper.setCameraProperties(monkey.getShader(), MainGLRendererHelper.camera);
        monkey.onDrawFrame();
    }

}
