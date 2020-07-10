package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.Matrix;
import com.greymatter.miner.mainui.touch.Clickable;
import com.greymatter.miner.mainui.touch.touchcheckers.TouchChecker;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.physics.objects.Collider;
import com.greymatter.miner.physics.objects.GeneralCollider;
import javax.vecmath.Vector2f;

public abstract class Drawable implements Clickable {
    private Material material;
    private float[] modelMatrix;
    private Shader shader;
    private boolean transformationsUpdated;
    private int vertexArray, vertexBuffer;
    private Collider collider;
    private String id;
    private TouchChecker touchChecker;
    private Transforms transforms;

    public Drawable(String id) {
        this.id = id;
        this.transformationsUpdated = false;
        this.modelMatrix = new float[16];
        Matrix.setIdentityM(modelMatrix, 0);
        transforms = new Transforms();
        transforms.setLinkedDrawable(this);
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
            Matrix.translateM(modelMat, 0, transforms.getTranslation().x,
                                                    transforms.getTranslation().y,
                                                    transforms.getTranslation().z);
            Matrix.rotateM(modelMat, 0, transforms.getRotation().x, 1, 0, 0);
            Matrix.rotateM(modelMat, 0, transforms.getRotation().y, 0, 1, 0);
            Matrix.rotateM(modelMat, 0, transforms.getRotation().z, 0, 0, 1);
            Matrix.scaleM(modelMat, 0, transforms.getScale().x,
                                                transforms.getScale().y,
                                                transforms.getScale().z);
            this.modelMatrix = modelMat;
        }
    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return touchChecker != null && touchChecker.isClicked(touchPoint);
    }

    public void transformationsUpdated() {
        transformationsUpdated = true;
    }

    public Drawable setShader(Shader shader) {
        this.shader = shader;
        return this;
    }

    public Drawable setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Drawable setVertexArrayObject(int vertexArrayObject ) {
        this.vertexArray = vertexArrayObject;
        return this;
    }

    public Drawable setVertexBufferObject(int vertexBufferObject ) {
        this.vertexBuffer = vertexBufferObject;
        return this;
    }

    public Drawable setTouchChecker(TouchChecker touchChecker) {
        this.touchChecker = touchChecker;
        return this;
    }

    public Drawable setCollider(Collider collider) {
        this.collider = collider;
        if(this.collider.getDrawable()==null) {
            this.collider.setDrawable(this);
        }

        collider.setTransforms(transforms);
        return this;
    }

    public void onTransformsChanged() {

    }

    public Material getMaterial() {
        return this.material;
    }

    public Shader getShader() {
        return shader;
    }

    public float[] getModelMatrix() { return this.modelMatrix; }

    public int getVertexArrayObject() {
        return this.vertexArray;
    }

    public int getVertexBufferObject() {
        return this.vertexBuffer;
    }

    public TouchChecker getTouchChecker() {
        return this.touchChecker;
    }

    public Transforms getTransforms() { return this.transforms; }

    public Collider getCollider() {
        return collider;
    }

    public Object3D asObject3D() {
        return (Object3D)this;
    }

    public Quad asQuad() {
        return (Quad) this;
    }

    public Line asLine() {
        return (Line) this;
    }

    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }

    public abstract Drawable attachPolygonTouchChecker();

    public abstract Drawable attachPolygonCollider();
}
