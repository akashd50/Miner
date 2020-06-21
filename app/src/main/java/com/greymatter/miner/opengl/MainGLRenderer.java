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

        MainGLObjectsHelper.groundQuad.translateBy(new Vector3f(0f,-1.5f,0f));

        MainGLObjectsHelper.planet.scaleTo(new Vector3f(100f,100f,1f));
        MainGLObjectsHelper.planet.translateBy(new Vector3f(0f,-100.5f, 0f));

        MainGLObjectsHelper.ball.scaleTo(new Vector3f(0.5f,0.5f,1f));
        MainGLObjectsHelper.ball.translateBy(new Vector3f(-0.5f,0f,0f));

        MainGLObjectsHelper.ball2.scaleTo(new Vector3f(0.2f,0.2f,1f));
        MainGLObjectsHelper.ball2.translateBy(new Vector3f(-0.5f,2f,0f));

        MainGLObjectsHelper.testLine = new Line(MainGLObjectsHelper.lineShader)
                .addVertices(MainGLObjectsHelper.ballCollider.asPolygonCollider()
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

        Drawable toDraw = MainGLObjectsHelper.backdropQuad;
        Drawable planet = MainGLObjectsHelper.planet;
        Drawable ball = MainGLObjectsHelper.ball;
        Drawable ball2 = MainGLObjectsHelper.ball2;
        Drawable testLine = MainGLObjectsHelper.testLine;

        /*<---------------------------------------update----------------------------------------->*/

        //MainGLObjectsHelper.camera.translateXY(ball.getCollider().getTranslation());
        CollisionDetectionSystem.updateSystemObjectsForces();

        ArrayList<Vector3f> vertexData = new ArrayList<>();
        vertexData.add(ball.getTranslation());
        Vector3f accPoint = new Vector3f(ball.getTranslation());
        accPoint.add(VectorHelper.multiply(ball.getVelocity(),40f));
        vertexData.add(accPoint);

        ((Line)testLine).updateVertexData(vertexData);

        /*<-----------------------------------draw quads----------------------------------------->*/
        ShaderHelper.useProgram(MainGLObjectsHelper.quadShader);
        ShaderHelper.setCameraProperties(toDraw.getShader(), MainGLObjectsHelper.camera);
        toDraw.onDrawFrame();

        /*<-----------------------------------draw 3d objects------------------------------------>*/
        ShaderHelper.useProgram(MainGLObjectsHelper.threeDObjectShader);
        ShaderHelper.setCameraProperties(MainGLObjectsHelper.threeDObjectShader, MainGLObjectsHelper.camera);
        planet.onDrawFrame();
        ball.onDrawFrame();
        ball2.onDrawFrame();

        /*<------------------------------------drawLines----------------------------------------->*/
        ShaderHelper.useProgram(MainGLObjectsHelper.lineShader);
        ShaderHelper.setCameraProperties(MainGLObjectsHelper.lineShader, MainGLObjectsHelper.camera);
        testLine.onDrawFrame();
    }

    public void onDestroy() {
        MainGLObjectsHelper.onDestroy();
    }

}
