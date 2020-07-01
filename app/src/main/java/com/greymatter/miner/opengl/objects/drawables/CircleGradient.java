package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;
import javax.vecmath.Vector4f;

public class CircleGradient extends Gradient {
    private Vector4f centerColor, edgeColor;
    private float radius, offsetFromCenter, offsetFromEdge;
    public CircleGradient(String id, float radius, Shader shader) {
        super(id);
        super.setShader(shader);
        this.radius = radius;
        this.offsetFromCenter = 0f;
        this.offsetFromEdge = 0f;
        this.centerColor = new Vector4f(1f,1f,1f,1f);
        this.edgeColor = new Vector4f(1f,1f,1f,1f);
        this.setUpVertexData();
    }

    private void setUpVertexData() {
        float[] vertices = new float[362*3];
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

        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(vertices, 3, super.getShader(), Constants.IN_POSITION);
        GLBufferHelper.glUnbindVertexArray();
        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();
        GLBufferHelper.glBindVertexArray(super.getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(super.getShader(), Constants.MODEL, super.getModelMatrix());
        ShaderHelper.setUniformVec4(super.getShader(), Constants.CENTER_COLOR, centerColor);
        ShaderHelper.setUniformVec4(super.getShader(), Constants.EDGE_COLOR, edgeColor);
        ShaderHelper.setUniformVec3(super.getShader(), Constants.TRANSLATION, super.getCollider().getTranslation());
        ShaderHelper.setUniformFloat(super.getShader(), Constants.RADIUS, radius);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 362);

        GLBufferHelper.glUnbindVertexArray();
    }

    public Vector4f getCenterColor() {
        return centerColor;
    }

    public CircleGradient withCenterColor(Vector4f centerColor) {
        this.centerColor = centerColor;
        return this;
    }

    public Vector4f getEdgeColor() {
        return edgeColor;
    }

    public CircleGradient withEdgeColor(Vector4f edgeColor) {
        this.edgeColor = edgeColor;
        return this;
    }

    public float getRadius() {
        return radius;
    }

    public CircleGradient withRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public float getOffsetFromCenter() {
        return offsetFromCenter;
    }

    public CircleGradient withOffsetFromCenter(float offsetFromCenter) {
        this.offsetFromCenter = offsetFromCenter;
        return this;
    }

    public float getOffsetFromEdge() {
        return offsetFromEdge;
    }

    public CircleGradient withOffsetFromEdge(float offsetFromEdge) {
        this.offsetFromEdge = offsetFromEdge;
        return this;
    }
}
