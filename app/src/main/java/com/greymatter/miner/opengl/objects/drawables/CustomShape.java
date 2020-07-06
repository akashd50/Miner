package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.BufferHelper;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;

import java.util.ArrayList;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class CustomShape extends Drawable {
    private ArrayList<Vector3f> shapeVertices;
    private ArrayList<Vector4f> vertexColors;
    public CustomShape(String id) {
        super(id);
        shapeVertices = new ArrayList<>();
        vertexColors = new ArrayList<>();
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());
        ShaderHelper.setUniformMatrix4fv(getShader(), Constants.MODEL, getModelMatrix());
        if(isSingleColor()) {
            ShaderHelper.setUniformVec4(getShader(), Constants.U_COLOR, vertexColors.get(0));
        }else{

        }

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, shapeVertices.size());

        GLBufferHelper.glUnbindVertexArray();
    }

    public CustomShape addVertex(Vector3f vert) {
        shapeVertices.add(vert);
        return this;
    }

    public CustomShape addVertex(Vector3f vert, Vector4f vertColor) {
        shapeVertices.add(vert);
        vertexColors.add(vertColor);
        return this;
    }

    public CustomShape setColor(Vector4f vertsColor) {
        vertexColors.add(vertsColor);
        return this;
    }

    public CustomShape build() {
        super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(BufferHelper.vec3AsFloatArray(shapeVertices),
                                                        3, getShader(), Constants.IN_POSITION);
        super.setVertexBufferObject(vertexBuffer);

        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

    public boolean isSingleColor() {
        return vertexColors.size() == 1 && shapeVertices.size()>1;
    }

    @Override
    public CustomShape withPolygonTouchChecker() {
        return null;
    }

    @Override
    public CustomShape withPolygonCollider() {
        return null;
    }
}
