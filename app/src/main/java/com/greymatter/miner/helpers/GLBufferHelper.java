package com.greymatter.miner.helpers;

import android.opengl.GLES30;
import android.util.Log;
import com.greymatter.miner.Path;
import com.greymatter.miner.opengl.shader.Shader;

public class GLBufferHelper {
    public static int glGenVertexArray() {
        int[] arrayObject = new int[1];
        GLES30.glGenVertexArrays(1, arrayObject, 0);
        return arrayObject[0];
    }

    public static void glBindVertexArray(int vertexArrayObject) {
        GLES30.glBindVertexArray(vertexArrayObject);
    }

    public static void glUnbindVertexArray() {
        glBindVertexArray(0);
    }

    public static int glGenBuffer() {
        int[] buffer = new int[1];
        GLES30.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }

    public static void glBindArrayBuffer(int bufferObject) {
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, bufferObject);
    }

    public static void glBufferArrayData(int bufferObject, float[] data) {
        glBindArrayBuffer(bufferObject);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, Path.SIZE_OF_FLOAT * data.length,
                BufferHelper.asFloatBuffer(data), GLES30.GL_DYNAMIC_DRAW);
        glBindArrayBuffer(0);
    }

    public static void glVertexAttributePointer(int bufferObject, Shader shader, String attribName, int coordsPerVertex) {
        glBindArrayBuffer(bufferObject);

        int attribLocation = GLES30.glGetAttribLocation(shader.getProgram(), attribName);
        if(attribLocation == -1) {
            Log.v("Shader Error: ", "Unable to find attribute \"" + attribName + "\" in " + shader.toString());
        }
        GLES30.glEnableVertexAttribArray(attribLocation);
        GLES30.glVertexAttribPointer(attribLocation, coordsPerVertex, GLES30.GL_FLOAT, false,
                coordsPerVertex * Path.SIZE_OF_FLOAT, 0);
        glBindArrayBuffer(0);
    }

    public static int putDataIntoArrayBuffer(float[] data, int coordsPerVertex,
                                             Shader shader, String attributeName) {
        int bufferObject = GLBufferHelper.glGenBuffer();
        glBufferArrayData(bufferObject, data);
        glVertexAttributePointer(bufferObject, shader, attributeName, coordsPerVertex);
        return bufferObject;
    }

    public static int setUpEmptyArrayBuffer(int sizeInBytes, int coordsPerVertex, Shader shader, String attributeName) {
        int bufferObject = GLBufferHelper.glGenBuffer();
        glBindArrayBuffer(bufferObject);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, sizeInBytes, null, GLES30.GL_DYNAMIC_DRAW);
        glBindArrayBuffer(0);
        glVertexAttributePointer(bufferObject, shader, attributeName, coordsPerVertex);

        return bufferObject;
    }

    public static void updateArrayBufferData(int arrayBuffer, float[] data, int coordsPerVertex,
                                             Shader shader, String attributeName) {
        glBufferArrayData(arrayBuffer, data);
        glVertexAttributePointer(arrayBuffer, shader, attributeName, coordsPerVertex);
    }

    public static void updateSubBufferData(int arrayBuffer, int startIndex, float[] data) {
        glBindArrayBuffer(arrayBuffer);
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, startIndex * (4 /*size of float*/), data.length * (4 /*size of float*/), BufferHelper.asFloatBuffer(data));
        glBindArrayBuffer(0);
    }

    public static void glVertexAttributeDivisor(Shader shader, String attributeName, int numDiv) {
        int attribLocation = GLES30.glGetAttribLocation(shader.getProgram(), attributeName);
        GLES30.glVertexAttribDivisor(attribLocation, numDiv);
    }

}
