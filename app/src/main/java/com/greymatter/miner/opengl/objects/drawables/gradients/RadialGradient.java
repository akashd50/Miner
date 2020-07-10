package com.greymatter.miner.opengl.objects.drawables.gradients;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.materials.Material;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class RadialGradient extends Gradient {
    private float radius, midPoint;
    private float tRadius, tMidPoint;
    private Shape shape;
    public RadialGradient(String id) {
        super(id);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(super.getVertexArrayObject());

        ShaderHelper.setUniformMatrix4fv(super.getShader(), ShaderConst.MODEL, super.getModelMatrix());
        ShaderHelper.setMaterialProperties(getShader(), getMaterial());
        ShaderHelper.setUniformFloat(super.getShader(), ShaderConst.GRADIENT_MID_POINT, midPoint);
        ShaderHelper.setUniformFloat(super.getShader(), ShaderConst.GRADIENT_RADIUS, radius);
        ShaderHelper.setUniformVec3(super.getShader(), ShaderConst.TRANSLATION, super.getTransforms().getTranslation());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, shape.getVerticesList().size());

        GLBufferHelper.glUnbindVertexArray();
    }

    public RadialGradient build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(shape.getVerticesArray(), 3, super.getShader(), ShaderConst.IN_POSITION);
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

    public RadialGradient setRadius(float radius) {
        this.tRadius = radius;
        return this;
    }

    public RadialGradient setMidPoint(float midPoint) {
        this.tMidPoint = midPoint;
        return this;
    }

    public RadialGradient setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public RadialGradient updateMidPoint(float diff) {
        this.midPoint+=diff;
        if(midPoint>radius) {
            midPoint = 0f;
        }
        if(midPoint < 0) {
            midPoint = radius;
        }
        return this;
    }

    public float getRadius() {
        return radius;
    }

    public float getMidPoint() {
        return midPoint;
    }

    @Override
    public RadialGradient setShader(Shader shader) {
        super.setShader(shader);
        return this;
    }

    @Override
    public RadialGradient setMaterial(Material material) {
        super.setMaterial(material);
        return this;
    }

    @Override
    public void onTransformsChanged() {
        radius = super.getTransforms().getScale().x * tRadius;
        midPoint = super.getTransforms().getScale().x * tMidPoint;
    }

    @Override
    public RadialGradient attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public RadialGradient attachPolygonCollider() {
        return this;
    }
}
