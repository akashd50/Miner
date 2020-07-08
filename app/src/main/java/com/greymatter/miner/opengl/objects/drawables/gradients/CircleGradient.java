package com.greymatter.miner.opengl.objects.drawables.gradients;

import android.opengl.GLES30;
import com.greymatter.miner.Res;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.materials.Material;

import javax.vecmath.Vector4f;

public class CircleGradient extends Gradient {
    float[] vertices;
    private float radius, midPoint;
    public CircleGradient(String id) {
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
        ShaderHelper.setUniformVec3(super.getShader(), ShaderConst.TRANSLATION, super.getCollider().getTranslation());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 362);

        GLBufferHelper.glUnbindVertexArray();
    }

    public CircleGradient load(float radius) {
        this.radius = radius;

        vertices = new float[362*3];
        int arrayIndex = 0;

        vertices[arrayIndex++] = super.getCollider().getTranslation().x;
        vertices[arrayIndex++] = super.getCollider().getTranslation().y;
        vertices[arrayIndex++] = super.getCollider().getTranslation().z;

        for(int angle=0;angle<360;angle++) {
            vertices[arrayIndex++] = radius * (float)Math.cos(Math.toRadians(angle));
            vertices[arrayIndex++] = radius * (float)Math.sin(Math.toRadians(angle));
            vertices[arrayIndex++] = 0f;
        }

        vertices[arrayIndex++] = radius * (float)Math.cos(Math.toRadians(0));
        vertices[arrayIndex++] = radius * (float)Math.sin(Math.toRadians(0));
        vertices[arrayIndex++] = 0f;
        return this;
    }

    public CircleGradient build() {
        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(vertices, 3, super.getShader(), ShaderConst.IN_POSITION);
        GLBufferHelper.glUnbindVertexArray();
        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
        return this;
    }

    public CircleGradient setCenterColor(Vector4f centerColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_CENTER_COLOR, centerColor);
        return this;
    }

    public CircleGradient setEdgeColor(Vector4f edgeColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_EDGE_COLOR, edgeColor);
        return this;
    }

    public CircleGradient setMidColor(Vector4f midColor) {
        getMaterial().asColoredMaterial().addColor(ShaderConst.GRADIENT_MID_COLOR, midColor);
        return this;
    }

    public CircleGradient setRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public CircleGradient setMidPoint(float midPoint) {
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
    public CircleGradient setShader(Shader shader) {
        super.setShader(shader);
        return this;
    }

    @Override
    public CircleGradient setMaterial(Material material) {
        super.setMaterial(material);
        return this;
    }

    @Override
    public CircleGradient attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public CircleGradient attachPolygonCollider() {
        return this;
    }
}
