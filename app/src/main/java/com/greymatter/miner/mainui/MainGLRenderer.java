package com.greymatter.miner.mainui;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector3f;
import android.opengl.GLES30;
import com.greymatter.miner.containers.DrawableContainer;
import com.greymatter.miner.game.containers.BackgroundObjectsContainer;
import com.greymatter.miner.game.containers.InteractiveGameObjectsContainer;
import com.greymatter.miner.game.containers.GameBuildingsContainer;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import java.util.ArrayList;
import static com.greymatter.miner.game.GC.*;

public class MainGLRenderer implements GLSurfaceView.Renderer {

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glEnable(GLES20.GL_BLEND);
        GLES30.glEnable(GLES20.GL_DEPTH_TEST);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        MainGLObjectsHelper.loadShaders();
        MainGLObjectsHelper.loadMaterials();
        MainGLObjectsHelper.loadObjects();
        MainGLObjectsHelper.loadPhysicsObjects();
        MainGLObjectsHelper.finishObjectsSetup();
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
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        Drawable planet = DrawableContainer.get(PLANET);
        Drawable mainCharacter = DrawableContainer.get(MAIN_CHARACTER);
        Drawable testLine = DrawableContainer.get(TEST_LINE);

        /*<---------------------------------------update----------------------------------------->*/
        //MainGLObjectsHelper.camera.translateXY(mainCharacter.getCollider().getTranslation());

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

        /*<-----------------------------------------draw----------------------------------------->*/
        BackgroundObjectsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
        GameBuildingsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
        InteractiveGameObjectsContainer.onDrawFrameByShader(MainGLObjectsHelper.camera);
    }

    public void onDestroy() {
        MainGLObjectsHelper.onDestroy();
    }
}
