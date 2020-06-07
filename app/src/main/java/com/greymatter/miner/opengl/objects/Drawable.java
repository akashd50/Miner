package com.greymatter.miner.opengl.objects;

import android.opengl.Matrix;

import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.GeneralCollider;

import javax.vecmath.Vector3f;

public abstract class Drawable {
    private Material material;
    private float[] modelMatrix;
    private Shader shader;
    private boolean transformationsUpdated;
    private int vertexArray;
    private Collider collider;

    public Drawable() {
        this.transformationsUpdated = false;
        this.modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
        this.setCollider(new GeneralCollider());
    }

    public void onDrawFrame() {
        if(transformationsUpdated) {
            applyTransformations();
            transformationsUpdated = false;
        }
    }

    private void applyTransformations() {
        if(collider!=null) {
            Matrix.setIdentityM(modelMatrix, 0);
            Matrix.translateM(modelMatrix, 0, collider.getTranslation().x,
                                                        collider.getTranslation().y,
                                                        collider.getTranslation().z);
            Matrix.rotateM(modelMatrix, 0, collider.getRotation().x, 1, 0, 0);
            Matrix.rotateM(modelMatrix, 0, collider.getRotation().y, 0, 1, 0);
            Matrix.rotateM(modelMatrix, 0, collider.getRotation().z, 0, 0, 1);
            Matrix.scaleM(modelMatrix, 0, collider.getScale().x,
                                                    collider.getScale().y,
                                                    collider.getScale().z);
        }
    }

    public void transformationsUpdated() {
        transformationsUpdated = true;
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

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
        if(this.collider.getDrawable()==null) this.collider.setDrawable(this);
    }

    //collider helper functions
    public void setAcceleration(Vector3f acceleration) {
        collider.setAcceleration(acceleration);
    }

    public void setVelocity(Vector3f velocity) {
        collider.setVelocity(velocity);
    }

    public void updateAcceleration(Vector3f acceleration) {
        collider.updateAcceleration(acceleration);
    }

    public void updateVelocity(Vector3f velocity) {
        collider.updateVelocity(velocity);
    }

    public void setMass(float mass) {
        collider.setMass(mass);
    }

    public void setRestitution(float restitution) {
        collider.setRestitution(restitution);
    }

    public void scaleTo(Vector3f newScale) {
        collider.scaleTo(newScale);
    }

    public void scaleBy(Vector3f newScale) {
        collider.scaleBy(newScale);
    }

    public void translateTo(Vector3f position) {
        collider.translateTo(position);
    }

    public void translateBy(Vector3f translation) {
        collider.translateBy(translation);
    }

    public void rotateTo(Vector3f rotation) {
        collider.rotateTo(rotation);
    }

    public void rotateBy(Vector3f rotation) {
        collider.rotateBy(rotation);
    }
}
