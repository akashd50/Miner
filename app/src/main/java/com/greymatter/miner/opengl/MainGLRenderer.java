package com.greymatter.miner.opengl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Shader;

public class MainGLRenderer implements GLSurfaceView.Renderer {
    private Camera camera;
    private Shader simpleTriangleShader;
    private Triangle triangle;

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        simpleTriangleShader = new Shader(Constants.SIMPLE_TRIANGLE);
        triangle = new Triangle(simpleTriangleShader.getProgram());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
        camera = new Camera(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1.0f,0f,0f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        float[] modelMat = new float[16];
        Matrix.setIdentityM(modelMat,0);

        simpleTriangleShader.useProgram();

        int matLocation = GLES30.glGetUniformLocation(simpleTriangleShader.getProgram(), "projection");
        GLES30.glUniformMatrix4fv(matLocation, 1,false, camera.getProjectionMatrix(), 0);
        int viewLoc = GLES30.glGetUniformLocation(simpleTriangleShader.getProgram(), "view");
        GLES30.glUniformMatrix4fv(viewLoc, 1,false, camera.getViewMatrix(), 0);
        int modelLoc = GLES30.glGetUniformLocation(simpleTriangleShader.getProgram(), "model");
        GLES30.glUniformMatrix4fv(modelLoc, 1,false, modelMat, 0);


        GLES30.glBindVertexArray(triangle.vertexArrayObject[0]);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }

}
