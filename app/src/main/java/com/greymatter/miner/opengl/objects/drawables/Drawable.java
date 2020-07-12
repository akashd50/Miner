package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.mainui.touch.Clickable;
import com.greymatter.miner.mainui.touch.touchcheckers.TouchChecker;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.gradients.Gradient;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import com.greymatter.miner.physics.objects.rb.GeneralRB;
import javax.vecmath.Vector2f;

public abstract class Drawable implements Clickable {
    private Material material;
    private Shader shader;
    private int vertexArray, vertexBuffer;
    private RigidBody rigidBody;
    private String id;
    private TouchChecker touchChecker;
    private Transforms transforms;
    public Drawable(String id) {
        this.id = id;

        transforms = new Transforms();
        transforms.setLinkedDrawable(this);
        this.setRigidBody(new GeneralRB());
    }

    public void onDrawFrame() {
        transforms.applyTransformations();
    }

    @Override
    public boolean isClicked(Vector2f touchPoint) {
        return touchChecker != null && touchChecker.isClicked(touchPoint);
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

    public Drawable setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        if(this.rigidBody.getDrawable()==null) {
            this.rigidBody.setDrawable(this);
        }

        rigidBody.setTransforms(transforms);
        return this;
    }

    public void onTransformsChanged() {}

    public Material getMaterial() {
        return this.material;
    }

    public Shader getShader() {
        return shader;
    }

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

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public String toString() {
        return this.id;
    }

    public String getId() {
        return this.id;
    }

    //typecasting
    public Object3D asObject3D() {
        return (Object3D)this;
    }

    public Quad asQuad() {
        return (Quad) this;
    }

    public Line asLine() {
        return (Line) this;
    }

    public Gradient asGradient() {
        return (Gradient)this;
    }

    public RadialGradient asRadialGradient() {
        return (RadialGradient)this;
    }

    //abstract functions
    public abstract Drawable attachPolygonTouchChecker();
    public abstract Drawable attachPolygonCollider();
}
