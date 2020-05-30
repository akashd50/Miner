package com.greymatter.miner.opengl.objects;

import com.greymatter.miner.opengl.helpers.Constants;
import com.greymatter.miner.opengl.helpers.GLBufferHelper;

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
