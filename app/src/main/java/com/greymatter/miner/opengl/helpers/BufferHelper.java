package com.greymatter.miner.opengl.helpers;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferHelper {
    public static FloatBuffer asFloatBuffer(float[] data) {
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 4);
        bb.order(ByteOrder.nativeOrder());

        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(data);
        vertexBuffer.position(0);
        return vertexBuffer;
    }

    public static ByteBuffer asByteBuffer(Bitmap bitmap) {
        int bytes = bitmap.getByteCount();
        ByteBuffer buffer = ByteBuffer.allocate(bytes);
        bitmap.copyPixelsToBuffer(buffer);
        return buffer;
    }
}
