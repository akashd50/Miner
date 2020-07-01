package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import com.greymatter.miner.opengl.objects.Shader;

import java.util.ArrayList;

import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class Line extends Drawable {
    private ArrayList<Vector3f> lineVertices;
    private Vector4f lineColor;
    public Line(String id, Shader shader) {
        super(id);
        super.setShader(shader);
        lineVertices = new ArrayList<>();
        lineColor = new Vector4f(1f,1f,1f,1f);
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

    public Line withColor(Vector4f color) {
        this.lineColor = color;
        return this;
    }

    public Line addVertices(ArrayList<Vector3f> toAdd) {
        lineVertices.addAll(toAdd);
        return this;
    }

    public float[] asArray() {
        float[] toReturn = new float[lineVertices.size() * 3];
        int index = 0;
        for(Vector3f vector : lineVertices) {
            toReturn[index++] = vector.x;
            toReturn[index++] = vector.y;
            toReturn[index++] = vector.z;
        }
        return toReturn;
    }

    public Line updateVertexData(ArrayList<Vector3f> newData) {
        this.lineVertices.clear();
        this.lineVertices.addAll(newData);

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        GLBufferHelper.updateArrayBufferData(getVertexBufferObject(), asArray(),
                3,  getShader(), Constants.IN_POSITION);
        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

    public Line build() {
        setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(asArray(), 3,
                getShader(), Constants.IN_POSITION);
        this.setVertexBufferObject(vertexBuffer);

        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

}
