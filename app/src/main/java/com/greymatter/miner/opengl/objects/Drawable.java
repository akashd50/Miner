package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;
import javax.vecmath.Vector3f;

public abstract class Drawable {
    private Vector3f translation, rotation, scale;
    private Material material;
    private float[] modelMatrix;
    private Shader shader;
    private boolean transformationsUpdated;
    private int vertexArray;

    public Drawable() {
        this.translation = new Vector3f(0f,0f,0f);
        this.rotation = new Vector3f(0f,0f,0f);
        this.scale = new Vector3f(1.0f,1.0f, 1.0f);
        this.transformationsUpdated = false;
        this.modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
    }

    public void onDrawFrame() {
        if(transformationsUpdated) {
            applyTransformations();
            transformationsUpdated = false;
        }
    }

    private void applyTransformations() {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix,0,translation.x,translation.y,translation.z);
        Matrix.rotateM(modelMatrix, 0, rotation.x, 1, 0, 0);
        Matrix.rotateM(modelMatrix, 0, rotation.y, 0, 1, 0);
        Matrix.rotateM(modelMatrix, 0, rotation.z, 0, 0, 1);
        Matrix.scaleM(modelMatrix,0,scale.x,scale.y,scale.z);
    }

    public void scaleTo(Vector3f newScale) {
        this.scale = newScale;
        this.transformationsUpdated = true;
    }

    public void scaleBy(Vector3f newScale) {
        this.scale.add(newScale);
        this.transformationsUpdated = true;
    }

    public void translateTo(Vector3f position) {
        this.translation = position;
        this.transformationsUpdated = true;
    }

    public void translateBy(Vector3f translation) {
        this.translation.add(translation);
        this.transformationsUpdated = true;
    }

    public void rotateTo(Vector3f rotation) {
        this.rotation = rotation;
        this.transformationsUpdated = true;
    }

    public void rotateBy(Vector3f rotation) {
        this.rotation.add(rotation);
        this.transformationsUpdated = true;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getVertexArrayObject() {
        return this.vertexArray;
    }

    public void setVertexArrayObject(int vertexArrayObject ) { this.vertexArray = vertexArrayObject; }

    public float[] getModelMatrix() { return this.modelMatrix; }
}
