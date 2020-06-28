package com.greymatter.miner.mainui.renderers;

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
import com.greymatter.miner.mainui.touch.MainGLTouchHelper;
import com.greymatter.miner.mainui.touch.touchviewmodes.ViewModeManager;
import com.greymatter.miner.opengl.objects.drawables.Drawable;
import com.greymatter.miner.opengl.objects.drawables.Line;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.physics.collisioncheckers.CollisionDetectionSystem;
import java.util.ArrayList;
import static com.greymatter.miner.game.GC.*;

public class MainGLRenderer implements GLSurfaceView.Renderer  {

    public MainGLRenderer() {}

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
        MainGLTouchHelper.onSurfaceChanged(MainGLObjectsHelper.camera);
        //MainGLObjectsHelper.camera.translateTo(new Vector3f(0f,-100f,5f));
        //MainGLObjectsHelper.camera.updateZoomValue(120f);
        MainGLObjectsHelper.camera.translateTo(new Vector3f(0f,0f,5f));
        MainGLObjectsHelper.camera.setZoomValue(5f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        ViewModeManager.getActiveRendererMode().onDrawFrame();
    }

    public void onDestroy() {
        MainGLObjectsHelper.onDestroy();
    }
}
