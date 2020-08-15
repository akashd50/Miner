package com.greymatter.miner.opengl.objects.drawables.gradients;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.loaders.enums.ObjId;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.shader.ShaderHelper;
import com.greymatter.miner.opengl.shader.Shader;
import com.greymatter.miner.opengl.objects.materials.Material;

import javax.vecmath.Vector4f;

public class RadialGradient extends Gradient {
    private float radius, midPoint;
    public RadialGradient(ObjId id) {
        super(id);
    }

    public RadialGradient build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(getShape().getVerticesArray(), 3, getRenderer().getShader(), ShaderConst.IN_POSITION);
        GLBufferHelper.glUnbindVertexArray();
        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
        return this;
    }

    public RadialGradient setCenterColor(Vector4f centerColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_CENTER_COLOR, centerColor);
        return this;
    }

    public RadialGradient setEdgeColor(Vector4f edgeColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_EDGE_COLOR, edgeColor);
        return this;
    }

    public RadialGradient setMidColor(Vector4f midColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_MID_COLOR, midColor);
        return this;
    }

    @Override
    public RadialGradient setShape(Shape shape) {
        super.setShape(shape);
        this.radius = shape.getRadius();
        return this;
    }

    public RadialGradient setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public RadialGradient setMidPoint(float midPoint) {
        this.midPoint = midPoint;
        return this;
    }

    public float getRadius() {
        return radius;
    }

    public float getMidPoint() {
        return midPoint;
    }

    @Override
    public RadialGradient setMaterial(Material material) {
        super.setMaterial(material);
        return this;
    }
}
