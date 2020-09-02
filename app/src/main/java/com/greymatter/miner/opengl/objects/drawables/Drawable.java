package com.greymatter.miner.opengl.objects.drawables;

import com.greymatter.miner.opengl.objects.Transforms;
import com.greymatter.miner.opengl.objects.drawables.gradients.Gradient;
import com.greymatter.miner.opengl.objects.drawables.gradients.RadialGradient;
import com.greymatter.miner.opengl.objects.materials.Material;
import com.greymatter.miner.opengl.objects.renderers.Renderer;
import com.greymatter.miner.opengl.objects.drawables.object3d.Obj;

import java.util.ArrayList;

import javax.vecmath.Vector3f;

public abstract class Drawable {
    private String id;
    private Material material;
    private int vertexArray, vertexBuffer;
    private Transforms transforms;
    private Shape shape;
    private Renderer renderer;
    public Drawable(String id) {
        this.id = id;
    }

    public Drawable build() {
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

    public Drawable setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public Drawable setTransforms(Transforms transforms) {
        this.transforms = transforms;
        this.transforms.setLinkedDrawable(this);
        return this;
    }

    public Drawable setRenderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void onTransformsChanged() {}

    public Material getMaterial() {
        return this.material;
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

    public Shape getShape() {
        return shape;
    }

    public ArrayList<Vector3f> getOrderedOuterMesh() {
        return shape.getOrderedOuterMesh();
    }

    public ArrayList<Vector3f> getOptimizedOOMesh(float opt) {
        return shape.getOptimizedOuterMesh(opt);
    }

    public String toString() {
        return this.id.toString();
    }

    public String getId() {
        return this.id;
    }

    //typecasting
    public Obj asObj() {
        return (Obj)this;
    }
    public InstanceGroup asInstanceGroup() {
        return (InstanceGroup)this;
    }
    public Instance asInstance() {
        return (Instance) this;
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
}
