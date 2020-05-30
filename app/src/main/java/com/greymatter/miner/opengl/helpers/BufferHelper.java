package com.greymatter.miner.opengl.helpers;

import android.graphics.Bitmap;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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
}
