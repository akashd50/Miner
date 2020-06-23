package com.greymatter.miner.mainui;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import android.opengl.GLES30;

import com.greymatter.miner.game.ControllableGameObjectsContainer;
import com.greymatter.miner.game.GameBuildingsContainer;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;

import java.util.ArrayList;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glEnable(GLES20.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        MainGLObjectsHelper.loadShaders();
        MainGLObjectsHelper.loadMaterials();
        MainGLObjectsHelper.loadObjects();
        MainGLObjectsHelper.loadPhysicsObjects();

        MainGLObjectsHelper.atmosphere.getCollider().scaleTo(new Vector3f(150f,150f,1f));
        MainGLObjectsHelper.atmosphere.getCollider().translateBy(new Vector3f(0f,-75f, 0f));

        MainGLObjectsHelper.planet.getCollider().scaleTo(new Vector3f(100f,100f,1f));
        MainGLObjectsHelper.planet.getCollider().translateBy(new Vector3f(0f,-100.5f, 0f));

        MainGLObjectsHelper.mainCharacter.getCollider().scaleTo(new Vector3f(0.5f,0.5f,1f));
        MainGLObjectsHelper.mainCharacter.getCollider().translateBy(new Vector3f(-0.5f,0f,0f));

        MainGLObjectsHelper.testBall.getCollider().scaleTo(new Vector3f(0.2f,0.2f,1f));
        MainGLObjectsHelper.testBall.getCollider().translateBy(new Vector3f(-0.5f,2f,0f));

        MainGLObjectsHelper.mainBase.getCollider().scaleTo(new Vector3f(4f,2.7f,1f));
        MainGLObjectsHelper.mainBase.getCollider().translateTo(new Vector3f(-2.4f,2f,0f));

        MainGLObjectsHelper.testLine = new Line("testLine", MainGLObjectsHelper.lineShader)
                .addVertices(MainGLObjectsHelper.mainCharCollider.asPolygonCollider()
                        .getTransformedVertices()).build();

        MainGLObjectsHelper.initiatePhysicsProcesses();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);

        MainGLObjectsHelper.onSurfaceChanged(width, height);
        MainGLTouchHelper.onViewChanged(MainGLObjectsHelper.camera);
        //MainGLObjectsHelper.camera.translateTo(new Vector3f(0f,-100f,5f));
        //MainGLObjectsHelper.camera.updateZoomValue(120f);
        MainGLObjectsHelper.camera.translateTo(new Vector3f(0f,0f,5f));
        MainGLObjectsHelper.camera.updateZoomValue(5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0.05f,0.05f,0.1f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        Drawable planet = MainGLObjectsHelper.planet;
        Drawable mainCharacter = MainGLObjectsHelper.mainCharacter;
        Drawable ball2 = MainGLObjectsHelper.testBall;
        Drawable testLine = MainGLObjectsHelper.testLine;

        /*<---------------------------------------update----------------------------------------->*/
        MainGLObjectsHelper.camera.translateXY(mainCharacter.getCollider().getTranslation());

        Vector3f fromCenterToCam = VectorHelper.sub(MainGLObjectsHelper.camera.getTranslation(), planet.getCollider().getTranslation());
        fromCenterToCam.normalize();
        MainGLObjectsHelper.camera.setUpVector(fromCenterToCam);

        CollisionDetectionSystem.updateSystemObjectsForces();

        ArrayList<Vector3f> vertexData = new ArrayList<>();
        vertexData.add(mainCharacter.getCollider().getTranslation());
        Vector3f accPoint = new Vector3f(mainCharacter.getCollider().getTranslation());
        accPoint.add(VectorHelper.multiply(mainCharacter.getCollider().getVelocity(),40f));
        vertexData.add(accPoint);

        ((Line)testLine).updateVertexData(vertexData);

        /*<-----------------------------------draw quads----------------------------------------->*/
//        ShaderHelper.useProgram(MainGLObjectsHelper.quadShader);
//        ShaderHelper.setCameraProperties(toDraw.getShader(), MainGLObjectsHelper.camera);


        /*<-----------------------------------draw 2d objects------------------------------------>*/
        useShader(MainGLObjectsHelper.threeDObjectShader);
        MainGLObjectsHelper.atmosphere.onDrawFrame();

        /*---------------------------------draw game buildings----------------------------------->*/
        useShader(MainGLObjectsHelper.quadShader);
        GameBuildingsContainer.onDrawFrame();

        useShader(MainGLObjectsHelper.threeDObjectShader);
        planet.onDrawFrame();
        ControllableGameObjectsContainer.onDrawFrame();

        /*<------------------------------------drawLines----------------------------------------->*/
        useShader(MainGLObjectsHelper.lineShader);
        testLine.onDrawFrame();

    }

    private void useShader(Shader shader) {
        ShaderHelper.useProgram(shader);
        ShaderHelper.setCameraProperties(shader, MainGLObjectsHelper.camera);
    }

    public void onDestroy() {
        MainGLObjectsHelper.onDestroy();
    }

}
