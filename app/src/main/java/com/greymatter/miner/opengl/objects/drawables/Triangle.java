package com.greymatter.miner.opengl.objects.drawables;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.objects.Shader;
import com.greymatter.miner.opengl.objects.drawables.object3d.Object3D;
import com.greymatter.miner.physics.objects.PolygonCollider;

public class Triangle extends Drawable {
    static float triangleCoords[] =
            {0f,0.5f,0f,
            -0.5f,0f,0f,
            0.5f,0f,0f};

    public Triangle(String id, Shader shader) {
        super(id);
        super.setShader(shader);

        setVertexArrayObject(GLBufferHelper.glGenVertexArray());
        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(triangleCoords, 3,
                getShader(), Constants.IN_POSITION);

        GLBufferHelper.glUnbindVertexArray();
    }

    @Override
    public void onDrawFrame() {
        super.onDrawFrame();

        GLBufferHelper.glBindVertexArray(getVertexArrayObject());

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);

        GLBufferHelper.glUnbindVertexArray();
    }

    @Override
    public Triangle withPolygonTouchChecker() {
        return this;
    }

    @Override
    public Triangle withPolygonCollider() {
        return this;
    }
}
