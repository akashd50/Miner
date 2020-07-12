package com.greymatter.miner.helpers;

import android.graphics.Bitmap;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

public class BufferHelper {
    public static FloatBuffer asFloatBuffer(float[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(data);
        vertexBuffer.position(0);
        return vertexBuffer;
    }

    public static Buffer asByteBuffer(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        IntBuffer buffer1 = IntBuffer.allocate(bytes);

        bitmap.copyPixelsToBuffer(buffer1);
        return buffer1;
    }

    public static float[] vec3AsFloatArray(ArrayList<Vector3f> arrayList) {
        float[] toReturn = new float[arrayList.size() * 3];
        int index = 0;
        for(Vector3f vector3f : arrayList) {
            toReturn[index++] = vector3f.x;
            toReturn[index++] = vector3f.y;
            toReturn[index++] = vector3f.z;
        }
        return toReturn;
    }

    public static float[] vec4AsFloatArray(ArrayList<Vector4f> arrayList) {
        float[] toReturn = new float[arrayList.size() * 4];
        int index = 0;
        for(Vector4f vector4f : arrayList) {
            toReturn[index++] = vector4f.x;
            toReturn[index++] = vector4f.y;
            toReturn[index++] = vector4f.z;
            toReturn[index++] = vector4f.w;
        }
        return toReturn;
    }

    public static float[] vec2AsFloatArray(ArrayList<Vector2f> arrayList) {
        float[] toReturn = new float[arrayList.size() * 2];
        int index = 0;
        for(Vector2f vector2f : arrayList) {
            toReturn[index++] = vector2f.x;
            toReturn[index++] = vector2f.y;
        }
        return toReturn;
    }
}
