package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.Matrix;

import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.GeneralCollider;

import javax.vecmath.Vector3f;

public abstract class Drawable {
    private Material material;
    private float[] modelMatrix;
    private Shader shader;
    private boolean transformationsUpdated;
    private int vertexArray, vertexBuffer;
    private Collider collider;

    public Drawable() {
        this.transformationsUpdated = false;
        this.modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
        this.setCollider(new GeneralCollider());
    }

    public void onDrawFrame() {
        if(transformationsUpdated) {
            Matrix.setIdentityM(this.modelMatrix, 0);
            applyTransformations(this.modelMatrix);
            transformationsUpdated = false;
        }
    }

    public void onDrawFrame(boolean applyTransformations) {
        if(applyTransformations) {
            Matrix.setIdentityM(this.modelMatrix, 0);
            applyTransformations(this.modelMatrix);
            transformationsUpdated = false;
        }
    }

    public void applyTransformations(float[] modelMat) {
        if(collider != null) {
            Matrix.translateM(modelMat, 0, collider.getTranslation().x,
                                                        collider.getTranslation().y,
                                                        collider.getTranslation().z);
            Matrix.rotateM(modelMat, 0, collider.getRotation().x, 1, 0, 0);
            Matrix.rotateM(modelMat, 0, collider.getRotation().y, 0, 1, 0);
            Matrix.rotateM(modelMat, 0, collider.getRotation().z, 0, 0, 1);
            Matrix.scaleM(modelMat, 0, collider.getScale().x,
                                                    collider.getScale().y,
                                                    collider.getScale().z);
            this.modelMatrix = modelMat.clone();
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

    public int getVertexBufferObject() {
        return this.vertexBuffer;
    }

    public void setVertexArrayObject(int vertexArrayObject ) { this.vertexArray = vertexArrayObject; }

    public void setVertexBufferObject(int vertexBufferObject ) { this.vertexBuffer = vertexBufferObject; }

    public float[] getModelMatrix() { return this.modelMatrix; }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
        if(this.collider.getDrawable()==null) this.collider.setDrawable(this);
    }

    //collider helper functions
    public void resetFriction() {
        collider.resetFriction();
    }

    public void applyFriction(Vector3f gUpdate) {
        collider.applyFriction(gUpdate);
    }

    public void addOrUpdateCollisionEvent(CollisionEvent collisionEvent) {
        collider.addOrUpdateCollisionEvent(collisionEvent);
    }

    public void resetGravity() {
        collider.resetGravity();
    }

    public void applyGravity(Vector3f gUpdate) {
        collider.applyGravity(gUpdate);
    }

    public void update() {
        collider.update();
    }

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

    public void isStaticObject(boolean isStatic) {
        collider.isStaticObject(isStatic);
    }

    public void updateTransformationsPerMovement(boolean dynamicallyUpdated) {
        collider.updateTransformationsPerMovement(dynamicallyUpdated);
    }

    public boolean isUpdatedPerMovement() {
        return collider.isUpdatedPerMovement();
    }

    public Vector3f getTranslation() {
        return collider.getTranslation();
    }

    public Vector3f getRotation() {
        return collider.getRotation();
    }

    public Vector3f getScale() {
        return collider.getScale();
    }

    public Vector3f getVelocity() {
        return collider.getVelocity();
    }

    public Vector3f getAcceleration() {
        return collider.getAcceleration();
    }

    public float getMass(){
        return collider.getMass();
    }

    public boolean isStaticObject() {
        return collider.isStaticObject();
    }

    public CollisionEvent getLastCollisionEvent(Drawable against) {
        return collider.getLastCollisionEvent(against);
    }
}
