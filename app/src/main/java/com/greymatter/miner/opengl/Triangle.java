package com.greymatter.miner.opengl;

import android.opengl.GLES30;

import com.greymatter.miner.opengl.helpers.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    public FloatBuffer vertexBuffer;
    public int[] vertexArrayObject, vertexBufferObject;
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] =
            {0f,0.5f,0f,
            -0.5f,0f,0f,
            0.5f,0f,0f};

    public Triangle(int program) {
        vertexArrayObject = new int[1];
        vertexBufferObject = new int[1];

        ByteBuffer bb = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        GLES30.glGenVertexArrays(1, vertexArrayObject, 0);
        GLES30.glBindVertexArray(vertexArrayObject[0]);

        GLES30.glGenBuffers(1, vertexBufferObject, 0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vertexBufferObject[0]);

        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, 4 * 9, vertexBuffer, GLES30.GL_STATIC_DRAW);

        int positionHandle = GLES30.glGetAttribLocation(program, Constants.IN_POSITION);
        GLES30.glEnableVertexAttribArray(positionHandle);

        GLES30.glVertexAttribPointer(positionHandle, 3,
                GLES30.GL_FLOAT, false,
                12, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindVertexArray(0);
    }



}
