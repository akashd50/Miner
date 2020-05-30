package com.greymatter.miner.opengl;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import android.opengl.GLES30;
import android.opengl.Matrix;
import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;

public class MainGLRenderer implements GLSurfaceView.Renderer {
    private Camera camera;
    private Shader simpleTriangleShader, quadShader;
    private Triangle triangle;
    private Material material;
    private Quad quad;

    public MainGLRenderer() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        simpleTriangleShader = new Shader(Constants.SIMPLE_TRIANGLE_SHADER);
        quadShader = new Shader(Constants.QUAD_SHADER);

        triangle = new Triangle(simpleTriangleShader);

        material = new Material(Constants.TEXTURES+"download.jpg",Constants.TEXTURES+"sample_particle.png");

        quad = new Quad(new Vector3f(0.0f,0.0f,0.0f), material, quadShader);
        quad.scaleTo(new Vector2f(0.5f,0.1f));
        quad.translateTo(new Vector3f());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
        camera = new Camera(width, height);
        camera.translateTo(new Vector3f(0f,0f,5f));
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(1.0f,0f,0f,1f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);


        ShaderHelper.useProgram(quadShader);
        ShaderHelper.setCameraProperties(quadShader, camera);
        quad.onDrawFrame();
    }

}
