package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.generalhelpers.VectorHelper;
import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.BufferHelper;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Line extends Drawable {
    private ArrayList<Vector3f> lineVertices;
    private Vector4f lineColor;
    private boolean dataUpdated;
    public Line(String id) {
        super(id);
        lineVertices = new ArrayList<>();
        lineColor = new Vector4f(1f,1f,1f,1f);
        dataUpdated = false;
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), Constants.MODEL, getModelMatrix());
        ShaderHelper.setUniformVec4(getShader(), Constants.U_COLOR, lineColor);

        GLES30.glLineWidth(10f);
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 0, lineVertices.size());

        GLBufferHelper.glUnbindVertexArray();
    }

    public Line addVertex(Vector3f toAdd) {
        lineVertices.add(toAdd);
        return this;
    }

    public Line setColor(Vector4f color) {
        this.lineColor = color;
        return this;
    }

    public Line addVertices(ArrayList<Vector3f> toAdd) {
        lineVertices.addAll(toAdd);
        return this;
    }

    public Line setVertices(ArrayList<Vector3f> newData) {
        this.lineVertices.clear();
        this.lineVertices.addAll(newData);
        this.dataUpdated = true;
        return this;
    }

    public Line build() {
        if(!dataUpdated) {
            super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        }

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        if(!dataUpdated) {
            int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(BufferHelper.vec3AsFloatArray(lineVertices),
                    3, getShader(), Constants.IN_POSITION);
            super.setVertexBufferObject(vertexBuffer);
        }else{
            GLBufferHelper.updateArrayBufferData(getVertexBufferObject(), BufferHelper.vec3AsFloatArray(lineVertices),
                                                                3,  getShader(), Constants.IN_POSITION);
        }
        GLBufferHelper.glUnbindVertexArray();
        this.dataUpdated = false;
        return this;
    }

    @Override
    public Line setShader(Shader shader) {
        super.setShader(shader);
        return this;
    }

    @Override
    public Line attachPolygonTouchChecker() {
        return this;
    }

    @Override
    public Line attachPolygonCollider() {
        return this;
    }

}
