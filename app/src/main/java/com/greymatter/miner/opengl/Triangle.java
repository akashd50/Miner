package com.greymatter.miner.opengl;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;
import com.greymatter.miner.opengl.objects.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    public int vertexArray;
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] =
            {0f,0.5f,0f,
            -0.5f,0f,0f,
            0.5f,0f,0f};

    public Triangle(Shader shader) {
        vertexArray = GLBufferHelper.glGenVertexArray();
        GLBufferHelper.glBindVertexArray(vertexArray);

        int vertexBuffer = GLBufferHelper.putDataIntoArrayBuffer(triangleCoords, 3,
                shader, Constants.IN_POSITION);

        GLBufferHelper.glUnbindVertexArray();
    }



}
