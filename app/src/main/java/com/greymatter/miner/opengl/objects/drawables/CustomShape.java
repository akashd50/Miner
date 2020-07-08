package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;
import com.greymatter.miner.Res;
import com.greymatter.miner.ShaderConst;
import com.greymatter.miner.opengl.helpers.BufferHelper;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.helpers.ShaderHelper;
import java.util.ArrayList;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class CustomShape extends Drawable {
    private ArrayList<Vector3f> shapeVertices;
    public CustomShape(String id) {
        super(id);
        shapeVertices = new ArrayList<>();
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        ShaderHelper.setUniformMatrix4fv(getShader(), ShaderConst.MODEL, getModelMatrix());
        ShaderHelper.setMaterialProperties(getShader(), getMaterial());
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, shapeVertices.size());

        GLBufferHelper.glUnbindVertexArray();
    }

    public CustomShape addVertex(Vector3f vert) {
        shapeVertices.add(vert);
        return this;
    }

    public CustomShape addVertex(Vector3f vert, Vector4f vertColor) {
        shapeVertices.add(vert);
        getMaterial().asColoredMaterial().addColor(vertColor);
        return this;
    }

    public CustomShape setColor(Vector4f vertsColor) {
        getMaterial().asColoredMaterial().addColor(vertsColor);
        return this;
    }

    public CustomShape build() {
        super.setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(BufferHelper.vec3AsFloatArray(shapeVertices),
                                                        3, getShader(), ShaderConst.IN_POSITION);
        super.setVertexBufferObject(vertexBuffer);

        GLBufferHelper.glUnbindVertexArray();
        return this;
    }

    @Override
    public CustomShape attachPolygonTouchChecker() {
        return null;
    }

    @Override
    public CustomShape attachPolygonCollider() {
        return null;
    }
}
