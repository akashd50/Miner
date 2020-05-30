package com.greymatter.miner.opengl;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.objects.Camera;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Quad;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.Triangle;

import javax.vecmath.Vector3f;

class MainGLRendererHelper {
    static Camera camera;
    static Shader simpleTriangleShader, quadShader;
    static Triangle triangle;
    static Material material;
    static Quad quad;

    static void initializeCamera(int width, int height) {
        camera = new Camera(width, height);
    }

    static void loadObjects() {
        triangle = new Triangle(simpleTriangleShader);
        quad = new Quad(new Vector3f(0.0f,0.0f,0.0f), material, quadShader);
    }

    static void loadMaterials() {
        material = new Material("download.jpg","");
    }

    static void loadShaders() {
        simpleTriangleShader = new Shader(Constants.SIMPLE_TRIANGLE_SHADER);
        quadShader = new Shader(Constants.QUAD_SHADER);
    }
}
