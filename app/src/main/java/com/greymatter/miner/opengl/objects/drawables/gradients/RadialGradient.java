package com.greymatter.miner.opengl.objects.drawables.gradients;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.ValueAnimator;
import com.greymatter.miner.opengl.objects.drawables.Shape;
import com.greymatter.miner.opengl.objects.materials.Material;

import javax.vecmath.Vector4f;

public class RadialGradient extends Gradient {
    private float radius, midPoint;
    private float tRadius, tMidPoint;
    private Shape shape;
    private ValueAnimator animator;
    public RadialGradient(String id) {
        super(id);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        //midPoint = animator.update().getUpdatedFloat();

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
        this.radius = radius;
        return this;
    }

    public RadialGradient setMidPoint(float midPoint) {
        this.tMidPoint = midPoint;
        this.midPoint = midPoint;
        return this;
    }

    public RadialGradient setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public RadialGradient setAnimator(ValueAnimator animator) {
        this.animator = animator;
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
    public RadialGradient attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public RadialGradient attachPolygonCollider() {
        return this;
    }
}
