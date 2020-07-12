package com.greymatter.miner.opengl.objects.drawables.textureedged;

import android.opengl.GLES30;

import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.helpers.GLBufferHelper;
import com.greymatter.miner.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.materials.textured.TexturedMaterial;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.Drawable;

public class TextureEdgedCircle extends Drawable {
    private float radius, edgeWidth;
    private int numPoints;
    public TextureEdgedCircle(String id , Shader shader, TexturedMaterial material) {
        super(id);
        super.setShader(shader);
        super.setMaterial(material);
    }

    public TextureEdgedCircle buildWith(float radius, int numPoints, float edgeWidth) {
        this.radius = radius;
        this.numPoints = numPoints;
        this.edgeWidth = edgeWidth;
        setUpVertexData(numPoints);
        return this;
    }

    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getTransforms().getModelMatrix());
        ShaderHelper.setMaterialProperties(getShader(), getMaterial());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, numPoints * 2 /*triangles*/ * 3 /*vertices per triangle*/ );

        GLBufferHelper.glUnbindVertexArray();
    }

    private void setUpVertexData(int numPoints) {
        float[] vertices = new float[numPoints*18];
        float[] uvs = new float[numPoints*12];
        int verticesIndex = 0;
        int uvsIndex = 0;

        float angleRatio = 360f/(float)numPoints;

        for(float angle = 0; angle < 360f; angle+=angleRatio) {
            vertices[verticesIndex++] = radius * (float)Math.cos(Math.toRadians(angle));
            vertices[verticesIndex++] = radius * (float)Math.sin(Math.toRadians(angle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 1.0f;
            uvs[uvsIndex++] = 1.0f;

            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.cos(Math.toRadians(angle));
            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.sin(Math.toRadians(angle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 1.0f;
            uvs[uvsIndex++] = 0.0f;

            float nextAngle = angle + angleRatio;

            vertices[verticesIndex++] = (radius) * (float)Math.cos(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = (radius) * (float)Math.sin(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 0.0f;
            uvs[uvsIndex++] = 1.0f;

            //second'

            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.cos(Math.toRadians(angle));
            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.sin(Math.toRadians(angle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 1.0f;
            uvs[uvsIndex++] = 0.0f;

            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.cos(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = (radius + edgeWidth) * (float)Math.sin(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 0.0f;
            uvs[uvsIndex++] = 0.0f;

            vertices[verticesIndex++] = (radius) * (float)Math.cos(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = (radius) * (float)Math.sin(Math.toRadians(nextAngle));
            vertices[verticesIndex++] = 0f;

            uvs[uvsIndex++] = 0.0f;
            uvs[uvsIndex++] = 1.0f;
        }

        int vertexArrayObj = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArrayObj);
        int vertexBufferObj = GLBufferHelper.putDataIntoArrayBuffer(vertices, 3, super.getShader(), ShaderConst.IN_POSITION);
        int uvBufferObj = GLBufferHelper.putDataIntoArrayBuffer(uvs,2,super.getShader(), ShaderConst.IN_UV);
        GLBufferHelper.glUnbindVertexArray();

        super.setVertexArrayObject(vertexArrayObj);
        super.setVertexBufferObject(vertexBufferObj);
    }

    @Override
    public TextureEdgedCircle attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public TextureEdgedCircle attachPolygonCollider() {
        return this;
    }
}
