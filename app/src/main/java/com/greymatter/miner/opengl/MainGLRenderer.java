package com.greymatter.miner.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Drawable;
import com.greymatter.miner.opengl.objects.Line;

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
        MainGLRendererHelper.loadPhysicsObjects();

        MainGLRendererHelper.groundQuad.getCollider().translateBy(new Vector3f(0f,-1.5f,0f));
        MainGLRendererHelper.characterQuad.getCollider().scaleTo(new Vector3f(0.07f,0.1f,0f));
        MainGLRendererHelper.planet.getCollider().scaleTo(new Vector3f(70f,70f,1f));
        MainGLRendererHelper.planet.getCollider().translateBy(new Vector3f(0f,-70.5f, 0f));
        MainGLRendererHelper.ball.getCollider().scaleTo(new Vector3f(0.2f,0.2f,1f));
        MainGLRendererHelper.ball.getCollider().translateBy(new Vector3f(-0.5f,0f,0f));

        MainGLRendererHelper.testLine = new Line(MainGLRendererHelper.lineShader)
                .addVertices(MainGLRendererHelper.ballCollider.asPolygonCollider()
                        .getTransformedVertices()).build();

        MainGLRendererHelper.initiatePhysicsProcesses();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);

        MainGLRendererHelper.onSurfaceChanged(width, height);
        MainGLTouchHelper.onViewChanged(MainGLRendererHelper.camera);
        MainGLRendererHelper.camera.translateTo(new Vector3f(0f,0f,5f));
        MainGLRendererHelper.camera.updateZoomValue(10.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0.05f,0.05f,0.1f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        Drawable toDraw = MainGLRendererHelper.backdropQuad;
        Drawable ground = MainGLRendererHelper.groundQuad;
        Drawable character = MainGLRendererHelper.characterQuad;
        Drawable planet = MainGLRendererHelper.planet;
        Drawable ball = MainGLRendererHelper.ball;
        Drawable testLine = MainGLRendererHelper.testLine;

        /*<---------------------------------------update----------------------------------------->*/
        ball.getCollider().update();


        /*<-----------------------------------draw quads----------------------------------------->*/
        ShaderHelper.useProgram(MainGLRendererHelper.quadShader);
        ShaderHelper.setCameraProperties(toDraw.getShader(), MainGLRendererHelper.camera);
        //toDraw.onDrawFrame();
        //ground.onDrawFrame();
        character.getCollider().rotateBy(new Vector3f(1f,1f,0f));
        character.onDrawFrame();

        /*<-----------------------------------draw 3d objects------------------------------------>*/
        ShaderHelper.useProgram(MainGLRendererHelper.threeDObjectShader);
        ShaderHelper.setCameraProperties(MainGLRendererHelper.threeDObjectShader, MainGLRendererHelper.camera);
        planet.onDrawFrame();
        ball.onDrawFrame();

        /*<------------------------------------drawLines----------------------------------------->*/
        ShaderHelper.useProgram(MainGLRendererHelper.lineShader);
        ShaderHelper.setCameraProperties(MainGLRendererHelper.lineShader, MainGLRendererHelper.camera);
        testLine.onDrawFrame();
    }

    public void onDestroy() {
        MainGLRendererHelper.onDestroy();
    }

}
