package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.Matrix;

import com.greymatter.miner.mainui.touch.Clickable;
import com.greymatter.miner.opengl.objects.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.CollisionEvent;
import com.greymatter.miner.physics.objects.GeneralCollider;

import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

public abstract class Drawable implements Clickable {
    private Material material;
    private float[] modelMatrix;
    private Shader shader;
    private boolean transformationsUpdated;
    private int vertexArray, vertexBuffer;
    private Collider collider;
    private String id;
    private ArrayList<String> tags;
    public Drawable(String id) {
        this.id = id;
        this.tags = new ArrayList<>();
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

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return false;
    }

    public Drawable withTag(String tag) {
        this.tags.add(tag);
        return this;
    }

    public boolean hasTag(String tag) {
        return this.tags.contains(tag);
    }

    public int getNumTags() {return this.tags.size();}

    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
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

    public void setVertexArrayObject(int vertexArrayObject ) {
        this.vertexArray = vertexArrayObject;
    }

    public void setVertexBufferObject(int vertexBufferObject ) {
        this.vertexBuffer = vertexBufferObject;
    }

    public float[] getModelMatrix() { return this.modelMatrix; }

    public Collider getCollider() {
        return collider;
    }

    public void setCollider(Collider collider) {
        this.collider = collider;
        if(this.collider.getDrawable()==null) this.collider.setDrawable(this);
    }
}
