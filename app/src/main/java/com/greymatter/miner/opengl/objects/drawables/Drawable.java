package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.enums.ObjId;
import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.animators.ValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.gradients.Gradient;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;
import com.greymatter.miner.physics.objects.rb.RigidBody;
import com.greymatter.miner.physics.objects.rb.GeneralRB;

public abstract class Drawable {
    private ObjId id;
    private Material material;
    private Shader shader;
    private int vertexArray, vertexBuffer;
    private RigidBody rigidBody;
    private Transforms transforms;
    private Shape shape;
    public Drawable(ObjId id) {
        this.id = id;
        this.transforms = new Transforms().setLinkedDrawable(this);
        this.setRigidBody(new GeneralRB());
    }

    public void animate(ValueAnimator animator) {}

    public void onDrawFrame() {}

    public Drawable build() {
        return this;
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

    public Drawable setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
        if(this.rigidBody.getDrawable()==null) {
            this.rigidBody.setDrawable(this);
        }

        rigidBody.setTransforms(transforms);
        return this;
    }

    public Drawable setShape(Shape shape) {
        this.shape = shape;
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

    public Transforms getTransforms() {
        return this.transforms;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public Shape getShape() {
        return shape;
    }

    public String toString() {
        return this.id.toString();
    }

    public ObjId getId() {
        return this.id;
    }

    //typecasting
    public Obj asObject3D() {
        return (Obj)this;
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
    public abstract Drawable attachPolygonCollider();
}
